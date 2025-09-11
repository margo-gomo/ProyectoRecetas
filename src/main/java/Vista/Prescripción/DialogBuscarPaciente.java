package Vista.Prescripción;

import Controlador.Controlador;
import Modelo.entidades.Paciente;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class DialogBuscarPaciente extends JDialog {

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
    private TableRowSorter<DefaultTableModel> sorter;

    private Controlador controlador;

    // ------------------------------------------------------------------------------------------
    // ------------------------------------- CONSTRUCTORES --------------------------------------
    // ------------------------------------------------------------------------------------------

    public DialogBuscarPaciente() {
        initUI();
    }

    public DialogBuscarPaciente(Controlador controlador) {
        this.controlador = controlador;
        initUI();
        cargarPacientesDesdeXml();
        configurarFiltroInteractivo();
    }

    private void initUI() {
        try { FlatLightLaf.setup(); } catch (Exception ignored) {}

        setTitle("Buscar paciente");
        setModal(true);
        setContentPane(contentPane);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(640, 420);
        setLocationRelativeTo(null);
        setResizable(true);

        aplicarEstilos();
        configurarTabla();

        if (getRootPane() != null && buttonOK != null)
            getRootPane().setDefaultButton(buttonOK);

        if (buttonOK != null)     buttonOK.addActionListener(e -> dispose());
        if (buttonCancel != null) buttonCancel.addActionListener(e -> dispose());

        // Doble click/ENTER para aceptar
        if (table1 != null) {
            table1.addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2 && buttonOK != null) buttonOK.doClick();
                }
            });
            table1.addKeyListener(new KeyAdapter() {
                @Override public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER && buttonOK != null) {
                        e.consume();
                        buttonOK.doClick();
                    }
                }
            });
        }

        if (buscarButton != null) buscarButton.addActionListener(e -> aplicarFiltro());

        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { dispose(); }
        });

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

        if (contentPane != null) {
            contentPane.setBackground(Color.WHITE);
            ((JComponent) contentPane).setBorder(new EmptyBorder(12, 12, 12, 12));
            blanquearFondosRec(contentPane);
        }

        if (comboBox1 != null) {
            comboBox1.setFont(FNT_TXT);
            comboBox1.setBackground(Color.WHITE);
            comboBox1.setForeground(Color.BLACK);
            comboBox1.setModel(new DefaultComboBoxModel<>(new String[]{
                    "nombre", "id", "teléfono"
            }));
            comboBox1.setSelectedItem("nombre");
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
        b.putClientProperty("JButton.buttonType", "roundRect");
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
        String[] columnas = { "ID", "Nombre", "Teléfono" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        if (table1 != null) {
            table1.setModel(modeloTabla);
            sorter = new TableRowSorter<>(modeloTabla);
            table1.setRowSorter(sorter);
        }
    }

    private void cargarPacientesDesdeXml() {
        if (controlador == null || table1 == null) return;
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);
        for (Paciente p : controlador.obtenerListaPacientes()) {
            model.addRow(new Object[]{ p.getId(), p.getNombre(), p.getTelefono() });
        }
    }

    private void configurarFiltroInteractivo() {
        if (textField1 == null || sorter == null) return;

        Runnable aplicar = this::aplicarFiltro;

        textField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { aplicar.run(); }
            @Override public void removeUpdate(DocumentEvent e) { aplicar.run(); }
            @Override public void changedUpdate(DocumentEvent e) { aplicar.run(); }
        });

        if (comboBox1 != null) comboBox1.addActionListener(e -> aplicar.run());
    }

    private void aplicarFiltro() {
        if (sorter == null) return;
        String t = (textField1 != null && textField1.getText() != null) ? textField1.getText().trim() : "";
        if (t.isEmpty()) { sorter.setRowFilter(null); return; }

        int col = 1; // nombre
        String key = (comboBox1 != null && comboBox1.getSelectedItem() != null)
                ? comboBox1.getSelectedItem().toString().trim().toLowerCase()
                : "nombre";

        switch (key) {
            case "id": col = 0; break;
            case "teléfono":
            case "telefono": col = 2; break;
            default: col = 1;
        }
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(t), col));
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------- API / HELPERS PÚBLICOS -----------------------------------
    // ------------------------------------------------------------------------------------------

    /** Limpia y agrega filas (útil para pruebas). */
    public void setFilaEjemplo(Object id, Object nombre, Object telefono) {
        if (modeloTabla == null) return;
        modeloTabla.setRowCount(0);
        modeloTabla.addRow(new Object[]{ id, nombre, telefono });
    }

    public Object getIdSeleccionado() {
        if (table1 == null || table1.getSelectedRow() < 0) return null;
        int viewRow = table1.getSelectedRow();
        int modelRow = table1.convertRowIndexToModel(viewRow);
        return table1.getModel().getValueAt(modelRow, 0);
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
}
