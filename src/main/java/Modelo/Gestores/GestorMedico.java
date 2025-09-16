package Modelo.Gestores;

import Modelo.DAO.MedicoDAO;
import Modelo.DAO.MedicoDAOImpl;
import Modelo.entidades.Medico;
import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class GestorMedico {

    public GestorMedico() {
        medicos=new MedicoDAOImpl();
    }

    public int cantidad() {
        return medicos.cantidad();
    }

    public List<Medico> obtenerListaMedicos(){
        return medicos.obtenerListaMedicos();
    }

    public boolean existeMedico(String id) {
        return medicos.buscarPorId(id) != null;
    }

    public Medico buscarPorId(String id) {
        return medicos.buscarPorId(id);
    }

    public Medico buscarPorNombre(String nombre) {
        return  medicos.buscarPorNombre(nombre);
    }

    public Medico agregar(Medico medico,int token) throws IllegalArgumentException,SecurityException { // (antes: aregarMedico)
        if(token!=0)
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        return medicos.agregar(medico);
    }

    public Medico actualizar(Medico medico,int token) throws IllegalArgumentException,SecurityException { // (antes: pacienteporactualizar)
        if(token!=0)
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        return medicos.actualizar(medico);
    }
    public Medico eliminar(String id,int token) throws IllegalArgumentException,SecurityException {
        if(token!=0)
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        return  medicos.eliminar(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Medico medico : medicos.obtenerListaMedicos()) {
            sb.append(String.format("%n\t%s,", medico));
        }
        sb.append("\n]");
        return sb.toString();
    }
    public void cargar()throws FileNotFoundException, JAXBException {
        File f=new File(ARCHIVO_DATOS);
        if(!f.exists()||f.length()==0)
            System.out.println("No hay datos previos para cargar.");
        else{
            MedicoDAOImpl impl = (MedicoDAOImpl) medicos;
            impl.cargar(new FileInputStream(f));
            System.out.println("Datos cargados correctamente.");
        }
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        MedicoDAOImpl impl = (MedicoDAOImpl) medicos;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS= "datos/medicos.xml";
    private final MedicoDAO medicos;

}
