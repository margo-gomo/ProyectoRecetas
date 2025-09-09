package Vista;

import Controlador.Entidades.ControladorReceta;
import Prescripcion.PrescripcionReceta;
import Prescripcion.Indicaciones;
import Vista.Prescripción.DialogBuscarMedicamento;
import Vista.Prescripción.DialogBuscarPaciente;
import Vista.Prescripción.DialogBuscarReceta;
import Vista.Prescripción.DialogSeleccionarFecha;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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
    private JTextField textField1; // Fecha actual (confección)
    private JTextField textField2; // Fecha de retiro
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
    private JButton hoyButton;
    private JButton button1;
    private JButton hoyButton1;
    private JButton button2;
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
    private JButton hoyButton2;
    private JButton button6;
    private JTextField textField9;
    private JButton hoyButton3;
    private JButton button7;
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

    private ControladorReceta controladorReceta;
    private DefaultTableModel modeloTablaRecetas;

    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MenuVista() {
        setTitle("Sistema de Prescripción y Despacho de Recetas");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        configurarTablaRecetas();

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
