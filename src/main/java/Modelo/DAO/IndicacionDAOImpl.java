package Modelo.DAO;

import Modelo.entidades.Receta.Indicacion;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class IndicacionDAOImpl implements IndicacionDAO {
    public IndicacionDAOImpl() {
        indicaciones = new HashMap<>();
    }
    @Override
    public int cantidad() {
        return 0;
    }

    @Override
    public List<Indicacion> obtenerListaIndicaciones() {
        return List.copyOf(indicaciones.values());
    }

    @Override
    public Indicacion buscarIndicacion(int codigo) {
        return indicaciones.get(codigo);
    }

    @Override
    public Indicacion agregar(Indicacion indicacion) throws IllegalArgumentException {
        if(!indicaciones.containsKey(indicacion.getMedicamento().getCodigo())){
            indicaciones.putIfAbsent(indicacion.getMedicamento().getCodigo(),indicacion);
            System.out.printf("Indicacion agregado correctamente: '%s'%n", indicacion);
        }
        else
            throw new IllegalArgumentException("Ya se añadió ese medicamento");
        return indicacion;
    }

    @Override
    public Indicacion actualizar(Indicacion indicacion) throws IllegalArgumentException{
        if(indicaciones.containsKey(indicacion.getMedicamento().getCodigo())){
            indicaciones.put(indicacion.getMedicamento().getCodigo(),indicacion);
            System.out.printf("Indicacion actualizada correctamente: '%s'%n", indicacion);
        }
        else
            throw new IllegalArgumentException("No hay un medicamento con ese codigo");
        return indicacion;
    }

    @Override
    public Indicacion eliminar(int codigo) throws IllegalArgumentException {
        Indicacion indicacion = indicaciones.remove(codigo);
        if(indicacion!=null)
            System.out.printf("Indicacion eliminada correctamente: '%s'%n", indicacion);
        else
            throw new IllegalArgumentException("No hay un medicamento con ese codigo");
        return indicacion;
    }
    private final Map<Integer, Indicacion> indicaciones;
}
