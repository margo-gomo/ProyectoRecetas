package Modelo.Gestores;

import Modelo.DAO.PacienteDAO;
import Modelo.entidades.Paciente;
import Modelo.entidades.Usuario;
import java.io.File;
import java.io.IOException;
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
        if(!("ADMIN".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            pacientes.add(paciente);
        }catch(SQLException e){
            throw new SQLException("Ya existe un paciente con ese id");
        }
    }
    public void actualizar(Paciente paciente, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMIN".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            pacientes.update(paciente);
        }catch(SQLException e){
            throw new SQLException("No existe un paciente con ese id");
        }
    }

    public void eliminar(Integer id, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMIN".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            pacientes.delete(id);
        }catch(SQLException e){
            throw new SQLException("No existe un paciente con ese id");
        }
    }
    public void cargar() throws SQLException, IOException {
        File f=new File(ARCHIVO_DATOS);
        if(f.exists())
            pacientes.importAllFromJson(f);
    }
    public void guardar () throws SQLException, IOException {
        pacientes.exportAllToJson(new File(ARCHIVO_DATOS));
    }
    private static final String ARCHIVO_DATOS= "src/main/datos/pacientes.json";
    private final PacienteDAO pacientes;
}
