package Gestores;

import DAO.MedicamentoDAO;
import entidades.Medicamento;

import java.util.List;

public class GestorMedicamento {

    public GestorMedicamento(MedicamentoDAO medicamentos) {
        // Lista en memoria para los médicos
        this.medicamentos=medicamentos;
    }

    // Cantidad total de médicos
    public int cantidadMedicamentos() {
        return medicamentos.cantidad();
    }

    public List<Medicamento> ObtenerListaMedicamentos(){
        return medicamentos.obtenerListaMedicamentos();
    }

    // Verifica si existe un médico con el ID indicado
    public boolean existeMedicamento(int codigo) {
        return medicamentos.buscarPorCodigo(codigo) != null;
    }

    // Busca un médico por ID exacto (o null si no existe)
    public Medicamento buscarPorID(int codigo) {
            return medicamentos.buscarPorCodigo(codigo);
    }

    // Busca por nombre con coincidencia aproximada (ignorando mayúsculas/minúsculas)
    public Medicamento buscarPorDescripcion(String descripcion) {
        return  medicamentos.buscarPorDescripcion(descripcion);
    }

    // Agrega un médico si el ID no está repetido
    public Medicamento agregar(Medicamento medicamento) throws IllegalArgumentException { // (antes: aregarMedicamento)
        return medicamentos.agregar(medicamento);
    }

    // Reemplaza los datos de un médico existente (match por ID)
    public Medicamento actualizar(Medicamento medicamento) throws IllegalArgumentException { // (antes: pacienteporactualizar)
        return medicamentos.actualizar(medicamento);
    }
    // Elimina un médico por ID
    public Medicamento eliminar(int codigo) throws IllegalArgumentException {
        return  medicamentos.eliminar(codigo);
    }

    // String legible de la lista (debug/impresión)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Medicamento medicamento : medicamentos.obtenerListaMedicamentos()) {
            sb.append(String.format("%n\t%s,", medicamento));
        }
        sb.append("\n]");
        return sb.toString();
    }

    private final MedicamentoDAO medicamentos;
}
