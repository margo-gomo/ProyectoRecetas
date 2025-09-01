package Controlador;
import Gestores.GestorPaciente;
import Vista.PanelPaciente;
import entidades.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class ControladorPacientes {

    private PanelPaciente vista;
    private GestorPaciente gestor;

    public ControladorPacientes(PanelPaciente vista) {
        this.vista = vista;
        this.gestor = new GestorPaciente();

        try {
            gestor.cargarXML();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar pacientes: " + e.getMessage());
        }

        cargarTabla();
        configurarEventos();
    }

    private void cargarTabla() {
        String[] columnas = {"ID", "Nombre", "Fecha Nacimiento", "Teléfono"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Paciente p : gestor.getPacientes()) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getFecha_nacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    p.getTelefono()
            });
        }

        vista.tablaPacientes.setModel(modelo);
    }

    private void configurarEventos() {
        vista.btnGuardar.addActionListener(e -> guardarPaciente());
        vista.btnEliminar.addActionListener(e -> eliminarPaciente());
        vista.btnBuscar.addActionListener(e -> buscarPacientePorId());
    }

    private void guardarPaciente() {
        String idStr = vista.txtId.getText().trim();
        String nombre = vista.txtNombre.getText().trim();
        String fechaStr = vista.txtFechaNacimiento.getText().trim();
        String telefonoStr = vista.txtTelefono.getText().trim();

        if (idStr.isEmpty() || nombre.isEmpty() || fechaStr.isEmpty() || telefonoStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios.");
            return;
        }

        int id, telefono;
        LocalDate fechaNacimiento;
        try {
            id = Integer.parseInt(idStr);
            telefono = Integer.parseInt(telefonoStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaNacimiento = LocalDate.parse(fechaStr, formatter);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "ID y teléfono deben ser números enteros.");
            return;
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(vista, "La fecha debe tener el formato dd/MM/yyyy.");
            return;
        }

        Paciente nuevo = new Paciente(id, nombre, telefono, fechaNacimiento);

        if (gestor.existePaciente(id)) {
            gestor.modificarPaciente(nuevo);
        } else {
            gestor.agregarPaciente(nuevo);
        }

        try {
            gestor.guardarXML();
            cargarTabla();
            JOptionPane.showMessageDialog(vista, "Paciente guardado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage());
        }
    }

    private void eliminarPaciente() {
        String idStr = vista.txtId.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el ID del paciente a eliminar.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            if (gestor.eliminarPaciente(id)) {
                gestor.guardarXML();
                cargarTabla();
                JOptionPane.showMessageDialog(vista, "Paciente eliminado.");
            } else {
                JOptionPane.showMessageDialog(vista, "No se encontró el paciente con ese ID.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El ID debe ser un número entero.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar cambios: " + ex.getMessage());
        }
    }

    private void buscarPacientePorId() {
        String idStr = JOptionPane.showInputDialog(vista, "Ingrese ID a buscar:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            Paciente encontrado = gestor.buscarPacienteID(id);
            if (encontrado != null) {
                vista.txtId.setText(String.valueOf(encontrado.getId()));
                vista.txtNombre.setText(encontrado.getNombre());
                vista.txtFechaNacimiento.setText(encontrado.getFecha_nacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                vista.txtTelefono.setText(String.valueOf(encontrado.getTelefono()));
            } else {
                JOptionPane.showMessageDialog(vista, "No se encontró ningún paciente con ese ID.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El ID debe ser un número entero.");
        }
    }

}
