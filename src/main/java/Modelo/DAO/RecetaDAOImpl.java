package Modelo.DAO;

import Adaptador.XMLUtils;
import Modelo.entidades.Receta.Receta;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;

public class RecetaDAOImpl implements RecetaDAO {
    public RecetaDAOImpl() {
        recetas=new HashMap<>();
    }
    @Override
    public int cantidad() {
        return recetas.size();
    }

    @Override
    public List<Receta> obtenerListaRecetas() {
        return List.copyOf(recetas.values());
    }

    @Override
    public Receta buscarReceta(int idPaciente, LocalDate fecha_confeccion) {
        for (Receta receta : recetas.values()) {
            if(receta.getFecha_confeccion().isEqual(fecha_confeccion)&&receta.getPaciente().getId()==idPaciente){
                return receta;
            }
        }
        return null;
    }

    @Override
    public Receta buscarRecetaPorCodigo(String codigo) {
        return recetas.get(codigo);
    }

    @Override
    public Receta agregar(Receta receta) throws IllegalArgumentException {
        if(!recetas.containsKey(receta.getCodigo())){
            receta.estadoConfeccionado();
            recetas.putIfAbsent(receta.getCodigo(),receta);
            System.out.printf("Receta agregada correctamente: '%s'%n", receta);
        }
        else
            throw new IllegalArgumentException(receta.toString());
        return receta;
    }

    @Override
    public Receta actualizar(Receta receta) throws IllegalArgumentException {
        if(recetas.containsKey(receta.getCodigo())){
            recetas.put(receta.getCodigo(),receta);
            System.out.printf("Receta actualizada correctamente: '%s'%n", receta);
        }
        else
            throw new IllegalArgumentException(receta.toString());
        return receta;
    }

    @Override
    public Receta eliminar(String codigo) throws IllegalArgumentException {
        Receta receta=recetas.remove(codigo);
        if(receta != null)
            System.out.printf("Receta eliminada correctamente: '%s'%n", receta);
        else
            throw new IllegalArgumentException(codigo);
        return receta;
    }

    public void cargar (InputStream in) throws JAXBException {
        RecetasDAOX rec=(RecetasDAOX) XMLUtils.loadFromXML(in, RecetasDAOX.class);
        recetas.clear();
        for(Receta r : rec.receta) {
            recetas.putIfAbsent(r.getCodigo(), r);
        }
    }
    public void guardar (OutputStream out) throws JAXBException {
        RecetasDAOX rec=new RecetasDAOX();
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(rec));
        }
    }

    private final Map<String,Receta> recetas;

    @XmlRootElement(name = "lista_recetas")
    @XmlAccessorType(XmlAccessType.FIELD)
    class RecetasDAOX{
        public RecetasDAOX(Map<String,Receta> r) {
            this();
            for(Receta rec:r.values())
                receta.add(rec);
        }
        public RecetasDAOX() {
            receta=new ArrayList<>();
        }
        @XmlElement
        public List<Receta> receta;
    }

}
