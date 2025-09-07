package DAO;
import entidades.Administrador;
import Adaptador.XMLUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.JAXBException;
import java.io.PrintWriter;
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

    @Override
    public Administrador actualizar(Administrador administrador) throws IllegalArgumentException {
        if(administradores.containsKey(administrador.getId())){
            administradores.put(administrador.getId(),administrador);
            System.out.printf("Administrador actualizado correctamente: '%s'%n", administrador);
        }
        else
            throw new IllegalArgumentException(administrador.getId());
        return administrador;
    }

    @Override
    public Administrador eliminar(String id) throws IllegalArgumentException {
        Administrador administrador=administradores.remove(id);
        if(administrador!=null)
            System.out.printf("Administrador eliminado correctamente: '%s'%n", administrador);
        else
            throw new IllegalArgumentException(id);
        return administrador;
    }

    @Override
    public Administrador cambiarClave(String id, String clave) throws IllegalArgumentException {
        Administrador administrador=administradores.get(id);
        if(administrador!=null){
            administrador.setClave(clave);
            if(administrador.getClave()!=null) {
                actualizar(administrador);
                System.out.printf("Clave cambiada correctamente: '%s'%n", clave);
            }
            else
                throw new IllegalArgumentException(clave);
        }
        else
            throw new IllegalArgumentException(id);
        return administrador;
    }
    public void cargar (InputStream in) throws JAXBException {
        AdministradorDAOX med=(AdministradorDAOX) XMLUtils.loadFromXML(in, AdministradorDAOX.class);
        administradores.clear();
        for(Administrador m : med.administrador)
            administradores.putIfAbsent(m.getId(),m);
    }
    public void guardar (OutputStream out) throws JAXBException {
        AdministradorDAOX med=new AdministradorDAOX();
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(med));
        }
    }
    private final Map<String,Administrador> administradores;
    @XmlRootElement(name = "administradores")
    @XmlAccessorType(XmlAccessType.FIELD)
    class AdministradorDAOX{
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
