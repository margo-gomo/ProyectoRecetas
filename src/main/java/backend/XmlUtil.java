/*package backend;

import Common.Message;
import jakarta.xml.bind.*;
import java.io.*;

public class XmlUtil {
    private static JAXBContext ctx;
    static {
        try { ctx = JAXBContext.newInstance(Message.class); }
        catch (Exception e) { e.printStackTrace(); }
    }

    public static String toXml(Object obj) throws JAXBException {
        StringWriter sw = new StringWriter();
        ctx.createMarshaller().marshal(obj, sw);
        return sw.toString();
    }

    public static Message fromXml(String xml) throws JAXBException {
        return (Message) ctx.createUnmarshaller().unmarshal(new StringReader(xml));
    }
}*/

