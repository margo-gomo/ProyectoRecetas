package Modelo.entidades.Receta;

import Adaptador.LocalDateAdapter;
import Modelo.entidades.Paciente;
import Modelo.entidades.Medicamento;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
@ToString
public class Receta {
    public Receta() {
        indicaciones = new HashMap<>();
        paciente = new Paciente();
        fecha_confeccion = LocalDate.now();
        fecha_retiro = null;
        estado = "NO finalizada";
        codigo="";
    }

    public int cantidad(){ return indicaciones.size(); }

    public List<Indicacion> obtenerListaIndicaciones(){
        return List.copyOf(indicaciones.values());
    }

    public List<Indicacion> agregarIndicaciones(List<Indicacion> indicaciones) throws IllegalArgumentException {
        if(indicaciones == null){
            throw new IllegalArgumentException(indicaciones.toString());
        }
        for(Indicacion indicacion : indicaciones)
            this.indicaciones.put(indicacion.getMedicamento().getCodigo(), indicacion);
        return obtenerListaIndicaciones();
        /*
        if(!indicaciones.containsKey(indicacion.getMedicamento().getCodigo())){
            indicaciones.putIfAbsent(indicacion.getMedicamento().getCodigo(),indicacion);
            System.out.printf("Indicacion agregado correctamente: '%s'%n", indicacion);
        }
        else
            throw new IllegalArgumentException(String.valueOf(indicaciones.get(indicacion.getMedicamento().getCodigo())));
        return indicacion;*/
    }

    public List<Indicacion> actualizarIndicaciones(List<Indicacion> indicaciones) throws IllegalArgumentException {
        if(indicaciones == null)
            throw new IllegalArgumentException(indicaciones.toString());
        this.indicaciones.clear();
        return agregarIndicaciones(indicaciones);
        /*if(indicaciones.containsKey(indicacion.getMedicamento().getCodigo())){
            indicaciones.put(indicacion.getMedicamento().getCodigo(),indicacion);
            System.out.printf("Indicacion actualizada correctamente: '%s'%n", indicacion);
        }
        else
            throw new IllegalArgumentException(String.valueOf(indicaciones.get(indicacion.getMedicamento().getCodigo())));
        return indicacion;*/
    }

    public Indicacion eliminarIndicacion(Indicacion indicacion) throws IllegalArgumentException {
        Indicacion i = indicaciones.remove(indicacion.getMedicamento().getCodigo());
        if(i!=null)
            System.out.printf("Indicacion eliminada correctamente: '%s'%n", indicacion);
        else
            throw new IllegalArgumentException(String.valueOf(codigo));
        return indicacion;
    }

    public Paciente agregarPacienteporId(List<Paciente> pacientes, int id) throws IllegalArgumentException {
        if(pacientes.isEmpty())
            throw new IllegalArgumentException(String.valueOf(pacientes));
        for(Paciente p: pacientes){
            if(p.getId()==id){
                paciente=p;
                System.out.printf("Paciente agregado correctamente: '%s'%n", paciente);
            }
        }
        if(paciente.getId()!=id)
            throw new IllegalArgumentException(String.valueOf(pacientes));
        return paciente;
    }

    public Paciente agregarPacientePorNombre(List<Paciente> pacientes, String nombre) throws IllegalArgumentException {
        if(pacientes.isEmpty())
            throw new IllegalArgumentException(String.valueOf(pacientes));
        String needle = nombre.toLowerCase();
        for(Paciente p: pacientes){
            String n = p.getNombre();
            if (n != null && n.toLowerCase().contains(needle)){
                paciente=p;
                System.out.printf("Paciente agregado correctamente: '%s'%n", paciente);
            }
        }
        String n = paciente.getNombre();
        if(!n.toLowerCase().contains(needle))
            throw new IllegalArgumentException(String.valueOf(pacientes));
        return paciente;
    }

    public void estadoConfeccionado() {
        estado = "confeccionada";
    }
    public List<Medicamento> getMedicamentos() {
        return indicaciones.values()
                .stream()
                .map(Indicacion::getMedicamento)
                .collect(Collectors.toList());
    }
    public void iniciarProceso() throws IllegalArgumentException {
        if ("confeccionada".equalsIgnoreCase(estado)) {
            if (!fechaDentroVentana(fecha_retiro))
                estado = "proceso";
            else
                throw new IllegalArgumentException(estado);
        } else
            throw new IllegalArgumentException(String.valueOf(fecha_retiro));
    }

    public void marcarLista() throws IllegalArgumentException {
        if ("proceso".equalsIgnoreCase(estado))
            estado = "lista";
        else
            throw new IllegalArgumentException(estado);
    }

    public void entregar() throws IllegalArgumentException {
        if ("lista".equalsIgnoreCase(estado)) {
            if (fechaDentroVentana(fecha_retiro))
                estado = "entregada";
            else
                throw new IllegalArgumentException(estado);
        } else
            throw new IllegalArgumentException(String.valueOf(fecha_retiro));
    }


    private static boolean fechaDentroVentana(LocalDate fechaRetiro) {
        if (fechaRetiro == null) return false;
        LocalDate hoy = hoy();
        LocalDate min = restarDias(hoy, VENTANA_DIAS);
        LocalDate max = sumarDias(hoy, VENTANA_DIAS);
        return esEntreInclusivo(fechaRetiro, min, max);
    }

    private static LocalDate hoy() { return LocalDate.now(); }
    private static LocalDate sumarDias(LocalDate fecha, int dias) { return fecha.plusDays(dias); }
    private static LocalDate restarDias(LocalDate fecha, int dias) { return fecha.minusDays(dias); }
    private static boolean esEntreInclusivo(LocalDate f, LocalDate ini, LocalDate fin) {
        return !f.isBefore(ini) && !f.isAfter(fin);
    }

    private static final int VENTANA_DIAS = 3;
    @XmlElement
    @Getter
    private final Map<Integer, Indicacion> indicaciones;

    @XmlElement
    @Getter
    private Paciente paciente;

    @Getter
    @Setter
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha_confeccion;

    @Getter
    @Setter
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha_retiro;

    @Getter
    @Setter
    @XmlElement
    private String estado;

    @Setter
    @Getter
    @XmlElement
    String codigo;
}
