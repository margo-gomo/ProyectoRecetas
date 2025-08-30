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
    public Medico(String nombre, String id, String especialidad){
        this.nombre = nombre;
        this.id = id;
        this.especialidad = especialidad;
        clave=this.id;
        token=1; //Token para identificar medico
    }
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private String clave;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    @XmlAttribute(name = "especialidad")
    private String especialidad;
    @Getter
    private int token;
}
