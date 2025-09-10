package Vista;

import Vista.Prescripción.DialogBuscarMedicamento;
import Vista.Prescripción.DialogBuscarPaciente;
import Vista.Prescripción.DialogBuscarReceta;
import Vista.Prescripción.DialogSeleccionarFecha;
import Modelo.entidades.Medico;
import Controlador.Controlador;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.HashSet;

public class MenuVista extends JFrame {

    // ------------------------------------------------------------------------------------------
    // -------------------------------- ATRIBUTOS DE LA VISTA -----------------------------------
    // ------------------------------------------------------------------------------------------

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
    private JTable tablaDashboard;
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
    private JTable tabHistorico;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JButton modificarButton;
    private JButton guardarButton2;
    private JButton borrarButton;
    private JButton buscarButton2;
    private JTable tabloMedicos;
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
    private JScrollPane scrollmedicos;
    private JTable tablaFarma;
    private JTable tablaPac;
    private JTable tablaMed;
    private JPanel panelLineas;
    private JPanel panelPastel;
    private JScrollPane scrollEstados;
    private JTable tablaEstados;

    // Campos formulario médicos
    private JTextField tfIdMedico;
    private JTextField tfNombreMedico;
    private JTextField tfEspMedico;

    private DefaultTableModel modeloTablaRecetas;
    private Controlador controlador;
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ------------------------------------------------------------------------------------------
    // ------------------------------------- CONSTRUCTOR ----------------------------------------
    // ------------------------------------------------------------------------------------------

