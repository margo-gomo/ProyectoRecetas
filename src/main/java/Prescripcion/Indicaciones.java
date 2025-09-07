package Prescripcion;
import entidades.Medicamento;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;
import lombok.Data;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@XmlRootElement (name = "Indicaciones")
@XmlAccessorType(XmlAccessType.FIELD)
public class Indicaciones {
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Indicaciones))
            return false;
        Indicaciones other = (Indicaciones) obj;
        return Objects.equals(medicamento.getCodigo(), other.medicamento.getCodigo()) && Objects.equals(medicamento.getDescripcion(), other.medicamento.getDescripcion());
    }
    @Override
    public int hashCode() {
        int hash=5;
        hash=29 * hash + Objects.hashCode(this.medicamento.getCodigo());
        hash=29 * hash + Objects.hashCode(this.medicamento.getDescripcion());
        return hash;
    }
    @Getter
    private Medicamento medicamento;
    @Getter
    @Setter
    private int cantidad;
    @Getter
    @Setter
    private String descripcion;
    @Getter
    @Setter
    @XmlElement(name = "duracion-dias")
    private int duracion;
}
