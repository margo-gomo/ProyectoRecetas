package Modelo.Gestores;
import Modelo.entidades.Mensaje;
import Modelo.entidades.Usuario;
import Modelo.DAO.MensajeDAO;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
public class GestorMensaje {
    public GestorMensaje()throws SQLException{
        mensajes=new MensajeDAO();
    }
    public List<Mensaje> obtenerListaMensajes()throws  SQLException{
        return mensajes.findAll();
    }
    public void agregarMensaje(Mensaje mensaje)throws SQLException{
        mensajes.add(mensaje);
    }
    public void actualizarMensaje(Mensaje mensaje)throws SQLException{
        mensajes.update(mensaje);
    }
    public MensajeDAO.MensajeDTO obtenerMensajeDTO(String id) throws SQLException{
        return mensajes.mensajedto(id);
    }
    public List<Mensaje> recibirMensjaes(String usuarioId) throws SQLException{
        return mensajes.findByRemitente(usuarioId);
    }
    public void cargar() throws SQLException, IOException {
        File f=new File(ARCHIVO_DATOS);
        if(f.exists())
            mensajes.importAllFromJson(f);
    }
    public void guardar () throws SQLException, IOException {
        mensajes.exportAllToJson(new File(ARCHIVO_DATOS));
    }
    private static final String ARCHIVO_DATOS= "src/main/datos/mensajes.json";
    private final MensajeDAO mensajes;
}
