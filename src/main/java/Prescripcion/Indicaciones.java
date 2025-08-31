package Prescripcion;
import entidades.Medicamento;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;
@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement (name = "Indicaciones")
@XmlAccessorType(XmlAccessType.FIELD)
public class Indicaciones {
    Medicamento medicamento;
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
