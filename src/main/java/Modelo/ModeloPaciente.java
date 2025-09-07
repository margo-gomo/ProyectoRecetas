package Modelo;
import DAO.PacienteDAO;
import DAO.PacienteDAOImpl;
import Gestores.GestorPaciente;
import entidades.Paciente;
import jakarta.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
public class ModeloPaciente {
    public ModeloPaciente() {
        dao = new PacienteDAOImpl();
        datos=new GestorPaciente(dao);
    }
    public int cantidad() {
        return datos.cantidad();
    }

    public List<Paciente> ObtenerListaPacientes(){
        return datos.obtenerListaPacientes();
    }

    public Paciente buscarPorId(int id) {
        return datos.buscarPorId(id);
    }

    public Paciente buscarPorNombre(String nombre) {
        return  datos.buscarPorNombre(nombre);
    }

    public Paciente agregar(Paciente paciente) throws IllegalArgumentException {
        return datos.agregar(paciente);
    }

    public Paciente actualizar(Paciente paciente) throws IllegalArgumentException {
        return datos.actualizar(paciente);
    }

    public Paciente eliminar(int id) throws IllegalArgumentException {
        return  datos.eliminar(id);
    }
    public GestorPaciente obtenerModelo(){
        return datos;
    }
    public void cargar()throws FileNotFoundException, JAXBException{
        PacienteDAOImpl impl = (PacienteDAOImpl) dao;
        impl.cargar(new FileInputStream(ARCHIVO_DATOS));
        System.out.println("Datos cargados correctamente.");
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        PacienteDAOImpl impl = (PacienteDAOImpl) dao;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS="datos/pacientes.xml";
    private final PacienteDAO dao;
    private final GestorPaciente datos;
}
