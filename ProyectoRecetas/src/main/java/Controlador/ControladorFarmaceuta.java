package Controlador;
import Vista.PanelFarmaceuta;
import entidades.Farmaceuta;
import Gestores.GestorFarmaceuta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControladorFarmaceuta {

    private PanelFarmaceuta vista;
    private GestorFarmaceuta gestor;

    public ControladorFarmaceuta(PanelFarmaceuta vista) {
        this.vista = vista;
        this.gestor = new GestorFarmaceuta();

        try {
            gestor.cargarXML();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar farmaceutas: " + e.getMessage());
        }

        cargarTabla();
        configurarEventos();
    }

    private void cargarTabla() {
        String[] columnas = {"ID", "Nombre"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Farmaceuta f : gestor.getFarmaceutas()) {
            modelo.addRow(new Object[]{f.getId(), f.getNombre()});
        }

        vista.tablaFarmaceutas.setModel(modelo);
    }

    private void configurarEventos() {
        vista.btnGuardar.addActionListener(e -> guardarFarmaceuta());
        vista.btnEliminar.addActionListener(e -> eliminarFarmaceuta());
        vista.btnBuscar.addActionListener(e -> buscarFarmaceutaPorId());
    }

    private void guardarFarmaceuta() {
        String id = vista.txtId.getText().trim();
        String nombre = vista.txtNombre.getText().trim();

        if (id.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios.");
            return;
        }

        Farmaceuta nuevo = new Farmaceuta(nombre, id);

        if (gestor.existeFarmaceuta(id)) {
            gestor.modificarFarmaceuta(nuevo);
        } else {
            gestor.agregarFarmaceuta(nuevo);
        }

        try {
            gestor.guardarXML();
            cargarTabla();
            JOptionPane.showMessageDialog(vista, "Farmaceuta guardado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage());
        }
    }

    private void eliminarFarmaceuta() {
        String id = vista.txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el ID del farmaceuta a eliminar.");
            return;
        }

        if (gestor.eliminarFarmaceuta(id)) {
            try {
                gestor.guardarXML();
                cargarTabla();
                JOptionPane.showMessageDialog(vista, "Farmaceuta eliminado.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error al guardar cambios: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontró el farmaceuta con ese ID.");
        }
    }

    private void buscarFarmaceutaPorId() {
        String id = JOptionPane.showInputDialog(vista, "Ingrese ID a buscar:");
        if (id == null || id.trim().isEmpty()) return;

        Farmaceuta encontrado = gestor.buscarFarmaceutaID(id.trim());
        if (encontrado != null) {
            vista.txtId.setText(encontrado.getId());
            vista.txtNombre.setText(encontrado.getNombre());
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontró ningún farmaceuta con ese ID.");
        }
    }

}