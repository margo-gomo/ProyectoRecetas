package Prescripcion;

import Adaptador.LocalDateAdapter;
import Gestores.GestorPaciente;
import entidades.Paciente;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@XmlRootElement (name = "prescripcion-recetas")
public class PrescripcionReceta {
    public PrescripcionReceta() {
        indicaciones = new ArrayList<>();
        paciente = new Paciente();
        fecha_confeccion = LocalDate.now();
        fecha_retiro = null;
        estado = "NO finalizada";
    }

    public int cantidad(){ return indicaciones.size(); }

    // Se le asigna un paciente a la receta buscándolo por id
    public boolean agregarPaciente(GestorPaciente gestorPaciente, int id) {
        if (gestorPaciente == null) return false;
        paciente = gestorPaciente.buscarPacienteID(id);
        return paciente != null;
    }

    // Se le asigna un paciente a la receta buscándolo por nombre
    public boolean agregarPaciente(GestorPaciente gestorPaciente, String nombre) {
        if (gestorPaciente == null) return false;
        paciente = gestorPaciente.buscarPacienteNombre(nombre);
        return paciente != null;
    }

    // Agrega una indicación a la receta
    public boolean agregarIndicacion(Indicaciones indicacion) {
        return indicaciones.add(indicacion);
    }

    // Modifica toda una indicación por código de medicamento  (antes: modificarIndicaccion)
    public boolean modificarIndicacion(Indicaciones indicacionPorModificar, int codigo) {
        if (indicacionPorModificar == null) return false;
        for (int i = 0; i < indicaciones.size(); i++) {
            if (indicaciones.get(i).getMedicamento().getCodigo() == codigo) {
                indicaciones.set(i, indicacionPorModificar);
                return true;
            }
        }
        return false;
    }

    // Elimina una indicación por código del medicamento
    public boolean eliminarIndicacion(int codigo) {
        for (int i = 0; i < indicaciones.size(); i++) {
            if (indicaciones.get(i).getMedicamento().getCodigo() == codigo) {
                indicaciones.remove(i);
                return true;
            }
        }
        return false;
    }

    // Cambia el estado de la receta a "NO finalizada"
    public void estadoNoFinalizado() {
        estado = "NO finalizada";
    }

    // Cambia el estado de la receta a "confeccionada"
    public void estadoConfeccionado() {
        estado = "confeccionada";
    }

    // Guarda la receta en datos/receta.xml
    public void guardarXML() throws Exception {
        FileOutputStream flujo = new FileOutputStream("datos/receta.xml");
        JAXBContext context = JAXBContext.newInstance(PrescripcionReceta.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, flujo);
    }

    // Carga la receta desde datos/receta.xml
    public void cargarXML() throws Exception {
        FileInputStream flujo = new FileInputStream("datos/receta.xml");
        JAXBContext context = JAXBContext.newInstance(PrescripcionReceta.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PrescripcionReceta temp = (PrescripcionReceta) unmarshaller.unmarshal(flujo);
        this.indicaciones = temp.indicaciones;
    }

    @XmlElement (name = "indicaciones")
    private List<Indicaciones> indicaciones;

    @XmlElement(name = "paciente")
    private Paciente paciente;

    @XmlAttribute(name = "fecha_confeccion")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha_confeccion;

    @XmlAttribute(name = "fecha_retiro")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha_retiro;

    @Getter
    @XmlElement(name = "estado")
    String estado;
}
