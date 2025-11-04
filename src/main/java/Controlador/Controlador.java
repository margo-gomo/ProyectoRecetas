package Controlador;
import Modelo.DAO.ConexionBD;
import Modelo.entidades.*;
import Modelo.Gestores.*;
import Modelo.entidades.Receta.Indicacion;
import Modelo.entidades.Receta.Receta;
import Modelo.Graficos.*;
import lombok.Getter;
import org.jfree.chart.ChartPanel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


public class Controlador {
    public Controlador(GestorUsuario modeloUsuario, GestorMedico modeloMedico,
                       GestorMedicamento modeloMedicamento, GestorPaciente modeloPaciente, GestorRecetaIndicacion modeloRecetasIndicacion,Usuario usuario_login, GraficosUtil graficosUtil,GestorMensaje modeloMensaje) {
        this.modeloUsuarios = modeloUsuario;
        this.modeloMedico = modeloMedico;
        this.modeloPaciente = modeloPaciente;
        this.modeloMedicamento = modeloMedicamento;
        this.modeloRecetasIndicacion = modeloRecetasIndicacion;
        this.usuario_login=usuario_login;
        this.graficosUtil=graficosUtil;
        this.modeloMensaje = modeloMensaje;
    }
    public Controlador() throws SQLException {
        this(new GestorUsuario(),new GestorMedico(),new GestorMedicamento(),new GestorPaciente(),new GestorRecetaIndicacion(),new Usuario(),new GraficosUtil(),new GestorMensaje());
    }
    public void usuarioLogin(String id, String clave) throws SQLException {
        final ObjectMapper M = new ObjectMapper();
        final String reqJson = String.format(
                "{\"op\":\"login\",\"id\":\"%s\",\"clave\":\"%s\"}",
                id.replace("\"","\\\""),
                clave.replace("\"","\\\"")
        );

        try (Socket s = new Socket("localhost", 5050);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out  = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {

            out.println(reqJson);
            String line = in.readLine();
            if (line == null) throw new IllegalStateException("Sin respuesta del backend de login.");

            JsonNode resp = M.readTree(line);
            if (!resp.path("ok").asBoolean(false)) {
                String msg = resp.has("msg") ? resp.get("msg").asText() :
                        resp.has("error") ? resp.get("error").asText() :
                                "Credenciales inválidas";
                throw new SecurityException(msg);
            }

            JsonNode user = resp.path("user");
            String nombreBackend = user.path("nombre").asText("");
            String idBackend     = user.path("id").asText(id);

            Usuario u = null;
            try { u = modeloUsuarios.buscar(idBackend); } catch (Exception ignore) {}

            if (u == null) {
                u = new Usuario(idBackend, (nombreBackend.isBlank()? idBackend : nombreBackend), "MEDICO");
            } else {
                if (!nombreBackend.isBlank()) u.setNombre(nombreBackend);
            }

            this.usuario_login = u;

        } catch (SecurityException se) {
            throw se;
        } catch (Exception io) {
            throw new SQLException("Fallo de conexión con backend de login: " + io.getMessage(), io);
        }
    }

    // =================== MENSAJES: enviar, recibir conversación y listar ===================

    /** Envía un mensaje al destinatario actual vía backend socket. */
    public Modelo.DAO.MensajeDAO.MensajeDTO enviarMensaje(String destinatarioId, String texto) throws SQLException {
        if (usuario_login == null || usuario_login.getId() == null)
            throw new SecurityException("No hay usuario autenticado.");
        if (destinatarioId == null || destinatarioId.isBlank())
            throw new IllegalArgumentException("Debe indicar el destinatario.");
        if (texto == null || texto.isBlank())
            throw new IllegalArgumentException("El texto del mensaje no puede estar vacío.");

        final ObjectMapper M = new ObjectMapper();
        final String reqJson = String.format(
                "{\"op\":\"enviarMensaje\",\"remitente\":\"%s\",\"destinatario\":\"%s\",\"texto\":\"%s\"}",
                usuario_login.getId().replace("\"","\\\""),
                destinatarioId.replace("\"","\\\""),
                texto.replace("\"","\\\"")
        );

        try (Socket s = new Socket("localhost", 5050);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out  = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {

            out.println(reqJson);
            String line = in.readLine();
            if (line == null) throw new IllegalStateException("Sin respuesta del backend (enviarMensaje).");

            JsonNode resp = M.readTree(line);
            if (!resp.path("ok").asBoolean(false)) {
                String msg = resp.has("msg") ? resp.get("msg").asText()
                        : resp.has("error") ? resp.get("error").asText()
                        : "Fallo al enviar mensaje";
                throw new SQLException(msg);
            }

            JsonNode nodo = resp.path("mensaje");
            Modelo.DAO.MensajeDAO.MensajeDTO dto =
                    M.convertValue(nodo, Modelo.DAO.MensajeDAO.MensajeDTO.class);
            return dto;

        } catch (Exception io) {
            throw new SQLException("Fallo de conexión con backend de mensajes: " + io.getMessage(), io);
        }
    }

    public java.util.List<Modelo.DAO.MensajeDAO.MensajeDTO> recibirMensajes(String otroId) throws SQLException {
        if (usuario_login == null || usuario_login.getId() == null)
            throw new SecurityException("No hay usuario autenticado.");
        if (otroId == null || otroId.isBlank())
            throw new IllegalArgumentException("Debe indicar el otro participante.");

        final ObjectMapper M = new ObjectMapper();
        final String reqJson = String.format(
                "{\"op\":\"recibirMensajes\",\"remitente\":\"%s\",\"destinatario\":\"%s\"}",
                usuario_login.getId().replace("\"","\\\""),
                otroId.replace("\"","\\\"")
        );

        try (Socket s = new Socket("localhost", 5050);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out  = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {

            out.println(reqJson);
            String line = in.readLine();
            if (line == null) throw new IllegalStateException("Sin respuesta del backend (recibirMensajes).");

            JsonNode resp = M.readTree(line);
            if (!resp.path("ok").asBoolean(false)) {
                String msg = resp.has("msg") ? resp.get("msg").asText()
                        : resp.has("error") ? resp.get("error").asText()
                        : "Fallo al recibir mensajes";
                throw new SQLException(msg);
            }

            JsonNode arr = resp.path("mensajes");
            java.util.List<Modelo.DAO.MensajeDAO.MensajeDTO> lista =
                    M.convertValue(arr, new com.fasterxml.jackson.core.type.TypeReference<java.util.List<Modelo.DAO.MensajeDAO.MensajeDTO>>() {});
            return (lista != null) ? lista : java.util.Collections.emptyList();

        } catch (Exception io) {
            throw new SQLException("Fallo de conexión con backend de mensajes: " + io.getMessage(), io);
        }
    }

    /** Lista todos los mensajes en los que participa el usuario actual (timeline). */
    public java.util.List<Modelo.DAO.MensajeDAO.MensajeDTO> listarConversaciones() throws SQLException {
        if (usuario_login == null || usuario_login.getId() == null)
            throw new SecurityException("No hay usuario autenticado.");

        final ObjectMapper M = new ObjectMapper();
        final String reqJson = String.format(
                "{\"op\":\"listarConversaciones\",\"userId\":\"%s\"}",
                usuario_login.getId().replace("\"","\\\"")
        );

        try (Socket s = new Socket("localhost", 5050);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out  = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {

            out.println(reqJson);
            String line = in.readLine();
            if (line == null) throw new IllegalStateException("Sin respuesta del backend (listarConversaciones).");

            JsonNode resp = M.readTree(line);
            if (!resp.path("ok").asBoolean(false)) {
                String msg = resp.has("msg") ? resp.get("msg").asText()
                        : resp.has("error") ? resp.get("error").asText()
                        : "Fallo al listar conversaciones";
                throw new SQLException(msg);
            }

            JsonNode arr = resp.path("mensajes");
            java.util.List<Modelo.DAO.MensajeDAO.MensajeDTO> lista =
                    M.convertValue(arr, new com.fasterxml.jackson.core.type.TypeReference<java.util.List<Modelo.DAO.MensajeDAO.MensajeDTO>>() {});
            return (lista != null) ? lista : java.util.Collections.emptyList();

        } catch (Exception io) {
            throw new SQLException("Fallo de conexión con backend de mensajes: " + io.getMessage(), io);
        }
    }


    public void cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar) throws IllegalArgumentException, SQLException {
        modeloUsuarios.cambiarClave(id, claveActual, claveNueva, claveConfirmar);
    }
    public List<Usuario> obtenerListaUsuarios() throws SQLException {
        return modeloUsuarios.obtenerListaUsuarios();
    }
    public List<Medico> obtenerListaMedicos() throws SQLException {
        return modeloMedico.obtenerListaMedicos();
    }
    public Medico buscarMedico(String id) throws SQLException {
        return modeloMedico.buscar(id);
    }
    public void agregarMedico(String id,String nom,String esp) throws SecurityException, SQLException {
        Usuario usuario=new Usuario(id,nom,"MEDICO");
        Medico medico=new Medico(usuario,esp);
        modeloMedico.agregar(medico,usuario_login);
    }
    public void actualizarMedico(String id,String nombre,String especialidad) throws SecurityException, SQLException {
        Medico medico = buscarMedico(id);
        medico.getUsuario().setNombre(nombre);
        medico.setEspecialidad(especialidad);
        modeloMedico.actualizar(medico,usuario_login);
    }
    public void eliminarMedico(String id) throws SQLException {
        modeloMedico.eliminar(id,usuario_login);
    }
    public List<Usuario> obtenerListaAdministradores() throws SQLException {
        return modeloUsuarios.obtenerListaAdministradores();
    }
    public List<Usuario> obtenerListaFarmaceutas() throws SQLException {
        return modeloUsuarios.obtenerListaFarmaceutas();
    }
    public List<Usuario> obtenerListaMedicosU() throws SQLException {
        return modeloUsuarios.obtenerListaMedicos();
    }
    public Usuario buscarUsuario(String id) throws SQLException {
        return modeloUsuarios.buscar(id);
    }
    public void agregarAdministrador(String id,String nombre)  throws SQLException{
        Administrador administrador = new Administrador(id,nombre);
        modeloUsuarios.agregar(administrador,usuario_login);
    }
    public void agregarFarmaceuta(String id,String nombre)  throws SQLException{
        Farmaceuta farmaceuta = new Farmaceuta(id,nombre);
        modeloUsuarios.agregar(farmaceuta,usuario_login);
    }
    public void actualizarUsuario(String id,String nombre) throws SecurityException, SQLException {
        Usuario usuario=buscarUsuario(id);
        usuario.setClave(nombre);
        usuario.setNombre(nombre);
        modeloUsuarios.actualizar(usuario,usuario_login);
    }
    public void eliminarUsuario(String id) throws SQLException{
        modeloUsuarios.eliminar(id,usuario_login);;
    }
    public List<Paciente> obtenerListaPacientes() throws SQLException {
        return modeloPaciente.obtenerListaPacientes();
    }
    public Paciente buscarPacientePorId(int id) throws SQLException {
        return modeloPaciente.buscar(id);
    }
    public void agregarPaciente(int id, String nombre, int telefono, Date fecha) throws SecurityException, SQLException {
        Paciente paciente = new Paciente(id,nombre,fecha,telefono);
        modeloPaciente.agregar(paciente,usuario_login);
    }
    public void actualizarPaciente(int id, String nombre, int telefono,Date fecha) throws SQLException {
        Paciente paciente = buscarPacientePorId(id);
        paciente.setNombre(nombre);
        paciente.setTelefono(telefono);
        paciente.setFecha_nacimiento(fecha);
        modeloPaciente.actualizar(paciente,usuario_login);
    }
    public void eliminarPaciente(int id) throws SecurityException, SQLException {
         modeloPaciente.eliminar(id,usuario_login);
    }
    public List<Medicamento> obtenerListaMedicamentos() throws SQLException {
        return modeloMedicamento.obtenerListaMedicamentos();
    }
    public Medicamento buscarMedicamento(String codigo) throws SQLException {
        return modeloMedicamento.buscar(codigo);
    }
    public void agregarMedicamento(String codigo,String nombre,String presentacion) throws SecurityException, SQLException {
        Medicamento medicamento = new Medicamento(codigo,nombre,presentacion);
        modeloMedicamento.agregar(medicamento,usuario_login);
    }
    public void actualizarMedicamento(String codigo,String nombre,String presentacion) throws SecurityException, SQLException {
        Medicamento medicamento = buscarMedicamento(codigo);
        medicamento.setNombre(nombre);
        medicamento.setPresentacion(presentacion);
        modeloMedicamento.actualizar(medicamento,usuario_login);
    }
    public void eliminarMedicamento(String codigo) throws SecurityException, SQLException {
        modeloMedicamento.eliminar(codigo,usuario_login);
    }
    public List<Receta> obtenerListaRecetas() throws SQLException {
        return modeloRecetasIndicacion.obtenerListaRecetas();
    }
    public  Receta buscarReceta(String codigo) throws SQLException {
        return modeloRecetasIndicacion.buscarReceta(codigo);
    }
    public void agregarReceta(String codigo,Paciente paciente,Date fecha_retiro,Date fecha_confeccion,Usuario farmaceuta_Proceso,Usuario farmaceuta_Lista,Usuario farmaceuta_Entregada) throws IllegalArgumentException, SecurityException, SQLException {
        Receta receta = new Receta(codigo, paciente, fecha_retiro, fecha_confeccion, farmaceuta_Proceso, farmaceuta_Lista, farmaceuta_Entregada);
        modeloRecetasIndicacion.agregarReceta(receta,usuario_login);
    }
    public void iniciarProceso(String codigo) throws IllegalArgumentException, SQLException,SecurityException {
        modeloRecetasIndicacion.iniciarProceso(codigo,usuario_login);
    }
    public void marcarLista(String codigo) throws IllegalArgumentException, SQLException,SecurityException {
        modeloRecetasIndicacion.marcarLista(codigo,usuario_login);
    }
    public void entregar(String codigo) throws IllegalArgumentException, SQLException, SecurityException {
        modeloRecetasIndicacion.entregar(codigo,usuario_login);
    }
    public List<Indicacion> obtenerListaIndicaciones() throws SQLException {
        return modeloRecetasIndicacion.getIndicacionList();
    }
    public Indicacion buscarIndicacion(String medicamentoCodigo){
        return modeloRecetasIndicacion.buscarIndicacionLista(medicamentoCodigo);
    }
    public void agregarIndicacion(String medicamento_codigo,int cantidad,String indicaiones,int duracion) throws IllegalArgumentException, SecurityException{
        Indicacion indicacion=new Indicacion();
        indicacion.setCantidad(cantidad);
        indicacion.setIndicaciones(indicaiones);
        indicacion.setDuracion(duracion);
        try {
            indicacion.setMedicamento(buscarMedicamento(medicamento_codigo));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        modeloRecetasIndicacion.agregarIndicacionLista(indicacion,usuario_login);
    }
    public void actualizarIndicacion(Medicamento medicamento,int cantidad,String indicaiones,int duracion)throws IllegalArgumentException, SecurityException{
        Indicacion indicacion=buscarIndicacion(medicamento.getCodigo());
        indicacion.setCantidad(cantidad);
        indicacion.setIndicaciones(indicaiones);
        indicacion.setDuracion(duracion);
        modeloRecetasIndicacion.actualizarIndicacionLista(indicacion,usuario_login);
    }
    public void eliminarIndicacionReceta(String codigo) throws SQLException, SecurityException{
        modeloRecetasIndicacion.eliminarIndicacionLista(codigo,usuario_login);
    }
    // ---------------- MÉTODOS DASHBOARD ----------------
    public void agregarMedicamentoCodigo(String codigo)throws IllegalArgumentException{
        modeloRecetasIndicacion.agregarMedicamentoCodigo(codigo);
    }
    public void eliminarMedicamentoCodigo(String codigo){
        modeloRecetasIndicacion.eliminarMedicamentoCodigo(codigo);
    }
    public void limpiarMedicamentoCodigo(){
        modeloRecetasIndicacion.limpiarMedicamentoCodigo();
    }
    public ChartPanel crearGraficoLineas(Date desde, Date hasta)throws IllegalArgumentException, SQLException{
        return graficosUtil.crearGraficoLineas(modeloRecetasIndicacion.estadisticaMedicamentosPorMes(desde,hasta));
    }
    public ChartPanel crearGraficoPastel()throws SQLException{
        return graficosUtil.crearGraficoPastel(modeloRecetasIndicacion.recetasPorEstado());
    }

    public List<Indicacion>mostrarIndicaciones(String codigo) throws SQLException {
        return modeloRecetasIndicacion.indiccionesReceta(codigo);
    }

    private final Modelo.Proxy.SocketProxy proxy = new Modelo.Proxy.SocketProxy();

    public void init() {
        try {
            modeloUsuarios.cargar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar usuarios");
        }
        try {
            modeloPaciente.cargar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar pacientes");
        }
        try {
            modeloMedicamento.cargar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar medicamentos");
        }
        try {
            modeloRecetasIndicacion.cargarRecetas();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar recetas");
        }
        try {
            modeloMedico.cargar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar medicos");
        }
        try {
            modeloRecetasIndicacion.cargarIndicaciones();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar indicaciones");
        }
        try {
            modeloMensaje.cargar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar mensajes");
        }
        // ---- PRUEBA PROXY ----
        proxy.setOnError(msg -> System.out.println("[UI] Error: " + msg));
        try {
            System.out.println("[UI] Intentando conectar al backend...");
            proxy.conectar("localhost", 5050);         // 1) conecta
            proxy.enviarLinea("{\"op\":\"ping\"}");     // 2) envía
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void cerrarAplicacion() {
        try { proxy.cerrar(); } catch (Exception ignored) {}
        try {
            modeloUsuarios.guardar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al guardar los datos");
        }
        try {
            modeloPaciente.guardar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al guardar los datos");
        }
        try {
            modeloMedicamento.guardar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al guardar los datos");
        }
        try {
            modeloRecetasIndicacion.guardarRecetas();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al guardar los datos");
        }
        try {
            modeloMedico.guardar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al guardar los datos");
        }
        try {
            modeloRecetasIndicacion.guardarIndicaciones();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al guardar los datos");
        }
        try {
            modeloMensaje.guardar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al guardar los datos");
        }
        ConexionBD.cerrarConexion();
        System.out.println("Aplicación finalizada..");
        System.exit(0);
    }
    private GestorMedico modeloMedico;
    private GestorPaciente modeloPaciente;
    private GestorMedicamento modeloMedicamento;
    private GestorRecetaIndicacion modeloRecetasIndicacion;
    private GestorUsuario modeloUsuarios;
    private GestorMensaje modeloMensaje;
    @Getter
    private Usuario usuario_login;
    private GraficosUtil graficosUtil;

}
