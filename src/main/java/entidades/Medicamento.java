package entidades;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@XmlRootElement(name = "medicamento")
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
    @XmlAttribute(name = "codigo")
    private String codigo;

    @Getter @Setter
    private String nombre;

    @Getter @Setter
    @XmlElement(name = "presentacion")
    private String presentacion;

    @Getter @Setter
    private String descripcion;
}
