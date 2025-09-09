package Controlador.Entidades;
import entidades.Paciente;
import Modelo.ModeloPaciente;
import jakarta.xml.bind.JAXBException;
import java.io.FileNotFoundException;


public class ControladorPaciente {
    public ControladorPaciente(ModeloPaciente modelo){
        this.modelo = modelo;
    }

    public ControladorPaciente(){
        this(new ModeloPaciente());
    }

    public void init() {
        try {
            modelo.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
    }
    public Paciente buscarPorId(int id) {
        return modelo.buscarPorId(id);
    }

    public Paciente buscarPorNombre(String nombre) {
        return  modelo.buscarPorNombre(nombre);
    }

    public Paciente agregar(Paciente paciente) throws IllegalArgumentException {
        return modelo.agregar(paciente);
    }

    public Paciente actualizar(Paciente paciente) throws IllegalArgumentException {
        return modelo.actualizar(paciente);
    }

    public Paciente eliminar(int id) throws IllegalArgumentException {
        return  modelo.eliminar(id);
    }
    public void cerrarAplicacion() {
        try {
            modelo.guardar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al guardar los datos: '%s'%n",
                    ex.getMessage());
        }

        System.out.println("Aplicación finalizada..");
        System.exit(0);
    }
    private ModeloPaciente modelo;
}
