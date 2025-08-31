package entidades;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import Adaptador.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement (name = "paciente")
@XmlAccessorType(XmlAccessType.FIELD)
public class Paciente {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    @XmlElement(name="teléfono")
    private int telefono;
    @Getter
    @Setter
    @XmlAttribute(name = "fecha_nacimiento")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha_nacimiento;
}
