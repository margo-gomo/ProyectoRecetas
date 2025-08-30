package Gestores;

import entidades.Farmaceuta;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;

import entidades.Medico;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "lista-farmaceuta")
public class GestorFarmaceuta {

    public GestorFarmaceuta() {
        // Lista en memoria para los farmaceutas
        farmaceutas = new ArrayList<>();
    }

    // Cantidad total de farmaceutas
    public int cantidadFarmaceutas() {
        return farmaceutas.size();
    }

    // Indica si existe un farmaceuta con el ID indicado
    public boolean existeFarmaceuta(String id) {
        for (Farmaceuta f : farmaceutas) {
            if (f.getId().equals(id)) return true;
        }
        return false;
    }

    // Busca y retorna un farmaceuta por ID exacto (o null si no existe)
    public Farmaceuta buscarFarmaceutaID(String id) {
        for (Farmaceuta f : farmaceutas) {
            if (f.getId().equals(id)) return f;
        }
        return null;
    }

    // Busca por nombre con coincidencia aproximada (ignorando mayúsculas/minúsculas)
    public Farmaceuta buscarFarmaceutaNombre(String nombre) {
        if (nombre == null) return null;
        String needle = nombre.toLowerCase();
        for (Farmaceuta f : farmaceutas) {
            String n = f.getNombre();
            if (n != null && n.toLowerCase().contains(needle)) return f;
        }
        return null;
    }

    // Agrega un farmaceuta si el ID no está repetido
    public boolean agregarFarmaceuta(Farmaceuta farmaceuta) { // (antes: aregarFarmaceuta)
        if (farmaceuta == null) return false;
        if (!existeFarmaceuta(farmaceuta.getId())) {
            farmaceutas.add(farmaceuta);
            return true;
        }
        return false;
    }

    // Reemplaza los datos del farmaceuta que tenga el mismo ID
    public boolean modificarFarmaceuta(Farmaceuta farmaceutaPorActualizar) { // (antes: pacienteporactualizar)
        if (farmaceutaPorActualizar == null) return false;
        for (int i = 0; i < farmaceutas.size(); i++) {
            if (farmaceutas.get(i).getId() == farmaceutaPorActualizar.getId()) {
                farmaceutas.set(i, farmaceutaPorActualizar);
                return true;
            }
        }
        return false;
    }

    // Cambia la clave de un farmaceuta existente (match por ID)
    public boolean cambiarclave(String id, String clave){
        for (Farmaceuta farmaceuta : farmaceutas) {
            if (farmaceuta.getId().equals(id)) {
                farmaceuta.setClave(clave);
                return true;
            }
        }
        return false;
    }

    // Elimina un farmaceuta por ID
    public boolean eliminarFarmaceuta(String id) {
        for (int i = 0; i < farmaceutas.size(); i++) {
            if (farmaceutas.get(i).getId().equals(id)) {
                farmaceutas.remove(i);
                return true;
            }
        }
        return false;
    }

    // Guarda toda la lista en datos/farmaceutas.xml
    public void guardarXML() throws Exception {
        FileOutputStream flujo = new FileOutputStream("datos/farmaceutas.xml");
        JAXBContext context = JAXBContext.newInstance(GestorFarmaceuta.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, flujo);
    }

    // Carga la lista completa desde datos/farmaceutas.xml
    public void cargarXML() throws Exception {
        FileInputStream flujo = new FileInputStream("datos/farmaceutas.xml");
        JAXBContext context = JAXBContext.newInstance(GestorFarmaceuta.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GestorFarmaceuta temp = (GestorFarmaceuta) unmarshaller.unmarshal(flujo);
        this.farmaceutas = temp.farmaceutas;
    }

    // Muestra la lista en texto legible (debug/impresión)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Farmaceuta f : farmaceutas) {
            sb.append(String.format("%n\t%s,", f));
        }
        return sb.append("\n").toString();
    }

    @XmlElement(name = "farmaceuta")
    private List<Farmaceuta> farmaceutas;
}
