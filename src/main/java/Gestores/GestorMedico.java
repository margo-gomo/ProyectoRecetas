package Gestores;

import DAO.MedicoDAO;
import entidades.Medico;

import java.util.List;

public class GestorMedico {

    public GestorMedico(MedicoDAO medicos) {
        // Lista en memoria para los médicos
        this.medicos=medicos;
    }

    // Cantidad total de médicos
    public int cantidadMedicos() {
        return medicos.cantidad();
    }

    public List<Medico> ObtenerListaMedicos(){
        return medicos.obtenerListaMedicos();
    }

    // Verifica si existe un médico con el ID indicado
    public boolean existeMedico(String id) {
        return medicos.buscarPorId(id) != null;
    }

    // Busca un médico por ID exacto (o null si no existe)
    public Medico buscarPorID(String id) {
        return medicos.buscarPorId(id);
    }

    // Busca por nombre con coincidencia aproximada (ignorando mayúsculas/minúsculas)
    public Medico buscarPorNombre(String nombre) {
        return  medicos.buscarPorNombre(nombre);
    }

    // Agrega un médico si el ID no está repetido
    public Medico agregar(Medico medico) throws IllegalArgumentException { // (antes: aregarMedico)
        return medicos.agregar(medico);
    }

    // Reemplaza los datos de un médico existente (match por ID)
    public Medico actualizar(Medico medico) throws IllegalArgumentException { // (antes: pacienteporactualizar)
        return medicos.actualizar(medico);
    }
    // Elimina un médico por ID
    public Medico eliminar(String id) throws IllegalArgumentException {
        return  medicos.eliminar(id);
    }
    // Cambia la clave de un medico existente (match por ID)
    public Medico cambiarClave(String id, String clave){
        return  medicos.cambiarClave(id,clave);
    }

    // String legible de la lista (debug/impresión)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Medico medico : medicos.obtenerListaMedicos()) {
            sb.append(String.format("%n\t%s,", medico));
        }
        sb.append("\n]");
        return sb.toString();
    }

    private final MedicoDAO medicos;
}
