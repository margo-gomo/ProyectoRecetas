package entidades;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
//admin
@XmlRootElement(name = "administrador")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Data
public class Administrador {

    public Administrador(String nombre, String id){
        this.nombre = nombre;
        this.id = id;
        this.clave = this.id;
        this.token = 2; // Token para identificar administrador
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
    private int token;
}

