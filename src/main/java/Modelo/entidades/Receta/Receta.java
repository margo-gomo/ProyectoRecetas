package Modelo.entidades.Receta;
import Modelo.entidades.Usuario;
import Modelo.entidades.Paciente;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Calendar;
import java.util.Objects;
@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@DatabaseTable(tableName = "receta")
public class Receta {
    public Receta(String codigo,Paciente paciente,Date fecha_retiro,Date fecha_confeccion,Usuario farmaceuta_Proceso,Usuario farmaceuta_Lista,Usuario farmaceuta_Entregada) {
        this.codigo = codigo;
        this.paciente = paciente;
        this.fecha_retiro = fecha_retiro;
        this.fecha_confeccion = fecha_confeccion;
        estado="CONFECCIONADA";
        this.farmaceuta_Proceso = farmaceuta_Proceso;
        this.farmaceuta_Lista = farmaceuta_Lista;
        this.farmaceuta_Entregada = farmaceuta_Entregada;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Receta))
            return false;
        Receta other = (Receta) obj;
        return Objects.equals(codigo, other.codigo);
    }
    @Override
    public int hashCode() {
        int hash=5;
        hash=29 * hash + Objects.hashCode(this.codigo);
        return hash;
    }
    public boolean fechaDentroVentana() {
        Date hoy = hoy();
        Date min = restarDias(hoy, 3);
        Date max = sumarDias(hoy, 3);
        return esEntreInclusivo(fecha_retiro, min, max);
    }

    private static Date hoy() {
        // Devolvemos solo la fecha (sin hora)
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private static Date sumarDias(Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DAY_OF_YEAR, dias);
        return cal.getTime();
    }

    private static Date restarDias(Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DAY_OF_YEAR, -dias);
        return cal.getTime();
    }

    private static boolean esEntreInclusivo(Date f, Date ini, Date fin) {
        return !f.before(ini) && !f.after(fin);
    }
    @DatabaseField(id = true)
    String codigo;
    @DatabaseField(columnName = "id_paciente",foreign = true,canBeNull = false)
    private Paciente paciente;
    @DatabaseField(canBeNull = false)
    private Date fecha_confeccion;
    @DatabaseField(canBeNull = false)
    private Date fecha_retiro;
    @DatabaseField(canBeNull = false)
    private String estado;
    @DatabaseField(foreign = true,columnName = "id_farmaceuta_proceso")
    private Usuario farmaceuta_Proceso;
    @DatabaseField(foreign = true,columnName = "id_farmaceuta_lista")
    private Usuario farmaceuta_Lista;
    @DatabaseField(foreign = true,columnName = "id_farmaceuta_entregada")
    private Usuario farmaceuta_Entregada;
}
    /*public int cantidad(){ return indicaciones.size(); }

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


    public List<Medicamento> getMedicamentos() {
        return indicaciones.values()
                .stream()
                .map(Indicacion::getMedicamento)
                .collect(Collectors.toList());
    }*/
    /*public int iniciarProceso(Usuario farmaceuta) throws IllegalArgumentException {
        if ("CONFECCIONADA".equals(estado)) {
            if (fechaDentroVentana(fecha_retiro)) {
                farmaceuta=idFarmaceuta;
                return 0;
            }
            else
                return 1;
        } else
            return 2;
    }
    public int marcarLista(Usuario farmaceuta) throws IllegalArgumentException,SecurityException {
        if ("proceso".equals(estado)) {
            if(!idFarmaceuta.equals(idFarmaceutaProceso)){
                idFarmaceutaLista=idFarmaceuta;
                return 0;
            }
            else
                return 1;

        }
        else
            return 2;

    }

    public int entregar(String idFarmaceuta) throws IllegalArgumentException,SecurityException {
        if ("lista".equals(estado)) {
            if(!idFarmaceuta.equals(farmaceuta_Lista)){
                idFarmaceutaEntregar=idFarmaceuta;
                return 0;
            }
            else
                return 1;

        } else
            return 2;

    }


    private boolean fechaDentroVentana(LocalDate fechaRetiro) {
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
    }*/