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
import lombok.ToString;
import java.time.LocalDate;
import java.util.Objects;

import Adaptador.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@XmlRootElement (name = "paciente")
@XmlAccessorType(XmlAccessType.FIELD)
public class Paciente {
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Paciente))
            return false;
        Paciente other = (Paciente) obj;
        return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
    }
    @Override
    public int hashCode() {
        int hash=5;
        hash=29 * hash + Objects.hashCode(this.id);
        hash=29 * hash + Objects.hashCode(this.nombre);
        return hash;
    }
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
