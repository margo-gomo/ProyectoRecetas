package Modelo.DAO;

import Adaptador.XMLUtils;
import Modelo.entidades.Medicamento;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import org.xml.sax.SAXException;

public class MedicamentoDAO implements DAOAbstracto<Integer, Medicamento> {
    public MedicamentoDAO() throws SQLException {
        this.dao=DaoManager.createDao(ConexionBD.getConexion(), Medicamento.class);
    }
    @Override
    public void add(Medicamento e) throws SQLException {
        dao.create(e);
    }

    @Override
    public Medicamento findById(Integer id) throws SQLException {
        return dao.queryForId(id);
    }

    @Override
    public List<Medicamento> findAll() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public void update(Medicamento e) throws SQLException {
        dao.update(e);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        dao.deleteById(id);
    }
    public void guardar (OutputStream out) throws JAXBException, SQLException {
        MedicamentoDAO.MedicamentoDAOX medicamentos=new MedicamentoDAO.MedicamentoDAOX(findAll());
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(medicamentos));
        }
    }
    public void cargar(File xmlFile) throws SQLException, ParserConfigurationException, IOException, SAXException {
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
    }
    private final Dao<Medicamento, Integer> dao;
    @XmlRootElement(name = "medicamentos")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class MedicamentoDAOX{
        public MedicamentoDAOX(List<Medicamento> lista) {
            this();
            medicamentos.addAll(lista);
        }
        public MedicamentoDAOX() {
            medicamentos = new ArrayList<>();
        }
        @XmlElement
        private List<Medicamento> medicamentos;
    }
}
