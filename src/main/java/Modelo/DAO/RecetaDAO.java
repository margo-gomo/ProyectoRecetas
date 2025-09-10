package Modelo.DAO;

import Modelo.entidades.Receta.Receta;

import java.time.LocalDate;
import java.util.List;

public interface RecetaDAO {
    public int cantidad();
    public List<Receta> obtenerListaRecetas();
    public Receta buscarReceta(int idPaciente, LocalDate fecha_confeccion);
    public Receta buscarRecetaPorCodigo(String codigo);
    public Receta agregar(Receta receta) throws IllegalArgumentException;
    public Receta actualizar(Receta receta) throws IllegalArgumentException;
    public Receta eliminar(String codigo) throws IllegalArgumentException;
}
