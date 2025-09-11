package Modelo.Gestores;
import Modelo.DAO.IndicacionDAO;
import Modelo.DAO.IndicacionDAOImpl;
import Modelo.entidades.Receta.Indicacion;

import java.util.List;
public class GestorIndicacion {
    public GestorIndicacion() {
        indicaciones = new IndicacionDAOImpl();
    }
    public int cantidad() {
        return indicaciones.cantidad();
    }
    public List<Indicacion> obtenerListaIndicaciones() {
        return indicaciones.obtenerListaIndicaciones();
    }
    public Indicacion buscarIndicacion(int codigo) {
        return indicaciones.buscarIndicacion(codigo);
    }
    public Indicacion agregar(Indicacion indicacion,int token) throws IllegalArgumentException,SecurityException {
        if(token!=1)
            throw new SecurityException(String.valueOf(token));
        return indicaciones.agregar(indicacion);
    }
    public Indicacion actualizar(Indicacion indicacion,int token)throws IllegalArgumentException,SecurityException {
        if(token!=1)
            throw new SecurityException(String.valueOf(token));
        return indicaciones.actualizar(indicacion);
    }
    public Indicacion eliminar(int codigo,int token)throws IllegalArgumentException,SecurityException {
        if(token!=1)
            throw new SecurityException(String.valueOf(token));
        return indicaciones.eliminar(codigo);
    }
    public final IndicacionDAO indicaciones;
}
