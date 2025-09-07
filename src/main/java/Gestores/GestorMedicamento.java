package Gestores;

import DAO.MedicamentoDAO;
import entidades.Medicamento;

import java.util.List;

public class GestorMedicamento {

    public GestorMedicamento(MedicamentoDAO medicamentos) {
        // Lista en memoria para los médicos
        this.medicamentos=medicamentos;
    }

    public int cantidad() {
        return medicamentos.cantidad();
    }

    public List<Medicamento> obtenerListaMedicamentos(){
        return medicamentos.obtenerListaMedicamentos();
    }

    public boolean existeMedicamento(int codigo) {
        return medicamentos.buscarPorCodigo(codigo) != null;
    }

    public Medicamento buscarPorCodigo(int codigo) {
            return medicamentos.buscarPorCodigo(codigo);
    }

    public Medicamento buscarPorDescripcion(String descripcion) {
        return  medicamentos.buscarPorDescripcion(descripcion);
    }

    public Medicamento agregar(Medicamento medicamento) throws IllegalArgumentException {
        return medicamentos.agregar(medicamento);
    }

    public Medicamento actualizar(Medicamento medicamento) throws IllegalArgumentException {
        return medicamentos.actualizar(medicamento);
    }

    public Medicamento eliminar(int codigo) throws IllegalArgumentException {
        return  medicamentos.eliminar(codigo);
    }

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
