package entidades;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@XmlRootElement (name = "farmaceuta")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Data
public class Farmaceuta {
    public  Farmaceuta(int id, String nombre) {
        this.nombre = nombre;
        this.id = id;
        clave=this.id;
    }
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private int clave;
}
