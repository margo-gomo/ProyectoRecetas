package entidades;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Farmaceuta {
    public  Farmaceuta(int id, String nombre) {
        this.nombre = nombre;
        this.id = id;
        clave=this.id;
    }
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private int clave;
}
