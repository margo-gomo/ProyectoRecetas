package Modelo.Gestores;

import Modelo.DAO.MedicoDAO;
import Modelo.entidades.Medico;
import Modelo.entidades.Usuario;
import jakarta.xml.bind.JAXBException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
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
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        medicos.add(medico);
    }
    public void actualizar(Medico medico, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        medicos.update(medico);
    }

    public void eliminar(String id, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        medicos.delete(id);
    }

    /*public void cargar() throws IOException, JAXBException, SQLException, ParserConfigurationException, SAXException {
        File f=new File(ARCHIVO_DATOS);
        if(f.exists()&&f.length()!=0&&obtenerListaMedicos()==null)
            medicos.cargar(f);
    }*/
    public void guardar () throws JAXBException, SQLException, FileNotFoundException {
        medicos.guardar(new FileOutputStream(ARCHIVO_DATOS));
    }
    private static final String ARCHIVO_DATOS= "src/datos/medicos.xml";
    private final MedicoDAO medicos;

}
