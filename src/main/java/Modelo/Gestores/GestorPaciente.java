package Modelo.Gestores;

import Modelo.DAO.PacienteDAO;
import Modelo.entidades.Paciente;
import Modelo.entidades.Usuario;
import jakarta.xml.bind.JAXBException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class GestorPaciente {
    public GestorPaciente()throws SQLException {
        pacientes=new PacienteDAO();
    }

    public List<Paciente> obtenerListaPacientes()throws SQLException{
        return pacientes.findAll();
    }

    public Paciente buscar(int id)throws SQLException {
        Paciente paciente= pacientes.findById(id);
        if(paciente==null)
            throw new SQLException("No existe un usuario con esas credenciales");
        return paciente;
    }
    public void agregar(Paciente paciente, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            pacientes.add(paciente);
        }catch(SQLException e){
            throw new SQLException("Ya existe un paciente con ese id");
        }
    }
    public void actualizar(Paciente paciente, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            pacientes.update(paciente);
        }catch(SQLException e){
            throw new SQLException("No existe un paciente con ese id");
        }
    }

    public void eliminar(Integer id, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            pacientes.delete(id);
        }catch(SQLException e){
            throw new SQLException("No existe un paciente con ese id");
        }
    }

    /*public void cargar() throws IOException, JAXBException, SQLException, ParserConfigurationException, SAXException {
        File f=new File(ARCHIVO_DATOS);
        if(f.exists()&&f.length()!=0&&obtenerListaPacientes()==null)
            pacientes.cargar(f);
    }*/
    public void guardar () throws JAXBException, SQLException, FileNotFoundException {
        pacientes.guardar(new FileOutputStream(ARCHIVO_DATOS));
    }
    private static final String ARCHIVO_DATOS= "src/datos/pacientes.xml";
    private final PacienteDAO pacientes;
}
