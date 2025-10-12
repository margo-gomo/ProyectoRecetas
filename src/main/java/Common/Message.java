package Common;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    public String type;
    public Payload payload;

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Payload {
        public String id;
        public String clave;
        public Boolean ok;
        public String rol;
        public String msg;
        public String status;
        public String from;
        public String to;
        public String texto;
    }
}