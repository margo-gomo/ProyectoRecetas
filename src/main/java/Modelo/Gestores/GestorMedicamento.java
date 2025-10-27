package Modelo.Gestores;

import Modelo.DAO.MedicamentoDAO;
import Modelo.entidades.Medicamento;
import Modelo.entidades.Usuario;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GestorMedicamento {

    public GestorMedicamento()throws SQLException {
        medicamentos=new MedicamentoDAO();
    }

    public List<Medicamento> obtenerListaMedicamentos()throws SQLException{
        return medicamentos.findAll();
    }

    public Medicamento buscar(String codigo)throws SQLException {
            return medicamentos.findById(codigo);
    }
    public void agregar(Medicamento medicamento, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMIN".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            medicamentos.add(medicamento);
        }catch(SQLException e){
            throw new SQLException("Ya existe un medicamento con ese codigo");
        }
    }
    public void actualizar(Medicamento medicamento, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMIN".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            medicamentos.update(medicamento);
        }catch(SQLException e){
            throw new SQLException("No existe un medicamento con ese codigo");
        }
    }

    public void eliminar(String codigo, Usuario usuario) throws SecurityException,SQLException {
        if(!("ADMIN".equals(usuario.getTipo())))
            throw new SecurityException("No tienes los permisos para realizar esta accion");
        try{
            medicamentos.delete(codigo);
        }catch(SQLException e){
            throw new SQLException("No existe un medicamento con ese codigo");
        }
    }

    public void cargar() throws SQLException, IOException {
        File f=new File(ARCHIVO_DATOS);
        if(obtenerListaMedicamentos().isEmpty()&&f.exists())
            medicamentos.importAllFromJson(f);
    }
    public void guardar () throws SQLException, IOException {
        medicamentos.exportAllToJson(new File(ARCHIVO_DATOS));
    }
    private static final String ARCHIVO_DATOS= "src/main/datos/medicamentos.json";
    private final MedicamentoDAO medicamentos;
}
