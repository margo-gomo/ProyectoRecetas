package Controlador;
import Modelo.entidades.*;
import Modelo.Gestores.*;
import Modelo.entidades.Receta.Indicacion;
import Modelo.entidades.Receta.Receta;
import Modelo.Graficos.*;
import org.jfree.chart.ChartPanel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Controlador {
    public Controlador(GestorUsuario modeloUsuario, GestorMedico modeloMedico,
                       GestorMedicamento modeloMedicamento, GestorPaciente modeloPaciente, GestorRecetaIndicacion modeloRecetasIndicacion,Usuario usuario_login, GraficosUtil graficosUtil) {
        this.modeloUsuarios = modeloUsuario;
        this.modeloMedico = modeloMedico;
        this.modeloPaciente = modeloPaciente;
        this.modeloMedicamento = modeloMedicamento;
        this.modeloRecetasIndicacion = modeloRecetasIndicacion;
        this.usuario_login=usuario_login;
        this.graficosUtil=graficosUtil;
    }
    public Controlador() throws SQLException {
        this(new GestorUsuario(),new GestorMedico(),new GestorMedicamento(),new GestorPaciente(),new GestorRecetaIndicacion(),new Usuario(),new GraficosUtil());
    }
    public void usuarioLogin(String id,String clave) throws SQLException {
        usuario_login=modeloUsuarios.buscarIdClave(id,clave);
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
        medico.setNombre(nombre);
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
    public void agregarMedicamento(String codigo,String nombre,String presentacion,String descripcion) throws SecurityException, SQLException {
        Medicamento medicamento = new Medicamento(codigo,nombre,presentacion,descripcion);
        modeloMedicamento.agregar(medicamento,usuario_login);
    }
    public void actualizarMedicamento(String codigo,String nombre,String presentacion,String descripcion) throws SecurityException, SQLException {
        Medicamento medicamento = buscarMedicamento(codigo);
        medicamento.setNombre(nombre);
        medicamento.setDescripcion(descripcion);
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
        return modeloRecetasIndicacion.obtenerListaIndicaciones();
    }
    public Indicacion buscarIndicacion(String medicamentoCodigo){
        return modeloRecetasIndicacion.buscarIndicacionLista(medicamentoCodigo);
    }
    public void agregarIndicacion(Medicamento medicamento,int cantidad,String indicaiones,int duracion) throws IllegalArgumentException, SecurityException{
        Indicacion indicacion=new Indicacion(medicamento,cantidad,indicaiones,duracion);
        modeloRecetasIndicacion.agregarIndicacionLista(indicacion,usuario_login);
    }
    public void actualizarIndicacion(Medicamento medicamento,int cantidad,String indicaiones,int duracion)throws IllegalArgumentException, SecurityException{
        Indicacion indicacion=buscarIndicacion(medicamento.getCodigo());
        indicacion.setCantidad(cantidad);
        indicacion.setIndicaiones(indicaiones);
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
    public void init() {
        try {
            modeloUsuarios.cargar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar los datos");
        }
        try {
            modeloPaciente.cargar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar los datos");
        }
        try {
            modeloMedicamento.cargar();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar los datos");
        }
        try {
            modeloRecetasIndicacion.cargarRecetas();
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar los datos");
        }
        try {
            modeloMedico.cargar(obtenerListaUsuarios());
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar los datos");
        }
        try {
            modeloRecetasIndicacion.cargarIndicaciones(obtenerListaMedicamentos());
        } catch (SQLException  | IOException ex) {
            System.err.printf("Ocurrió un error al cargar los datos");
        }
    }
    public void cerrarAplicacion() {
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
        System.out.println("Aplicación finalizada..");
        System.exit(0);
    }
    private GestorMedico modeloMedico;
    private GestorPaciente modeloPaciente;
    private GestorMedicamento modeloMedicamento;
    private GestorRecetaIndicacion modeloRecetasIndicacion;
    private GestorUsuario modeloUsuarios;
    private Usuario usuario_login;
    private GraficosUtil graficosUtil;
}
