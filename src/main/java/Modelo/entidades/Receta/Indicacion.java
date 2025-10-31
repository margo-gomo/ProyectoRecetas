package Modelo.entidades.Receta;
import Modelo.entidades.Medicamento;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@DatabaseTable(tableName = "indicacion")
public class Indicacion {
    public Indicacion(Receta receta, Medicamento medicamento,int cantidad,String indicaciones,int duracion) {
        this.receta = receta;
        this.medicamento = medicamento;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.duracion = duracion;
    }
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true,canBeNull = false,columnName = "receta_codigo")
    private Receta receta;
    @DatabaseField(foreign = true,canBeNull = false,columnName = "medicamento_codigo")
    private Medicamento medicamento;
    @DatabaseField(canBeNull = false)
    private int cantidad;
    @DatabaseField
    private String indicaciones;
    @DatabaseField(canBeNull = false,columnName = "duracion_dias")
    private int duracion;
}