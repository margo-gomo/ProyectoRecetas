package Prescripcion;
import Adaptador.LocalDateAdapter;
import Gestores.GestorPaciente;
import entidades.Medicamento;
import entidades.Paciente;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import Gestores.GestorMedicamento;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@XmlRootElement (name = "prescripcion-recetas")
public class PrescripcionReceta {
    public PrescripcionReceta() {
        indicacciones = new ArrayList<>();
        paciente= new Paciente();
        fecha_confeccion= LocalDate.now();
        fecha_retiro = null;
    }
    public boolean agregarPaciente(GestorPaciente gestorPaciente, int id) {
        if (gestorPaciente == null) return false;
        paciente=gestorPaciente.buscarPacienteID(id);
        return paciente != null;
    }
    public boolean agregarPaciente(GestorPaciente gestorPaciente, String nombre) {
        if (gestorPaciente == null) return false;
        paciente=gestorPaciente.buscarPacienteNombre(nombre);
        return paciente != null;
    }
    public boolean agregarMedicamento(GestorMedicamento gestorMedicamento, int codigo, int cantidad, String descripcion, int duracion) {
        if (gestorMedicamento == null) return false;
        Medicamento medicamento=gestorMedicamento.buscarMedicamentoCodigo(codigo);
        if(medicamento==null) return false;
        indicacciones.add(new Indicacciones(medicamento,cantidad,descripcion,duracion));
        return true;
    }
    @XmlElement (name = "indicacciones")
    private List<Indicacciones> indicacciones;
    private Paciente paciente;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha_confeccion;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha_retiro;
}
