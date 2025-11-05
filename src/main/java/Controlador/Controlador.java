package Controlador;

import Modelo.DAO.ConexionBD;
import Modelo.DAO.MensajeDAO;
import Modelo.Gestores.*;
import Modelo.Graficos.GraficosUtil;
import Modelo.Proxy.SocketProxy;
import Modelo.entidades.*;
import Modelo.entidades.Receta.Indicacion;
import Modelo.entidades.Receta.Receta;
import lombok.Getter;
import org.jfree.chart.ChartPanel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador principal del sistema.
 * - Gestión de Usuarios / Médicos / Pacientes / Medicamentos
 * - Prescripciones e Indicaciones
 * - Dashboard (gráficos)
 * - Mensajería y Login contra backend socket (ClientHandler)
 */
public class Controlador {

    // ========================= Constructores =========================
    public Controlador(GestorUsuario modeloUsuario,
                       GestorMedico modeloMedico,
                       GestorMedicamento modeloMedicamento,
                       GestorPaciente modeloPaciente,
                       GestorRecetaIndicacion modeloRecetasIndicacion,
                       Usuario usuario_login,
                       GraficosUtil graficosUtil,
                       GestorMensaje modeloMensaje) {
        this.modeloUsuarios = modeloUsuario;
        this.modeloMedico = modeloMedico;
        this.modeloPaciente = modeloPaciente;
        this.modeloMedicamento = modeloMedicamento;
        this.modeloRecetasIndicacion = modeloRecetasIndicacion;
        this.usuario_login = usuario_login;
        this.graficosUtil = graficosUtil;
        this.modeloMensaje = modeloMensaje;
    }

    public Controlador() throws SQLException {
        this(new GestorUsuario(),
                new GestorMedico(),
                new GestorMedicamento(),
                new GestorPaciente(),
                new GestorRecetaIndicacion(),
                new Usuario(),
                new GraficosUtil(),
                new GestorMensaje());
    }

    // ========================= Backend (Socket) =========================
    private String backendHost = "localhost";
    private int backendPort = 5050;

    private static final ObjectMapper JSON = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public void setBackendEndpoint(String host, int port) {
        this.backendHost = host;
        this.backendPort = port;
    }

    public boolean pingBackend() {
        try {
            ObjectNode req = JSON.createObjectNode().put("op", "ping");
            JsonNode resp = callBackend(req);
            return resp.path("ok").asBoolean(false);
        } catch (Exception e) {
            return false;
        }
    }

