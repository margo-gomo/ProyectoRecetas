package Modelo.DAO;

import Adaptador.XMLUtils;
import Modelo.entidades.Farmaceuta;
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

public class FarmaceutaDAOImpl implements FarmaceutaDAO {
    public FarmaceutaDAOImpl() {
        farmaceutas=new HashMap<>();
    }
    @Override
    public int cantidad() {
        return farmaceutas.size();
    }

    @Override
    public List<Farmaceuta> obtenerListaFarmaceutas() {
        return List.copyOf(farmaceutas.values());
    }

    @Override
    public Farmaceuta buscarPorId(String id) {
        return farmaceutas.get(id);
    }

    @Override
    public Farmaceuta buscarPorNombre(String nombre) {
        String needle = nombre.toLowerCase();
        for (Farmaceuta farmaceuta : farmaceutas.values()){
            String n = farmaceuta.getNombre();
            if (n != null && n.toLowerCase().contains(needle)) return farmaceuta;
        }
        return null;
    }

    @Override
    public Farmaceuta agregar(Farmaceuta farmaceuta) throws IllegalArgumentException {
        if(!farmaceutas.containsKey(farmaceuta.getId())){
            farmaceutas.putIfAbsent(farmaceuta.getId(),farmaceuta);
            System.out.printf("Farmaceuta agregado correctamente: '%s'%n", farmaceuta);
        }
        else
            throw new IllegalArgumentException(farmaceuta.getId());
        return farmaceuta;
    }

    @Override
    public Farmaceuta actualizar(Farmaceuta farmaceuta) throws IllegalArgumentException {
        if(farmaceutas.containsKey(farmaceuta.getId())){
            farmaceutas.put(farmaceuta.getId(),farmaceuta);
            System.out.printf("Farmaceuta actualizado correctamente: '%s'%n", farmaceuta);
        }
        else
            throw new IllegalArgumentException(farmaceuta.getId());
        return farmaceuta;
    }

    @Override
    public Farmaceuta eliminar(String id) throws IllegalArgumentException {
        Farmaceuta farmaceuta=farmaceutas.remove(id);
        if(farmaceuta!=null)
            System.out.printf("Farmaceuta eliminado correctamente: '%s'%n", farmaceuta);
        else
            throw new IllegalArgumentException(id);
        return farmaceuta;
    }

    @Override
    public Farmaceuta cambiarClave(String id, String claveActual, String claveNueva, String claveConfirmar) throws IllegalArgumentException {
        Farmaceuta farmaceuta=farmaceutas.get(id);
        if(farmaceuta!=null){
            if(farmaceuta.getClave().equals(claveActual)){
                if(claveNueva.equals(claveConfirmar)){
                    farmaceuta.setClave(claveConfirmar);
                    if(farmaceuta.getClave()!=null) {
                        actualizar(farmaceuta);
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
        return farmaceuta;
    }
    public void cargar (InputStream in) throws JAXBException {
        FarmaceutaDAOX far=(FarmaceutaDAOX) XMLUtils.loadFromXML(in, FarmaceutaDAOX.class);
        farmaceutas.clear();
        for(Farmaceuta f : far.farmaceuta)
            farmaceutas.putIfAbsent(f.getId(),f);
    }
    public void guardar (OutputStream out) throws JAXBException {
        FarmaceutaDAOX med=new FarmaceutaDAOX();
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(med));
        }
    }
    private final Map<String,Farmaceuta> farmaceutas;
    @XmlRootElement(name = "lista_farmaceutas")
    @XmlAccessorType(XmlAccessType.FIELD)
    class FarmaceutaDAOX{
        public FarmaceutaDAOX(Map<String,Farmaceuta> f){
            this();
            for(Farmaceuta far : f.values())
                farmaceuta.add(far);
        }
        public FarmaceutaDAOX(){
            farmaceuta= new ArrayList<>();
        }
        @XmlElement
        public List<Farmaceuta> farmaceuta;
    }
}
