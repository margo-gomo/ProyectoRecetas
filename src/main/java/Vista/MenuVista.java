package Vista;

import Controlador.Entidades.ControladorReceta;
import Vista.Prescripción.DialogBuscarMedicamento;
import Vista.Prescripción.DialogBuscarPaciente;
import Vista.Prescripción.DialogBuscarReceta;
import Vista.Prescripción.DialogSeleccionarFecha;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

public class MenuVista extends JFrame {

    private JTabbedPane tabbedPanePrincipal;
    private JPanel panelPrincipal;
    private JButton buscarPacienteButton;
    private JButton agregarMedicamentoButton;
    private JButton elegirFechaButton;
    private JTable table1;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton descartarButton;
    private JButton detallesButton;
    private JButton buscarRecetaButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton iniciarProcesoButton;
    private JButton marcarListaButton;
    private JButton entregarButton;
    private JButton detallesButton1;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JButton elegirFechaButton3;
    private JButton elegirFechaButton4;
    private JComboBox comboBox1;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JTable table2;
    private JButton refrescarButton;
    private JButton limpiarButton1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField textField7;
    private JTextField textField8;
    private JButton buscarButton;
    private JButton buscarButton1;
    private JComboBox comboBox4;
    private JFormattedTextField formattedTextField3;
    private JButton elegirFechaButton1;
    private JTextField textField9;
    private JButton elegirFechaButton2;
    private JButton exportarButton;
    private JButton verDetallesButton;
    private JButton limpiarButton2;
    private JButton aplicarFiltrosButton;
    private JTable table3;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JButton modificarButton;
    private JButton guardarButton2;
    private JButton borrarButton;
    private JButton buscarButton2;
    private JTable table4;
    private JButton limpiarButton3;
    private JButton generarReporteButton;
    private JFormattedTextField formattedTextField4;
    private JTextField textField15;
    private JPanel panelContenedor;
    private JPanel controlPrescripcionPanel;
    private JPanel RecetaMedicaPrescripcionPanel;
    private JComboBox comboBox5;
    private JButton guardarFarm;
    private JButton modificarFarm;
    private JButton borrarFarm;
    private JButton limpiarFarm;
    private JButton guardarPaciente;
    private JButton modificarPaciente;
    private JButton borrarPaciente;
    private JButton limpiarPaciente;
    private JButton buscarFarma;
    private JButton generarFarma;
    private JButton buscarPaciente;
    private JButton generarPaciente;
    private JButton guardarMedicamento;
    private JButton modificarMedicamento;
    private JButton borrarMedicamento;
    private JButton limpiarMedicamento;
    private JButton buscarMedicamento;
    private JButton generarMedicamento;
    private JTable tablaDespacho;

