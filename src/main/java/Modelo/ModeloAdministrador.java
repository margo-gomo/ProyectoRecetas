package Modelo;
import DAO.AdministradorDAO;
import DAO.AdministradorDAOImpl;
import Gestores.GestorAdministrador;
import entidades.Administrador;
import jakarta.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
public class ModeloAdministrador {
    public ModeloAdministrador() {
        dao=new AdministradorDAOImpl();
        datos=new GestorAdministrador(dao);
    }
    public int cantidad() {
        return datos.cantidad();
    }

    public List<Administrador> obtenerListaAdministradores(){
        return datos.obtenerListaAdministradores();
    }

    public Administrador buscarPorId(String id) {
        return datos.buscarPorId(id);
    }

    public Administrador buscarPorNombre(String nombre) {
        return  datos.buscarPorNombre(nombre);
    }

    public Administrador agregar(Administrador administrador) throws IllegalArgumentException {
        return datos.agregar(administrador);
    }

    public Administrador actualizar(Administrador administrador) throws IllegalArgumentException {
        return datos.actualizar(administrador);
    }
    public Administrador eliminar(String id) throws IllegalArgumentException {
        return  datos.eliminar(id);
    }
    public Administrador cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar){
        return  datos.cambiarClave(id, claveActual, claveNueva, claveConfirmar);
    }
    public GestorAdministrador obtenerModelo(){
        return datos;
    }
    public void cargar()throws FileNotFoundException, JAXBException{
        AdministradorDAOImpl impl=(AdministradorDAOImpl)dao;
        impl.cargar(new FileInputStream(ARCHIVO_DATOS));
        System.out.println("Datos cargados correctamente.");
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        AdministradorDAOImpl impl=(AdministradorDAOImpl)dao;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS="datos/administradores.xml";
    private final AdministradorDAO dao;
    private final GestorAdministrador datos;
}
