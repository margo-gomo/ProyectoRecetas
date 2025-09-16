package Modelo.entidades.Receta;

import Adaptador.LocalDateAdapter;
import Modelo.entidades.Paciente;
import Modelo.entidades.Medicamento;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class Receta {
    public Receta() {
        indicaciones = new HashMap<>();
        paciente = new Paciente();
        fecha_confeccion = LocalDate.now();
        fecha_retiro = null;
        estado = "NO finalizada";
        codigo="";
        idFarmaceutaProceso="No procesada";
        idFarmaceutaLista="No lista";
        idFarmaceutaEntregar="No entregada";
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
    }

    public List<Indicacion> actualizarIndicaciones(List<Indicacion> indicaciones) throws IllegalArgumentException {
        if(indicaciones == null)
            throw new IllegalArgumentException(indicaciones.toString());
        this.indicaciones.clear();
        return agregarIndicaciones(indicaciones);
    }

    public Indicacion eliminarIndicacion(Indicacion indicacion) throws IllegalArgumentException {
        Indicacion i = indicaciones.remove(indicacion.getMedicamento().getCodigo());
        if(i!=null)
            System.out.printf("Indicacion eliminada correctamente: '%s'%n", indicacion);
        else
            throw new IllegalArgumentException(String.valueOf(codigo));
        return indicacion;
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
    public void iniciarProceso(String idFarmaceuta) throws IllegalArgumentException {
        if ("confeccionada".equalsIgnoreCase(estado)) {
            if (fechaDentroVentana(fecha_retiro)) {
                estado = "proceso";
                idFarmaceutaProceso=idFarmaceuta;
            }
            else
                throw new IllegalArgumentException("La receta está fuera de la ventana de 3 días");
        } else
            throw new IllegalArgumentException("La receta no está solamente confeccionada");
    }

    public void marcarLista(String idFarmaceuta) throws IllegalArgumentException,SecurityException {
        if ("proceso".equalsIgnoreCase(estado)) {
            estado = "lista";
            idFarmaceutaLista=idFarmaceuta;
        }
        else
            throw new IllegalArgumentException("La receta que no está en proceso.");
    }

    public void entregar(String idFarmaceuta) throws IllegalArgumentException,SecurityException {
        if ("lista".equalsIgnoreCase(estado)) {
                estado = "entregada";
                idFarmaceutaEntregar=idFarmaceuta;
        } else
            throw new IllegalArgumentException("La receta no está lista");
    }


    private static boolean fechaDentroVentana(LocalDate fechaRetiro) {
        if (fechaRetiro == null) return false;
        LocalDate hoy = hoy();
        LocalDate min = restarDias(hoy, 3);
        LocalDate max = sumarDias(hoy, 3);
        return esEntreInclusivo(fechaRetiro, min, max);
    }

    private static LocalDate hoy() { return LocalDate.now(); }
    private static LocalDate sumarDias(LocalDate fecha, int dias) { return fecha.plusDays(dias); }
    private static LocalDate restarDias(LocalDate fecha, int dias) { return fecha.minusDays(dias); }
    private static boolean esEntreInclusivo(LocalDate f, LocalDate ini, LocalDate fin) {
        return !f.isBefore(ini) && !f.isAfter(fin);
    }
    @Setter
    @Getter
    String codigo;
    @Getter
    @XmlElement
    private final Map<Integer, Indicacion> indicaciones;

    @Getter
    @Setter
    @XmlElement
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

    @Getter
    @Setter
    private String idFarmaceutaProceso;

    @Getter
    @Setter
    private String idFarmaceutaLista;

    @Getter
    @Setter
    private String idFarmaceutaEntregar;
}