    private DefaultTableModel modeloTablaRecetas;
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MenuVista() {
        setTitle("Sistema de Prescripción y Despacho de Recetas");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        configurarTablaRecetas();
        aplicarEstilosGenerales();

        buscarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogBuscarPaciente dialog = new DialogBuscarPaciente();
                dialog.setModal(true);
                dialog.setLocationRelativeTo(MenuVista.this);
                dialog.setVisible(true);
            }
        });

        agregarMedicamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogBuscarMedicamento dialog = new DialogBuscarMedicamento();
                dialog.setModal(true);
                dialog.setLocationRelativeTo(MenuVista.this);
                dialog.setVisible(true);
            }
        });

        elegirFechaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogSeleccionarFecha dialog = new DialogSeleccionarFecha();
                dialog.setModal(true);
                dialog.setLocationRelativeTo(MenuVista.this);
                dialog.setVisible(true);
            }
        });

        buscarRecetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogBuscarReceta dialog = new DialogBuscarReceta();
                dialog.setModal(true);
                dialog.setSize(700, 450);
                dialog.setLocationRelativeTo(MenuVista.this);
                dialog.setResizable(true);
                dialog.setVisible(true);
            }
        });

        elegirFechaButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogSeleccionarFecha dialog = new DialogSeleccionarFecha();
                dialog.setModal(true);
                dialog.setLocationRelativeTo(MenuVista.this);
                dialog.setVisible(true);
            }
        });
        elegirFechaButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogSeleccionarFecha dialog = new DialogSeleccionarFecha();
                dialog.setModal(true);
                dialog.setLocationRelativeTo(MenuVista.this);
                dialog.setVisible(true);
            }
        });

        elegirFechaButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogSeleccionarFecha dialog = new DialogSeleccionarFecha();
                dialog.setModal(true);
                dialog.setLocationRelativeTo(MenuVista.this);
                dialog.setVisible(true);
            }
        });
        elegirFechaButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogSeleccionarFecha dialog = new DialogSeleccionarFecha();
                dialog.setModal(true);
                dialog.setLocationRelativeTo(MenuVista.this);
                dialog.setVisible(true);
            }
        });
    }

    private void aplicarEstilosGenerales() {
        JButton[] todosLosBotones = {
                buscarPacienteButton, agregarMedicamentoButton, elegirFechaButton,
                guardarButton, limpiarButton, descartarButton, detallesButton, buscarRecetaButton,
                iniciarProcesoButton, marcarListaButton, entregarButton, detallesButton1,
                button3, button4, button5, refrescarButton, limpiarButton1,
                buscarButton, buscarButton1, elegirFechaButton1, elegirFechaButton2,
                elegirFechaButton3, elegirFechaButton4, exportarButton, verDetallesButton,
                limpiarButton2, aplicarFiltrosButton, modificarButton, guardarButton2,
                borrarButton, buscarButton2, limpiarButton3, generarReporteButton,
                guardarFarm, modificarFarm, borrarFarm, limpiarFarm,
                guardarPaciente, modificarPaciente, borrarPaciente, limpiarPaciente,
                buscarFarma, generarFarma, buscarPaciente, generarPaciente,
                guardarMedicamento, modificarMedicamento, borrarMedicamento, limpiarMedicamento,
                buscarMedicamento, generarMedicamento
        };

        for (JButton b : todosLosBotones) {
            if (b != null) {
                b.setFocusPainted(false);
                b.setBorderPainted(false);
                b.setBackground(new Color(66, 133, 244));
                b.setForeground(Color.WHITE);
                b.setFont(new Font("Segoe UI", Font.BOLD, 13));
                b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        JTable[] todasLasTablas = {table1, table2, table3, table4};
        for (JTable t : todasLasTablas) {
            if (t != null) {
                t.setFillsViewportHeight(true);
                t.setRowHeight(25);
                t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
                t.getTableHeader().setBackground(new Color(66, 133, 244));
                t.getTableHeader().setForeground(Color.WHITE);
                t.setSelectionBackground(new Color(204, 228, 255));
                t.setSelectionForeground(Color.BLACK);

                t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                                                                   boolean isSelected, boolean hasFocus,
                                                                   int row, int column) {
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        if (!isSelected) {
                            c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                        }
                        return c;
                    }
                });
            }
        }

        JComponent[] camposTexto = {
                textField1, textField2, textField3, textField4, textField5, textField6,
                textField7, textField8, textField9, textField10, textField11, textField12,
                textField15, formattedTextField1, formattedTextField2, formattedTextField3, formattedTextField4
        };

        for (JComponent f : camposTexto) {
            if (f != null) {
                f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                f.setBackground(Color.WHITE);
                f.setForeground(Color.BLACK);
                if (f instanceof JTextField) ((JTextField) f).setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
            }
        }

        JPanel[] paneles = {panelPrincipal, panelContenedor, controlPrescripcionPanel, RecetaMedicaPrescripcionPanel};
        for (JPanel p : paneles) {
            if (p != null) {
                p.setBackground(Color.WHITE);
            }
        }

        if (tabbedPanePrincipal != null) {
            tabbedPanePrincipal.setFont(new Font("Segoe UI", Font.BOLD, 13));
            tabbedPanePrincipal.setBackground(Color.WHITE);
        }
    }

    private void configurarTablaRecetas() {
        String[] columnas = {"ID Paciente", "Nombre Paciente", "Medicamentos", "Fecha Confección"};
        modeloTablaRecetas = new DefaultTableModel(columnas, 0);
        table1.setModel(modeloTablaRecetas);
    }

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Error iniciando FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> new MenuVista().setVisible(true));
    }
}
