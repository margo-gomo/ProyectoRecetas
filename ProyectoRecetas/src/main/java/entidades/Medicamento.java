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

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement(name = "medicamento")
@XmlAccessorType(XmlAccessType.FIELD)
public class Medicamento {

    @Getter @Setter
    @XmlAttribute(name = "codigo")
    private int codigo;

    @Getter @Setter
    private String nombre;

    @Getter @Setter
    @XmlElement(name = "presentacion")
    private String presentacion;

    @Getter @Setter
    private String descripcion;
}
