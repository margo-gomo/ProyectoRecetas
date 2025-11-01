package Modelo.entidades.Receta;
import Modelo.entidades.Medicamento;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "indicacion")
public class Indicacion {
    @DatabaseField(id = true)
    private String id;
    @DatabaseField(foreign = true,canBeNull = false,columnName = "receta_codigo",foreignAutoRefresh = true)
    private Receta receta;
    @DatabaseField(foreign = true,canBeNull = false,columnName = "medicamento_codigo",foreignAutoRefresh = true)
    private Medicamento medicamento;
    @DatabaseField(canBeNull = false)
    private int cantidad;
    @DatabaseField
    private String indicaciones;
    @DatabaseField(canBeNull = false,columnName = "duracion_dias")
    private int duracion;
}