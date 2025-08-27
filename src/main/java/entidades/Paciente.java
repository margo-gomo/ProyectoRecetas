package entidades;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Paciente {
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int telefono;
    @Getter
    @Setter
    private LocalDate fecha_nacimiento;
}
