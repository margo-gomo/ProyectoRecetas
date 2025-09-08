package Modelo;

import DAO.MedicoDAO;
import DAO.MedicoDAOImpl;
import Gestores.GestorMedico;
import entidades.Medico;
import jakarta.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
public class ModeloMedico {
    public ModeloMedico() {
        dao = new MedicoDAOImpl();
        datos=new GestorMedico(dao);
    }
    public int cantidad(){
        return dao.cantidad();
    }
    public List<Medico> obtenerListaMedico(){
        return datos.obtenerListaMedicos();
    }
    public Medico buscarPorId(String id){
        return datos.buscarPorId(id);
    }
    public Medico buscarPorNombre(String nombre){
        return datos.buscarPorNombre(nombre);
    }
    public Medico agregar(Medico medico) throws IllegalArgumentException{
        return datos.agregar(medico);
    }
    public Medico actualizar(Medico medico) throws IllegalArgumentException{
        return datos.actualizar(medico);
    }
    public Medico eliminar(String id) throws IllegalArgumentException{
        return datos.eliminar(id);
    }
    public Medico cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar)throws IllegalArgumentException{
        return datos.cambiarClave(id,claveActual,claveNueva,claveConfirmar);
    }
    public GestorMedico obtenerModelo(){
        return datos;
    }
    public void cargar()throws FileNotFoundException, JAXBException{
        MedicoDAOImpl impl = (MedicoDAOImpl) dao;
        impl.cargar(new FileInputStream(ARCHIVO_DATOS));
        System.out.println("Datos cargados correctamente.");
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        MedicoDAOImpl impl = (MedicoDAOImpl) dao;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS="datos/medicos.xml";
    private final MedicoDAO dao;
    private final GestorMedico datos;
}
