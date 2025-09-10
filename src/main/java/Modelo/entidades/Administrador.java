package Modelo.entidades;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.Objects;

//admin
@XmlRootElement(name = "administrador")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@ToString
@Data
public class Administrador {

    public Administrador(String nombre, String id){
        this.nombre = nombre;
        this.id = id;
        this.clave = this.id;
        this.token = 0; // Token para identificar administrador
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Administrador))
            return false;
        Administrador other = (Administrador) obj;
        return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
    }
    @Override
    public int hashCode() {
        int hash=5;
        hash=29 * hash + Objects.hashCode(this.id);
        hash=29 * hash + Objects.hashCode(this.nombre);
        return hash;
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

