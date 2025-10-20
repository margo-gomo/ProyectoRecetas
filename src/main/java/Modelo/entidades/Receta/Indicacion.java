package Modelo.entidades.Receta;
import Modelo.entidades.Medicamento;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.*;

import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Indicacion {
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Indicacion))
            return false;
        Indicacion other = (Indicacion) obj;
        return Objects.equals(medicamento.getCodigo(), other.medicamento.getCodigo()) && Objects.equals(medicamento.getDescripcion(), other.medicamento.getDescripcion());
    }
    @Override
    public int hashCode() {
        int hash=5;
        hash=29 * hash + Objects.hashCode(this.medicamento.getCodigo());
        hash=29 * hash + Objects.hashCode(this.medicamento.getDescripcion());
        return hash;
    }
    private Medicamento medicamento;
    private int cantidad;
    private String descripcion;
    private int duracion;
}
