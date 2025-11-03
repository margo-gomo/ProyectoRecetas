package Modelo.entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "mensaje")
public class Mensaje {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String remitente;

    @DatabaseField
    private String destinatario;

    @DatabaseField
    private String texto;

    @DatabaseField(columnName = "fecha_envio")
    private Date fechaEnvio;

    public Mensaje() {}

    public Mensaje(String remitente, String destinatario, String texto, Date fechaEnvio) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.texto = texto;
        this.fechaEnvio = fechaEnvio;
    }

    public Long getId() { return id; }
    public String getRemitente() { return remitente; }
    public String getDestinatario() { return destinatario; }
    public String getTexto() { return texto; }
    public Date getFechaEnvio() { return fechaEnvio; }

    public void setId(Long id) { this.id = id; }
    public void setRemitente(String r) { this.remitente = r; }
    public void setDestinatario(String d) { this.destinatario = d; }
    public void setTexto(String t) { this.texto = t; }
    public void setFechaEnvio(Date f) { this.fechaEnvio = f; }
}
