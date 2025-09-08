package Modelo;
import DAO.RecetaDAO;
import DAO.RecetaDAOImpl;
import Gestores.GestorRecetas;
import Prescripcion.PrescripcionReceta;
import jakarta.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
public class ModeloRecetas {
    public ModeloRecetas() {
        dao=new RecetaDAOImpl();
        datos=new GestorRecetas(dao);
    }

    public int cantidad() {
        return datos.cantidad();
    }

    public List<PrescripcionReceta> obtenerListaRecetas() {
        return datos.obtenerListaRecetas();
    }

    public PrescripcionReceta buscarReceta(int idPaciente, LocalDate fechaConfeccion) {
        return datos.buscarReceta(idPaciente, fechaConfeccion);
    }

    public PrescripcionReceta agregar(PrescripcionReceta receta) throws IllegalArgumentException {
        return  datos.agregar(receta);
    }

    public PrescripcionReceta actualizar(PrescripcionReceta recetaPorActualizar) throws IllegalArgumentException {
        return datos.actualizar(recetaPorActualizar);
    }

    public PrescripcionReceta eliminar(int idPaciente, LocalDate fechaConfeccion) throws IllegalArgumentException {
        return datos.eliminar(idPaciente, fechaConfeccion);
    }
    public GestorRecetas obtenerModelo(){
        return datos;
    }
    public void cargar()throws FileNotFoundException, JAXBException{
        RecetaDAOImpl impl=(RecetaDAOImpl)dao;
        impl.cargar(new FileInputStream(ARCHIVO_DATOS));
        System.out.println("Datos cargados correctamente.");
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        RecetaDAOImpl impl=(RecetaDAOImpl)dao;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS="datos/recetas.xml";
    private final RecetaDAO dao;
    private final GestorRecetas datos;
}
