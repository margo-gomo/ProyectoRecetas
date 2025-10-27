package Modelo.entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;
@Data
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "medicamento")
public class Medicamento {
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Medicamento))
            return false;
        Medicamento other = (Medicamento) obj;
        return Objects.equals(codigo, other.codigo) && Objects.equals(nombre, other.nombre);
    }
    @Override
    public int hashCode() {
        int hash=5;
        hash=29 * hash + Objects.hashCode(this.codigo);
        hash=29 * hash + Objects.hashCode(this.nombre);
        return hash;
    }
    @DatabaseField(id = true)
    private String codigo;
    @DatabaseField(canBeNull = false)
    private String nombre;
    @DatabaseField(canBeNull = false)
    private String presentacion;
    @DatabaseField(canBeNull = false)
    private String descripcion;
}
