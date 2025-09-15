package Modelo.DAO;

import Adaptador.XMLUtils;
import Modelo.entidades.Administrador;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AdministradorDAOImpl implements AdministradorDAO {
    public AdministradorDAOImpl() {
        administradores=new HashMap<>();
    }
    @Override
    public int cantidad() {
        return administradores.size();
    }

    @Override
    public List<Administrador> obtenerListaAdministradores() {
        return List.copyOf(administradores.values());
    }

    @Override
    public Administrador buscarPorId(String id) {
        return administradores.get(id);
    }

    @Override
    public Administrador buscarPorNombre(String nombre) {
        String needle = nombre.toLowerCase();
        for (Administrador administrador : administradores.values()){
            String n = administrador.getNombre();
            if (n != null && n.toLowerCase().contains(needle)) return administrador;
        }
        return null;
    }

    @Override
    public Administrador agregar(Administrador administrador) throws IllegalArgumentException {
        if(!administradores.containsKey(administrador.getId())){
            administradores.putIfAbsent(administrador.getId(),administrador);
            System.out.printf("Administrador agregado correctamente: '%s'%n", administrador);
        }
        else
            throw new IllegalArgumentException(administrador.getId());
        return administrador;
    }

    public void cargar (InputStream in) throws JAXBException {
        AdministradorDAOX adm=(AdministradorDAOX) XMLUtils.loadFromXML(in, AdministradorDAOX.class);
        administradores.clear();
        for(Administrador a : adm.administrador)
            administradores.putIfAbsent(a.getId(),a);
    }
    public void guardar (OutputStream out) throws JAXBException {
        AdministradorDAOX adm=new AdministradorDAOX (administradores);
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(adm));
        }
    }
    private final Map<String,Administrador> administradores;
    @XmlRootElement(name = "administradores")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class AdministradorDAOX{
        public AdministradorDAOX(Map<String,Administrador> m){
            this();
            for(Administrador med : m.values())
                administrador.add(med);
        }
        public AdministradorDAOX(){
            administrador= new ArrayList<>();
        }
        @XmlElement
        public List<Administrador> administrador;
    }
}
