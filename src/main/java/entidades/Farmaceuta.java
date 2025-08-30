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
    public  Farmaceuta(String id, String nombre) {
        this.nombre = nombre;
        this.id = id;
        clave=this.id;
        token=2; //Token para identificar farmaceuta;
    }
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private String clave;
    @Getter
    int token;
}
