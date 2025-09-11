package Modelo.Gestores;

import Modelo.DAO.MedicamentoDAO;
import Modelo.DAO.MedicamentoDAOImpl;
import Modelo.entidades.Medicamento;
import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class GestorMedicamento {

    public GestorMedicamento() {
        // Lista en memoria para los médicos
        medicamentos=new MedicamentoDAOImpl();
    }

    public int cantidad() {
        return medicamentos.cantidad();
    }

    public List<Medicamento> obtenerListaMedicamentos(){
        return medicamentos.obtenerListaMedicamentos();
    }

    public boolean existeMedicamento(int codigo) {
        return medicamentos.buscarPorCodigo(codigo) != null;
    }

    public Medicamento buscarPorCodigo(int codigo) {
            return medicamentos.buscarPorCodigo(codigo);
    }

    public  Medicamento buscarPorNombre(String nombre) {
        return medicamentos.buscarPorNombre(nombre);
    }

    public Medicamento buscarPorDescripcion(String descripcion) {
        return  medicamentos.buscarPorDescripcion(descripcion);
    }

    public Medicamento agregar(Medicamento medicamento,int token) throws IllegalArgumentException,SecurityException {
        if(token!=0)
            throw new SecurityException(String.valueOf(token));
        return medicamentos.agregar(medicamento);
    }

    public Medicamento actualizar(Medicamento medicamento,int token) throws IllegalArgumentException,SecurityException {
        if(token!=0)
            throw new SecurityException(String.valueOf(token));
        return medicamentos.actualizar(medicamento);
    }

    public Medicamento eliminar(int codigo,int token) throws IllegalArgumentException,SecurityException {
        if(token!=0)
            throw new SecurityException(String.valueOf(token));
        return  medicamentos.eliminar(codigo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Medicamento medicamento : medicamentos.obtenerListaMedicamentos()) {
            sb.append(String.format("%n\t%s,", medicamento));
        }
        sb.append("\n]");
        return sb.toString();
    }
    public void cargar()throws FileNotFoundException, JAXBException {
        File f=new File(ARCHIVO_DATOS);
        if(!f.exists()||f.length()==0)
            System.out.println("No hay datos previos para cargar.");
        else {
            MedicamentoDAOImpl impl=(MedicamentoDAOImpl) medicamentos;
            impl.cargar(new FileInputStream(ARCHIVO_DATOS));
            System.out.println("Datos cargados correctamente.");
        }
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        MedicamentoDAOImpl impl=(MedicamentoDAOImpl) medicamentos;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS="datos/medicamentos.xml";
    private final MedicamentoDAO medicamentos;
}
