package Interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Menu {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable table1;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        table1=new JTable();
        String[] columnas = {"ID", "Nombre", "Especialidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        table1.setModel(modelo);
    }
}
