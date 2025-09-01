package Controlador;

import Vista.PanelMedicos;
import entidades.Medico;
import Gestores.GestorMedico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControladorMedicos {

    private PanelMedicos vista;
    private GestorMedico gestor;

    public ControladorMedicos(PanelMedicos vista) {
        this.vista = vista;
        this.gestor = new GestorMedico();

        try {
            gestor.cargarXML();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar médicos: " + e.getMessage());
        }

        cargarTabla();
        configurarEventos();
    }

    private void cargarTabla() {
        String[] columnas = {"ID", "Nombre", "Especialidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Medico m : gestor.getMedicos()) {
            modelo.addRow(new Object[]{m.getId(), m.getNombre(), m.getEspecialidad()});
        }

        vista.tablaMedicos.setModel(modelo);
    }

    private void configurarEventos() {
        vista.btnGuardar.addActionListener(e -> guardarMedico());
        vista.btnEliminar.addActionListener(e -> eliminarMedico());
        vista.btnBuscar.addActionListener(e -> buscarMedicoPorId());
    }

    private void guardarMedico() {
        String id = vista.txtId.getText().trim();
        String nombre = vista.txtNombre.getText().trim();
        String especialidad = vista.txtEspecialidad.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios.");
            return;
        }

        Medico nuevo = new Medico(nombre, id, especialidad); // clave = id por defecto

        if (gestor.existeMedico(id)) {
            gestor.modificarMedico(nuevo);
        } else {
            gestor.agregarMedico(nuevo);
        }

        try {
            gestor.guardarXML();
            cargarTabla();
            JOptionPane.showMessageDialog(vista, "Médico guardado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage());
        }
    }

    private void eliminarMedico() {
        String id = vista.txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el ID del médico a eliminar.");
            return;
        }

        if (gestor.eliminarMedico(id)) {
            try {
                gestor.guardarXML();
                cargarTabla();
                JOptionPane.showMessageDialog(vista, "Médico eliminado.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error al guardar cambios: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontró el médico con ese ID.");
        }
    }

    private void buscarMedicoPorId() {
        String id = JOptionPane.showInputDialog(vista, "Ingrese ID a buscar:");
        if (id == null || id.trim().isEmpty()) return;

        Medico encontrado = gestor.buscarMedicoID(id.trim());
        if (encontrado != null) {
            vista.txtId.setText(encontrado.getId());
            vista.txtNombre.setText(encontrado.getNombre());
            vista.txtEspecialidad.setText(encontrado.getEspecialidad());
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontró ningún médico con ese ID.");
        }
    }
}

