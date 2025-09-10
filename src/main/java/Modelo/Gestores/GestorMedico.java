package Modelo.Gestores;

import Modelo.DAO.MedicoDAO;
import Modelo.DAO.MedicoDAOImpl;
import Modelo.entidades.Medico;
import jakarta.xml.bind.JAXBException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class GestorMedico {

    public GestorMedico() {
        // Lista en memoria para los médicos
        medicos=new MedicoDAOImpl();
    }

    // Cantidad total de médicos
    public int cantidad() {
        return medicos.cantidad();
    }

    public List<Medico> obtenerListaMedicos(){
        return medicos.obtenerListaMedicos();
    }

    // Verifica si existe un médico con el ID indicado
    public boolean existeMedico(String id) {
        return medicos.buscarPorId(id) != null;
    }

    // Busca un médico por ID exacto (o null si no existe)
    public Medico buscarPorId(String id) {
        return medicos.buscarPorId(id);
    }

    // Busca por nombre con coincidencia aproximada (ignorando mayúsculas/minúsculas)
    public Medico buscarPorNombre(String nombre) {
        return  medicos.buscarPorNombre(nombre);
    }

    // Agrega un médico si el ID no está repetido
    public Medico agregar(Medico medico,int token) throws IllegalArgumentException { // (antes: aregarMedico)
        if(token!=0)
            throw new SecurityException(String.valueOf(token));
        return medicos.agregar(medico);
    }

    // Reemplaza los datos de un médico existente (match por ID)
    public Medico actualizar(Medico medico,int token) throws IllegalArgumentException { // (antes: pacienteporactualizar)
        if(token!=0)
            throw new SecurityException(String.valueOf(token));
        return medicos.actualizar(medico);
    }
    // Elimina un médico por ID
    public Medico eliminar(String id,int token) throws IllegalArgumentException {
        if(token!=0)
            throw new SecurityException(String.valueOf(token));
        return  medicos.eliminar(id);
    }
    // Cambia la clave de un medico existente (match por ID)
    public Medico cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar){
        return  medicos.cambiarClave(id,claveActual,claveNueva,claveConfirmar);
    }

    // String legible de la lista (debug/impresión)
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
        MedicoDAOImpl impl = (MedicoDAOImpl) medicos;
        impl.cargar(new FileInputStream(ARCHIVO_DATOS));
        System.out.println("Datos cargados correctamente.");
    }
    public void guardar()throws FileNotFoundException, JAXBException{
        MedicoDAOImpl impl = (MedicoDAOImpl) medicos;
        impl.guardar(new FileOutputStream(ARCHIVO_DATOS));
        System.out.println("Datos guardados correctamente.");
    }
    private static final String ARCHIVO_DATOS="datos/medicos.xml";
    private final MedicoDAO medicos;

}
