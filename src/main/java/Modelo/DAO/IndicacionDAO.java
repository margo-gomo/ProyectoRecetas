package Modelo.DAO;
import Modelo.entidades.Receta.Indicacion;
import java.util.List;
public interface IndicacionDAO {
    public int cantidad();
    public List<Indicacion> obtenerListaIndicaciones();
    public Indicacion buscarIndicacion(int codigo);
    public Indicacion agregar(Indicacion indicacion) throws IllegalArgumentException;
    public Indicacion actualizar(Indicacion indicacion)throws IllegalArgumentException;
    public Indicacion eliminar(int codigo)throws IllegalArgumentException;
}
