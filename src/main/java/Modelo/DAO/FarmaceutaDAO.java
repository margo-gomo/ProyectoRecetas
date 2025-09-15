package Modelo.DAO;

import Modelo.entidades.Farmaceuta;

import java.util.List;
public interface FarmaceutaDAO {
    public int cantidad();
    public List<Farmaceuta> obtenerListaFarmaceutas();
    public Farmaceuta buscarPorId(String id);
    public Farmaceuta buscarPorNombre(String nombre);
    public Farmaceuta agregar(Farmaceuta farmaceuta) throws IllegalArgumentException;
    public Farmaceuta actualizar(Farmaceuta farmaceuta) throws IllegalArgumentException;
    public Farmaceuta eliminar(String id) throws IllegalArgumentException;
}
