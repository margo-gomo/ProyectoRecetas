package entidades;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Medico {
    public Medico(String nombre, int id, String especialidad){
        this.nombre = nombre;
        this.id = id;
        this.especialidad = especialidad;
        clave=this.id;
    }
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private int clave;
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String especialidad;

}
