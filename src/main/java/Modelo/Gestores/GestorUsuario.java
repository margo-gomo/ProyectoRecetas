package Modelo.Gestores;
import Modelo.DAO.UsuarioDAO;
import Modelo.entidades.Usuario;
import jakarta.xml.bind.JAXBException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
public class GestorUsuario {
    public GestorUsuario() throws SQLException {
        usuarios=new UsuarioDAO();
    }

    public List<Usuario> obtenerListaUsuarios()throws SQLException {
        return usuarios.findAll();
    }
    public List<Usuario> obtenerListaAdministradores()throws SQLException {
        return usuarios.findAllAdministradores();
    }
    public List<Usuario> obtenerListaFarmaceutas()throws SQLException {
        return usuarios.findAllFarmaceutas();
    }
    public List<Usuario> obtenerListaMedicos()throws SQLException {
        return usuarios.findAllMedicos();
    }
    public Usuario buscar(String id)throws SQLException {
        Usuario usuario= usuarios.findById(id);
        if(usuario==null)
            throw new SQLException("No existe un usuario con esas credenciales");
        return usuario;
    }
    public Usuario buscarIdClave(String id,String clave)throws SQLException {
        Usuario usuario=usuarios.findByIdAndClave(id,clave);
        if(usuario==null)
            throw new SQLException("No existe un usuario con esas credenciales");
        return usuario;
    }
    public void agregar(Usuario u, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            usuarios.add(u);
        }catch(SQLException e){
            throw new SQLException("Ya existe un usuario con esas credenciales");
        }
    }
    public void actualizar(Usuario u, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            usuarios.update(u);
        }catch(SQLException e){
            throw new SQLException("No existe un usuario con esas credenciales");
        }
    }
    public void cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar)throws IllegalArgumentException, SQLException{
        Usuario u=buscarIdClave(id,claveActual);
        if(!claveNueva.equals(claveConfirmar))
            throw new IllegalArgumentException("La clave por confirmar no coincide con la clave nueva propuesta.");
        if(claveNueva.isEmpty()||claveConfirmar.isEmpty())
            throw new IllegalArgumentException("Rellene todos los campos");
        u.setClave(claveNueva);
        usuarios.update(u);
    }
    public void eliminar(String id, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            usuarios.delete(id);
        }catch(SQLException e){
            throw new SQLException("No existe un usuario con esas credenciales");
        }
    }

    /*public void cargar() throws IOException, JAXBException, SQLException, ParserConfigurationException, SAXException {
        File f=new File(ARCHIVO_DATOS);
        if(f.exists()&&f.length()!=0&&obtenerListaUsuarios()==null)
            usuarios.cargar(f);
    }*/
    public void guardar () throws JAXBException, SQLException, FileNotFoundException {
        usuarios.guardar(new FileOutputStream(ARCHIVO_DATOS));
    }
    private static final String ARCHIVO_DATOS= "src/datos/usuarios.xml";
    private final UsuarioDAO usuarios;
}
