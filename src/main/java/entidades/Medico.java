package entidades;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@XmlRootElement(name = "medico")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Data
public class Medico {
    public Medico(String nombre, int id, String especialidad){
        this.nombre = nombre;
        this.id = id;
        this.especialidad = especialidad;
        clave=this.id;
    }
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private int clave;
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    @XmlAttribute(name = "especialidad")
    private String especialidad;
}
