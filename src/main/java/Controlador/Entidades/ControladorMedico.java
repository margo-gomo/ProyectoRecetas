package Controlador.Entidades;

import Modelo.ModeloMedico;
import entidades.Medico;
import jakarta.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class ControladorMedico {
    public ControladorMedico(ModeloMedico modelo){
        this.modelo=modelo;
    }
    public ControladorMedico(){
        this(new ModeloMedico());
    }
    public void init() {
        try {
            modelo.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
    }
    public Medico buscarPorId(String id){
        return modelo.buscarPorId(id);
    }
    public Medico buscarPorNombre(String nombre){
        return modelo.buscarPorNombre(nombre);
    }
    public Medico agregar(Medico medico) throws IllegalArgumentException{
        return modelo.agregar(medico);
    }
    public Medico actualizar(Medico medico) throws IllegalArgumentException{
        return modelo.actualizar(medico);
    }
    public Medico eliminar(String id) throws IllegalArgumentException{
        return modelo.eliminar(id);
    }
    public Medico cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar)throws IllegalArgumentException{
        return modelo.cambiarClave(id,claveActual,claveNueva,claveConfirmar);
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
    private ModeloMedico modelo;
}

