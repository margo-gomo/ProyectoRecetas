package Gestores;

import Prescripcion.PrescripcionReceta;
import DAO.RecetaDAO;
import java.time.LocalDate;
import java.util.List;
import java.io.File;


public class GestorRecetas {

    public GestorRecetas(RecetaDAO recetas) {
        this.recetas = recetas;
    }

    public int cantidad() {
        return recetas.cantidad();
    }
    public List<PrescripcionReceta> obtenerListaRecetas() {
        return recetas.obtenerListaPrescripcionRecetas();
    }

    public boolean existeReceta(int idPaciente, LocalDate fechaConfeccion) {
        return recetas.buscarReceta(idPaciente, fechaConfeccion)!=null;
    }

    public PrescripcionReceta buscarReceta(int idPaciente, LocalDate fechaConfeccion) {
        return recetas.buscarReceta(idPaciente, fechaConfeccion);
    }

    public PrescripcionReceta agregar(PrescripcionReceta receta) throws IllegalArgumentException {
        return  recetas.agregar(receta);
    }

    public PrescripcionReceta actualizar(PrescripcionReceta recetaPorActualizar) throws IllegalArgumentException {
        return recetas.actualizar(recetaPorActualizar);
    }

    public PrescripcionReceta eliminar(int idPaciente, LocalDate fechaConfeccion) throws IllegalArgumentException {
        return recetas.eliminar(idPaciente, fechaConfeccion);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (PrescripcionReceta receta : recetas.obtenerListaPrescripcionRecetas()) {
            sb.append(String.format("%n\t%s,", receta));
        }
        sb.append("\n]");
        return sb.toString();
    }

    private boolean fechasIguales(LocalDate a, LocalDate b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }
    public PrescripcionReceta upsertReceta(PrescripcionReceta receta) throws IllegalArgumentException {
        PrescripcionReceta existente = recetas.buscarReceta(receta.getPaciente().getId(), receta.getFecha_confeccion());
        if (existente != null) {
            return recetas.actualizar(receta);
        } else {
            return recetas.agregar(receta);
        }
    }
    public void cargarXML(String rutaArchivo) throws IllegalArgumentException {
        try {
            jakarta.xml.bind.JAXBContext context = jakarta.xml.bind.JAXBContext.newInstance(RecetaDAO.class);
            jakarta.xml.bind.Unmarshaller unmarshaller = context.createUnmarshaller();
            RecetaDAO cargado = (RecetaDAO) unmarshaller.unmarshal(new java.io.File(rutaArchivo));
            this.recetas = cargado;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al cargar XML: " + e.getMessage(), e);
        }
    }
    public void guardarXML(String rutaArchivo) throws IllegalArgumentException {
        try {
            jakarta.xml.bind.JAXBContext context = jakarta.xml.bind.JAXBContext.newInstance(RecetaDAO.class);
            jakarta.xml.bind.Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(recetas, new java.io.File(rutaArchivo));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al guardar XML: " + e.getMessage(), e);
        }
    }


    private RecetaDAO recetas;
}
