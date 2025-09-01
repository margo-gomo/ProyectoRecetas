package Prescripcion;

import Adaptador.LocalDateAdapter;
import Gestores.GestorPaciente;
import entidades.Paciente;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@XmlRootElement (name = "prescripcion-recetas")
@XmlAccessorType(XmlAccessType.FIELD)
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

    // Modifica toda una indicación por código de medicamento
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

    @Getter
    private List<Indicaciones> indicaciones;

    private Paciente paciente;

    @Getter
    @XmlAttribute(name = "fecha_confeccion")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha_confeccion;

    @Getter
    @XmlAttribute(name = "fecha_retiro")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha_retiro;

    @Getter
    String estado;
}
