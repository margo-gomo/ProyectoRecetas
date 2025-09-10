package Vista.Prescripción;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class DialogBuscarMedicamento extends JDialog {

    // ------------------------------------------------------------------------------------------
    // -------------------------------- ATRIBUTOS DE LA VISTA -----------------------------------
    // ------------------------------------------------------------------------------------------

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table1;
    private JComboBox<String> comboBox1;
    private JTextField textField1;
    private JButton buscarButton;

    private DefaultTableModel modeloTabla;

    // ------------------------------------------------------------------------------------------
    // ------------------------------------- CONSTRUCTOR ----------------------------------------
    // ------------------------------------------------------------------------------------------

    public DialogBuscarMedicamento() {
        try { FlatLightLaf.setup(); } catch (Exception ignored) {}

        setTitle("Buscar medicamento");
        setModal(true);
        setContentPane(contentPane);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(640, 420);
        setLocationRelativeTo(null);
        setResizable(true);

        // Estilo unificado + tabla
        aplicarEstilos();
        configurarTabla();

        // Acciones por defecto
        if (getRootPane() != null && buttonOK != null)
            getRootPane().setDefaultButton(buttonOK);

        if (buttonOK != null)     buttonOK.addActionListener(e -> dispose());
        if (buttonCancel != null) buttonCancel.addActionListener(e -> dispose());

        // Cerrar con [X]
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { dispose(); }
        });

        // Cerrar con ESC
        if (contentPane != null) {
            contentPane.registerKeyboardAction(e -> dispose(),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                    JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        }
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------- ESTILOS / LOOK & FEEL ------------------------------------
    // ------------------------------------------------------------------------------------------

    private void aplicarEstilos() {
        final Color PRIMARY = new Color(66, 133, 244);
        final Color SECOND  = new Color(204, 228, 255);
        final Font  FNT_TXT = new Font("Segoe UI", Font.PLAIN, 13);
        final Font  FNT_BTN = new Font("Segoe UI", Font.BOLD, 13);

        // Panel raíz blanco + margen
        if (contentPane != null) {
            contentPane.setBackground(Color.WHITE);
            ((JComponent) contentPane).setBorder(new EmptyBorder(12, 12, 12, 12));
            blanquearFondosRec(contentPane);
        }

        // Campos de búsqueda
        if (comboBox1 != null) {
            comboBox1.setFont(FNT_TXT);
            comboBox1.setBackground(Color.WHITE);
            comboBox1.setForeground(Color.BLACK);
        }
        if (textField1 != null) {
            textField1.setFont(FNT_TXT);
            textField1.setForeground(Color.BLACK);
            textField1.setBackground(Color.WHITE);
            textField1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        }

        if (buttonOK != null)     estiloPrimario(buttonOK, PRIMARY, FNT_BTN);
        if (buttonCancel != null) estiloSecundario(buttonCancel, SECOND, PRIMARY, FNT_BTN);

        Set<JButton> pintados = new HashSet<>();
        if (buttonOK != null) pintados.add(buttonOK);
        if (buttonCancel != null) pintados.add(buttonCancel);
        estilizarBotonesRestantes(contentPane, pintados, SECOND, PRIMARY, FNT_BTN);


        igualarTamanoBotones(160, 36, buttonOK, buttonCancel);

        if (table1 != null) {
            table1.setFillsViewportHeight(true);
            table1.setRowHeight(25);
            table1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            table1.getTableHeader().setBackground(PRIMARY);
            table1.getTableHeader().setForeground(Color.WHITE);
            table1.setSelectionBackground(SECOND);
            table1.setSelectionForeground(Color.BLACK);
            table1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                               boolean isSelected, boolean hasFocus,
                                                               int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (!isSelected)
                        c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                    return c;
                }
            });
        }
    }

    private void blanquearFondosRec(Container root) {
        if (root == null) return;
        for (Component c : root.getComponents()) {
            if (c instanceof JPanel) {
                c.setBackground(Color.WHITE);
                ((JComponent) c).setOpaque(true);
            }
            if (c instanceof Container) blanquearFondosRec((Container) c);
        }
    }

    private void estiloPrimario(JButton b, Color primary, Font fnt) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(primary);
        b.setForeground(Color.WHITE);
        b.setFont(fnt);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setRolloverEnabled(true);
        b.putClientProperty("JButton.buttonType", "roundRect"); // hover FlatLaf
    }

    private void estiloSecundario(JButton b, Color bg, Color fg, Font fnt) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(fnt);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setRolloverEnabled(true);
        b.putClientProperty("JButton.buttonType", "roundRect");
    }

    private void estilizarBotonesRestantes(Container root, Set<JButton> ya, Color bg, Color fg, Font fnt) {
        if (root == null) return;
        for (Component c : root.getComponents()) {
            if (c instanceof JButton) {
                JButton b = (JButton) c;
                if (!ya.contains(b)) estiloSecundario(b, bg, fg, fnt);
            } else if (c instanceof Container) {
                estilizarBotonesRestantes((Container) c, ya, bg, fg, fnt);
            }
        }
    }

    private void igualarTamanoBotones(int w, int h, JButton... btns) {
        Dimension d = new Dimension(w, h);
        for (JButton b : btns) {
            if (b == null) continue;
            b.setMinimumSize(d);
            b.setPreferredSize(d);
            b.setMaximumSize(d);
        }
    }

    // ------------------------------------------------------------------------------------------
    // --------------------------------- CONFIGURACIÓN DE TABLA ---------------------------------
    // ------------------------------------------------------------------------------------------

    private void configurarTabla() {
        String[] columnas = { "Código", "Nombre", "Presentación" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        if (table1 != null) table1.setModel(modeloTabla);
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------- API / HELPERS PÚBLICOS -----------------------------------
    // ------------------------------------------------------------------------------------------


    public void setFilaEjemplo(Object codigo, Object nombre, Object presentacion) {
        if (modeloTabla == null) return;
        modeloTabla.setRowCount(0);
        modeloTabla.addRow(new Object[]{codigo, nombre, presentacion});
    }

    public Object getCodigoSeleccionado() {
        if (table1 == null || table1.getSelectedRow() < 0) return null;
        return table1.getValueAt(table1.getSelectedRow(), 0);
    }

    public void setOnAceptar(ActionListener l) {
        if (buttonOK != null) buttonOK.addActionListener(l);
    }

    public void setOnCancelar(ActionListener l) {
        if (buttonCancel != null) buttonCancel.addActionListener(l);
    }

    public JComboBox<String> getComboBoxFiltro() { return comboBox1; }
    public JTextField getCampoBusqueda() { return textField1; }
    public JTable getTabla() { return table1; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }

    // ------------------------------------------------------------------------------------------
    // ------------------------------------------ MAIN ------------------------------------------
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DialogBuscarMedicamento dialog = new DialogBuscarMedicamento();
            dialog.setVisible(true);
            System.exit(0);
        });
    }
}
