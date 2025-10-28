package Modelo.entidades;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.NoArgsConstructor;
import lombok.Data;
@NoArgsConstructor
@Data
@DatabaseTable(tableName = "medico")
public class Medico {
    public Medico(Usuario usuario, String especialidad) {
        this.usuario = usuario;
        this.especialidad = especialidad;
    }
    @DatabaseField(foreign = true, foreignAutoCreate = true,foreignAutoRefresh = true,columnName = "usuario_id")
    private Usuario usuario;
    @DatabaseField
    private String especialidad;
}
