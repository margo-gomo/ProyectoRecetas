package Gestores;

import entidades.Medico;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "lista-medicos")
public class GestorMedico {

    public GestorMedico() {
        // Lista en memoria para los médicos
        medicos = new ArrayList<>();
    }

    // Cantidad total de médicos
    public int cantidadMedicos() {
        return medicos.size();
    }

    // Verifica si existe un médico con el ID indicado
    public boolean existeMedico(int id) {
        for (Medico medico : medicos) {
            if (medico.getId() == id) return true;
        }
        return false;
    }

    // Busca un médico por ID exacto (o null si no existe)
    public Medico buscarMedicoID(int id) {
        for (Medico medico : medicos) {
            if (medico.getId() == id) return medico;
        }
        return null;
    }

    // Busca por nombre con coincidencia aproximada (ignorando mayúsculas/minúsculas)
    public Medico buscarMedicoNombre(String nombre) {
        if (nombre == null) return null;
        String needle = nombre.toLowerCase();
        for (Medico medico : medicos) {
            String n = medico.getNombre();
            if (n != null && n.toLowerCase().contains(needle)) return medico;
        }
        return null;
    }

    // Agrega un médico si el ID no está repetido
    public boolean agregarMedico(Medico medico) {
        if (medico == null) return false;
        if (!existeMedico(medico.getId())) {
            medicos.add(medico);
            return true;
        }
        return false;
    }

    // Reemplaza los datos de un médico existente (match por ID)
    public boolean modificarMedico(Medico medicoPorActualizar) {
        if (medicoPorActualizar == null) return false;
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId() == medicoPorActualizar.getId()) {
                medicos.set(i, medicoPorActualizar);
                return true;
            }
        }
        return false;
    }

    // Elimina un médico por ID
    public boolean eliminarMedico(int id) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId() == id) {
                medicos.remove(i);
                return true;
            }
        }
        return false;
    }

    // Guarda toda la lista en datos/medicos.xml
    public void guardarXML() throws Exception {
        FileOutputStream flujo = new FileOutputStream("datos/medicos.xml");
        JAXBContext context = JAXBContext.newInstance(GestorMedico.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, flujo);
    }

    // Carga la lista completa desde datos/medicos.xml
    public void cargarXML() throws Exception {
        FileInputStream flujo = new FileInputStream("datos/medicos.xml");
        JAXBContext context = JAXBContext.newInstance(GestorMedico.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GestorMedico temp = (GestorMedico) unmarshaller.unmarshal(flujo);
        this.medicos = temp.medicos;
    }

    // String legible de la lista (debug/impresión)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Medico medico : medicos) {
            sb.append(String.format("%n\t%s,", medico));
        }
        return sb.append("\n").toString();
    }

    @XmlElement(name = "medico")
    private List<Medico> medicos;
}
