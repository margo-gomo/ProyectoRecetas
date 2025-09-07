package DAO;
import entidades.Farmaceuta;
import java.util.List;
public interface FarmaceutaDAO {
    public int cantidad();
    public List<Farmaceuta> obtenerListaFarmaceutas();
    public Farmaceuta buscarPorId(String id);
    public Farmaceuta buscarPorNombre(String nombre);
    public Farmaceuta agregar(Farmaceuta farmaceuta) throws IllegalArgumentException;
    public Farmaceuta actualizar(Farmaceuta farmaceuta) throws IllegalArgumentException;
    public Farmaceuta eliminar(String id) throws IllegalArgumentException;
    public Farmaceuta cambiarClave(String id,String clave) throws IllegalArgumentException;
}
