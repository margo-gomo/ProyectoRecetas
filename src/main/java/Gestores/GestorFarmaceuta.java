package Gestores;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;
import entidades.Farmaceuta;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import entidades.Farmaceuta;
@XmlRootElement (name = "lista-farmaceuta")
public class GestorFarmaceuta {
    public GestorFarmaceuta() {
        farmaceutas = new ArrayList<>();
    }
    public int cantidadFarmaceutas() {
        return farmaceutas.size();
    }
    public boolean existeFarmaceuta(int id) {
        for (Farmaceuta farmaceuta : farmaceutas) {
            if (farmaceuta.getId() == id) {
                return true;
            }
        }
        return false;
    }
    public Farmaceuta buscarFarmaceutaID(int id) {
        for (Farmaceuta farmaceuta : farmaceutas) {
            if (farmaceuta.getId() == id) {
                return farmaceuta;
            }
        }
        return null;
    }
    public Farmaceuta buscarFarmaceutaNombre(String nombre) {
        for (Farmaceuta farmaceuta : farmaceutas) {
            if (farmaceuta.getNombre().equals(nombre)) {
                return farmaceuta;
            }
        }
        return null;
    }
    public boolean aregarFarmaceuta(Farmaceuta farmaceuta) {
        if (!existeFarmaceuta(farmaceuta.getId())) {
            farmaceutas.add(farmaceuta);
            return true;
        }
        return false;
    }
    public boolean modificarFarmaceuta(Farmaceuta pacienteporactualizar) {
        for (int i = 0; i < farmaceutas.size(); i++) {
            if (farmaceutas.get(i).getId() == pacienteporactualizar.getId()) {
                farmaceutas.set(i, pacienteporactualizar);
                return true;
            }
        }
        return false;
    }
    public boolean eliminarFarmaceuta(int id) {
        for (int i = 0; i < farmaceutas.size(); i++) {
            if (farmaceutas.get(i).getId() == id) {
                farmaceutas.remove(i);
                return true;
            }
        }
        return false;
    }
    public void guardarXML() throws Exception {
        FileOutputStream flujo = new FileOutputStream("datos/farmaceuta.xml");
        JAXBContext context = JAXBContext.newInstance(GestorFarmaceuta.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, flujo);
    }

    public void cargarXML() throws Exception {
        FileInputStream flujo = new FileInputStream("datos/farmaceuta.xml");
        JAXBContext context = JAXBContext.newInstance(GestorFarmaceuta.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GestorFarmaceuta temp = (GestorFarmaceuta) unmarshaller.unmarshal(flujo);
        this.farmaceutas = temp.farmaceutas;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Farmaceuta farmaceuta : farmaceutas) {
            sb.append(String.format("%n\t%s,", farmaceuta));
        }
        sb.append("\n");
        return sb.toString();
    }
    @XmlElement(name = "farmaceuta")
    private List<Farmaceuta> farmaceutas;
}
