package Gestores;
import DAO.AdministradorDAO;
import entidades.Administrador;

import java.util.List;

//admin
public class GestorAdministrador {

    public GestorAdministrador(AdministradorDAO administradores) {
        // Lista en memoria para los médicos
        this.administradores=administradores;
    }

    // Cantidad total de médicos
    public int cantidadAdministradors() {
        return administradores.cantidad();
    }

    public List<Administrador> ObtenerListaAdministradores(){
        return administradores.obtenerListaAdministradores();
    }

    // Verifica si existe un médico con el ID indicado
    public boolean existeAdministrador(String id) {
        return administradores.buscarPorId(id) != null;
    }

    // Busca un médico por ID exacto (o null si no existe)
    public Administrador buscarPorID(String id) {
        return administradores.buscarPorId(id);
    }

    // Busca por nombre con coincidencia aproximada (ignorando mayúsculas/minúsculas)
    public Administrador buscarPorNombre(String nombre) {
        return  administradores.buscarPorNombre(nombre);
    }

    // Agrega un médico si el ID no está repetido
    public Administrador agregar(Administrador administrador) throws IllegalArgumentException { // (antes: aregarAdministrador)
        return administradores.agregar(administrador);
    }

    // Reemplaza los datos de un médico existente (match por ID)
    public Administrador actualizar(Administrador administrador) throws IllegalArgumentException { // (antes: pacienteporactualizar)
        return administradores.actualizar(administrador);
    }
    // Elimina un médico por ID
    public Administrador eliminar(String id) throws IllegalArgumentException {
        return  administradores.eliminar(id);
    }
    // Cambia la clave de un administrador existente (match por ID)
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

