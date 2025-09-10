package Modelo.Gestores;
import Modelo.DAO.AdministradorDAO;
import Modelo.DAO.AdministradorDAOImpl;
import Modelo.entidades.Administrador;

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

    private final AdministradorDAO administradores;
}

