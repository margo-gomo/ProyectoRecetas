package Modelo.Gestores;

import Modelo.DAO.MedicoDAO;
import Modelo.entidades.Medico;
import Modelo.entidades.Usuario;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class GestorMedico {

    public GestorMedico() throws SQLException {
        medicos=new MedicoDAO();
    }

    public List<Medico> obtenerListaMedicos()throws SQLException {
        return medicos.findAll();
    }

    public Medico buscar(String id)throws SQLException {
        Medico medico= medicos.findById(id);
        if(medico==null)
            throw new SQLException("No existe un medico con esas credenciales");
        return medico;
    }
    public void agregar(Medico medico, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMIN".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        medicos.add(medico);
    }
    public void actualizar(Medico medico, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMIN".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        medicos.update(medico);
    }

    public void eliminar(String id, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMIN".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        medicos.delete(id);
    }

    public void cargar(List<Usuario>usuarios) throws SQLException, IOException {
        File f=new File(ARCHIVO_DATOS);
        if(obtenerListaMedicos().isEmpty()&&f.exists())
            medicos.importFromJson(f, usuarios);
    }
    public void guardar () throws SQLException, IOException {
        medicos.exportAllToJson(new File(ARCHIVO_DATOS));
    }
    private static final String ARCHIVO_DATOS= "src/main/datos/medicos.json";
    private final MedicoDAO medicos;

}
