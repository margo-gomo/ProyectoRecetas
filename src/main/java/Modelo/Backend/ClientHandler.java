package Modelo.Backend;

import Modelo.DAO.MensajeDAO;
import Modelo.entidades.Mensaje;
import Modelo.entidades.Usuario;
import Modelo.DAO.UsuarioDAO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

/**
 * Protocolo por línea (JSON):
 *   {"op":"ping"}
 *   {"op":"login","id":"<id>","clave":"<clave>"}
 *   {"op":"enviarMensaje","remitente":"<id>","destinatario":"<id>","texto":"..."}
 *   {"op":"recibirMensajes","remitente":"<yo>","destinatario":"<otro>"}
 *   {"op":"listarConversaciones","userId":"<id>"}
 *   --- NUEVO ---
 *   {"op":"listarUsuariosOnline"}
 *   {"op":"heartbeat","id":"<id>"}
 *   {"op":"logout"}
 */
public class ClientHandler implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private final MensajeDAO mensajes;
    private final UsuarioDAO usuarioDAO;

    private final OnlineRegistry registry;
    private Usuario currentUser = null;

    public ClientHandler(Socket socket, OnlineRegistry registry) throws SQLException {
        this.socket = socket;
        this.registry = registry;
        this.mensajes = new MensajeDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    @Override
    public void run() {
        final String who = socket.getRemoteSocketAddress().toString();
        System.out.println("[Backend] Handler iniciado " + who);
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

            String line;
            while ((line = in.readLine()) != null) {
                if (line.isBlank()) continue;
                System.out.println("[Backend] RX: " + line);

                JsonNode req;
                try {
                    req = MAPPER.readTree(line);
                } catch (Exception ex) {
                    sendError("JSON inválido: " + ex.getMessage());
                    continue;
                }

                final String op = req.path("op").asText(null);
                if (op == null) { sendError("Falta campo 'op'"); continue; }

                if (currentUser != null && currentUser.getId() != null) {
                    try { registry.heartbeat(currentUser.getId()); } catch (Exception ignored) {}
                }

                try {
                    switch (op) {
                        case "ping" -> handlePing(req);
                        case "login" -> handleLogin(req);
                        case "enviarMensaje" -> handleEnviarMensaje(req);
                        case "recibirMensajes" -> handleRecibirMensajes(req);
                        case "listarConversaciones" -> handleListarConversaciones(req);

                        // --- NUEVOS HANDLERS ---
                        case "listarUsuariosOnline" -> handleListarUsuariosOnline();
                        case "heartbeat" -> handleHeartbeat(req);
                        case "logout" -> handleLogout();
                        case "subscribe" -> handleSubscribe(req);
                        default -> sendError("Operación no soportada: " + op);
                    }
                } catch (Exception ex) {
                    sendError("Error interno: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println("[Backend] Handler error: " + ex.getMessage());
        } finally {
            try {
                if (currentUser != null && currentUser.getId() != null) {
                    registry.logout(currentUser.getId());
                    ServidorBackend.CONNECTIONS.unregister(currentUser.getId());
                    // emitir logout para los demás
                    ServidorBackend.CONNECTIONS.broadcastUserLogout(currentUser.getId());
                }
            } catch (Exception ignored) {}
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("[Backend] Handler finalizado");
        }

    }

    /* -------------------- Handlers -------------------- */

    private void handlePing(JsonNode req) {
        ObjectNode resp = ok();
        resp.set("echo", req);
        send(resp);
    }

    private void handleLogin(JsonNode req) throws SQLException {
        String id = text(req, "id");
        String clave = text(req, "clave");
        if (id == null || clave == null) { sendError("Campos requeridos: id, clave"); return; }

        String nombre = mensajes.validarUsuario(id, clave);
        if (nombre == null) { sendFail("Credenciales inválidas"); return; }

        Usuario u = usuarioDAO.findById(id);
        if (u == null) {
            u = new Usuario(id, nombre, "MEDICO");
        }

        this.currentUser = u;
        try { registry.markOnline(u); } catch (Exception ignored) {}

        ObjectNode user = MAPPER.createObjectNode();
        user.put("id", u.getId());
        user.put("nombre", u.getNombre());
        user.put("tipo", u.getTipo());

        ObjectNode resp = ok();
        resp.set("user", user);
        send(resp);
    }

    private void handleSubscribe(JsonNode req) {
        String id = text(req, "id");
        if (id == null) { sendError("subscribe necesita id"); return; }

        Usuario u;
        try {
            u = usuarioDAO.findById(id);
        } catch (SQLException e) {
            u = null;
        }
        if (u == null) {
            u = new Usuario(id, (currentUser != null && id.equals(currentUser.getId()))
                    ? currentUser.getNombre() : id,
                    (currentUser != null && id.equals(currentUser.getId()))
                            ? currentUser.getTipo()   : "MEDICO");
        }

        this.currentUser = u;
        try { registry.markOnline(u); } catch (Exception ignored) {}

        ServidorBackend.CONNECTIONS.register(u.getId(), out);
        System.out.println("[Backend] subscribe: registered persistent connection for " + u.getId());
        ServidorBackend.CONNECTIONS.broadcastUserLogin(u.getId(), u.getNombre());

        send(ok());
    }

    private void handleEnviarMensaje(JsonNode req) throws SQLException {
        String remitente = text(req, "remitente");
        String destinatario = text(req, "destinatario");
        String texto = text(req, "texto");
        if (remitente == null || destinatario == null || texto == null || texto.isBlank()) {
            sendError("Campos requeridos: remitente, destinatario, texto");
            return;
        }

        MensajeDAO.MensajeDTO dto = mensajes.enviar(remitente, destinatario, texto);

        ObjectNode resp = ok();
        resp.set("mensaje", MAPPER.valueToTree(dto));
        send(resp);
    }

    private void handleRecibirMensajes(JsonNode req) throws SQLException {
        String remitente = text(req, "remitente");
        String destinatario = text(req, "destinatario");
        if (remitente == null || destinatario == null) {
            sendError("Campos requeridos: remitente, destinatario");
            return;
        }

        List<Mensaje> conversacion = mensajes.recibirMensaje(remitente, destinatario);
        List<MensajeDAO.MensajeDTO> dtos = mensajes.toDTOs(conversacion);
        ArrayNode arr = MAPPER.valueToTree(dtos);

        ObjectNode resp = ok();
        resp.set("mensajes", arr);
        send(resp);
    }

    private void handleListarConversaciones(JsonNode req) throws SQLException {
        String userId = text(req, "userId");
        if (userId == null) { sendError("Campo requerido: userId"); return; }

        List<MensajeDAO.MensajeDTO> lista = mensajes.listarConversacionesDe(userId);
        ArrayNode arr = MAPPER.valueToTree(lista);

        ObjectNode resp = ok();
        resp.set("mensajes", arr);
        send(resp);
    }

    // --- NUEVOS ---

    private void handleListarUsuariosOnline() {
        ArrayNode arr = MAPPER.createArrayNode();
        for (Usuario u : registry.list()) {
            ObjectNode dto = MAPPER.createObjectNode();
            dto.put("id", u.getId());
            dto.put("nombre", u.getNombre() == null ? "" : u.getNombre());
            dto.put("tipo", u.getTipo() == null ? "" : u.getTipo());
            arr.add(dto);
        }
        ObjectNode resp = ok();
        resp.set("usuarios", arr);
        send(resp);
    }

    private void handleHeartbeat(JsonNode req) {
        String id = text(req, "id");
        String target = (id != null) ? id : (currentUser != null ? currentUser.getId() : null);
        if (target != null) {
            try { registry.heartbeat(target); } catch (Exception ignored) {}
        }
        send(ok());
    }

    private void handleLogout() {
        if (currentUser != null && currentUser.getId() != null) {
            String id = currentUser.getId();
            try { registry.logout(id); } catch (Exception ignored) {}
            try { ServidorBackend.CONNECTIONS.unregister(id); } catch (Exception ignored) {}
            ServidorBackend.CONNECTIONS.broadcastUserLogout(id);
            System.out.println("[Backend] handleLogout: user logged out -> " + id);
            currentUser = null;
        }
        send(ok());
    }

    /* -------------------- Utilidades -------------------- */

    private void send(ObjectNode obj) {
        String json = obj.toString();
        System.out.println("[Backend] TX: " + json);
        out.println(json);
    }

    private void sendError(String msg) {
        ObjectNode o = MAPPER.createObjectNode();
        o.put("ok", false);
        o.put("error", msg);
        send(o);
    }

    private void sendFail(String msg) {
        ObjectNode o = MAPPER.createObjectNode();
        o.put("ok", false);
        o.put("msg", msg);
        send(o);
    }

    private ObjectNode ok() {
        ObjectNode o = MAPPER.createObjectNode();
        o.put("ok", true);
        return o;
    }

    private String text(JsonNode n, String field) {
        JsonNode v = n.get(field);
        return (v != null && !v.isNull()) ? v.asText() : null;
    }
}
