package Gestores;

import entidades.Paciente;
import DAO.PacienteDAO;
import java.util.List;

public class GestorPaciente { 
    public GestorPaciente(PacienteDAO pacientes) {
        // Lista en memoria para los médicos
        this.pacientes=pacientes;
    }

    // Cantidad total de médicos
    public int cantidadPacientes() {
        return pacientes.cantidad();
    }

    public List<Paciente> ObtenerListaPacientes(){
        return pacientes.obtenerListaPacientes();
    }

    // Verifica si existe un médico con el ID indicado
    public boolean existePaciente(int id) {
        return pacientes.buscarPorId(id) != null;
    }

    // Busca un médico por ID exacto (o null si no existe)
    public Paciente buscarPorId(int id) {
        return pacientes.buscarPorId(id);
    }

    // Busca por nombre con coincidencia aproximada (ignorando mayúsculas/minúsculas)
    public Paciente buscarPorNombre(String nombre) {
        return  pacientes.buscarPorNombre(nombre);
    }

    // Agrega un médico si el ID no está repetido
    public Paciente agregar(Paciente paciente) throws IllegalArgumentException { // (antes: aregarPaciente)
        return pacientes.agregar(paciente);
    }

    // Reemplaza los datos de un médico existente (match por ID)
    public Paciente actualizar(Paciente paciente) throws IllegalArgumentException { // (antes: pacienteporactualizar)
        return pacientes.actualizar(paciente);
    }
    // Elimina un médico por ID
    public Paciente eliminar(int id) throws IllegalArgumentException {
        return  pacientes.eliminar(id);
    }

    // String legible de la lista (debug/impresión)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Paciente paciente : pacientes.obtenerListaPacientes()) {
            sb.append(String.format("%n\t%s,", paciente));
        }
        sb.append("\n]");
        return sb.toString();
    }

    private final PacienteDAO pacientes;
}
