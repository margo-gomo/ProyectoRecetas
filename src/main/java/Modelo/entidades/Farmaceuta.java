package Modelo.entidades;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class Farmaceuta implements Usuario {
    public Farmaceuta() {
        nombre=null;
        id=null;
        clave=null;
        token=2;
    }
    public  Farmaceuta(String id, String nombre) {
        this.nombre = nombre;
        this.id = id;
        clave=this.id;
        token=2; //Token para identificar farmaceuta;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Farmaceuta))
            return false;
        Farmaceuta other = (Farmaceuta) obj;
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
