package Modelo.entidades;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.Objects;

@XmlRootElement(name = "medico")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@ToString
public class Medico implements Usuario {
    public Medico(String nombre, String id, String especialidad){
        this.nombre = nombre;
        this.id = id;
        this.especialidad = especialidad;
        clave=this.id;
        token=1; //Token para identificar medico
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Medico))
            return false;
        Medico other = (Medico) obj;
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
    @Setter
    @XmlAttribute(name = "especialidad")
    private String especialidad;
    @Getter
    private int token;
}
