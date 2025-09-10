package Modelo.Gestores;
import Modelo.DAO.AdministradorDAO;
import Modelo.entidades.Administrador;

import java.util.List;

//admin
public class GestorAdministrador {

    public GestorAdministrador(AdministradorDAO administradores) {
        // Lista en memoria para los médicos
        this.administradores=administradores;
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

    public Administrador actualizar(Administrador administrador) throws IllegalArgumentException {
        return administradores.actualizar(administrador);
    }
    public Administrador eliminar(String id) throws IllegalArgumentException {
        return  administradores.eliminar(id);
    }
    public Administrador cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar){
        return  administradores.cambiarClave(id, claveActual, claveNueva, claveConfirmar);
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