    public MenuVista() {
        setTitle("Sistema de Prescripción y Despacho de Recetas");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        // Inicialización base
        configurarTablaRecetas();
        aplicarEstilosGenerales();
        configurarTablaMedicos();
        configurarTablaFarmaceutas();
        configurarTablaPacientes();
        configurarTablaMedicamentos();
        configurarTablaHistorico();
        configurarTablaDashboard();
        configurarTablaEstados();

        // ------------------------- LISTENERS BÁSICOS (DIÁLOGOS) -------------------------
        if (buscarPacienteButton != null) {
            buscarPacienteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DialogBuscarPaciente dialog = new DialogBuscarPaciente();
                    dialog.setModal(true);
                    dialog.setLocationRelativeTo(MenuVista.this);
                    dialog.setVisible(true);
                }
            });
        }

        if (agregarMedicamentoButton != null) {
            agregarMedicamentoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DialogBuscarMedicamento dialog = new DialogBuscarMedicamento();
                    dialog.setModal(true);
                    dialog.setLocationRelativeTo(MenuVista.this);
                    dialog.setVisible(true);
                }
            });
        }

        if (buscarRecetaButton != null) {
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

        ActionListener fechaListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogSeleccionarFecha dialog = new DialogSeleccionarFecha();
                dialog.setModal(true);
                dialog.setLocationRelativeTo(MenuVista.this);
                dialog.setVisible(true);
            }
        };
        if (elegirFechaButton  != null) elegirFechaButton.addActionListener(fechaListener);
        if (elegirFechaButton1 != null) elegirFechaButton1.addActionListener(fechaListener);
        if (elegirFechaButton2 != null) elegirFechaButton2.addActionListener(fechaListener);
        if (elegirFechaButton3 != null) elegirFechaButton3.addActionListener(fechaListener);
        if (elegirFechaButton4 != null) elegirFechaButton4.addActionListener(fechaListener);

        // ------------------------- LISTENER: GUARDAR MÉDICO -------------------------
        if (guardarButton2 != null) {
            guardarButton2.addActionListener(new ActionListener() { // Médicos
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!validarCamposMedico()) return;

                    if (controlador == null) {
                        JOptionPane.showMessageDialog(MenuVista.this,
                                "No hay controlador inicializado.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String id  = tfIdMedico.getText().trim();
                    String nom = tfNombreMedico.getText().trim();
                    String esp = tfEspMedico.getText().trim();

                    try {
                        Medico existente = controlador.buscarMedicoPorId(id);
                        if (existente != null) {
                            JOptionPane.showMessageDialog(MenuVista.this,
                                    "Ya existe un médico con ID: " + id,
                                    "Duplicado", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        Medico nuevo = new Medico(id, nom, esp);
                        Medico agregado = controlador.agregarMedico(nuevo);
                        agregarMedicoATabla(agregado);
                        limpiarCamposMedico();

                        JOptionPane.showMessageDialog(MenuVista.this,
                                "Médico guardado correctamente.",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(MenuVista.this,
                                "Error al guardar: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------- SETTER DEL CONTROLADOR -----------------------------------
    // ------------------------------------------------------------------------------------------

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------- ESTILOS DEL MENÚ PRINCIPAL --------------------------------
    // ------------------------------------------------------------------------------------------

    private void aplicarEstilosGenerales() {
        // Paleta común (login/cambio clave/menú)
        final Color PRIMARY = new Color(66, 133, 244);   // celeste principal
        final Color SECOND  = new Color(204, 228, 255);  // celeste claro

        // Paneles y tabs
        JPanel[] paneles = { panelPrincipal, panelContenedor, controlPrescripcionPanel, RecetaMedicaPrescripcionPanel };
        for (JPanel p : paneles) if (p != null) p.setBackground(Color.WHITE);
        if (tabbedPanePrincipal != null) {
            tabbedPanePrincipal.setFont(new Font("Segoe UI", Font.BOLD, 13));
            tabbedPanePrincipal.setBackground(Color.WHITE);
        }

        // Campos de texto
        JComponent[] camposTexto = {
                textField1, textField2, textField3, textField4, textField5, textField6,
                textField7, textField8, textField9, textField10, textField11, textField12,
                textField15, formattedTextField1, formattedTextField2, formattedTextField3, formattedTextField4,
                tfIdMedico, tfNombreMedico, tfEspMedico
        };
        for (JComponent c : camposTexto) {
            if (c == null) continue;
            c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            c.setBackground(Color.WHITE);
            c.setForeground(Color.BLACK);
            if (c instanceof JTextField) {
                ((JTextField) c).setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
            }
        }

        // Botones primarios (confirmar/crear)
        JButton[] primarios = {
                guardarButton, guardarButton2,
                guardarFarm, guardarPaciente, guardarMedicamento,
                generarFarma, generarPaciente, generarMedicamento,
                iniciarProcesoButton, aplicarFiltrosButton, exportarButton,
                generarReporteButton, entregarButton, marcarListaButton
        };

        // Botones secundarios (búsqueda, limpieza, detalles, fechas, etc.)
        JButton[] secundarios = {
                buscarPacienteButton, agregarMedicamentoButton, buscarRecetaButton,
                buscarButton, buscarButton1, buscarButton2, buscarFarma, buscarPaciente, buscarMedicamento,
                elegirFechaButton, elegirFechaButton1, elegirFechaButton2, elegirFechaButton3, elegirFechaButton4,
                limpiarButton, limpiarButton1, limpiarButton2, limpiarButton3, limpiarFarm, limpiarPaciente, limpiarMedicamento,
                descartarButton, detallesButton, detallesButton1, verDetallesButton, refrescarButton,
                modificarButton, modificarFarm, modificarPaciente, modificarMedicamento,
                borrarButton, borrarFarm, borrarPaciente, borrarMedicamento,
                button3, button4, button5
        };

        for (JButton b : primarios) if (b != null) estiloPrimario(b, PRIMARY);
        for (JButton b : secundarios) if (b != null) estiloSecundario(b, SECOND, PRIMARY);

        // Asegura que ningún botón quede sin estilo (secundario por defecto)
        Set<JButton> yaEstilados = new HashSet<>();
        addButtonsToSet(yaEstilados, primarios);
        addButtonsToSet(yaEstilados, secundarios);
        estilizarBotonesRestantes(panelPrincipal, yaEstilados, SECOND, PRIMARY);

        // Tablas con look & feel unificado
        JTable[] todasLasTablas = { table1, tablaDashboard, tabHistorico, tabloMedicos, tablaDespacho, tablaFarma, tablaPac, tablaMed, tablaEstados };
        for (JTable t : todasLasTablas) {
            if (t == null) continue;
            t.setFillsViewportHeight(true);
            t.setRowHeight(25);
            t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            t.getTableHeader().setBackground(PRIMARY);
            t.getTableHeader().setForeground(Color.WHITE);
            t.setSelectionBackground(SECOND);
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

    // ------------------------------------------------------------------------------------------
    // --------------------------------- CONFIGURACIÓN DE TABLAS --------------------------------
    // ------------------------------------------------------------------------------------------

    private void configurarTablaMedicos() {
        if (tabloMedicos != null) {
            String[] columnasMedicos = {"ID", "Nombre", "Especialidad"};
            DefaultTableModel modeloMedicos = new DefaultTableModel(columnasMedicos, 0);
            tabloMedicos.setModel(modeloMedicos);
        }
    }

    private void configurarTablaFarmaceutas() {
        if (tablaFarma != null) {
            String[] columnasFarma = {"ID", "Nombre"};
            DefaultTableModel modeloFarma = new DefaultTableModel(columnasFarma, 0);
            tablaFarma.setModel(modeloFarma);
        }
    }

    private void configurarTablaPacientes() {
        if (tablaPac != null) {
            String[] columnasPac = {"ID Paciente", "Nombre", "Fecha de Nacimiento", "Teléfono"};
            DefaultTableModel modeloPac = new DefaultTableModel(columnasPac, 0);
            tablaPac.setModel(modeloPac);
        }
    }

    private void configurarTablaMedicamentos() {
        if (tablaMed != null) {
            String[] columnasMed = {"Código", "Nombre", "Descripción"};
            DefaultTableModel modeloMed = new DefaultTableModel(columnasMed, 0);
            tablaMed.setModel(modeloMed);
        }
    }

    private void configurarTablaRecetas() {
        String[] columnasRecetas = {"ID Paciente", "Nombre Paciente", "Medicamentos", "Fecha Confección"};
        modeloTablaRecetas = new DefaultTableModel(columnasRecetas, 0);
        if (table1 != null) table1.setModel(modeloTablaRecetas);

        if (tablaDespacho != null) {
            String[] columnasDespacho = {"ID Paciente", "Nombre Paciente", "Fecha Actual", "Fecha de Retiro", "Estado"};
            DefaultTableModel modeloDespacho = new DefaultTableModel(columnasDespacho, 0);
            tablaDespacho.setModel(modeloDespacho);
        }
    }

    private void configurarTablaHistorico() {
        if (tabHistorico != null) {
            String[] columnasHistorico = {"ID Paciente", "Nombre Paciente", "Medicamentos", "Fecha Confección", "Fecha de Retiro", "Estado"};
            DefaultTableModel modeloHistorico = new DefaultTableModel(columnasHistorico, 0) {
                @Override public boolean isCellEditable(int row, int column) { return false; }
            };
            tabHistorico.setModel(modeloHistorico);
        }
    }

    private void configurarTablaEstados() {
        if (tablaEstados != null) {
            String[] columnasEstados = {"Estado", "Cantidad de Recetas"};
            DefaultTableModel modeloEstados = new DefaultTableModel(columnasEstados, 0) {
                @Override public boolean isCellEditable(int row, int column) { return false; }
            };
            tablaEstados.setModel(modeloEstados);
        }
    }

    private void configurarTablaDashboard() {
        if (tablaDashboard != null) {
            String[] columnasDashboard = { "Mes/Año", "Cantidad de Recetas" };
            DefaultTableModel modeloDashboard = new DefaultTableModel(columnasDashboard, 0) {
                @Override public boolean isCellEditable(int row, int column) { return false; }
            };
            tablaDashboard.setModel(modeloDashboard);
        }
    }

    // ------------------------------------------------------------------------------------------
    // --------------------------- HELPERS: FORMULARIO MÉDICOS ---------------------------------
    // ------------------------------------------------------------------------------------------

    private void limpiarCamposMedico() {
        if (tfIdMedico != null) tfIdMedico.setText("");
        if (tfNombreMedico != null) tfNombreMedico.setText("");
        if (tfEspMedico != null) tfEspMedico.setText("");
        if (tfIdMedico != null) tfIdMedico.requestFocus();
    }

    private void agregarMedicoATabla(Medico m) {
        if (tabloMedicos == null || m == null) return;
        DefaultTableModel model = (DefaultTableModel) tabloMedicos.getModel();
        model.addRow(new Object[]{ m.getId(), m.getNombre(), m.getEspecialidad() });
    }

    private boolean validarCamposMedico() {
        String id  = (tfIdMedico != null)     ? tfIdMedico.getText().trim()     : "";
        String nom = (tfNombreMedico != null) ? tfNombreMedico.getText().trim() : "";
        String esp = (tfEspMedico != null)    ? tfEspMedico.getText().trim()    : "";

        if (id.isEmpty() || nom.isEmpty() || esp.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Complete el ID, Nombre y Especialidad.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------ HELPERS: ESTILO UNIFICADO --------------------------------
    // ------------------------------------------------------------------------------------------

    private void estiloPrimario(JButton b, Color primary) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(primary);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void estiloSecundario(JButton b, Color bg, Color fg) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void addButtonsToSet(Set<JButton> set, JButton... btns) {
        if (btns == null) return;
        for (JButton b : btns) if (b != null) set.add(b);
    }

    private void estilizarBotonesRestantes(Container root, Set<JButton> yaEstilados, Color bg, Color fg) {
        if (root == null) return;
        for (Component comp : root.getComponents()) {
            if (comp instanceof JButton) {
                JButton b = (JButton) comp;
                if (!yaEstilados.contains(b)) {
                    estiloSecundario(b, bg, fg);
                }
            } else if (comp instanceof Container) {
                estilizarBotonesRestantes((Container) comp, yaEstilados, bg, fg);
            }
        }
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------------------ MAIN ------------------------------------------
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Error iniciando FlatLaf: " + ex.getMessage());
        }
        SwingUtilities.invokeLater(() -> new MenuVista().setVisible(true));
    }
}
