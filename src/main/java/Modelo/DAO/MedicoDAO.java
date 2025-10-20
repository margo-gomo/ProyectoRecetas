package Modelo.DAO;

import Adaptador.XMLUtils;
import Modelo.entidades.Medicamento;
import Modelo.entidades.Medico;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.io.File;
import java.io.IOException;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MedicoDAO implements DAOAbstracto<String,Medico> {
    public MedicoDAO() throws SQLException {
        this.dao = DaoManager.createDao(ConexionBD.getConexion(), Medico.class);
    }
    @Override
    public void add(Medico e) throws SQLException {
        dao.createOrUpdate(e);
    }

    @Override
    public Medico findById(String id) throws SQLException {
        return dao.queryForId(id);
    }
    
    @Override
    public List<Medico> findAll() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public void update(Medico e) throws SQLException {
        dao.update(e);
    }

    @Override
    public void delete(String id) throws SQLException {
        dao.deleteById(id);
    }
    public void guardar (OutputStream out) throws JAXBException, SQLException {
        MedicoDAO.MedicoDAOX medicos=new MedicoDAO.MedicoDAOX(findAll());
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(medicos));
        }
    }
    /*public void cargar(File xmlFile) throws SQLException, ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        NodeList listaMedicamentos = doc.getElementsByTagName("medicamento");
        for (int i = 0; i < listaMedicamentos.getLength(); i++) {
            Node nodo = listaMedicamentos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                String idText = elemento.getElementsByTagName("codigo").item(0).getTextContent().trim();
                String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent().trim();
                String presentacion = elemento.getElementsByTagName("presentacion").item(0).getTextContent().trim();
                String descripcion = elemento.getElementsByTagName("descripcion").item(0).getTextContent().trim();
                int codigo = Integer.parseInt(idText);
                Medicamento medicamento = new Medicamento(codigo, nombre, presentacion, descripcion);
                add(medicamento);
            }
        }
    }*/
    private final Dao<Medico, String> dao;
    @XmlRootElement(name = "medicos")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class MedicoDAOX{
        public MedicoDAOX(List<Medico> lista) {
            this();
            medicos.addAll(lista);
        }
        public MedicoDAOX() {
            medicos = new ArrayList<>();
        }
        @XmlElement
        private List<Medico> medicos;
    }
}
