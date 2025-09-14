package Modelo.DAO;

import Adaptador.XMLUtils;
import Modelo.entidades.Medico;
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

public class MedicoDAOImpl implements MedicoDAO {
    public MedicoDAOImpl() {
        medicos=new HashMap<>();
    }
    @Override
    public int cantidad() {
        return medicos.size();
    }

    @Override
    public List<Medico> obtenerListaMedicos() {
        return List.copyOf(medicos.values());
    }

    @Override
    public Medico buscarPorId(String id) {
        return medicos.get(id);
    }

    @Override
    public Medico buscarPorNombre(String nombre) {
        String needle = nombre.toLowerCase();
        for (Medico medico : medicos.values()){
            String n = medico.getNombre();
            if (n != null && n.toLowerCase().contains(needle)) return medico;
        }
        return null;
    }

    @Override
    public Medico agregar(Medico medico) throws IllegalArgumentException {
        if(!medicos.containsKey(medico.getId())){
            medicos.putIfAbsent(medico.getId(),medico);
            System.out.printf("Medico agregado correctamente: '%s'%n", medico);
        }
        else
            throw new IllegalArgumentException("Ya existe un médico con el mismo ID");
        return medico;
    }

    @Override
    public Medico actualizar(Medico medico) throws IllegalArgumentException {
        if(medicos.containsKey(medico.getId())){
            medicos.put(medico.getId(),medico);
            System.out.printf("Medico actualizado correctamente: '%s'%n", medico);
        }
        else
            throw new IllegalArgumentException("No existe un médico con ese ID");
        return medico;
    }

    @Override
    public Medico eliminar(String id) throws IllegalArgumentException {
        Medico medico=medicos.remove(id);
        if(medico!=null)
            System.out.printf("Medico eliminado correctamente: '%s'%n", medico);
        else
            throw new IllegalArgumentException("No existe un médico con ese ID");
        return medico;
    }

    @Override
    public Medico cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar) throws IllegalArgumentException {
        Medico medico=medicos.get(id);
        if(medico!=null){
            if(medico.getClave().equals(claveActual)){
                if(claveNueva.equals(claveConfirmar)){
                    medico.setClave(claveConfirmar);
                    if(medico.getClave()!=null) {
                        actualizar(medico);
                        System.out.printf("Clave cambiada correctamente: '%s'%n", claveConfirmar);
                    }
                    else
                        throw new IllegalArgumentException(claveNueva);
                }
                else
                    throw new IllegalArgumentException(claveConfirmar);
            }
            else
                throw new IllegalArgumentException(claveActual);
        }
        else
            throw new IllegalArgumentException(id);
        return medico;
    }
    public void cargar (InputStream in) throws JAXBException {
        MedicoDAOX med=(MedicoDAOX) XMLUtils.loadFromXML(in,MedicoDAOX.class);
        medicos.clear();
        for(Medico m : med.medico)
            medicos.putIfAbsent(m.getId(),m);
    }
    public void guardar (OutputStream out) throws JAXBException {
        MedicoDAOX med=new MedicoDAOX(medicos);
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(med));
        }
    }
    private final Map<String,Medico> medicos;
    @XmlRootElement(name = "lista_medicos")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class MedicoDAOX{
        public MedicoDAOX(Map<String,Medico> m){
            this();
            for(Medico med : m.values())
                medico.add(med);
        }
        public MedicoDAOX(){
            medico= new ArrayList<>();
        }
        @XmlElement
        public List<Medico> medico;
    }
}
