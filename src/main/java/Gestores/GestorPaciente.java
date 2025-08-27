package Gestores;

import entidades.Paciente;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        // Lista en memoria donde se guardan los pacientes
        pacientes = new ArrayList<>();
    }

    // Devuelve cuántos pacientes hay registrados
    public int cantidadPacientes() {
        return pacientes.size();
    }

    // Verifica si existe un paciente con el ID indicado
    public boolean existePaciente(int id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId() == id) return true;
        }
        return false;
    }

    // Busca un paciente por ID exacto (o null si no existe)
    public Paciente buscarPacienteID(int id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId() == id) return paciente;
        }
        return null;
    }

    // Busca por nombre con coincidencia aproximada (ignorando mayúsculas/minúsculas)
    public Paciente buscarPacienteNombre(String nombre) {
        if (nombre == null) return null;
        String needle = nombre.toLowerCase();
        for (Paciente paciente : pacientes) {
            String n = paciente.getNombre();
            if (n != null && n.toLowerCase().contains(needle)) return paciente;
        }
        return null;
    }

    // Agrega un paciente si no hay otro con el mismo ID
    public boolean agregarPaciente(Paciente paciente) {
        if (paciente == null) return false;
        if (!existePaciente(paciente.getId())) {
            pacientes.add(paciente);
            return true;
        }
        return false;
    }

    // Reemplaza los datos del paciente que tenga el mismo ID
    public boolean modificarPaciente(Paciente pacientePorActualizar) {
        if (pacientePorActualizar == null) return false;
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == pacientePorActualizar.getId()) {
                pacientes.set(i, pacientePorActualizar);
                return true;
            }
        }
        return false;
    }

    // Elimina un paciente por ID
    public boolean eliminarPaciente(int id) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == id) {
                pacientes.remove(i);
                return true;
            }
        }
        return false;
    }

    // Guarda toda la lista en datos/pacientes.xml
    public void guardarXML() throws Exception {
        FileOutputStream flujo = new FileOutputStream("datos/pacientes.xml");
        JAXBContext context = JAXBContext.newInstance(GestorPaciente.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, flujo);
    }

    // Carga la lista completa desde datos/pacientes.xml
    public void cargarXML() throws Exception {
        FileInputStream flujo = new FileInputStream("datos/pacientes.xml");
        JAXBContext context = JAXBContext.newInstance(GestorPaciente.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GestorPaciente temp = (GestorPaciente) unmarshaller.unmarshal(flujo);
        this.pacientes = temp.pacientes;
    }

    // Muestra la lista en un formato legible
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Paciente paciente : pacientes) {
            sb.append(String.format("%n\t%s,", paciente));
        }
        return sb.append("\n").toString();
    }

    @XmlElement(name = "paciente")
    private List<Paciente> pacientes;
}
