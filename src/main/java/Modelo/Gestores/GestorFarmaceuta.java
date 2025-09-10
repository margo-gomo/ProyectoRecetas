package Modelo.Gestores;

import Modelo.DAO.FarmaceutaDAO;
import Modelo.DAO.FarmaceutaDAOImpl;
import Modelo.entidades.Farmaceuta;
import jakarta.xml.bind.JAXBException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class GestorFarmaceuta {

    public GestorFarmaceuta() {
        // Lista en memoria para los médicos
        farmaceutas=new FarmaceutaDAOImpl();
    }

    public int cantidad() {
        return farmaceutas.cantidad();
    }

    public List<Farmaceuta> obtenerListaFarmaceutas(){
        return farmaceutas.obtenerListaFarmaceutas();
    }

    public boolean existeFarmaceuta(String id) {
        return farmaceutas.buscarPorId(id) != null;
    }

    public Farmaceuta buscarPorId(String id) {
        return farmaceutas.buscarPorId(id);
    }

    public Farmaceuta buscarPorNombre(String nombre) {
        return  farmaceutas.buscarPorNombre(nombre);
    }

    public Farmaceuta agregar(Farmaceuta farmaceuta,int token) throws IllegalArgumentException {
        if(token!=0)
            throw new SecurityException();
        return farmaceutas.agregar(farmaceuta);
    }

    public Farmaceuta actualizar(Farmaceuta farmaceuta,int token) throws IllegalArgumentException {
        if(token!=0)
            throw new SecurityException();
        return farmaceutas.actualizar(farmaceuta);
    }

    public Farmaceuta eliminar(String id,int token) throws IllegalArgumentException {
        if(token!=0)
            throw new SecurityException();
        return  farmaceutas.eliminar(id);
    }
    public Farmaceuta cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar){
        return  farmaceutas.cambiarClave(id, claveActual, claveNueva, claveConfirmar);
    }

    // String legible de la lista (debug/impresión)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Farmaceuta farmaceuta : farmaceutas.obtenerListaFarmaceutas()) {
            sb.append(String.format("%n\t%s,", farmaceuta));
        }
        sb.append("\n]");
        return sb.toString();
    }
    public void cargar()throws FileNotFoundException, JAXBException {
        FarmaceutaDAOImpl impl = (FarmaceutaDAOImpl) farmaceutas;
        impl.cargar(new FileInputStream(ARCHIVO_DATOS));
        System.out.println("Datos cargados correctamente.");
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        FarmaceutaDAOImpl impl = (FarmaceutaDAOImpl) farmaceutas;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS="datos/farmaceutas.xml";
    private final FarmaceutaDAO farmaceutas;
}