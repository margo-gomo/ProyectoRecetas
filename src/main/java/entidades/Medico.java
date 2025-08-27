package entidades;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
public class Medico {
    @Getter
    @Setter
    private String nombre;
    private int id;
    @Getter
    @Setter
    private String clave;
    private String especialidad;
}
