package Controlador;
import Modelo.entidades.*;
import Modelo.Gestores.*;
import Modelo.entidades.Receta.Receta;
import Modelo.login;
import jakarta.xml.bind.JAXBException;
import java.io.FileNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controlador {
    public Controlador(GestorAdministrador modeloAdministrador,GestorMedico modeloMedico, GestorFarmaceuta modeloFarmaceuta,
                       GestorMedicamento modeloMedicamento,GestorPaciente modeloPaciente,GestorRecetas modeloRecetas) {
        this.modeloAdministrador = modeloAdministrador;
        this.modeloMedico = modeloMedico;
        this.modeloFarmaceuta = modeloFarmaceuta;
        this.modeloPaciente = modeloPaciente;
        this.modeloMedicamento = modeloMedicamento;
        this.modeloRecetas = modeloRecetas;
        usuarios.limpiar();
        usuarios.cargarUsuarios(this.modeloAdministrador.obtenerListaAdministradores());
        usuarios.cargarUsuarios(this.modeloMedico.obtenerListaMedicos());
        usuarios.cargarUsuarios(this.modeloFarmaceuta.obtenerListaFarmaceutas());
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
        usuarios.limpiar();
        usuarios.cargarUsuarios(modeloAdministrador.obtenerListaAdministradores());
        usuarios.cargarUsuarios(modeloMedico.obtenerListaMedicos());
        usuarios.cargarUsuarios(modeloFarmaceuta.obtenerListaFarmaceutas());
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
    public Medicamento buscarMedicamentoPorCodigo(int codigo){
        return modeloMedicamento.buscarPorCodigo(codigo);
    }
    public Medicamento buscarMedicamentoPorDescripcion(String descripcion){
        return modeloMedicamento.buscarPorDescripcion(descripcion);
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
    public Receta eliminarReceta(int idPaciente, LocalDate fechaConfeccion) throws IllegalArgumentException, SecurityException{
        return modeloRecetas.eliminar(idPaciente, fechaConfeccion,token);
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
    private GestorAdministrador modeloAdministrador;
    private GestorMedico modeloMedico;
    private GestorFarmaceuta modeloFarmaceuta;
    private GestorPaciente modeloPaciente;
    private GestorMedicamento modeloMedicamento;
    private GestorRecetas modeloRecetas;
    private login usuarios;
    private int token;
}
