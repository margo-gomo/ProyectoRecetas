package Gestores;

import Prescripcion.PrescripcionReceta;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDate;

@XmlRootElement(name = "lista_recetas")
public class GestorRecetas {

    public GestorRecetas() {
        recetas = new ArrayList<>();
    }

    public int cantidadRecetas() {
        return recetas.size();
    }

    public boolean existeReceta(int idPaciente, LocalDate fechaConfeccion) {
        for (PrescripcionReceta r : recetas) {
            if (r.getPaciente() != null
                    && r.getPaciente().getId() == idPaciente
                    && fechasIguales(r.getFecha_confeccion(), fechaConfeccion)) {
                return true;
            }
        }
        return false;
    }

    public PrescripcionReceta buscarReceta(int idPaciente, LocalDate fechaConfeccion) {
        for (PrescripcionReceta r : recetas) {
            if (r.getPaciente() != null
                    && r.getPaciente().getId() == idPaciente
                    && fechasIguales(r.getFecha_confeccion(), fechaConfeccion)) {
                return r;
            }
        }
        return null;
    }

    public boolean agregarReceta(PrescripcionReceta receta) {
        if (receta == null || receta.getPaciente() == null) return false;
        int pid = receta.getPaciente().getId();
        LocalDate fc = receta.getFecha_confeccion();
        if (!existeReceta(pid, fc)) {
            recetas.add(receta);
            return true;
        }
        return false;
    }

    public boolean modificarReceta(PrescripcionReceta recetaPorActualizar) {
        if (recetaPorActualizar == null || recetaPorActualizar.getPaciente() == null) return false;
        int pid = recetaPorActualizar.getPaciente().getId();
        LocalDate fc = recetaPorActualizar.getFecha_confeccion();
        for (int i = 0; i < recetas.size(); i++) {
            PrescripcionReceta r = recetas.get(i);
            if (r.getPaciente() != null
                    && r.getPaciente().getId() == pid
                    && fechasIguales(r.getFecha_confeccion(), fc)) {
                recetas.set(i, recetaPorActualizar);
                return true;
            }
        }
        return false;
    }

    public boolean upsertReceta(PrescripcionReceta receta) {
        if (modificarReceta(receta)) return true;
        return agregarReceta(receta);
    }

    public boolean eliminarReceta(int idPaciente, LocalDate fechaConfeccion) {
        for (int i = 0; i < recetas.size(); i++) {
            PrescripcionReceta r = recetas.get(i);
            if (r.getPaciente() != null
                    && r.getPaciente().getId() == idPaciente
                    && fechasIguales(r.getFecha_confeccion(), fechaConfeccion)) {
                recetas.remove(i);
                return true;
            }
        }
        return false;
    }

    public void guardarXML() throws Exception {
        FileOutputStream flujo = new FileOutputStream("datos/recetas.xml");
        JAXBContext context = JAXBContext.newInstance(GestorRecetas.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, flujo);
    }

    public void cargarXML() throws Exception {
        FileInputStream flujo = new FileInputStream("datos/recetas.xml");
        JAXBContext context = JAXBContext.newInstance(GestorRecetas.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GestorRecetas temp = (GestorRecetas) unmarshaller.unmarshal(flujo);
        this.recetas = temp.recetas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PrescripcionReceta r : recetas) {
            sb.append(String.format("%n\t%s,", r));
        }
        return sb.append("\n").toString();
    }

    private boolean fechasIguales(LocalDate a, LocalDate b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }

    @XmlElement(name = "receta")
    private List<PrescripcionReceta> recetas;
}
