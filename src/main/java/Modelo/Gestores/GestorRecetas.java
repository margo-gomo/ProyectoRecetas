package Modelo.Gestores;

import Modelo.DAO.RecetaDAO;
import Modelo.DAO.RecetaDAOImpl;
import Modelo.entidades.Receta.Indicacion;
import Modelo.entidades.Receta.Receta;
import jakarta.xml.bind.JAXBException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

public class GestorRecetas {

    public GestorRecetas() {
        recetas=new RecetaDAOImpl();
    }

    public int cantidad() {
        return recetas.cantidad();
    }
    public List<Receta> obtenerListaRecetas() {
        return recetas.obtenerListaRecetas();
    }

    public boolean existeReceta(int idPaciente, LocalDate fechaConfeccion) {
        return recetas.buscarReceta(idPaciente, fechaConfeccion)!=null;
    }

    public Receta buscarReceta(int idPaciente, LocalDate fechaConfeccion) {
        return recetas.buscarReceta(idPaciente, fechaConfeccion);
    }

    public Receta agregar(Receta receta,int token) throws IllegalArgumentException {
        if(token!=1)
            throw new SecurityException(String.valueOf(token));
        return  recetas.agregar(receta);
    }

    public Receta actualizar(Receta recetaPorActualizar,int token) throws IllegalArgumentException {
        if(token!=1||token!=2)
            throw new SecurityException(String.valueOf(token));
        return recetas.actualizar(recetaPorActualizar);
    }

    public Receta eliminar(String codigo,int token) throws IllegalArgumentException {
        if(token!=1)
            throw new SecurityException(String.valueOf(token));
        return recetas.eliminar(codigo);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Receta receta : recetas.obtenerListaRecetas()) {
            sb.append(String.format("%n\t%s,", receta));
        }
        sb.append("\n]");
        return sb.toString();
    }

    private boolean fechasIguales(LocalDate a, LocalDate b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }
    public void cargar()throws FileNotFoundException, JAXBException {
        RecetaDAOImpl impl=(RecetaDAOImpl)recetas;
        impl.cargar(new FileInputStream(ARCHIVO_DATOS));
        System.out.println("Datos cargados correctamente.");
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        RecetaDAOImpl impl=(RecetaDAOImpl)recetas;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS="datos/recetas.xml";
    private final RecetaDAO recetas;
}
