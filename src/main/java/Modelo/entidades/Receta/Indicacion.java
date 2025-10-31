package Modelo.entidades.Receta;
import Modelo.entidades.Medicamento;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;
@Data
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "indicacion")
public class Indicacion {
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Indicacion))
            return false;
        Indicacion other = (Indicacion) obj;
        return Objects.equals(receta_codigo, other.receta_codigo) && Objects.equals(medicamento_codigo, other.medicamento_codigo);
    }
    @Override
    public int hashCode() {
        int hash=5;
        hash=29 * hash + Objects.hashCode(this.receta_codigo);
        hash=29 * hash + Objects.hashCode(this.medicamento_codigo);
        return hash;
    }
    @DatabaseField(canBeNull = false)
    private String receta_codigo;
    @DatabaseField(canBeNull = false)
    private String medicamento_codigo;
    @DatabaseField(persisted = false)
    private Receta receta;
    @DatabaseField(persisted = false)
    private Medicamento medicamento;
    @DatabaseField(canBeNull = false)
    private int cantidad;
    @DatabaseField
    private String indicaciones;
    @DatabaseField(canBeNull = false,columnName = "duracion_dias")
    private int duracion;
}