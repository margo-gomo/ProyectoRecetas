package Modelo.DAO;

import Modelo.entidades.Medico;

import java.util.List;
public interface MedicoDAO {
    public int cantidad();
    public List<Medico> obtenerListaMedicos();
    public Medico buscarPorId(String id);
    public Medico buscarPorNombre(String nombre);
    public Medico agregar(Medico medico) throws IllegalArgumentException;
    public Medico actualizar(Medico medico) throws IllegalArgumentException;
    public Medico eliminar(String id) throws IllegalArgumentException;
    public Medico cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar) throws IllegalArgumentException;
}
