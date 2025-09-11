package Modelo.entidades;

import Adaptador.LocalDateAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@ToString
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
