package entidades;
import lombok.*;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
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
