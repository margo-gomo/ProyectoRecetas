package Gestores;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import entidades.Medico;
@XmlRootElement(name = "lista-medicos")
public class GestorMedico {
    public GestorMedico() {
        medicos = new ArrayList<>();
    }
    public int cantidadMedicos() {
        return medicos.size();
    }
    public boolean existeMedico(int id) {
        for (Medico medico : medicos) {
            if (medico.getId() == id) {
                return true;
            }
        }
        return false;
    }
    public Medico buscarMedicoID(int id) {
        for (Medico medico : medicos) {
            if (medico.getId() == id) {
                return medico;
            }
        }
        return null;
    }
    public Medico buscarMedicoNombre(String nombre) {
        for (Medico medico : medicos) {
            if (medico.getNombre().equals(nombre)) {
                return medico;
            }
        }
        return null;
    }
    public boolean aregarMedico(Medico medico) {
        if (!existeMedico(medico.getId())) {
            medicos.add(medico);
            return true;
        }
        return false;
    }
    public boolean modificarMedico(Medico pacienteporactualizar) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId() == pacienteporactualizar.getId()) {
                medicos.set(i, pacienteporactualizar);
                return true;
            }
        }
        return false;
    }
    public boolean eliminarMedico(int id) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId() == id) {
                medicos.remove(i);
                return true;
            }
        }
        return false;
    }
    public void guardarXML(OutputStream flujo) throws Exception {
        JAXBContext context = JAXBContext.newInstance(GestorMedico.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, flujo);
    }

    public void cargarXML(InputStream flujo) throws Exception {
        JAXBContext context = JAXBContext.newInstance(GestorMedico.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GestorMedico temp = (GestorMedico) unmarshaller.unmarshal(flujo);
        this.medicos = temp.medicos;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Medico medico : medicos) {
            sb.append(String.format("%n\t%s,", medico));
        }
        sb.append("\n");
        return sb.toString();
    }
    @XmlElement(name = "medico")
    private List <Medico> medicos;
}
