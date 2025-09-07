package Modelo;
import DAO.FarmaceutaDAO;
import DAO.FarmaceutaDAOImpl;
import Gestores.GestorFarmaceuta;
import entidades.Farmaceuta;
import jakarta.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
public class ModeloFarmaceuta {
    public ModeloFarmaceuta() {
        dao = new FarmaceutaDAOImpl();
        datos=new GestorFarmaceuta(dao);
    }
    public int cantidad(){
        return dao.cantidad();
    }
    public List<Farmaceuta> obtenerListaFarmaceuta(){
        return datos.obtenerListaFarmaceutas();
    }
    public Farmaceuta buscarPorId(String id){
        return dao.buscarPorId(id);
    }
    public Farmaceuta buscarPorNombre(String nombre){
        return dao.buscarPorNombre(nombre);
    }
    public Farmaceuta agregar(Farmaceuta medico) throws IllegalArgumentException{
        return dao.agregar(medico);
    }
    public Farmaceuta actualizar(Farmaceuta medico) throws IllegalArgumentException{
        return dao.actualizar(medico);
    }
    public Farmaceuta eliminar(String id) throws IllegalArgumentException{
        return dao.eliminar(id);
    }
    public Farmaceuta cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar)throws IllegalArgumentException{
        return datos.cambiarClave(id,claveActual,claveNueva,claveConfirmar);
    }
    public GestorFarmaceuta obtenerModelo(){
        return datos;
    }
    public void cargar()throws FileNotFoundException, JAXBException{
        FarmaceutaDAOImpl impl = (FarmaceutaDAOImpl) dao;
        impl.cargar(new FileInputStream(ARCHIVO_DATOS));
        System.out.println("Datos cargados correctamente.");
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        FarmaceutaDAOImpl impl = (FarmaceutaDAOImpl) dao;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS="datos/farmaceutas.xml";
    private final FarmaceutaDAO dao;
    private final GestorFarmaceuta datos;
}
