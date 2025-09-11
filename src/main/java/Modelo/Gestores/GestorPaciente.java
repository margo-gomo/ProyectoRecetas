package Modelo.Gestores;

import Modelo.DAO.PacienteDAO;
import Modelo.DAO.PacienteDAOImpl;
import Modelo.entidades.Paciente;
import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class GestorPaciente { 
    public GestorPaciente() {
        // Lista en memoria para los médicos
        pacientes=new PacienteDAOImpl();
    }

    public int cantidad() {
        return pacientes.cantidad();
    }

    public List<Paciente> obtenerListaPacientes(){
        return pacientes.obtenerListaPacientes();
    }

    public boolean existePaciente(int id) {
        return pacientes.buscarPorId(id) != null;
    }

    public Paciente buscarPorId(int id) {
        return pacientes.buscarPorId(id);
    }

    public Paciente buscarPorNombre(String nombre) {
        return  pacientes.buscarPorNombre(nombre);
    }

    public Paciente agregar(Paciente paciente,int token) throws IllegalArgumentException,SecurityException { // (antes: aregarPaciente)
        if(token!=0)
            throw new SecurityException();
        return pacientes.agregar(paciente);
    }

    public Paciente actualizar(Paciente paciente,int token) throws IllegalArgumentException,SecurityException { // (antes: pacienteporactualizar)
        if(token!=0)
            throw new SecurityException();
        return pacientes.actualizar(paciente);
    }

    public Paciente eliminar(int id,int token) throws IllegalArgumentException,SecurityException {
        if(token!=0)
            throw new SecurityException();
        return  pacientes.eliminar(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Paciente paciente : pacientes.obtenerListaPacientes()) {
            sb.append(String.format("%n\t%s,", paciente));
        }
        sb.append("\n]");
        return sb.toString();
    }
    public void cargar()throws FileNotFoundException, JAXBException {
        File f=new File(ARCHIVO_DATOS);
        if(!f.exists()||f.length()==0)
            System.out.println("No hay datos previos para cargar.");
        else{
            PacienteDAOImpl impl = (PacienteDAOImpl) pacientes;
            impl.cargar(new FileInputStream(ARCHIVO_DATOS));
            System.out.println("Datos cargados correctamente.");
        }
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        PacienteDAOImpl impl = (PacienteDAOImpl) pacientes;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS="datos/pacientes.xml";
    private final PacienteDAO pacientes;
}