    private JsonNode callBackend(ObjectNode req) throws IOException {
        try (Socket s = new Socket(backendHost, backendPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {

            out.println(req.toString());
            String line = in.readLine();
            if (line == null) throw new IOException("Sin respuesta del backend.");
            return JSON.readTree(line);
        }
    }

    // ========================= Autenticación (via backend) =========================
    public void usuarioLogin(String id, String clave) throws SQLException {
        try {
            ObjectNode req = JSON.createObjectNode()
                    .put("op", "login")
                    .put("id", id)
                    .put("clave", clave);

            JsonNode resp = callBackend(req);
            if (!resp.path("ok").asBoolean(false)) {
                String msg = resp.has("msg") ? resp.get("msg").asText()
                        : resp.has("error") ? resp.get("error").asText()
                        : "Credenciales inválidas";
                throw new SecurityException(msg);
            }

            JsonNode user = resp.path("user");
            String nombreBackend = user.path("nombre").asText("");
            String idBackend = user.path("id").asText(id);

            Usuario u;
            try {
                u = modeloUsuarios.buscar(idBackend);
            } catch (Exception ignore) {
                u = null;
            }

            if (u == null) {
                u = new Usuario(idBackend, (nombreBackend.isBlank() ? idBackend : nombreBackend), "MEDICO");
            } else if (!nombreBackend.isBlank()) {
                u.setNombre(nombreBackend);
            }

            this.usuario_login = u;

            // === NUEVO: registrar sesión activa ===
            sesionesActivas.add(this.usuario_login.getId());

        } catch (SecurityException se) {
            throw se;
        } catch (Exception ex) {
            throw new SQLException("Fallo de conexión con backend de login: " + ex.getMessage(), ex);
        }
    }

    // === NUEVO: logout de la sesión actual ===
    public void logoutActual() {
        if (usuario_login != null) {
            sesionesActivas.remove(usuario_login.getId());
            usuario_login = null;
        }
    }

    // ========================= Mensajería (vía backend) =========================

    /** Envía un mensaje (remitente = usuario_login). */
    public MensajeDAO.MensajeDTO enviarMensaje(String destinatarioId, String texto) throws SQLException {
        if (usuario_login == null || usuario_login.getId() == null)
            throw new SecurityException("No hay usuario autenticado.");
        if (destinatarioId == null || destinatarioId.isBlank())
            throw new IllegalArgumentException("Debe indicar el destinatario.");
        if (texto == null || texto.isBlank())
            throw new IllegalArgumentException("El texto del mensaje no puede estar vacío.");

        try {
            ObjectNode req = JSON.createObjectNode()
                    .put("op", "enviarMensaje")
                    .put("remitente", usuario_login.getId())
                    .put("destinatario", destinatarioId)
                    .put("texto", texto);

            JsonNode resp = callBackend(req);
            if (!resp.path("ok").asBoolean(false)) {
                String msg = resp.has("msg") ? resp.get("msg").asText()
                        : resp.has("error") ? resp.get("error").asText()
                        : "Fallo al enviar mensaje";
                throw new SQLException(msg);
            }

            JsonNode nodo = resp.path("mensaje");
            return JSON.convertValue(nodo, MensajeDAO.MensajeDTO.class);

        } catch (Exception ex) {
            throw new SQLException("Fallo de conexión con backend de mensajes: " + ex.getMessage(), ex);
        }
    }

    /** Devuelve la conversación entre el usuario actual y 'otroId'. */
    public List<MensajeDAO.MensajeDTO> recibirMensajes(String otroId) throws SQLException {
        if (usuario_login == null || usuario_login.getId() == null)
            throw new SecurityException("No hay usuario autenticado.");
        if (otroId == null || otroId.isBlank())
            throw new IllegalArgumentException("Debe indicar el otro participante.");

        try {
            ObjectNode req = JSON.createObjectNode()
                    .put("op", "recibirMensajes")
                    .put("remitente", usuario_login.getId())
                    .put("destinatario", otroId);

            JsonNode resp = callBackend(req);
            if (!resp.path("ok").asBoolean(false)) {
                String msg = resp.has("msg") ? resp.get("msg").asText()
                        : resp.has("error") ? resp.get("error").asText()
                        : "Fallo al recibir mensajes";
                throw new SQLException(msg);
            }

            JsonNode arr = resp.path("mensajes");
            List<MensajeDAO.MensajeDTO> lista = JSON.convertValue(
                    arr, new TypeReference<List<MensajeDAO.MensajeDTO>>() {
                    });
            return (lista != null) ? lista : Collections.emptyList();

        } catch (Exception ex) {
            throw new SQLException("Fallo de conexión con backend de mensajes: " + ex.getMessage(), ex);
        }
    }

    /** Lista el timeline de conversaciones del usuario actual. */
    public List<MensajeDAO.MensajeDTO> listarConversaciones() throws SQLException {
        if (usuario_login == null || usuario_login.getId() == null)
            throw new SecurityException("No hay usuario autenticado.");

        try {
            ObjectNode req = JSON.createObjectNode()
                    .put("op", "listarConversaciones")
                    .put("userId", usuario_login.getId());

            JsonNode resp = callBackend(req);
            if (!resp.path("ok").asBoolean(false)) {
                String msg = resp.has("msg") ? resp.get("msg").asText()
                        : resp.has("error") ? resp.get("error").asText()
                        : "Fallo al listar conversaciones";
                throw new SQLException(msg);
            }

            JsonNode arr = resp.path("mensajes");
            List<MensajeDAO.MensajeDTO> lista = JSON.convertValue(
                    arr, new TypeReference<List<MensajeDAO.MensajeDTO>>() {
                    });
            return (lista != null) ? lista : Collections.emptyList();

        } catch (Exception ex) {
            throw new SQLException("Fallo de conexión con backend de mensajes: " + ex.getMessage(), ex);
        }
    }

    /** Devuelve una lista de posibles destinatarios (todos los usuarios excepto el actual). */
    public List<Usuario> obtenerPosiblesDestinatarios() throws SQLException {
        List<Usuario> todos = obtenerListaUsuarios();
        if (usuario_login == null) return todos;
        return todos.stream()
                .filter(u -> u != null && u.getId() != null && !u.getId().equals(usuario_login.getId()))
                .collect(Collectors.toList());
    }

    // ========================= Usuarios / Roles =========================
    public void cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar)
            throws IllegalArgumentException, SQLException {
        modeloUsuarios.cambiarClave(id, claveActual, claveNueva, claveConfirmar);
    }

    public List<Usuario> obtenerListaUsuarios() throws SQLException {
        return modeloUsuarios.obtenerListaUsuarios();
    }

    // === NUEVO: listar SOLO usuarios logueados ===
    public List<Usuario> obtenerUsuariosLogueados() throws SQLException {
        List<Usuario> todos = obtenerListaUsuarios();
        List<Usuario> online = new ArrayList<>();
        synchronized (sesionesActivas) {
            for (Usuario u : todos) {
                if (u != null && u.getId() != null && sesionesActivas.contains(u.getId())) {
                    online.add(u);
                }
            }
        }
        return online;
    }

    public List<Usuario> obtenerListaAdministradores() throws SQLException {
        return modeloUsuarios.obtenerListaAdministradores();
    }

    public List<Usuario> obtenerListaFarmaceutas() throws SQLException {
        return modeloUsuarios.obtenerListaFarmaceutas();
    }

    public List<Medico> obtenerListaMedicos() throws SQLException {
        return modeloMedico.obtenerListaMedicos();
    }

    public List<Usuario> obtenerListaMedicosU() throws SQLException {
        return modeloUsuarios.obtenerListaMedicos();
    }

    public Usuario buscarUsuario(String id) throws SQLException {
        return modeloUsuarios.buscar(id);
    }

    public void agregarAdministrador(String id, String nombre) throws SQLException {
        Administrador administrador = new Administrador(id, nombre);
        modeloUsuarios.agregar(administrador, usuario_login);
    }

    public void agregarFarmaceuta(String id, String nombre) throws SQLException {
        Farmaceuta farmaceuta = new Farmaceuta(id, nombre);
        modeloUsuarios.agregar(farmaceuta, usuario_login);
    }

    public void agregarMedico(String id, String nom, String esp) throws SecurityException, SQLException {
        Usuario usuario = new Usuario(id, nom, "MEDICO");
        Medico medico = new Medico(usuario, esp);
        modeloMedico.agregar(medico, usuario_login);
    }

    public void actualizarUsuario(String id, String nombre) throws SecurityException, SQLException {
        Usuario usuario = buscarUsuario(id);
        usuario.setNombre(nombre);
        modeloUsuarios.actualizar(usuario, usuario_login);
    }

    public void eliminarUsuario(String id) throws SQLException {
        modeloUsuarios.eliminar(id, usuario_login);
    }

    public Medico buscarMedico(String id) throws SQLException {
        return modeloMedico.buscar(id);
    }

    public void actualizarMedico(String id, String nombre, String especialidad) throws SecurityException, SQLException {
        Medico medico = buscarMedico(id);
        medico.getUsuario().setNombre(nombre);
        medico.setEspecialidad(especialidad);
        modeloMedico.actualizar(medico, usuario_login);
    }

    public void eliminarMedico(String id) throws SQLException {
        modeloMedico.eliminar(id, usuario_login);
    }

    // ========================= Pacientes =========================
    public List<Paciente> obtenerListaPacientes() throws SQLException {
        return modeloPaciente.obtenerListaPacientes();
    }

    public Paciente buscarPacientePorId(int id) throws SQLException {
        return modeloPaciente.buscar(id);
    }

    public void agregarPaciente(int id, String nombre, int telefono, Date fecha) throws SecurityException, SQLException {
        Paciente paciente = new Paciente(id, nombre, fecha, telefono);
        modeloPaciente.agregar(paciente, usuario_login);
    }

    public void actualizarPaciente(int id, String nombre, int telefono, Date fecha) throws SQLException {
        Paciente paciente = buscarPacientePorId(id);
        paciente.setNombre(nombre);
        paciente.setTelefono(telefono);
        paciente.setFecha_nacimiento(fecha);
        modeloPaciente.actualizar(paciente, usuario_login);
    }

    public void eliminarPaciente(int id) throws SecurityException, SQLException {
        modeloPaciente.eliminar(id, usuario_login);
    }

    // ========================= Medicamentos =========================
    public List<Medicamento> obtenerListaMedicamentos() throws SQLException {
        return modeloMedicamento.obtenerListaMedicamentos();
    }

    public Medicamento buscarMedicamento(String codigo) throws SQLException {
        return modeloMedicamento.buscar(codigo);
    }

    public void agregarMedicamento(String codigo, String nombre, String presentacion) throws SecurityException, SQLException {
        Medicamento medicamento = new Medicamento(codigo, nombre, presentacion);
        modeloMedicamento.agregar(medicamento, usuario_login);
    }

    public void actualizarMedicamento(String codigo, String nombre, String presentacion) throws SecurityException, SQLException {
        Medicamento medicamento = buscarMedicamento(codigo);
        medicamento.setNombre(nombre);
        medicamento.setPresentacion(presentacion);
        modeloMedicamento.actualizar(medicamento, usuario_login);
    }

    public void eliminarMedicamento(String codigo) throws SecurityException, SQLException {
        modeloMedicamento.eliminar(codigo, usuario_login);
    }

    // ========================= Recetas / Indicaciones =========================
    public List<Receta> obtenerListaRecetas() throws SQLException {
        return modeloRecetasIndicacion.obtenerListaRecetas();
    }

    public Receta buscarReceta(String codigo) throws SQLException {
        return modeloRecetasIndicacion.buscarReceta(codigo);
    }

    public void agregarReceta(String codigo, Paciente paciente, Date fecha_retiro, Date fecha_confeccion,
                              Usuario farmaceuta_Proceso, Usuario farmaceuta_Lista, Usuario farmaceuta_Entregada)
            throws IllegalArgumentException, SecurityException, SQLException {
        Receta receta = new Receta(codigo, paciente, fecha_retiro, fecha_confeccion,
                farmaceuta_Proceso, farmaceuta_Lista, farmaceuta_Entregada);
        modeloRecetasIndicacion.agregarReceta(receta, usuario_login);
    }

    public void iniciarProceso(String codigo) throws IllegalArgumentException, SQLException, SecurityException {
        modeloRecetasIndicacion.iniciarProceso(codigo, usuario_login);
    }

    public void marcarLista(String codigo) throws IllegalArgumentException, SQLException, SecurityException {
        modeloRecetasIndicacion.marcarLista(codigo, usuario_login);
    }

    public void entregar(String codigo) throws IllegalArgumentException, SQLException, SecurityException {
        modeloRecetasIndicacion.entregar(codigo, usuario_login);
    }

    public List<Indicacion> obtenerListaIndicaciones() throws SQLException {
        return modeloRecetasIndicacion.getIndicacionList();
    }

    public Indicacion buscarIndicacion(String medicamentoCodigo) {
        return modeloRecetasIndicacion.buscarIndicacionLista(medicamentoCodigo);
    }

    public void agregarIndicacion(String medicamento_codigo, int cantidad, String indicaiones, int duracion)
            throws IllegalArgumentException, SecurityException {
        Indicacion indicacion = new Indicacion();
        indicacion.setCantidad(cantidad);
        indicacion.setIndicaciones(indicaiones);
        indicacion.setDuracion(duracion);
        try {
            indicacion.setMedicamento(buscarMedicamento(medicamento_codigo));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        modeloRecetasIndicacion.agregarIndicacionLista(indicacion, usuario_login);
    }

    public void actualizarIndicacion(Medicamento medicamento, int cantidad, String indicaiones, int duracion)
            throws IllegalArgumentException, SecurityException {
        Indicacion indicacion = buscarIndicacion(medicamento.getCodigo());
        indicacion.setCantidad(cantidad);
        indicacion.setIndicaciones(indicaiones);
        indicacion.setDuracion(duracion);
        modeloRecetasIndicacion.actualizarIndicacionLista(indicacion, usuario_login);
    }

    public void eliminarIndicacionReceta(String codigo) throws SQLException, SecurityException {
        modeloRecetasIndicacion.eliminarIndicacionLista(codigo, usuario_login);
    }

    public List<Indicacion> mostrarIndicaciones(String codigo) throws SQLException {
        return modeloRecetasIndicacion.indiccionesReceta(codigo);
    }

    // ========================= Dashboard =========================
    public void agregarMedicamentoCodigo(String codigo) throws IllegalArgumentException {
        modeloRecetasIndicacion.agregarMedicamentoCodigo(codigo);
    }

    public void eliminarMedicamentoCodigo(String codigo) {
        modeloRecetasIndicacion.eliminarMedicamentoCodigo(codigo);
    }

    public void limpiarMedicamentoCodigo() {
        modeloRecetasIndicacion.limpiarMedicamentoCodigo();
    }

    public ChartPanel crearGraficoLineas(Date desde, Date hasta) throws IllegalArgumentException, SQLException {
        return graficosUtil.crearGraficoLineas(modeloRecetasIndicacion.estadisticaMedicamentosPorMes(desde, hasta));
    }

    public ChartPanel crearGraficoPastel() throws SQLException {
        return graficosUtil.crearGraficoPastel(modeloRecetasIndicacion.recetasPorEstado());
    }

    // ========================= Ciclo de vida (init/close) =========================
    private final SocketProxy proxy = new SocketProxy();

    public void init() {
        try { modeloUsuarios.cargar(); } catch (SQLException | IOException ex) { System.err.println("Error cargando usuarios"); }
        try { modeloPaciente.cargar(); } catch (SQLException | IOException ex) { System.err.println("Error cargando pacientes"); }
        try { modeloMedicamento.cargar(); } catch (SQLException | IOException ex) { System.err.println("Error cargando medicamentos"); }
        try { modeloRecetasIndicacion.cargarRecetas(); } catch (SQLException | IOException ex) { System.err.println("Error cargando recetas"); }
        try { modeloMedico.cargar(); } catch (SQLException | IOException ex) { System.err.println("Error cargando médicos"); }
        try { modeloRecetasIndicacion.cargarIndicaciones(); } catch (SQLException | IOException ex) { System.err.println("Error cargando indicaciones"); }
        try { modeloMensaje.cargar(); } catch (SQLException | IOException ex) { System.err.println("Error cargando mensajes"); }

        proxy.setOnError(msg -> System.out.println("[UI] Error: " + msg));
        try {
            System.out.println("[UI] Intentando conectar al backend...");
            proxy.conectar(backendHost, backendPort);
            proxy.enviarLinea("{\"op\":\"ping\"}");
        } catch (Exception ex) {
            // Si el backend no está arriba, la UI igual puede funcionar; solo logueamos.
            System.err.println("[UI] No se pudo pingear backend: " + ex.getMessage());
        }
    }

    public void cerrarAplicacion() {
        try { proxy.cerrar(); } catch (Exception ignored) {}

        try { modeloUsuarios.guardar(); } catch (SQLException | IOException ex) { System.err.println("Error guardando usuarios"); }
        try { modeloPaciente.guardar(); } catch (SQLException | IOException ex) { System.err.println("Error guardando pacientes"); }
        try { modeloMedicamento.guardar(); } catch (SQLException | IOException ex) { System.err.println("Error guardando medicamentos"); }
        try { modeloRecetasIndicacion.guardarRecetas(); } catch (SQLException | IOException ex) { System.err.println("Error guardando recetas"); }
        try { modeloMedico.guardar(); } catch (SQLException | IOException ex) { System.err.println("Error guardando médicos"); }
        try { modeloRecetasIndicacion.guardarIndicaciones(); } catch (SQLException | IOException ex) { System.err.println("Error guardando indicaciones"); }
        try { modeloMensaje.guardar(); } catch (SQLException | IOException ex) { System.err.println("Error guardando mensajes"); }

        ConexionBD.cerrarConexion();
        System.out.println("Aplicación finalizada...");
        System.exit(0);
    }

    // ========================= Campos =========================
    private GestorMedico modeloMedico;
    private GestorPaciente modeloPaciente;
    private GestorMedicamento modeloMedicamento;
    private GestorRecetaIndicacion modeloRecetasIndicacion;
    private GestorUsuario modeloUsuarios;
    private GestorMensaje modeloMensaje;

    @Getter
    private Usuario usuario_login;

    private GraficosUtil graficosUtil;

    private final java.util.Set<String> sesionesActivas =
            java.util.Collections.synchronizedSet(new java.util.HashSet<>());
}
