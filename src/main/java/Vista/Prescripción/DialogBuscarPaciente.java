package Vista.Prescripción;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;

public class DialogBuscarPaciente extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table1;
    private JComboBox comboBox1;
    private JTextField textField1;

    private DefaultTableModel modeloTabla;

    public DialogBuscarPaciente() {
        setContentPane(contentPane);
        setModal(true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(true);
        getRootPane().setDefaultButton(buttonOK);

        aplicarEstilos();
        configurarTabla();

        buttonOK.addActionListener(e -> dispose());
        buttonCancel.addActionListener(e -> dispose());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void aplicarEstilos() {
        JButton[] botones = {buttonOK, buttonCancel};
        for (JButton b : botones) {
            if (b != null) {
                b.setFocusPainted(false);
                b.setBorderPainted(false);
                b.setBackground(new Color(66, 133, 244));
                b.setForeground(Color.WHITE);
                b.setFont(new Font("Segoe UI", Font.BOLD, 13));
                b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        if (table1 != null) {
            table1.setFillsViewportHeight(true);
            table1.setRowHeight(25);
            table1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            table1.getTableHeader().setBackground(new Color(66, 133, 244));
            table1.getTableHeader().setForeground(Color.WHITE);
            table1.setSelectionBackground(new Color(204, 228, 255));
            table1.setSelectionForeground(Color.BLACK);
            table1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                               boolean isSelected, boolean hasFocus,
                                                               int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (!isSelected) c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                    return c;
                }
            });
        }

        if (comboBox1 != null) {
            comboBox1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            comboBox1.setBackground(Color.WHITE);
            comboBox1.setForeground(Color.BLACK);
        }

        if (textField1 != null) {
            textField1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            textField1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        }

        if (contentPane != null) contentPane.setBackground(Color.WHITE);
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        if (table1 != null) table1.setModel(modeloTabla);
    }

    public static void main(String[] args) {
        DialogBuscarPaciente dialog = new DialogBuscarPaciente();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
