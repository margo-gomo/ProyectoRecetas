package Gestores;
import entidades.Administrador;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
//admin
@XmlRootElement(name = "lista-administradores")
public class GestorAdministrador {

    public GestorAdministrador() {
        administradores = new ArrayList<>();
    }

    public int cantidadAdministradores() {
        return administradores.size();
    }

    public boolean existeAdministrador(String id) {
        for (Administrador admin : administradores) {
            if (admin.getId().equals(id)) return true;
        }
        return false;
    }

    public Administrador buscarAdministradorID(String id) {
        for (Administrador admin : administradores) {
            if (admin.getId().equals(id)) return admin;
        }
        return null;
    }

    public Administrador buscarAdministradorNombre(String nombre) {
        if (nombre == null) return null;
        String needle = nombre.toLowerCase();
        for (Administrador admin : administradores) {
            String n = admin.getNombre();
            if (n != null && n.toLowerCase().contains(needle)) return admin;
        }
        return null;
    }

    public boolean agregarAdministrador(Administrador admin) {
        if (admin == null) return false;
        if (!existeAdministrador(admin.getId())) {
            administradores.add(admin);
            return true;
        }
        return false;
    }

    public boolean modificarAdministrador(Administrador adminPorActualizar) {
        if (adminPorActualizar == null) return false;
        for (int i = 0; i < administradores.size(); i++) {
            if (administradores.get(i).getId().equals(adminPorActualizar.getId())) {
                administradores.set(i, adminPorActualizar);
                return true;
            }
        }
        return false;
    }

    public boolean cambiarClave(String id, String clave){
        for (Administrador admin : administradores) {
            if (admin.getId().equals(id)) {
                admin.setClave(clave);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarAdministrador(String id) {
        for (int i = 0; i < administradores.size(); i++) {
            if (administradores.get(i).getId().equals(id)) {
                administradores.remove(i);
                return true;
            }
        }
        return false;
    }

    public void guardarXML() throws Exception {
        FileOutputStream flujo = new FileOutputStream("datos/administradores.xml");
        JAXBContext context = JAXBContext.newInstance(GestorAdministrador.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, flujo);
    }

    public void cargarXML() throws Exception {
        FileInputStream flujo = new FileInputStream("datos/administradores.xml");
        JAXBContext context = JAXBContext.newInstance(GestorAdministrador.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GestorAdministrador temp = (GestorAdministrador) unmarshaller.unmarshal(flujo);
        this.administradores = temp.administradores;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Administrador admin : administradores) {
            sb.append(String.format("%n\t%s,", admin));
        }
        return sb.append("\n").toString();
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    @XmlElement(name = "administrador")
    private List<Administrador> administradores;
}

