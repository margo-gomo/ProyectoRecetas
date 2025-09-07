package DAO;

import entidades.Medicamento;

import java.util.List;

public interface MedicamentoDAO {
    public int cantidad();
    public List<Medicamento> obtenerListaMedicamentos();
    public Medicamento buscarPorCodigo(int codigo);
    public Medicamento buscarPorDescripcion(String descripcion);
    public Medicamento agregar(Medicamento medicamento) throws IllegalArgumentException;
    public Medicamento actualizar(Medicamento medicamento) throws IllegalArgumentException;
    public Medicamento eliminar(int codigo) throws IllegalArgumentException;
}
