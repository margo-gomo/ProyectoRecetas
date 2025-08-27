package Gestores;
import entidades.Paciente;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "lista_paciente")
public class GestorPaciente {
    public GestorPaciente() {
        pacientes = new ArrayList<>();
    }
    public int cantidadPacientes() {
        return pacientes.size();
    }
    public boolean existePaciente(int id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId() == id) {
                return true;
            }
        }
        return false;
    }
    public Paciente buscarPacienteID(int id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId() == id) {
                return paciente;
            }
        }
        return null;
    }
    public Paciente buscarPacienteNombre(String nombre) {
        for (Paciente paciente : pacientes) {
            if (paciente.getNombre().equals(nombre)) {
                return paciente;
            }
        }
        return null;
    }
    public boolean aregarPaciente(Paciente paciente) {
        if (!existePaciente(paciente.getId())) {
            pacientes.add(paciente);
            return true;
        }
        return false;
    }
    public boolean modificarPaciente(Paciente pacienteporactualizar) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == pacienteporactualizar.getId()) {
                pacientes.set(i, pacienteporactualizar);
                return true;
            }
        }
        return false;
    }
    public boolean eliminarPaciente(int id) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == id) {
                pacientes.remove(i);
                return true;
            }
        }
        return false;
    }
    public void guardarXML(OutputStream flujo) throws Exception {
        JAXBContext context = JAXBContext.newInstance(GestorPaciente.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, flujo);
    }

    public void cargarXML(InputStream flujo) throws Exception {
        JAXBContext context = JAXBContext.newInstance(GestorPaciente.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GestorPaciente temp = (GestorPaciente) unmarshaller.unmarshal(flujo);
        this.pacientes = temp.pacientes;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Paciente paciente : pacientes) {
            sb.append(String.format("%n\t%s,", paciente));
        }
        sb.append("\n");
        return sb.toString();
    }
    @XmlElement(name = "paciente")
    private List<Paciente> pacientes;
}