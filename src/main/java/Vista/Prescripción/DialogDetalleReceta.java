package Vista.Prescripción;

import com.formdev.flatlaf.FlatLightLaf;
import Modelo.entidades.Receta.Indicacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DialogDetalleReceta extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JTable tablaDetalles;

    private DefaultTableModel modeloTabla;

    public DialogDetalleReceta() {
        try { FlatLightLaf.setup(); } catch (Exception ignored) {}

        setTitle("Detalle de Receta");
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(true);

        inicializarComponentes();
        aplicarEstilos();
        registrarEventos();
    }

    private void inicializarComponentes() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // Modelo de solo lectura
        modeloTabla = new DefaultTableModel(
                new Object[]{"Medicamento", "Cantidad", "Duración (días)", "Indicaciones"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // solo lectura
            }
        };

        tablaDetalles = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaDetalles);
        contentPane.add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonOK = new JButton("Aceptar");
        panelBotones.add(buttonOK);

        contentPane.add(panelBotones, BorderLayout.SOUTH);
    }

    private void aplicarEstilos() {
        final Color PRIMARY = new Color(66, 133, 244);
        final Color SECOND  = new Color(204, 228, 255);
        final Font  FNT_TXT = new Font("Segoe UI", Font.PLAIN, 13);
        final Font  FNT_BTN = new Font("Segoe UI", Font.BOLD, 13);

        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        contentPane.setBackground(Color.WHITE);

        // Tabla
        tablaDetalles.setFont(FNT_TXT);
        tablaDetalles.setRowHeight(24);
        tablaDetalles.setFillsViewportHeight(true);

        // Estilo encabezados
        JTableHeader header = tablaDetalles.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setOpaque(true);
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);

        // Botones
        igualarTamanoBotones(120, 32, buttonOK);
    }

    private void registrarEventos() {
        buttonOK.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { dispose(); }
        });

        contentPane.registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    // --------------------- ESTILOS ----------------------

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

    private void igualarTamanoBotones(int w, int h, JButton... btns) {
        Dimension d = new Dimension(w, h);
        for (JButton b : btns) {
            if (b == null) continue;
            b.setMinimumSize(d);
            b.setPreferredSize(d);
            b.setMaximumSize(d);
        }
    }

    // --------------------- INTEGRACIÓN ----------------------

    public void setIndicaciones(List<Indicacion> indicaciones) {
        modeloTabla.setRowCount(0);
        if (indicaciones == null) return;
        for (Indicacion ind : indicaciones) {
            if (ind != null && ind.getMedicamento() != null) {
                modeloTabla.addRow(new Object[]{
                        ind.getMedicamento().getNombre(),
                        ind.getCantidad(),
                        ind.getDuracion(),
                        ind.getDescripcion()
                });
            }
        }
    }
}
