package Adaptador;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * -----------------------------------------------------------------------------
 *
 * Este código es de uso estrictamente académico. Se proporciona "TAL CUAL", sin
 * ninguna garantía expresa o implícita, incluyendo, pero sin limitarse a, la
 * garantía de comerciabilidad o idoneidad para un propósito particular.
 *
 * Puede copiarse, modificarse y redistribuirse libremente, siempre que se
 * mantenga este aviso y se otorgue el debido crédito al autor original en
 * cualquier trabajo derivado o aplicación donde se utilice.
 *
 * El autor no se hace responsable por daños directos o indirectos derivados del
 * uso de este código.
 *
 * -----------------------------------------------------------------------------
 *
 * (c) 2025
 *
 * @author Georges Alfaro S.
 * @version 1.0.0 2025-08-19
 *
 * -----------------------------------------------------------------------------
 */
public class XMLUtils {

    public static <T> T loadFromXML(InputStream in, Class<T> clazz)
            throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(clazz);
        Unmarshaller mrs = ctx.createUnmarshaller();
        return clazz.cast(mrs.unmarshal(in));
    }

    public static <T> String toXMLString(T obj)
            throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext ctx = JAXBContext.newInstance(obj.getClass());
        Marshaller mrs = ctx.createMarshaller();
        mrs.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        mrs.marshal(obj, sw);
        return sw.toString();
    }

}