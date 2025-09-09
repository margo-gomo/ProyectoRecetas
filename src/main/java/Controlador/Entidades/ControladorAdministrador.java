package Controlador.Entidades;
import entidades.Administrador;
import Modelo.ModeloAdministrador;
import jakarta.xml.bind.JAXBException;
import java.io.FileNotFoundException;
public class ControladorAdministrador {
    public ControladorAdministrador(ModeloAdministrador modelo) {
        this.modelo = modelo;
    }
    public ControladorAdministrador(){
        this(new ModeloAdministrador());
    }
    public void init() {
        try {
            modelo.cargar();
        } catch (JAXBException | FileNotFoundException ex) {
            System.err.printf("Ocurrió un error al cargar los datos: '%s'%n",
                    ex.getMessage());
        }
    }
    public Administrador buscarPorId(String id) {
        return modelo.buscarPorId(id);
    }

    public Administrador buscarPorNombre(String nombre) {
        return  modelo.buscarPorNombre(nombre);
    }

    public Administrador agregar(Administrador administrador) throws IllegalArgumentException {
        return modelo.agregar(administrador);
    }

    public Administrador actualizar(Administrador administrador) throws IllegalArgumentException {
        return modelo.actualizar(administrador);
    }
    public Administrador eliminar(String id) throws IllegalArgumentException {
        return  modelo.eliminar(id);
    }
    public Administrador cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar){
        return  modelo.cambiarClave(id, claveActual, claveNueva, claveConfirmar);
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
    private ModeloAdministrador modelo;
}
