package Modelo.entidades;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;
@Data
@NoArgsConstructor
@DatabaseTable(tableName = "usuario")
public class Usuario {
    public Usuario(String id,String nombre,String tipo){
        this.id=id;
        clave=this.id;
        this.nombre=nombre;
        this.tipo=tipo;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Usuario))
            return false;
        Usuario other = (Usuario) obj;
        return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
    }
    @Override
    public int hashCode() {
        int hash=5;
        hash=29 * hash + Objects.hashCode(this.id);
        hash=29 * hash + Objects.hashCode(this.nombre);
        return hash;
    }
    @DatabaseField(id = true)
    private String id;
    @DatabaseField(canBeNull = false)
    private String clave;
    @DatabaseField(canBeNull = false)
    private String nombre;
    @DatabaseField(canBeNull = false)
    private String tipo;
}
