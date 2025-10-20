package Modelo.Gestores;

import Modelo.DAO.MedicamentoDAO;
import Modelo.entidades.Medicamento;
import Modelo.entidades.Usuario;
import jakarta.xml.bind.JAXBException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class GestorMedicamento {

    public GestorMedicamento()throws SQLException {
        medicamentos=new MedicamentoDAO();
    }

    public List<Medicamento> obtenerListaMedicamentos()throws SQLException{
        return medicamentos.findAll();
    }

    public Medicamento buscar(int codigo)throws SQLException {
            return medicamentos.findById(codigo);
    }
    public void agregar(Medicamento medicamento, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            medicamentos.add(medicamento);
        }catch(SQLException e){
            throw new SQLException("Ya existe un medicamento con ese codigo");
        }
    }
    public void actualizar(Medicamento medicamento, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            medicamentos.update(medicamento);
        }catch(SQLException e){
            throw new SQLException("No existe un medicamento con ese codigo");
        }
    }

    public void eliminar(Integer codigo, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMINISTRADOR".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            medicamentos.delete(codigo);
        }catch(SQLException e){
            throw new SQLException("No existe un medicamento con ese codigo");
        }
    }

    public void cargar() throws IOException, JAXBException, SQLException, ParserConfigurationException, SAXException {
        File f=new File(ARCHIVO_DATOS);
        if(f.exists()&&f.length()!=0&&obtenerListaMedicamentos()==null)
            medicamentos.cargar(f);
    }
    public void guardar () throws JAXBException, SQLException, FileNotFoundException {
        medicamentos.guardar(new FileOutputStream(ARCHIVO_DATOS));
    }
    private static final String ARCHIVO_DATOS= "src/datos/medicamentos.xml";
    private final MedicamentoDAO medicamentos;
}
