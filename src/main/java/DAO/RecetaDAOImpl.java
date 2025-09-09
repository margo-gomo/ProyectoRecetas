package DAO;

import Prescripcion.PrescripcionReceta;

import java.time.LocalDate;
import Adaptador.XMLUtils;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.JAXBException;
import java.io.PrintWriter;
import lombok.AllArgsConstructor;
import lombok.ToString;

public class RecetaDAOImpl implements RecetaDAO {
    public RecetaDAOImpl() {
        recetas=new HashMap<>();
    }
    @Override
    public int cantidad() {
        return recetas.size();
    }

    @Override
    public List<PrescripcionReceta> obtenerListaPrescripcionRecetas() {
        return List.copyOf(recetas.values());
    }

    @Override
    public PrescripcionReceta buscarReceta(int idPaciente, LocalDate fecha_confeccion) {
        ClaveCompuesta clave=new ClaveCompuesta(idPaciente,fecha_confeccion);
        return recetas.get(clave);
    }

    @Override
    public PrescripcionReceta agregar(PrescripcionReceta receta) throws IllegalArgumentException {
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
    public PrescripcionReceta actualizar(PrescripcionReceta receta) throws IllegalArgumentException {
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
    public PrescripcionReceta eliminar(int idPaciente, LocalDate fecha_confeccion) throws IllegalArgumentException {
        ClaveCompuesta clave=new ClaveCompuesta(idPaciente,fecha_confeccion);
        PrescripcionReceta receta=recetas.remove(clave);
        if(receta != null)
            System.out.printf("Receta eliminada correctamente: '%s'%n", receta);
        else
            throw new IllegalArgumentException(clave.toString());
        return receta;
    }
    public void cargar (InputStream in) throws JAXBException {
        RecetasDAOX rec=(RecetasDAOX) XMLUtils.loadFromXML(in, RecetasDAOX.class);
        recetas.clear();
        for(PrescripcionReceta r : rec.receta) {
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
    private final Map<ClaveCompuesta,PrescripcionReceta> recetas;

    @XmlRootElement(name = "lista_recetas")
    @XmlAccessorType(XmlAccessType.FIELD)
    class RecetasDAOX{
        public RecetasDAOX(Map<ClaveCompuesta,PrescripcionReceta> r) {
            this();
            for(PrescripcionReceta rec:r.values())
                receta.add(rec);
        }
        public RecetasDAOX() {
            receta=new ArrayList<>();
        }
        @XmlElement
        public List<PrescripcionReceta> receta;
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
