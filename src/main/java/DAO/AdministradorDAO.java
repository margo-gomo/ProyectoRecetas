package DAO;

import entidades.Administrador;

import java.util.List;

public interface AdministradorDAO {
    public int cantidad();
    public List<Administrador> obtenerListaAdministradores();
    public Administrador buscarPorId(String id);
    public Administrador buscarPorNombre(String nombre);
    public Administrador agregar(Administrador administrador) throws IllegalArgumentException;
    public Administrador actualizar(Administrador administrador) throws IllegalArgumentException;
    public Administrador eliminar(String id) throws IllegalArgumentException;
    public Administrador cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar) throws IllegalArgumentException;
}
