package Adaptador;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v, formato);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.format(formato);
    }
}