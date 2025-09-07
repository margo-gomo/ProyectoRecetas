package DAO;

import entidades.Paciente;

import java.util.List;

public interface PacienteDAO {
    public int cantidad();
    public List<Paciente> obtenerListaPacientes();
    public Paciente buscarPorId(int id);
    public Paciente buscarPorNombre(String nombre);
    public Paciente agregar(Paciente paciente) throws IllegalArgumentException;
    public Paciente actualizar(Paciente paciente) throws IllegalArgumentException;
    public Paciente eliminar(int id) throws IllegalArgumentException;
}
