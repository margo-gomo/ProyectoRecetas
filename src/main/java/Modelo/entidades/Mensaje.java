package Modelo.entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@DatabaseTable(tableName = "mensaje")
public class Mensaje {
    @DatabaseField(id = true)
    private String id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true,canBeNull = false)
    private Usuario remitente;

    @DatabaseField(foreign = true, foreignAutoRefresh = true,canBeNull = false)
    private Usuario destinatario;

    @DatabaseField(canBeNull = false)
    private String texto;

    @DatabaseField(canBeNull = false)
    private int leido;
}
