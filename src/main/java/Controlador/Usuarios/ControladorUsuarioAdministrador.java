package Controlador.Usuarios;

import Modelo.*;
import Prescripcion.PrescripcionReceta;
import entidades.*;
import Estadísticas.Dashboard;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import jakarta.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class ControladorUsuarioAdministrador{
    public ControladorUsuarioAdministrador(ModeloMedico modeloMedico, ModeloPaciente modeloPaciente, ModeloFarmaceuta modeloFarmaceuta, ModeloMedicamento modeloMedicamento,ModeloRecetas modeloRecetas) {
        this.modeloMedico = modeloMedico;
        this.modeloPaciente = modeloPaciente;
        this.modeloFarmaceuta = modeloFarmaceuta;
        this.modeloMedicamento = modeloMedicamento;
        this.modeloRecetas = modeloRecetas;
    }
    public ControladorUsuarioAdministrador() {
        this(new ModeloMedico(),new ModeloPaciente(),new ModeloFarmaceuta(),new ModeloMedicamento(),new ModeloRecetas());
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
    }

    public Medico buscarMedicoPorId(String id){
        return modeloMedico.buscarPorId(id);
    }
    public Medico buscarMedicoPorNombre(String nombre){
        return modeloMedico.buscarPorNombre(nombre);
    }
    public Medico agregarMedico(Medico medico) throws IllegalArgumentException{
        return modeloMedico.agregar(medico);
    }
    public Medico actualizarMedico(Medico medico) throws IllegalArgumentException{
        return modeloMedico.actualizar(medico);
    }
    public Medico eliminarMedico(String id) throws IllegalArgumentException{
        return modeloMedico.eliminar(id);
    }
    public List<Medico> obtenerListaMedicos(){
        return modeloMedico.obtenerListaMedico();
    }

    public Farmaceuta buscarFarmaceutaPorId(String id){
        return modeloFarmaceuta.buscarPorId(id);
    }
    public Farmaceuta buscarFarmaceutaPorNombre(String nombre){
        return modeloFarmaceuta.buscarPorNombre(nombre);
    }
    public Farmaceuta agregarFarmaceuta(Farmaceuta medico) throws IllegalArgumentException{
        return modeloFarmaceuta.agregar(medico);
    }
    public Farmaceuta actualizarFarmaceuta(Farmaceuta medico) throws IllegalArgumentException{
        return modeloFarmaceuta.actualizar(medico);
    }
    public Farmaceuta eliminarFarmaceuta(String id) throws IllegalArgumentException{
        return modeloFarmaceuta.eliminar(id);
    }

    public Paciente buscarPorId(int id) {
        return modeloPaciente.buscarPorId(id);
    }

    public Paciente buscarPorNombre(String nombre) {
        return  modeloPaciente.buscarPorNombre(nombre);
    }

    public Paciente agregar(Paciente paciente) throws IllegalArgumentException {
        return modeloPaciente.agregar(paciente);
    }

    public Paciente actualizar(Paciente paciente) throws IllegalArgumentException {
        return modeloPaciente.actualizar(paciente);
    }

    public Paciente eliminar(int id) throws IllegalArgumentException {
        return  modeloPaciente.eliminar(id);
    }

    public PrescripcionReceta buscarReceta(int idPaciente, LocalDate fechaConfeccion) {
        return modeloRecetas.buscarReceta(idPaciente, fechaConfeccion);
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
        System.out.println("Aplicación finalizada..");
        System.exit(0);
    }
    public Map<YearMonth,Integer> medicamentoPorMes(List<Medicamento> medicamentosSeleccionados,
                                                LocalDate startDate, LocalDate endDate){
        Dashboard dashboard = new Dashboard();
        return dashboard.medicamentosPorMes(modeloRecetas.obtenerListaRecetas(),medicamentosSeleccionados,startDate,endDate);
    }
    public Map<String, Long>recetasPorEstado(){
        Dashboard dashboard = new Dashboard();
        return dashboard.recetasPorEstado(modeloRecetas.obtenerListaRecetas());
    }
    private ModeloMedico modeloMedico;
    private ModeloPaciente modeloPaciente;
    private ModeloFarmaceuta modeloFarmaceuta;
    private ModeloMedicamento modeloMedicamento;
    private ModeloRecetas modeloRecetas;
}