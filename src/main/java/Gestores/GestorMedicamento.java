package Gestores;

import entidades.Medicamento;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "lista_medicamentos")
public class GestorMedicamento {

    public GestorMedicamento() {
        // Lista en memoria para el catálogo
        medicamentos = new ArrayList<>();
    }

    // Cantidad total en el catálogo
    public int cantidadMedicamentos() {
        return medicamentos.size();
    }

    // ¿Existe un medicamento con ese código?
    public boolean existeMedicamento(int codigo) {
        for (Medicamento m : medicamentos) {
            if (m.getCodigo() == codigo) return true;
        }
        return false;
    }

    // Busca por código exacto
    public Medicamento buscarMedicamentoCodigo(int codigo) {
        for (Medicamento m : medicamentos) {
            if (m.getCodigo() == codigo) return m;
        }
        return null;
    }

    // Busca por descripción o nombre (coincidencia aproximada, ignore case)
    public Medicamento buscarMedicamentoDescripcion(String texto) {
        if (texto == null) return null;
        String needle = texto.toLowerCase();
        for (Medicamento m : medicamentos) {
            String nombre = m.getNombre();
            String desc   = m.getDescripcion();
            String pres   = m.getPresentacion();

            if ((nombre != null && nombre.toLowerCase().contains(needle)) ||
                    (desc   != null && desc.toLowerCase().contains(needle))   ||
                    (pres   != null && pres.toLowerCase().contains(needle))) {
                return m; // primer match
            }
        }
        return null;
    }

    // Agrega si el código no está repetido
    public boolean agregarMedicamento(Medicamento med) {
        if (med == null) return false;
        if (!existeMedicamento(med.getCodigo())) {
            medicamentos.add(med);
            return true;
        }
        return false;
    }

    // Reemplaza el medicamento que tenga el mismo código
    public boolean modificarMedicamento(Medicamento medPorActualizar) {
        if (medPorActualizar == null) return false;
        for (int i = 0; i < medicamentos.size(); i++) {
            if (medicamentos.get(i).getCodigo() == medPorActualizar.getCodigo()) {
                medicamentos.set(i, medPorActualizar);
                return true;
            }
        }
        return false;
    }

    // Elimina por código
    public boolean eliminarMedicamento(int codigo) {
        for (int i = 0; i < medicamentos.size(); i++) {
            if (medicamentos.get(i).getCodigo() == codigo) {
                medicamentos.remove(i);
                return true;
            }
        }
        return false;
    }

    // Guarda el catálogo en datos/medicamentos.xml
    public void guardarXML() throws Exception {
        FileOutputStream flujo = new FileOutputStream("datos/medicamentos.xml");
        JAXBContext context = JAXBContext.newInstance(GestorMedicamento.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, flujo);
    }

    // Carga el catálogo desde datos/medicamentos.xml
    public void cargarXML() throws Exception {
        FileInputStream flujo = new FileInputStream("datos/medicamentos.xml");
        JAXBContext context = JAXBContext.newInstance(GestorMedicamento.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GestorMedicamento temp = (GestorMedicamento) unmarshaller.unmarshal(flujo);
        this.medicamentos = temp.medicamentos;
    }

    // Representación simple para depurar
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Medicamento m : medicamentos) {
            sb.append(String.format("%n\t%s,", m));
        }
        return sb.append("\n").toString();
    }

    @XmlElement(name = "medicamento")
    private List<Medicamento> medicamentos;
}
