package Modelo.DAO;

import Adaptador.XMLUtils;
import Modelo.entidades.Medicamento;
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
public class MedicamentoDAOImpl implements MedicamentoDAO {
    public MedicamentoDAOImpl() {
        medicamentos=new HashMap<>();
    }
    @Override
    public int cantidad() {
        return medicamentos.size();
    }

    @Override
    public List<Medicamento> obtenerListaMedicamentos() {
        return List.copyOf(medicamentos.values());
    }

    @Override
    public Medicamento buscarPorCodigo(int codigo) {
        return medicamentos.get(codigo);
    }

    @Override
    public Medicamento buscarPorNombre(String nombre) {
        return medicamentos.get(nombre);
    }

    @Override
    public Medicamento buscarPorDescripcion(String descripcion) {
        String needle = descripcion.toLowerCase();
        for (Medicamento medicamento : medicamentos.values()){
            String n = medicamento.getDescripcion();
            if (n != null && n.toLowerCase().contains(needle)) return medicamento;
        }
        return null;
    }

    @Override
    public Medicamento agregar(Medicamento medicamento) throws IllegalArgumentException {
        if(!medicamentos.containsKey(medicamento.getCodigo())){
            medicamentos.putIfAbsent(medicamento.getCodigo(),medicamento);
            System.out.printf("Medicamento agregado correctamente: '%s'%n", medicamento);
        }
        else
            throw new IllegalArgumentException("Ya existe un medicamento con el mismo codigo");
        return medicamento;
    }

    @Override
    public Medicamento actualizar(Medicamento medicamento) throws IllegalArgumentException {
        if(medicamentos.containsKey(medicamento.getCodigo())){
            medicamentos.put(medicamento.getCodigo(),medicamento);
            System.out.printf("Medicamento actualizado correctamente: '%s'%n", medicamento);
        }
        else
            throw new IllegalArgumentException("No existe un medicamento con ese codigo");
        return medicamento;
    }

    @Override
    public Medicamento eliminar(int codigo) throws IllegalArgumentException {
        Medicamento medicamento=medicamentos.remove(codigo);
        if(medicamento!=null)
            System.out.printf("Medicamento eliminado correctamente: '%s'%n", medicamento);
        else
            throw new IllegalArgumentException("No existe un medicamento con ese codigo");
        return medicamento;
    }

    public void cargar (InputStream in) throws JAXBException {
        MedicamentoDAOX med=(MedicamentoDAOX) XMLUtils.loadFromXML(in, MedicamentoDAOX.class);
        medicamentos.clear();
        for(Medicamento m : med.medicamento)
            medicamentos.putIfAbsent(m.getCodigo(),m);
    }
    public void guardar (OutputStream out) throws JAXBException {
        MedicamentoDAOX med=new MedicamentoDAOX(medicamentos);
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(med));
        }
    }
    private final Map<Integer,Medicamento> medicamentos;
    @XmlRootElement(name = "lista_medicamentos")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class MedicamentoDAOX{
        public MedicamentoDAOX(Map<Integer,Medicamento> m){
            this();
            for(Medicamento med : m.values())
                medicamento.add(med);
        }
        public MedicamentoDAOX(){
            medicamento= new ArrayList<>();
        }
        @XmlElement
        public List<Medicamento> medicamento;
    }
}
