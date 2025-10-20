package Modelo.DAO;
import Adaptador.XMLUtils;
import Modelo.entidades.Paciente;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
public class PacienteDAO implements DAOAbstracto<Integer, Paciente>{
    public PacienteDAO() throws SQLException {
        this.dao=DaoManager.createDao(ConexionBD.getConexion(), Paciente.class);
    }
    @Override
    public void add(Paciente e) throws SQLException {
        
    }

    @Override
    public Paciente findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<Paciente> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void update(Paciente e) throws SQLException {

    }

    @Override
    public void delete(Integer id) throws SQLException {

    }
    public void guardar (OutputStream out) throws JAXBException, SQLException {
        PacienteDAO.PacienteDAOX pacientes=new PacienteDAO.PacienteDAOX(findAll());
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(pacientes));
        }
    }
    private final Dao<Paciente, String> dao;
    @XmlRootElement(name = "pacientes")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class PacienteDAOX{
        public PacienteDAOX(List<Paciente> lista) {
            this();
            pacientes.addAll(lista);
        }
        public PacienteDAOX() {
            pacientes = new ArrayList<>();
        }
        @XmlElement
        private List<Paciente> pacientes;
    }
}
