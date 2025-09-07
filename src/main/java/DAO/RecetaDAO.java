package DAO;

import Prescripcion.PrescripcionReceta;

import java.time.LocalDate;
import java.util.List;

public interface RecetaDAO {
    public int cantidad();
    public List<PrescripcionReceta> obtenerListaPrescripcionRecetas();
    public PrescripcionReceta buscarReceta(int idPaciente, LocalDate fecha_confeccion);
    public PrescripcionReceta agregar(PrescripcionReceta receta) throws IllegalArgumentException;
    public PrescripcionReceta actualizar(PrescripcionReceta receta) throws IllegalArgumentException;
    public PrescripcionReceta eliminar(int idPaciente, LocalDate fecha_confeccion) throws IllegalArgumentException;;
}
