package Prescripcion;

import Adaptador.LocalDateAdapter;
import Gestores.GestorPaciente;
import entidades.Paciente;
import entidades.Medicamento;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ToString
@XmlRootElement (name = "prescripcion-recetas")
@XmlAccessorType(XmlAccessType.FIELD)
public class PrescripcionReceta {
    public PrescripcionReceta() {
        indicaciones = new HashMap<>();
        paciente = new Paciente();
        fecha_confeccion = LocalDate.now();
        fecha_retiro = null;
        estado = "NO finalizada";
    }

    public int cantidad(){ return indicaciones.size(); }

    public List<Indicaciones> obtenerListaIndicaciones(){
        return List.copyOf(indicaciones.values());
    }

    public Indicaciones agregarIndicacion(Indicaciones indicacion) throws IllegalArgumentException {
        if(!indicaciones.containsKey(indicacion.getMedicamento().getCodigo())){
            indicaciones.putIfAbsent(indicacion.getMedicamento().getCodigo(),indicacion);
            System.out.printf("Indicacion agregado correctamente: '%s'%n", indicacion);
        }
        else
            throw new IllegalArgumentException(String.valueOf(indicaciones.get(indicacion.getMedicamento().getCodigo())));
        return indicacion;
    }

    public Indicaciones actualizarIndicacion(Indicaciones indicacion) throws IllegalArgumentException {
        if(indicaciones.containsKey(indicacion.getMedicamento().getCodigo())){
            indicaciones.put(indicacion.getMedicamento().getCodigo(),indicacion);
            System.out.printf("Indicacion actualizada correctamente: '%s'%n", indicacion);
        }
        else
            throw new IllegalArgumentException(String.valueOf(indicaciones.get(indicacion.getMedicamento().getCodigo())));
        return indicacion;
    }

    public Indicaciones eliminarIndicacion(int codigo) throws IllegalArgumentException {
        Indicaciones indicacion = indicaciones.remove(codigo);
        if(indicacion!=null)
            System.out.printf("Indicacion eliminada correctamente: '%s'%n", indicacion);
        else
            throw new IllegalArgumentException(String.valueOf(codigo));
        return indicacion;
    }

    public Paciente agregarPaciente(GestorPaciente gestorPaciente, int id) throws IllegalArgumentException {
        if (gestorPaciente != null){
            paciente = gestorPaciente.buscarPorId(id);
            if (paciente != null)
                System.out.printf("Paciente agregado correctamente: '%s'%n", paciente);
            else
                throw new IllegalArgumentException(String.valueOf(id));
        }
        else
            throw new IllegalArgumentException(String.valueOf(gestorPaciente));
        return paciente;
    }

    public Paciente agregarPaciente(GestorPaciente gestorPaciente, String nombre) throws IllegalArgumentException {
        if (gestorPaciente != null){
            paciente = gestorPaciente.buscarPorNombre(nombre);
            if (paciente != null)
                System.out.printf("Paciente agregado correctamente: '%s'%n", paciente);
            else
                throw new IllegalArgumentException(nombre);
        }
        else
            throw new IllegalArgumentException(gestorPaciente.toString());
        return paciente;
    }

    public void estadoConfeccionado() {
        estado = "confeccionada";
    }

    @XmlElement
    @Getter
    private final Map<Integer, Indicaciones> indicaciones;

    @XmlElement
    @Getter
    private Paciente paciente;

    @Getter
    @Setter
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha_confeccion;

    @Getter
    @Setter
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha_retiro;

    @Getter
    @Setter
    private String estado;

    public List<Medicamento> getMedicamentos() {
        return indicaciones.values()
                .stream()
                .map(Indicaciones::getMedicamento)
                .collect(Collectors.toList());
    }
}
