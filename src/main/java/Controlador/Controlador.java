package Controlador;
import Modelo.entidades.*;
import Modelo.Gestores.*;
import Modelo.DAO.*;
import Modelo.entidades.Receta.Indicacion;
import Modelo.entidades.Receta.Receta;
import Modelo.login;
import Modelo.Estadísticas.*;
import jakarta.xml.bind.JAXBException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import lombok.Setter;
public class Controlador {
    public Controlador(GestorAdministrador modeloAdministrador,GestorMedico modeloMedico, GestorFarmaceuta modeloFarmaceuta,
                       GestorMedicamento modeloMedicamento,GestorPaciente modeloPaciente,GestorRecetas modeloRecetas,GestorIndicacion modeloIndicacion) {
        this.modeloAdministrador = modeloAdministrador;
        this.modeloMedico = modeloMedico;
        this.modeloFarmaceuta = modeloFarmaceuta;
        this.modeloPaciente = modeloPaciente;
        this.modeloMedicamento = modeloMedicamento;
        this.modeloRecetas = modeloRecetas;
        this.modeloIndicacion = modeloIndicacion;
        usuarios=new login();
        dashboard=new Dashboard();
        historial=new Historicos();
    }
    public Controlador() {
        this(new GestorAdministrador(),new GestorMedico(),new GestorFarmaceuta(),new GestorMedicamento(),new GestorPaciente(),new GestorRecetas(),new GestorIndicacion());
        usuarios=new login();
        dashboard=new Dashboard();
        historial=new Historicos();
    }
    public int devolverToken(String id, String clave) throws SecurityException{
        token= usuarios.devolverToken(id, clave);
        return token;
    }
    public String devolverId(String id, String clave) throws SecurityException{
        idUsuario=usuarios.devolverId(id, clave);
        return idUsuario;
    }
    public void init() {
        try {
            modeloMedico.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloPaciente.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloFarmaceuta.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloMedicamento.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloRecetas.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
        usuarios.cargarUsuarios(modeloAdministrador.obtenerListaAdministradores());
        usuarios.cargarUsuarios(modeloMedico.obtenerListaMedicos());
        usuarios.cargarUsuarios(modeloFarmaceuta.obtenerListaFarmaceutas());
    }
    public List<Medico> obtenerListaMedicos(){
        return modeloMedico.obtenerListaMedicos();
    }
    public Medico buscarMedicoPorId(String id) {
        return modeloMedico.buscarPorId(id);
    }
    public Medico buscarMedicoPorNombre(String nombre){
        return modeloMedico.buscarPorNombre(nombre);
    }
    public Medico agregarMedico(String id,String nom,String esp) throws IllegalArgumentException, SecurityException{
        Medico medico = new Medico();
        medico.setId(id);
        medico.setNombre(nom);
        medico.setEspecialidad(esp);
        return modeloMedico.agregar(medico,token);
    }
    public Medico actualizarMedico(String id,String nombre,String especialidad) throws IllegalArgumentException, SecurityException{
        Medico medico = buscarMedicoPorId(id);
        if(medico==null){
            throw new IllegalArgumentException("No existe un médico con ID: "+id);
        }
        medico.setNombre(nombre);
        medico.setEspecialidad(especialidad);
        return modeloMedico.actualizar(medico,token);
    }
    public Medico eliminarMedico(String id) throws IllegalArgumentException, SecurityException{
        return modeloMedico.eliminar(id,token);
    }
    public List<Farmaceuta> obtenerListaFarmaceutas(){
        return modeloFarmaceuta.obtenerListaFarmaceutas();
    }
    public Farmaceuta buscarFarmaceutaPorId(String id) {
        return modeloFarmaceuta.buscarPorId(id);
    }
    public Farmaceuta buscarFarmaceutaPorNombre(String nombre) {
        return modeloFarmaceuta.buscarPorNombre(nombre);
    }
    public Farmaceuta agregarFarmaceuta(String id,String nombre)  throws IllegalArgumentException, SecurityException{
        Farmaceuta farmaceuta = new Farmaceuta();
        farmaceuta.setId(id);
        farmaceuta.setNombre(nombre);
        return modeloFarmaceuta.agregar(farmaceuta,token);
    }
    public Farmaceuta actualizarFarmaceuta(String id,String nombre) throws IllegalArgumentException, SecurityException{
        Farmaceuta farmaceuta = buscarFarmaceutaPorId(id);
        if(farmaceuta==null){
            throw new IllegalArgumentException("No existe un farmaceuta con ID: "+id);
        }
        if (nombre.isEmpty())
            throw new IllegalArgumentException("Rellene todos los campos");
        farmaceuta.setNombre(nombre);
        return modeloFarmaceuta.actualizar(farmaceuta,token);
    }
    public Farmaceuta eliminarFarmaceuta(String id) throws IllegalArgumentException, SecurityException{
        return modeloFarmaceuta.eliminar(id,token);
    }
    public List<Paciente> obtenerListaPacientes(){
        return modeloPaciente.obtenerListaPacientes();
    }
    public Paciente buscarPacientePorId(int id){
        return modeloPaciente.buscarPorId(id);
    }
    public Paciente buscarPacientePorNombre(String nombre){
        return modeloPaciente.buscarPorNombre(nombre);
    }
    public Paciente agregarPaciente(int id, String nombre, int telefono,LocalDate fecha) throws IllegalArgumentException, SecurityException{
        Paciente paciente = new Paciente();
        paciente.setId(id);
        paciente.setNombre(nombre);
        paciente.setTelefono(telefono);
        paciente.setFecha_nacimiento(fecha);
        return modeloPaciente.agregar(paciente,token);
    }
    public Paciente actualizarPaciente(int id, String nombre, int telefono,LocalDate fecha) throws IllegalArgumentException, SecurityException{
        Paciente paciente = buscarPacientePorId(id);
        if(paciente==null)
            throw new IllegalArgumentException("No existe un paciente con ID: "+id);
        paciente.setNombre(nombre);
        paciente.setTelefono(telefono);
        paciente.setFecha_nacimiento(fecha);
        return modeloPaciente.actualizar(paciente,token);
    }
    public Paciente eliminarPaciente(int id) throws IllegalArgumentException, SecurityException{
        return modeloPaciente.eliminar(id,token);
    }
    public List<Medicamento> obtenerListaMedicamentos(){
        return modeloMedicamento.obtenerListaMedicamentos();
    }
    public Medicamento buscarMedicamentoPorCodigo(int codigo){
        return modeloMedicamento.buscarPorCodigo(codigo);
    }
    public Medicamento buscarMedicamentoPorDescripcion(String descripcion){
        return modeloMedicamento.buscarPorDescripcion(descripcion);
    }
    public Medicamento agregarMedicamento(int codigo,String nombre,String descripcion) throws IllegalArgumentException, SecurityException{
        Medicamento medicamento = new Medicamento();
        medicamento.setCodigo(codigo);
        medicamento.setNombre(nombre);
        medicamento.setDescripcion(descripcion);
        return modeloMedicamento.agregar(medicamento,token);
    }
    public Medicamento actualizarMedicamento(int codigo,String nombre,String descripcion) throws IllegalArgumentException, SecurityException{
        Medicamento medicamento = buscarMedicamentoPorCodigo(codigo);
        if(medicamento==null)
            throw new IllegalArgumentException("No existe un medicamento con ID: "+codigo);
        if (nombre.isEmpty()||descripcion.isEmpty())
            throw new IllegalArgumentException("Rellene todos los campos");
        medicamento.setNombre(nombre);
        medicamento.setDescripcion(descripcion);
        return modeloMedicamento.actualizar(medicamento,token);
    }
    public Medicamento eliminarMedicamento(int codigo) throws IllegalArgumentException, SecurityException{
        return modeloMedicamento.eliminar(codigo,token);
    }
    public List<Receta> obtenerListaRecetas(){
        return modeloRecetas.obtenerListaRecetas();
    }
    public  Receta buscarRecetaPorCodigo(String codigo){
        return modeloRecetas.buscarRecetaPorCodigo(codigo);
    }
    public Receta buscarReceta(int idPaciente, LocalDate fechaConfeccion){
        return modeloRecetas.buscarReceta(idPaciente, fechaConfeccion);
    }
    public Receta agregarReceta(Receta receta) throws IllegalArgumentException, SecurityException{
        return modeloRecetas.agregar(receta,token);
    }
    public Receta actualizarReceta(Receta receta) throws IllegalArgumentException, SecurityException{
        return modeloRecetas.actualizar(receta,token);
    }
    public Receta eliminarReceta(String codigo) throws IllegalArgumentException, SecurityException{
        return modeloRecetas.eliminar(codigo,token);
    }
    public List<Indicacion> obtenerListaIndicaciones(){
        return modeloIndicacion.obtenerListaIndicaciones();
    }
    public Indicacion buscarIndicacion(int codigo){
        return modeloIndicacion.buscarIndicacion(codigo);
    }
    public Indicacion agregarIndicacion(Indicacion indicacion) throws IllegalArgumentException, SecurityException{
        return modeloIndicacion.agregar(indicacion,token);
    }
    public Indicacion actualizarIndicacion(Indicacion indicacion)throws IllegalArgumentException, SecurityException{
        return modeloIndicacion.actualizar(indicacion,token);
    }
    public Indicacion eliminarIndicacion(int codigo) throws IllegalArgumentException, SecurityException{
        return modeloIndicacion.eliminar(codigo,token);
    }
    public void iniciarProceso(Receta receta) throws IllegalArgumentException{
        modeloRecetas.iniciarProceso(receta,token,idUsuario);
    }
    public void marcarLista(Receta receta) throws IllegalArgumentException{
        modeloRecetas.marcarLista(receta,token,idUsuario);
    }
    public void entregar(Receta receta) throws IllegalArgumentException{
        modeloRecetas.entregar(receta,token,idUsuario);
    }
    public DateTimeFormatter getFormatoFecha() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
    public void cerrarAplicacion() {
        try {
            modeloMedico.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloPaciente.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloFarmaceuta.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloMedicamento.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }
        try {
            modeloRecetas.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }
        if(usuarios.cantidad()!=0)
            usuarios.limpiar();
        if(dashboard.cantidad()!=0)
            dashboard.limpiar();
        System.out.println("Aplicación finalizada..");
        System.exit(0);
    }
    public void limpiarDashboard(){
        dashboard.limpiar();
    }
    public void agregarMedicamentoDashboard(String nombre){
        dashboard.agregarMedicameno(modeloMedicamento.buscarPorNombre(nombre));
    }
    public Map<YearMonth, Integer> DashboardMedicamentosPorMes(LocalDate startDate, LocalDate endDate){
        return dashboard.medicamentosPorMes(modeloRecetas.obtenerListaRecetas(), startDate, endDate);
    }
    public Map<String, Long> DashboardRecetasPorEstado(){
        return dashboard.recetasPorEstado(modeloRecetas.obtenerListaRecetas());
    }
    public Receta buscarRecetaHistorial(String codigo){
        return historial.buscarPorCodigo(buscarRecetaPorCodigo(codigo));
    }
    public Receta buscarRecetaHistorial(int idPaciente, LocalDate fechaConfeccion){
        return historial.buscarPorPacienteFecha(buscarReceta(idPaciente, fechaConfeccion));
    }
    public List<Indicacion>mostrarIndicaciones(){
        return historial.mostrarIndicaciones();
    }
    private GestorAdministrador modeloAdministrador;
    private GestorMedico modeloMedico;
    private GestorFarmaceuta modeloFarmaceuta;
    private GestorPaciente modeloPaciente;
    private GestorMedicamento modeloMedicamento;
    private GestorRecetas modeloRecetas;
    private GestorIndicacion modeloIndicacion;
    private login usuarios;
    @Setter
    private int token;
    private String idUsuario;
    private Dashboard  dashboard;
    private Historicos historial;
}
