package Modelo.DAO;

import Adaptador.XMLUtils;
import Modelo.entidades.Receta.Receta;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.ToString;

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
        ClaveCompuesta clave=new ClaveCompuesta(idPaciente,fecha_confeccion);
        return recetas.get(clave);
    }

    @Override
    public Receta agregar(Receta receta) throws IllegalArgumentException {
        ClaveCompuesta clave=new ClaveCompuesta(receta.getPaciente().getId(),receta.getFecha_confeccion());
        if(!recetas.containsKey(clave)){
            receta.estadoConfeccionado();
            recetas.putIfAbsent(clave,receta);
            System.out.printf("Receta agregada correctamente: '%s'%n", receta);
        }
        else
            throw new IllegalArgumentException(clave.toString());
        return receta;
    }

    @Override
    public Receta actualizar(Receta receta) throws IllegalArgumentException {
        ClaveCompuesta clave=new ClaveCompuesta(receta.getPaciente().getId(),receta.getFecha_confeccion());
        if(recetas.containsKey(clave)){
            recetas.put(clave,receta);
            System.out.printf("Receta actualizada correctamente: '%s'%n", receta);
        }
        else
            throw new IllegalArgumentException(clave.toString());
        return receta;
    }

    @Override
    public Receta eliminar(int idPaciente, LocalDate fecha_confeccion) throws IllegalArgumentException {
        ClaveCompuesta clave=new ClaveCompuesta(idPaciente,fecha_confeccion);
        Receta receta=recetas.remove(clave);
        if(receta != null)
            System.out.printf("Receta eliminada correctamente: '%s'%n", receta);
        else
            throw new IllegalArgumentException(clave.toString());
        return receta;
    }
    public void cargar (InputStream in) throws JAXBException {
        RecetasDAOX rec=(RecetasDAOX) XMLUtils.loadFromXML(in, RecetasDAOX.class);
        recetas.clear();
        for(Receta r : rec.receta) {
            ClaveCompuesta clave = new ClaveCompuesta(r.getPaciente().getId(), r.getFecha_confeccion());
            recetas.putIfAbsent(clave, r);
        }
    }
    public void guardar (OutputStream out) throws JAXBException {
        RecetasDAOX rec=new RecetasDAOX();
        try(PrintWriter printwriter=new PrintWriter(out)){
            printwriter.println(XMLUtils.toXMLString(rec));
        }
    }
    private final Map<ClaveCompuesta,Receta> recetas;

    @XmlRootElement(name = "lista_recetas")
    @XmlAccessorType(XmlAccessType.FIELD)
    class RecetasDAOX{
        public RecetasDAOX(Map<ClaveCompuesta,Receta> r) {
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

    @AllArgsConstructor
    @ToString
    class ClaveCompuesta {
        private final int idPaciente;
        private final LocalDate fecha_confeccion;

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof ClaveCompuesta))
                return false;
            ClaveCompuesta other = (ClaveCompuesta) obj;
            return Objects.equals(idPaciente, other.idPaciente) && Objects.equals(fecha_confeccion, other.fecha_confeccion);
        }
        @Override
        public int hashCode() {
            int hash=5;
            hash=29 * hash + Objects.hashCode(this.idPaciente);
            hash=29 * hash + Objects.hashCode(this.fecha_confeccion);
            return hash;
        }

    }
}
