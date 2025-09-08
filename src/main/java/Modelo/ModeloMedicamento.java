package Modelo;
import DAO.MedicamentoDAO;
import DAO.MedicamentoDAOImpl;
import Gestores.GestorMedicamento;
import entidades.Medicamento;
import jakarta.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
public class ModeloMedicamento {
    public ModeloMedicamento(){
        dao = new MedicamentoDAOImpl();
        datos=new GestorMedicamento(dao);
    }
    public int cantidadM() {
        return datos.cantidad();
    }

    public List<Medicamento> obtenerListaMedicamentos(){
        return datos.obtenerListaMedicamentos();
    }

    public Medicamento buscarPorCodigo(int codigo) {
        return datos.buscarPorCodigo(codigo);
    }

    public Medicamento buscarPorDescripcion(String descripcion) {
        return  datos.buscarPorDescripcion(descripcion);
    }

    public Medicamento agregar(Medicamento medicamento) throws IllegalArgumentException {
        return datos.agregar(medicamento);
    }

    public Medicamento actualizar(Medicamento medicamento) throws IllegalArgumentException {
        return datos.actualizar(medicamento);
    }

    public Medicamento eliminar(int codigo) throws IllegalArgumentException {
        return  datos.eliminar(codigo);
    }
    public GestorMedicamento obtenerModelo(){
        return datos;
    }
    public void cargar()throws FileNotFoundException, JAXBException {
        MedicamentoDAOImpl impl=(MedicamentoDAOImpl)dao;
        impl.cargar(new FileInputStream(ARCHIVO_DATOS));
        System.out.println("Datos cargados correctamente.");
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        MedicamentoDAOImpl impl=(MedicamentoDAOImpl)dao;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS="datos/medicamentos.xml";
    private final MedicamentoDAO dao;
    private final GestorMedicamento datos;
}
