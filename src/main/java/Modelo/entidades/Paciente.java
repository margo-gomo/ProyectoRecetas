package Modelo.entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "paciente")
public class Paciente {

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String nombre;

    @DatabaseField(canBeNull = false)
    private Date fecha_nacimiento;

    @DatabaseField(canBeNull = false, columnName = "telefono")
    private int telefono;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Paciente)) return false;
        Paciente other = (Paciente) obj;
        return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.nombre);
        return hash;
    }
}
