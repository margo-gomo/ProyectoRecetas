package Modelo.Gestores;
import Modelo.DAO.AdministradorDAO;
import Modelo.DAO.AdministradorDAOImpl;
import Modelo.entidades.Administrador;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

//admin
public class GestorAdministrador {

    public GestorAdministrador() {
        // Lista en memoria para los médicos
        administradores=new AdministradorDAOImpl();
    }

    public int cantidad() {
        return administradores.cantidad();
    }

    public List<Administrador> obtenerListaAdministradores(){
        return administradores.obtenerListaAdministradores();
    }

    public boolean existeAdministrador(String id) {
        return administradores.buscarPorId(id) != null;
    }

    public Administrador buscarPorId(String id) {
        return administradores.buscarPorId(id);
    }

    public Administrador buscarPorNombre(String nombre) {
        return  administradores.buscarPorNombre(nombre);
    }

    public Administrador agregar(Administrador administrador) throws IllegalArgumentException {
        return administradores.agregar(administrador);
    }
    public void cargar()throws FileNotFoundException, JAXBException {
        File f=new File(ARCHIVO_DATOS);
        if(!f.exists()||f.length()==0)
            System.out.println("No hay datos previos para cargar.");
        else{
            AdministradorDAOImpl impl = (AdministradorDAOImpl) administradores;
            impl.cargar(new FileInputStream(ARCHIVO_DATOS));
            System.out.println("Datos cargados correctamente.");
        }
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        AdministradorDAOImpl impl = (AdministradorDAOImpl) administradores;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    // String legible de la lista (debug/impresión)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Administrador administrador : administradores.obtenerListaAdministradores()) {
            sb.append(String.format("%n\t%s,", administrador));
        }
        sb.append("\n]");
        return sb.toString();
    }
    private static final String ARCHIVO_DATOS="datos/administradores.xml";
    private final AdministradorDAO administradores;
}

