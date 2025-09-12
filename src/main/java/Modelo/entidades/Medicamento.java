package Modelo.entidades;

import jakarta.xml.bind.annotation.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Medicamento {
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Medicamento))
            return false;
        Medicamento other = (Medicamento) obj;
        return Objects.equals(codigo, other.codigo) && Objects.equals(nombre, other.nombre);
    }
    @Override
    public int hashCode() {
        int hash=5;
        hash=29 * hash + Objects.hashCode(this.codigo);
        hash=29 * hash + Objects.hashCode(this.nombre);
        return hash;
    }
    @Getter @Setter
    private int codigo;

    @Getter @Setter
    private String nombre;

    @Getter @Setter
    @XmlElement(name = "presentacion")
    private String presentacion;

    @Getter @Setter
    private String descripcion;
}
