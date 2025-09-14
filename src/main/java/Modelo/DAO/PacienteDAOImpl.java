package Modelo.DAO;

import Adaptador.XMLUtils;
import Modelo.entidades.Paciente;
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

public class PacienteDAOImpl implements PacienteDAO {
    public PacienteDAOImpl() {
        pacientes=new HashMap<>();
    }
    @Override
    public int cantidad() {
        return pacientes.size();
    }

    @Override
    public List<Paciente> obtenerListaPacientes() {
        return List.copyOf(pacientes.values());
    }

    @Override
    public Paciente buscarPorId(int id) {
        return pacientes.get(id);
    }

    @Override
    public Paciente buscarPorNombre(String nombre) {
        String needle = nombre.toLowerCase();
        for (Paciente paciente : pacientes.values()){
            String n = paciente.getNombre();
            if (n != null && n.toLowerCase().contains(needle)) return paciente;
        }
        return null;
    }

    @Override
    public Paciente agregar(Paciente paciente) throws IllegalArgumentException {
        if(!pacientes.containsKey(paciente.getId())){
            pacientes.putIfAbsent(paciente.getId(),paciente);
            System.out.printf("Paciente agregado correctamente: '%s'%n", paciente);
        }
        else
            throw new IllegalArgumentException("Ya existe un paciente con el mismo ID");
        return paciente;
    }

    @Override
    public Paciente actualizar(Paciente paciente) throws IllegalArgumentException {
        if(pacientes.containsKey(paciente.getId())){
            pacientes.put(paciente.getId(),paciente);
            System.out.printf("Paciente actualizado correctamente: '%s'%n", paciente);
        }
        else
            throw new IllegalArgumentException("No existe un paciente con ese ID");
        return paciente;
    }

    @Override
    public Paciente eliminar(int id) throws IllegalArgumentException {
        Paciente paciente=pacientes.remove(id);
        if(paciente!=null)
            System.out.printf("Paciente eliminado correctamente: '%s'%n", paciente);
        else
            throw new IllegalArgumentException("No existe un paciente con ese ID");
        return paciente;
    }
    
    public void cargar (InputStream in) throws JAXBException {
        PacienteDAOX pac=(PacienteDAOX) XMLUtils.loadFromXML(in, PacienteDAOX.class);
        pacientes.clear();
        for(Paciente p : pac.paciente)
            pacientes.putIfAbsent(p.getId(),p);
    }
    public void guardar (OutputStream out) throws JAXBException {
        PacienteDAOX pac=new PacienteDAOX(pacientes);
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(pac));
        }
    }
    private final Map<Integer,Paciente> pacientes;
    @XmlRootElement(name = "lista_pacientes")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class PacienteDAOX{
        public PacienteDAOX(Map<Integer,Paciente> p){
            this();
            for(Paciente pac : p.values())
                paciente.add(pac);
        }
        public PacienteDAOX(){
            paciente= new ArrayList<>();
        }
        @XmlElement
        public List<Paciente> paciente;
    }
}