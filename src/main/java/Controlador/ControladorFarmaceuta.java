package Controlador;
import entidades.Farmaceuta;
import Modelo.ModeloFarmaceuta;
import jakarta.xml.bind.JAXBException;
import java.io.FileNotFoundException;


public class ControladorFarmaceuta {
    public ControladorFarmaceuta(ModeloFarmaceuta modelo){
        this.modelo=modelo;
    }
    public ControladorFarmaceuta(){
        this(new ModeloFarmaceuta());
    }
    public void init() {
        try {
            modelo.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
    }
    public Farmaceuta buscarPorId(String id){
        return modelo.buscarPorId(id);
    }
    public Farmaceuta buscarPorNombre(String nombre){
        return modelo.buscarPorNombre(nombre);
    }
    public Farmaceuta agregar(Farmaceuta medico) throws IllegalArgumentException{
        return modelo.agregar(medico);
    }
    public Farmaceuta actualizar(Farmaceuta medico) throws IllegalArgumentException{
        return modelo.actualizar(medico);
    }
    public Farmaceuta eliminar(String id) throws IllegalArgumentException{
        return modelo.eliminar(id);
    }
    public Farmaceuta cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar)throws IllegalArgumentException{
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
    private ModeloFarmaceuta modelo;
}