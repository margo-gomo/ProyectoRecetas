package Modelo.entidades.Receta;
import Modelo.entidades.Medicamento;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;
@Data
@NoArgsConstructor
@DatabaseTable(tableName = "indicacion")
public class Indicacion {
    public Indicacion(Medicamento medicamento,int cantidad,String indicaiones,int duracion) {
        receta=null;
        this.medicamento=medicamento;
        this.cantidad=cantidad;
        this.indicaciones=indicaiones;
        this.duracion=duracion;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Indicacion))
            return false;
        Indicacion other = (Indicacion) obj;
        return Objects.equals(receta.getCodigo(), other.receta.getCodigo()) && Objects.equals(medicamento.getCodigo(), other.medicamento.getCodigo());
    }
    @Override
    public int hashCode() {
        int hash=5;
        hash=29 * hash + Objects.hashCode(this.receta.getCodigo());
        hash=29 * hash + Objects.hashCode(this.medicamento.getCodigo());
        return hash;
    }
    @DatabaseField(foreign = true,columnName = "receta_codigo")
    private Receta receta;
    @DatabaseField(foreign = true,columnName = "medicamento_codigo")
    private Medicamento medicamento;
    @DatabaseField(canBeNull = false)
    private int cantidad;
    @DatabaseField
    private String indicaciones;
    @DatabaseField(canBeNull = false)
    private int duracion;
}