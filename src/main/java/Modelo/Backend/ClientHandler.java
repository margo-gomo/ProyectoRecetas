package Modelo.Backend;

import Modelo.DAO.MensajeDAO;

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
 *   {"op":"listarConversaciones","userId":"<id>"}
 */
public class ClientHandler implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private final MensajeDAO mensajes;

    public ClientHandler(Socket socket) throws SQLException {
        this.socket = socket;
        this.mensajes = new MensajeDAO();
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

                try {
                    switch (op) {
                        case "ping" -> handlePing(req);
                        case "login" -> handleLogin(req);
                        case "enviarMensaje" -> handleEnviarMensaje(req);
                        case "listarConversaciones" -> handleListarConversaciones(req);
                        default -> sendError("Operación no soportada: " + op);
                    }
                } catch (Exception ex) {
                    sendError("Error interno: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println("[Backend] Handler error: " + ex.getMessage());
        } finally {
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
        if (id == null || clave == null) {
            sendError("Campos requeridos: id, clave");
            return;
        }

        String nombre = mensajes.validarUsuario(id, clave); // null si no existe o clave inválida
        if (nombre == null) {
            sendFail("Credenciales inválidas");
            return;
        }

        ObjectNode user = MAPPER.createObjectNode();
        user.put("id", id);
        user.put("nombre", nombre);

        ObjectNode resp = ok();
        resp.set("user", user);
        send(resp);
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

    private void handleListarConversaciones(JsonNode req) throws SQLException {
        String userId = text(req, "userId");
        if (userId == null) { sendError("Campo requerido: userId"); return; }

        List<MensajeDAO.MensajeDTO> lista = mensajes.listarConversacionesDe(userId);
        ArrayNode arr = MAPPER.valueToTree(lista);

        ObjectNode resp = ok();
        resp.set("mensajes", arr);
        send(resp);
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
