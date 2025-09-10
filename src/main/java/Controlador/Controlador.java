package Controlador;
import Modelo.entidades.*;
import Modelo.Gestores.*;
import Modelo.DAO.*;
import Modelo.Estadísticas.Dashboard;
import Modelo.entidades.Receta.Receta;
import Modelo.login;
import jakarta.xml.bind.JAXBException;
import lombok.Setter;

import javax.management.modelmbean.ModelMBean;
import java.awt.*;
import java.io.FileNotFoundException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class Controlador {
    public Controlador(GestorAdministrador modeloAdministrador,GestorMedico modeloMedico, GestorFarmaceuta modeloFarmaceuta,
                       GestorMedicamento modeloMedicamento,GestorPaciente modeloPaciente,GestorRecetas modeloRecetas) {
        this.modeloAdministrador = modeloAdministrador;
        this.modeloMedico = modeloMedico;
        this.modeloFarmaceuta = modeloFarmaceuta;
        this.modeloPaciente = modeloPaciente;
        this.modeloMedicamento = modeloMedicamento;
        this.modeloRecetas = modeloRecetas;
        usuarios=new login();
        dashboard=new Dashboard();
    }
    public Controlador() {
        this(new GestorAdministrador(),new GestorMedico(),new GestorFarmaceuta(),new GestorMedicamento(),new GestorPaciente(),new GestorRecetas());
    }
    public int devolverToken(String id, String clave) throws SecurityException{
        token= usuarios.devolverToken(id, clave);
        return token;
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
    public Medico agregarMedico(Medico medico) throws IllegalArgumentException, SecurityException{
        return modeloMedico.agregar(medico,token);
    }
    public Medico actualizarMedico(Medico medico) throws IllegalArgumentException, SecurityException{
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
    public Farmaceuta agregarFarmaceuta(Farmaceuta farmaceuta)  throws IllegalArgumentException, SecurityException{
        return modeloFarmaceuta.agregar(farmaceuta,token);
    }
    public Farmaceuta actualizarFarmaceuta(Farmaceuta farmaceuta) throws IllegalArgumentException, SecurityException{
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
    public Paciente agregarPaciente(Paciente paciente) throws IllegalArgumentException, SecurityException{
        return modeloPaciente.agregar(paciente,token);
    }
    public Paciente actualizarPaciente(Paciente paciente) throws IllegalArgumentException, SecurityException{
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
    public List<Receta> obtenerListaRecetas(){
        return modeloRecetas.obtenerListaRecetas();
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
    public Map<YearMonth, Integer> medicamentosPorMes(LocalDate startDate, LocalDate endDate){
        return dashboard.medicamentosPorMes(modeloRecetas.obtenerListaRecetas(), startDate, endDate);
    }
    public Map<String, Long> recetasPorEstado(){
        return dashboard.recetasPorEstado(modeloRecetas.obtenerListaRecetas());
    }
    private GestorAdministrador modeloAdministrador;
    private GestorMedico modeloMedico;
    private GestorFarmaceuta modeloFarmaceuta;
    private GestorPaciente modeloPaciente;
    private GestorMedicamento modeloMedicamento;
    private GestorRecetas modeloRecetas;
    private login usuarios;
    @Setter
    private int token;
    private Dashboard  dashboard;
}
