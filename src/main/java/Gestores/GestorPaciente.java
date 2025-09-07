package Gestores;

import entidades.Paciente;
import DAO.PacienteDAO;
import java.util.List;

public class GestorPaciente { 
    public GestorPaciente(PacienteDAO pacientes) {
        // Lista en memoria para los médicos
        this.pacientes=pacientes;
    }

    public int cantidad() {
        return pacientes.cantidad();
    }

    public List<Paciente> obtenerListaPacientes(){
        return pacientes.obtenerListaPacientes();
    }

    public boolean existePaciente(int id) {
        return pacientes.buscarPorId(id) != null;
    }

    public Paciente buscarPorId(int id) {
        return pacientes.buscarPorId(id);
    }

    public Paciente buscarPorNombre(String nombre) {
        return  pacientes.buscarPorNombre(nombre);
    }

    public Paciente agregar(Paciente paciente) throws IllegalArgumentException { // (antes: aregarPaciente)
        return pacientes.agregar(paciente);
    }

    public Paciente actualizar(Paciente paciente) throws IllegalArgumentException { // (antes: pacienteporactualizar)
        return pacientes.actualizar(paciente);
    }
    public Paciente eliminar(int id) throws IllegalArgumentException {
        return  pacientes.eliminar(id);
    }

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
