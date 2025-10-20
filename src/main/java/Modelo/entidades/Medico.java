package Modelo.entidades;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.NoArgsConstructor;
import lombok.Data;
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Data
@DatabaseTable(tableName = "medico")
public class Medico extends  Usuario {
    public Medico(Usuario usuario, String especialidad) {
        this.usuario = usuario;
        this.especialidad = especialidad;
    }
    @DatabaseField(id = true, foreign = true, foreignAutoCreate = true,foreignAutoRefresh = true,columnName = "usuario_id")
    private Usuario usuario;
    @DatabaseField
    private String especialidad;
}
