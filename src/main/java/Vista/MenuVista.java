package Vista;

import Vista.Prescripción.*;
import Modelo.entidades.Medico;
import Modelo.entidades.Paciente;
import Controlador.Controlador;
import Modelo.entidades.Medicamento;
import Modelo.entidades.Receta.Indicacion;
import Modelo.entidades.Receta.Receta;
import Modelo.entidades.Farmaceuta;
import Adaptador.LocalDateAdapter;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class MenuVista extends JFrame {

    // -------------------------------- ATRIBUTOS -----------------------------------

    private static final Color PRIMARY = new Color(66, 133, 244);
    private static final Color SECOND  = new Color(204, 228, 255);

    private JTabbedPane tabbedPanePrincipal;
    private JPanel panelPrincipal;
    private JButton buscarPacienteButton;
    private JButton agregarMedicamentoButton;
    private JButton elegirFechaButton;
    private JTable tablaPrescripcion;
    private JButton guardarButton;
    private JButton limpiarprescBtn;
    private JButton descartarMedicamentoPresc;
    private JButton detallesButton;
    private JButton buscarRecetaButton;
    private JTextField tfBusquedaMedicon;
    private JTextField tfBusquedaFarmaceutas;
    private JTextField tfBusquedaMedicamento;
    private JTextField tfBusquedaPaciente;
    private JTextField textField5;
    private JTextField textField6;
    private JButton iniciarProcesoButton;
    private JButton marcarListaButton;
    private JButton entregarButton;
    private JButton detallesButton1;
    private JFormattedTextField formattedTextFieldDesdeDash;
    private JFormattedTextField formattedTextFieldHastaDash;
    private JButton elegirFechaDesdeButtonDash;
    private JButton elegirFechaHastaButtonDash;
    private JComboBox comboBox1;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JTable tablaMesAnioDashboard;
    private JButton refrescarButtonDashboard;
    private JButton limpiarButtonDashboard;
    private JTextField textField7;
    private JTextField tfHistorico;
    private JButton buscarButton;
    private JButton buscarButton1;
    private JFormattedTextField formattedTextField3;
    private JButton elegirFechaButton1;
    private JTextField textField9;
    private JButton elegirFechaButton2;
    private JButton exportarHistoricoBtn;
    private JButton verDetallesButton;
    private JButton limpiarHistoricoBtn;
    private JButton aplicarFiltrosButton;
    private JTable tablaHistorico;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JButton modificarButton;
    private JButton guardarButton2;
    private JButton borrarButton;
    private JButton buscarButton2;
    private JTable tabloMedicos;
    private JButton limpiarButton3;
    private JButton generarReporteMedicosButton;
    private JFormattedTextField tfFechaNacPaciente;
    private JTextField tfTelefonoPaciente;
    private JPanel panelContenedor;
    private JPanel controlPrescripcionPanel;
    private JPanel RecetaMedicaPrescripcionPanel;
    private JComboBox comboMedicos;
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
    private JPanel panelLineasDashboard;
    private JPanel panelPastelDashboard;
    private JScrollPane scrollEstados;
    private JTable tablaEstadosDashboard;
    private JTextField tfIdMedico;
    private JTextField tfNombreMedico;
    private JTextField tfEspMedico;
    private JTextField tfIdFarma;
    private JTextField tfNombreFarma;
    private JTextField tfIdPaciente;
    private JTextField tfNombrePaciente;
    private JTextField tfCodigoMed;
    private JTextField tfNombreMed;
    private JTextField tfDescMed;
    private JButton elegirFechaButton5;
    private JPanel JPanelNormal;
    private JComboBox comboFarmaceutas;
    private JComboBox comboPacientes;
    private JComboBox comboMedicamentos;
    private JLabel labelNomPaciente;
    private JLabel labelFechaActualPresc;
    private JLabel labelFechaRetiroPresc;
    private JComboBox comboRecetaDespacho;
    private JTextField tfRecetasDespacho;
    private JTextField tfCodPresc;
    private JComboBox comboCodigoHist;
    private JPanel panelacerca;
    private JLabel lblTitulo;
    private JLabel lblDescripcion;

    private DefaultTableModel modeloTablaRecetas;
    private Controlador controlador;
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private TableRowSorter<DefaultTableModel> sorterMedicos;
    private TableRowSorter<DefaultTableModel> sorterFarma;
    private TableRowSorter<DefaultTableModel> sorterPac;
    private TableRowSorter<DefaultTableModel> sorterMed;
    private TableRowSorter<DefaultTableModel> sorterDesp;
    private Receta recetaEnPantalla;

    private Paciente pacienteSeleccionado;

    // ------------------------------------- CONSTRUCTOR ----------------------------------------

    public MenuVista(Controlador contr) {
        controlador = contr;

        setTitle("Sistema de Prescripción y Despacho de Recetas");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        configurarTablaRecetas();
        aplicarEstilosGenerales();
        configurarTablaMedicos();
        configurarTablaFarmaceutas();
        configurarTablaPacientes();
        configurarTablaMedicamentos();
        configurarTablaHistorico();
        configurarTablaDashboard();
        configurarTablaEstados();
        cargarDatosIniciales();
        cargarMedicamentosEnComboDashboard();
        recargarTablaIndicacionesDesdeControlador();
        cargarHistoricoEnTabla();
        configurarPanelAcerca();


        if (tabbedPanePrincipal != null) {
            tabbedPanePrincipal.setIconAt(8, FontIcon.of(FontAwesomeSolid.INFO_CIRCLE, 16, PRIMARY));    // Acerca de
            tabbedPanePrincipal.setIconAt(0, FontIcon.of(FontAwesomeSolid.FILE_MEDICAL, 16, PRIMARY)); // Prescripción
            tabbedPanePrincipal.setIconAt(1, FontIcon.of(FontAwesomeSolid.CHECK, 16, PRIMARY));        // Despacho
            tabbedPanePrincipal.setIconAt(2, FontIcon.of(FontAwesomeSolid.USER_MD, 16, PRIMARY));      // Médicos
            tabbedPanePrincipal.setIconAt(3, FontIcon.of(FontAwesomeSolid.PILLS, 16, PRIMARY));        // Farmacéutas
            tabbedPanePrincipal.setIconAt(4, FontIcon.of(FontAwesomeSolid.USER, 16, PRIMARY));         // Pacientes
            tabbedPanePrincipal.setIconAt(5, FontIcon.of(FontAwesomeSolid.CAPSULES, 16, PRIMARY));     // Medicamentos
            tabbedPanePrincipal.setIconAt(6, FontIcon.of(FontAwesomeSolid.HISTORY, 16, PRIMARY));      // Histórico
            tabbedPanePrincipal.setIconAt(7, FontIcon.of(FontAwesomeSolid.CHART_LINE, 16, PRIMARY));   // Dashboard
        }

        if (labelNomPaciente != null) {
            labelNomPaciente.setText("(sin paciente seleccionado)");
        }

        if (labelFechaActualPresc != null) {
            labelFechaActualPresc.setText(LocalDate.now().format(formatoFecha));
        }
        if (labelFechaRetiroPresc != null) {
            labelFechaRetiroPresc.setText("(sin fecha)");
        }

        // ------------------------- LISTENERS DE DIÁLOGOS -------------------------

        if (buscarPacienteButton != null) {
            buscarPacienteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DialogBuscarPaciente dialog = new DialogBuscarPaciente(controlador);
                    dialog.setModal(true);
                    dialog.setLocationRelativeTo(MenuVista.this);
                    dialog.setVisible(true);
                    if (dialog.isAceptado()) {
                        Object idObj = dialog.getIdSeleccionado();
                        if (idObj != null) {
                            try {
                                int id = Integer.parseInt(idObj.toString());
                                Paciente p = controlador.buscarPacientePorId(id);
                                if (p != null) {
                                    pacienteSeleccionado = p;
                                    if (labelNomPaciente != null) {
                                        labelNomPaciente.setText(p.getNombre() + " (ID " + p.getId() + ")");
                                    }
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(MenuVista.this,
                                        "El ID del paciente seleccionado no es válido.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        pacienteSeleccionado = null;
                        if (labelNomPaciente != null) {
                            labelNomPaciente.setText("(sin paciente seleccionado)");
                        }
                    }
                }
            });
        }

        if (agregarMedicamentoButton != null) {
            agregarMedicamentoButton.addActionListener(e -> {
                Integer fila = null;
                if (tablaPrescripcion != null && tablaPrescripcion.getSelectedRow() >= 0) {
                    fila = tablaPrescripcion.convertRowIndexToModel(tablaPrescripcion.getSelectedRow());
                }
                abrirDialogMedicamentoYAgregar(fila);
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
        if (elegirFechaButton != null) {
            elegirFechaButton.addActionListener(e -> seleccionarFechaParaLabel(labelFechaRetiroPresc));
        }
        if (elegirFechaButton1 != null) elegirFechaButton1.addActionListener(fechaListener);
        if (elegirFechaButton2 != null) elegirFechaButton2.addActionListener(fechaListener);
        if (elegirFechaDesdeButtonDash != null) {
            elegirFechaDesdeButtonDash.addActionListener(e -> seleccionarFechaPara(formattedTextFieldDesdeDash));
        }
        if (elegirFechaHastaButtonDash != null) {
            elegirFechaHastaButtonDash.addActionListener(e -> seleccionarFechaPara(formattedTextFieldHastaDash));
        }

        if (elegirFechaButton5 != null) {
            elegirFechaButton5.addActionListener(e -> seleccionarFechaPara(tfFechaNacPaciente));
        }

        // ----- GENERAR REPORTES (PDF) -----

        if (generarReporteMedicosButton != null) {
            generarReporteMedicosButton.addActionListener(e -> {
                try {
                    controlador.exportarMedicos();
                    JOptionPane.showMessageDialog(MenuVista.this,
                            "Reporte de médicos generado correctamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this,
                            "Error al generar reporte de médicos: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (generarFarma != null) {
            generarFarma.addActionListener(e -> {
                try {
                    controlador.exportarFarmaceutas();
                    JOptionPane.showMessageDialog(MenuVista.this,
                            "Reporte de farmacéutas generado correctamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this,
                            "Error al generar reporte de farmacéutas: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (generarPaciente != null) {
            generarPaciente.addActionListener(e -> {
                try {
                    controlador.exportarPacientes();
                    JOptionPane.showMessageDialog(MenuVista.this,
                            "Reporte de pacientes generado correctamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this,
                            "Error al generar reporte de pacientes: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (generarMedicamento != null) {
            generarMedicamento.addActionListener(e -> {
                try {
                    controlador.exportarMedicamentos();
                    JOptionPane.showMessageDialog(MenuVista.this,
                            "Reporte de medicamentos generado correctamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this,
                            "Error al generar reporte de medicamentos: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // GUARDAR PRESCRIPCIÓN
        if (guardarButton != null) {
            guardarButton.addActionListener(e -> guardarRecetaDesdeUI());
        }

        // LIMPIAR PRESCRIPCIÓN
        if (limpiarprescBtn != null) {
            limpiarprescBtn.addActionListener(e -> limpiarPrescripcionUI());
        }

        // DESCARTAR
        if (descartarMedicamentoPresc != null) {
            descartarMedicamentoPresc.addActionListener(e -> descartarIndicacionActual());
        }

        // GUARDAR MÉDICO
        if (guardarButton2 != null) {
            guardarButton2.addActionListener(new ActionListener() { // Médicos
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!validarCamposMedico()) return;


                    String id  = tfIdMedico.getText().trim();
                    String nom = tfNombreMedico.getText().trim();
                    String esp = tfEspMedico.getText().trim();

                    try {
                        Medico agregado = controlador.agregarMedico(id,nom,esp);
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
        // MODIFICAR MÉDICO
        if (modificarButton != null) {
            modificarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                    String id = obtenerIdDesdeFormularioOSel();
                    if (id.isEmpty()) {
                        JOptionPane.showMessageDialog(MenuVista.this,
                                "Ingrese el ID o seleccione un médico en la tabla.",
                                "Falta ID", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    try {
                        String nom = (tfNombreMedico != null) ? tfNombreMedico.getText().trim() : "";
                        String esp = (tfEspMedico != null) ? tfEspMedico.getText().trim() : "";
                        Medico resultado   = controlador.actualizarMedico(id, nom,esp);
                        actualizarMedicoEnTabla(resultado);

                        limpiarCamposMedico();

                        JOptionPane.showMessageDialog(MenuVista.this,
                                "Médico modificado correctamente.",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(MenuVista.this,
                                "Error al modificar: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        // BORRAR MÉDICO

        if (borrarButton != null) {
            borrarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String id = (tfIdMedico != null) ? tfIdMedico.getText().trim() : "";
                    if ((id == null || id.isEmpty()) && tabloMedicos != null && tabloMedicos.getSelectedRow() >= 0) {
                        int sel = tabloMedicos.getSelectedRow();
                        Object val = tabloMedicos.getValueAt(sel, 0);
                        id = (val != null) ? val.toString() : "";
                    }

                    if (id == null || id.isEmpty()) {
                        JOptionPane.showMessageDialog(MenuVista.this,
                                "Ingrese el ID del médico o seleccione una fila en la tabla.",
                                "Falta ID", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int opc = JOptionPane.showConfirmDialog(
                            MenuVista.this,
                            "¿Desea eliminar al médico con ID: " + id + "?",
                            "Confirmar borrado",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (opc != JOptionPane.YES_OPTION) return;

                    try {
                        controlador.eliminarMedico(id);

                        eliminarMedicoDeTablaPorId(id);
                        limpiarCamposMedico();

                        JOptionPane.showMessageDialog(MenuVista.this,
                                "Médico eliminado correctamente.",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(MenuVista.this,
                                "Error al eliminar: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        // LIMPIAR MÉDICO

        limpiarButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabloMedicos != null && tabloMedicos.isEditing()) {
                    tabloMedicos.getCellEditor().stopCellEditing();
                }
                if (tabloMedicos != null) {
                    tabloMedicos.clearSelection();
                }
                limpiarCamposMedico();
                if (tfBusquedaMedicon != null) tfBusquedaMedicon.setText("");
            }
        });

        // GUARDAR FARMACEUTA
        if (guardarFarm != null) {
            guardarFarm.addActionListener(e -> {
                if (!validarCamposFarmaceuta()) return;

                String id  = tfIdFarma.getText().trim();
                String nom = tfNombreFarma.getText().trim();

                try {
                    Farmaceuta agregado = controlador.agregarFarmaceuta(id,nom);

                    agregarFarmaceutaATabla(agregado);
                    limpiarCamposFarmaceuta();

                    JOptionPane.showMessageDialog(MenuVista.this, "Farmacéuta guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // MODIFICAR FARMACEUTA
        if (modificarFarm != null) {
            modificarFarm.addActionListener(e -> {

                String id = (tfIdFarma != null) ? tfIdFarma.getText().trim() : "";
                if (id.isEmpty() && tablaFarma != null && tablaFarma.getSelectedRow() >= 0) {
                    Object v = tablaFarma.getValueAt(tablaFarma.getSelectedRow(), 0);
                    id = (v != null) ? v.toString() : "";
                }
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Ingrese el ID o seleccione un farmacéuta en la tabla.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {

                    String nom = (tfNombreFarma != null) ? tfNombreFarma.getText().trim() : "";
                    Farmaceuta actualizado = controlador.actualizarFarmaceuta(id,nom);
                    actualizarFarmaceutaEnTabla(actualizado);
                    limpiarCamposFarmaceuta();

                    JOptionPane.showMessageDialog(MenuVista.this, "Farmacéuta modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // BORRAR FARMACEUTA
        if (borrarFarm != null) {
            borrarFarm.addActionListener(e -> {

                String id = (tfIdFarma != null) ? tfIdFarma.getText().trim() : "";
                if (id.isEmpty() && tablaFarma != null && tablaFarma.getSelectedRow() >= 0) {
                    Object v = tablaFarma.getValueAt(tablaFarma.getSelectedRow(), 0);
                    id = (v != null) ? v.toString() : "";
                }
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Ingrese el ID o seleccione un farmacéuta en la tabla.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int opc = JOptionPane.showConfirmDialog(
                        MenuVista.this,
                        "¿Desea eliminar al farmacéuta con ID: " + id + "?",
                        "Confirmar borrado",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (opc != JOptionPane.YES_OPTION) return;

                try {
                    controlador.eliminarFarmaceuta(id);
                    eliminarFarmaceutaDeTablaPorId(id);
                    limpiarCamposFarmaceuta();

                    JOptionPane.showMessageDialog(MenuVista.this, "Farmacéuta eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // LIMPIAR FARMACEUTA

        limpiarFarm.addActionListener(e -> {
            if (tablaFarma != null) tablaFarma.clearSelection();
            limpiarCamposFarmaceuta();
            if (tfBusquedaFarmaceutas != null) tfBusquedaFarmaceutas.setText("");
        });


        // GUARDAR PACIENTE
        if (guardarPaciente != null) {
            guardarPaciente.addActionListener(e -> {
                if (!validarCamposPacienteParaGuardar()) return;

                int id, tel;
                try {
                    id  = Integer.parseInt(tfIdPaciente.getText().trim());
                    tel = Integer.parseInt(tfTelefonoPaciente.getText().trim());
                } catch (NumberFormatException exN) {
                    JOptionPane.showMessageDialog(MenuVista.this, "ID y Teléfono deben ser numéricos.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                java.time.LocalDate fecha = leerFechaNacDelForm();
                if (fecha == null) return;
                String nom = tfNombrePaciente.getText().trim();
                try {

                    Paciente agregado = controlador.agregarPaciente(id,nom,tel,fecha);

                    agregarPacienteATabla(agregado);
                    limpiarCamposPaciente();

                    JOptionPane.showMessageDialog(MenuVista.this, "Paciente guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // MODIFICAR PACIENTE

        if (modificarPaciente != null) {
            modificarPaciente.addActionListener(e -> {
                if (!validarIdPacientePresente()) return;

                int id,telSt;
                try {
                    id = Integer.parseInt(tfIdPaciente.getText().trim());
                    telSt = Integer.parseInt(tfTelefonoPaciente.getText().trim());
                } catch (NumberFormatException exN) {
                    JOptionPane.showMessageDialog(MenuVista.this, "El ID y Teléfono del paciente debe ser numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {

                    String nom   = (tfNombrePaciente   != null) ? tfNombrePaciente.getText().trim()   : "";
                    String fecSt = (tfFechaNacPaciente != null) ? tfFechaNacPaciente.getText().trim() : "";
                    LocalDate fecha = leerFechaNacDelForm();

                    Paciente actualizado = controlador.actualizarPaciente(id,nom,telSt,fecha);
                    actualizarPacienteEnTabla(actualizado);
                    limpiarCamposPaciente();

                    JOptionPane.showMessageDialog(MenuVista.this, "Paciente modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // BORRAR PACIENTE
        if (borrarPaciente != null) {
            borrarPaciente.addActionListener(e -> {

                String idStr = (tfIdPaciente != null) ? tfIdPaciente.getText().trim() : "";
                if (idStr.isEmpty() && tablaPac != null && tablaPac.getSelectedRow() >= 0) {
                    Object v = tablaPac.getValueAt(tablaPac.getSelectedRow(), 0);
                    idStr = (v != null) ? v.toString() : "";
                }
                int id;
                try { id = Integer.parseInt(idStr); }
                catch (NumberFormatException exN) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Ingrese/seleccione un ID numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int opc = JOptionPane.showConfirmDialog(
                        MenuVista.this,
                        "¿Desea eliminar al paciente con ID: " + id + "?",
                        "Confirmar borrado",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (opc != JOptionPane.YES_OPTION) return;

                try {
                    controlador.eliminarPaciente(id);
                    eliminarPacienteDeTablaPorId(id);
                    limpiarCamposPaciente();

                    JOptionPane.showMessageDialog(MenuVista.this, "Paciente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // LIMPIAR PACIENTE

        limpiarPaciente.addActionListener(e -> {
            if (tablaPac != null) tablaPac.clearSelection();
            limpiarCamposPaciente();
            if (tfBusquedaPaciente != null) tfBusquedaPaciente.setText("");
        });

        // GUARDAR MEDICAMENTO

        if (guardarMedicamento != null) {
            guardarMedicamento.addActionListener(e -> {

                int codigo;
                try { codigo = Integer.parseInt(tfCodigoMed.getText().trim()); }
                catch (NumberFormatException exN) {
                    JOptionPane.showMessageDialog(MenuVista.this, "El código debe ser numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String nom = tfNombreMed.getText().trim();
                String des = tfDescMed.getText().trim();

                try {
                    Medicamento agregado = controlador.agregarMedicamento(codigo,nom,des);

                    agregarMedicamentoATabla(agregado);
                    limpiarCamposMedicamento();

                    JOptionPane.showMessageDialog(MenuVista.this, "Medicamento guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // MODIFICAR MEDICAMENTO
        if (modificarMedicamento != null) {
            modificarMedicamento.addActionListener(e -> {
                if (!validarCodigoMedicamentoPresente()) return;

                int codigo;
                try { codigo = Integer.parseInt(tfCodigoMed.getText().trim()); }
                catch (NumberFormatException exN) {
                    JOptionPane.showMessageDialog(MenuVista.this, "El código debe ser numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {

                    String nom = (tfNombreMed != null) ? tfNombreMed.getText().trim() : "";
                    String des = (tfDescMed   != null) ? tfDescMed.getText().trim()   : "";

                    Medicamento actualizado = controlador.actualizarMedicamento(codigo,nom,des);
                    actualizarMedicamentoEnTabla(actualizado);
                    limpiarCamposMedicamento();

                    JOptionPane.showMessageDialog(MenuVista.this, "Medicamento modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        // BORRAR MEDICAMENTO

        if (borrarMedicamento != null) {
            borrarMedicamento.addActionListener(e -> {
                if (controlador == null) {
                    JOptionPane.showMessageDialog(MenuVista.this, "No hay controlador inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String codStr = (tfCodigoMed != null) ? tfCodigoMed.getText().trim() : "";
                if (codStr.isEmpty() && tablaMed != null && tablaMed.getSelectedRow() >= 0) {
                    Object v = tablaMed.getValueAt(tablaMed.getSelectedRow(), 0);
                    codStr = (v != null) ? v.toString() : "";
                }
                int codigo;
                try { codigo = Integer.parseInt(codStr); }
                catch (NumberFormatException exN) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Ingrese/seleccione un código numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int opc = JOptionPane.showConfirmDialog(
                        MenuVista.this,
                        "¿Desea eliminar el medicamento con código: " + codigo + "?",
                        "Confirmar borrado",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (opc != JOptionPane.YES_OPTION) return;

                try {
                    if (controlador.buscarMedicamentoPorCodigo(codigo) == null) {
                        JOptionPane.showMessageDialog(MenuVista.this, "No existe un medicamento con código: " + codigo, "No encontrado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    controlador.eliminarMedicamento(codigo);
                    eliminarMedicamentoDeTablaPorCodigo(codigo);
                    limpiarCamposMedicamento();

                    JOptionPane.showMessageDialog(MenuVista.this, "Medicamento eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                finalizarVentana(evt);
            }
        });


        // LIMPIAR MEDICAMENTO

        limpiarMedicamento.addActionListener(e -> {
            if (tablaMed != null) tablaMed.clearSelection();
            limpiarCamposMedicamento();
            if (tfBusquedaMedicamento != null) tfBusquedaMedicamento.setText("");
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                finalizarVentana(evt);
            }
        });

        // DESPACHO
        if (iniciarProcesoButton != null) {
            iniciarProcesoButton.addActionListener(e -> {
                int fila = tablaDespacho.getSelectedRow();
                if (fila < 0) {
                    JOptionPane.showMessageDialog(this, "Seleccione una receta en la tabla de despacho.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int modelRow = tablaDespacho.convertRowIndexToModel(fila);
                String codigo = String.valueOf(tablaDespacho.getModel().getValueAt(modelRow, 0));
                try{
                    controlador.iniciarProceso(codigo);
                }catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al iniciar proceso: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }catch (SecurityException ex){
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al iniciar proceso: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (marcarListaButton != null) {
            marcarListaButton.addActionListener(e -> {
                int fila = tablaDespacho.getSelectedRow();
                if (fila < 0) {
                    JOptionPane.showMessageDialog(this, "Seleccione una receta en la tabla de despacho.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int modelRow = tablaDespacho.convertRowIndexToModel(fila);
                String codigo = String.valueOf(tablaDespacho.getModel().getValueAt(modelRow, 0));
                try{
                    controlador.marcarLista(codigo);
                }catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al iniciar proceso: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }catch (SecurityException ex){
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al iniciar proceso: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (entregarButton != null) {
            entregarButton.addActionListener(e -> {
                int fila = tablaDespacho.getSelectedRow();
                if (fila < 0) {
                    JOptionPane.showMessageDialog(this, "Seleccione una receta en la tabla de despacho.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int modelRow = tablaDespacho.convertRowIndexToModel(fila);
                String codigo = String.valueOf(tablaDespacho.getModel().getValueAt(modelRow, 0));
                try{
                    controlador.entregar(codigo);
                }catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al iniciar proceso: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }catch (SecurityException ex){
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al iniciar proceso: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (limpiarHistoricoBtn != null) {
            limpiarHistoricoBtn.addActionListener(e -> {
                if (tfHistorico != null) tfHistorico.setText("");
                if (sorterHistorico != null) sorterHistorico.setRowFilter(null);
                if (tablaHistorico != null) tablaHistorico.clearSelection();
            });
        }

        if (exportarHistoricoBtn != null) {
            exportarHistoricoBtn.addActionListener(e -> {
                try {
                    controlador.exportarHistorico(controlador.obtenerListaRecetas());
                    JOptionPane.showMessageDialog(MenuVista.this,
                            "Histórico exportado correctamente a PDF.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this,
                            "Error al exportar histórico: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (refrescarButtonDashboard != null) {
            refrescarButtonDashboard.addActionListener(e -> {
                try {
                    refrescarDashboard();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this,
                            "Error al refrescar Dashboard: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (limpiarButtonDashboard != null) {
            limpiarButtonDashboard.addActionListener(e -> limpiarDashboardUI());
        }

        tablaHistorico.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int fila = tablaHistorico.getSelectedRow();
                    if (fila >= 0) {
                        String codigoReceta = tablaHistorico.getValueAt(fila, 0).toString();

                        List<Indicacion> indicaciones = controlador.mostrarIndicaciones(codigoReceta);

                        DialogDetalleReceta dialog = new DialogDetalleReceta();
                        dialog.setIndicaciones(indicaciones);
                        dialog.setVisible(true);
                    }
                }
            }
        });

    }

    private void finalizarVentana(java.awt.event.WindowEvent evt) {
        cerrarAplicacion();
    }
    public void cerrarAplicacion() {
        controlador.cerrarAplicacion();
    }

    private void aplicarEstilosGenerales() {
        final Color PRIMARY = new Color(66, 133, 244);
        final Color SECOND = new Color(204, 228, 255);

        JPanel[] paneles = {panelPrincipal, panelContenedor, controlPrescripcionPanel, RecetaMedicaPrescripcionPanel};
        for (JPanel p : paneles) if (p != null) p.setBackground(Color.WHITE);
        if (tabbedPanePrincipal != null) {
            tabbedPanePrincipal.setFont(new Font("Segoe UI", Font.BOLD, 13));
            tabbedPanePrincipal.setBackground(Color.WHITE);
        }

        JComponent[] camposTexto = {
                tfBusquedaMedicon, tfBusquedaFarmaceutas, tfBusquedaMedicamento, tfBusquedaPaciente, textField5, textField6,
                textField7, tfHistorico, textField9, textField10, textField11, textField12,
                tfTelefonoPaciente, formattedTextFieldDesdeDash, formattedTextFieldHastaDash, formattedTextField3, tfFechaNacPaciente,
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

// ---------------- BOTONES CON ESTILO + ICONOS ----------------

        aplicarIconoYEstilo(guardarButton, FontAwesomeSolid.SAVE, true);
        aplicarIconoYEstilo(guardarButton2, FontAwesomeSolid.SAVE, true);
        aplicarIconoYEstilo(guardarFarm, FontAwesomeSolid.SAVE, true);
        aplicarIconoYEstilo(guardarPaciente, FontAwesomeSolid.SAVE, true);
        aplicarIconoYEstilo(guardarMedicamento, FontAwesomeSolid.SAVE, true);

        aplicarIconoYEstilo(generarReporteMedicosButton, FontAwesomeSolid.FILE_PDF, true);
        aplicarIconoYEstilo(generarFarma, FontAwesomeSolid.FILE_PDF, true);
        aplicarIconoYEstilo(generarPaciente, FontAwesomeSolid.FILE_PDF, true);
        aplicarIconoYEstilo(generarMedicamento, FontAwesomeSolid.FILE_PDF, true);
        aplicarIconoYEstilo(exportarHistoricoBtn, FontAwesomeSolid.FILE_EXPORT, true);

        aplicarIconoYEstilo(iniciarProcesoButton, FontAwesomeSolid.CHECK, true);
        aplicarIconoYEstilo(entregarButton, FontAwesomeSolid.CHECK, true);
        aplicarIconoYEstilo(marcarListaButton, FontAwesomeSolid.CHECK, true);
        aplicarIconoYEstilo(aplicarFiltrosButton, FontAwesomeSolid.FILTER, true);

        aplicarIconoYEstilo(buscarPacienteButton, FontAwesomeSolid.SEARCH, false);
        aplicarIconoYEstilo(agregarMedicamentoButton, FontAwesomeSolid.PLUS, false);
        aplicarIconoYEstilo(buscarRecetaButton, FontAwesomeSolid.SEARCH, false);

        aplicarIconoYEstilo(buscarButton, FontAwesomeSolid.SEARCH, false);
        aplicarIconoYEstilo(buscarButton1, FontAwesomeSolid.SEARCH, false);
        aplicarIconoYEstilo(buscarButton2, FontAwesomeSolid.SEARCH, false);
        aplicarIconoYEstilo(buscarFarma, FontAwesomeSolid.SEARCH, false);
        aplicarIconoYEstilo(buscarPaciente, FontAwesomeSolid.SEARCH, false);
        aplicarIconoYEstilo(buscarMedicamento, FontAwesomeSolid.SEARCH, false);

        aplicarIconoYEstilo(limpiarprescBtn, FontAwesomeSolid.BROOM, false);
        aplicarIconoYEstilo(limpiarButtonDashboard, FontAwesomeSolid.BROOM, false);
        aplicarIconoYEstilo(limpiarHistoricoBtn, FontAwesomeSolid.BROOM, false);
        aplicarIconoYEstilo(limpiarButton3, FontAwesomeSolid.BROOM, false);
        aplicarIconoYEstilo(limpiarFarm, FontAwesomeSolid.BROOM, false);
        aplicarIconoYEstilo(limpiarPaciente, FontAwesomeSolid.BROOM, false);
        aplicarIconoYEstilo(limpiarMedicamento, FontAwesomeSolid.BROOM, false);

        aplicarIconoYEstilo(modificarButton, FontAwesomeSolid.EDIT, false);
        aplicarIconoYEstilo(modificarFarm, FontAwesomeSolid.EDIT, false);
        aplicarIconoYEstilo(modificarPaciente, FontAwesomeSolid.EDIT, false);
        aplicarIconoYEstilo(modificarMedicamento, FontAwesomeSolid.EDIT, false);

        aplicarIconoYEstilo(borrarButton, FontAwesomeSolid.TRASH, false);
        aplicarIconoYEstilo(borrarFarm, FontAwesomeSolid.TRASH, false);
        aplicarIconoYEstilo(borrarPaciente, FontAwesomeSolid.TRASH, false);
        aplicarIconoYEstilo(borrarMedicamento, FontAwesomeSolid.TRASH, false);

        aplicarIconoYEstilo(descartarMedicamentoPresc, FontAwesomeSolid.TIMES, false);
        aplicarIconoYEstilo(detallesButton, FontAwesomeSolid.EYE, false);
        aplicarIconoYEstilo(detallesButton1, FontAwesomeSolid.EYE, false);
        aplicarIconoYEstilo(verDetallesButton, FontAwesomeSolid.EYE, false);
        aplicarIconoYEstilo(refrescarButtonDashboard, FontAwesomeSolid.SYNC, false);
        JTable[] todasLasTablas = {tablaPrescripcion, tablaMesAnioDashboard, tablaHistorico, tabloMedicos, tablaDespacho, tablaFarma, tablaPac, tablaMed, tablaEstadosDashboard};
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
    // --------------------------------- CONFIGURACIÓN DE XML -----------------------------------

    private void cargarDatosIniciales() {
        cargarMedicosEnTabla();
        cargarFarmaceutasEnTabla();
        cargarPacientesEnTabla();
        cargarMedicamentosEnTabla();
    }

    private void cargarMedicosEnTabla() {
        if (tabloMedicos == null || controlador == null) return;
        DefaultTableModel model = (DefaultTableModel) tabloMedicos.getModel();
        model.setRowCount(0);
        for (Medico m : controlador.obtenerListaMedicos()) {
            model.addRow(new Object[]{ m.getId(), m.getNombre(), m.getEspecialidad() });
        }
    }

    private void cargarFarmaceutasEnTabla() {
        if (tablaFarma == null || controlador == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaFarma.getModel();
        model.setRowCount(0);
        for (Modelo.entidades.Farmaceuta f : controlador.obtenerListaFarmaceutas()) {
            model.addRow(new Object[]{ f.getId(), f.getNombre() });
        }
    }

    private void cargarPacientesEnTabla() {
        if (tablaPac == null || controlador == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaPac.getModel();
        model.setRowCount(0);
        for (Modelo.entidades.Paciente p : controlador.obtenerListaPacientes()) {
            String fechaStr = (p.getFecha_nacimiento() != null)
                    ? p.getFecha_nacimiento().format(formatoFecha)
                    : "";
            model.addRow(new Object[]{ p.getId(), p.getNombre(), fechaStr, p.getTelefono() });
        }
    }

    private void cargarMedicamentosEnTabla() {
        if (tablaMed == null || controlador == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaMed.getModel();
        model.setRowCount(0);
        for (Modelo.entidades.Medicamento m : controlador.obtenerListaMedicamentos()) {
            model.addRow(new Object[]{ m.getCodigo(), m.getNombre(), m.getDescripcion() });
        }
    }

    private void cargarHistoricoEnTabla() {
        if (tablaHistorico == null || controlador == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaHistorico.getModel();
        model.setRowCount(0);

        java.util.List<Receta> recetas = controlador.obtenerListaRecetas();
        if (recetas == null) return;

        for (Receta r : recetas) {
            if (r == null) continue;
            String nombrePaciente = (r.getPaciente() != null) ? r.getPaciente().getNombre() : "";
            String medicamentos   = r.obtenerListaIndicaciones().stream()
                    .map(in -> in.getMedicamento() != null ? in.getMedicamento().getNombre() : "")
                    .filter(s -> !s.isEmpty())
                    .reduce((a,b) -> a + ", " + b).orElse("");

            model.addRow(new Object[]{
                    r.getCodigo(),
                    (r.getPaciente() != null ? r.getPaciente().getId() : ""),
                    nombrePaciente,
                    medicamentos,
                    (r.getFecha_confeccion() != null ? r.getFecha_confeccion().format(formatoFecha) : ""),
                    (r.getFecha_retiro() != null ? r.getFecha_retiro().format(formatoFecha) : ""),
                    r.getEstado()
            });
        }
    }

    // --------------------------------- CONFIGURACIÓN DE TABLAS --------------------------------

    private void configurarTablaMedicos() {
        if (tabloMedicos == null) return;

        String[] columnasMedicos = {"ID", "Nombre", "Especialidad"};
        DefaultTableModel modeloMedicos = new DefaultTableModel(columnasMedicos, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; } // <- no editable
        };
        tabloMedicos.setModel(modeloMedicos);

        tabloMedicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabloMedicos.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tabloMedicos.getSelectedRow();
            if (row >= 0) {
                Object vId  = tabloMedicos.getValueAt(row, 0);
                Object vNom = tabloMedicos.getValueAt(row, 1);
                Object vEsp = tabloMedicos.getValueAt(row, 2);

                if (tfIdMedico      != null) tfIdMedico.setText(vId  != null ? vId.toString()  : "");
                if (tfNombreMedico  != null) tfNombreMedico.setText(vNom != null ? vNom.toString() : "");
                if (tfEspMedico     != null) tfEspMedico.setText(vEsp != null ? vEsp.toString() : "");
            }
        });
        sorterMedicos = new TableRowSorter<>((DefaultTableModel) tabloMedicos.getModel());
        tabloMedicos.setRowSorter(sorterMedicos);
        conectarFiltroConCombo(
                tfBusquedaMedicon,
                comboMedicos,
                sorterMedicos,
                1,
                java.util.Map.of(
                        "nombre", 1,
                        "id", 0,
                        "especialidad", 2
                )
        );
    }

    private void configurarTablaFarmaceutas() {
        if (tablaFarma == null) return;

        String[] columnasFarma = {"ID", "Nombre"};
        DefaultTableModel modeloFarma = new DefaultTableModel(columnasFarma, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaFarma.setModel(modeloFarma);
        tablaFarma.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaFarma.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tablaFarma.getSelectedRow();
            if (row >= 0) {
                Object vId  = tablaFarma.getValueAt(row, 0);
                Object vNom = tablaFarma.getValueAt(row, 1);
                if (tfIdFarma      != null) tfIdFarma.setText(vId  != null ? vId.toString()  : "");
                if (tfNombreFarma  != null) tfNombreFarma.setText(vNom != null ? vNom.toString() : "");
            }
        });
        sorterFarma = new TableRowSorter<>((DefaultTableModel) tablaFarma.getModel());
        tablaFarma.setRowSorter(sorterFarma);
        conectarFiltroConCombo(
                tfBusquedaFarmaceutas,
                comboFarmaceutas,
                sorterFarma,
                1,
                java.util.Map.of(
                        "nombre", 1,
                        "id", 0
                )
        );
    }

    private void configurarTablaPacientes() {
        if (tablaPac == null) return;

        String[] columnasPac = {"ID Paciente", "Nombre", "Fecha de Nacimiento", "Teléfono"};
        DefaultTableModel modeloPac = new DefaultTableModel(columnasPac, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaPac.setModel(modeloPac);
        tablaPac.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaPac.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tablaPac.getSelectedRow();
            if (row >= 0) {
                Object vId   = tablaPac.getValueAt(row, 0);
                Object vNom  = tablaPac.getValueAt(row, 1);
                Object vFec  = tablaPac.getValueAt(row, 2);
                Object vTel  = tablaPac.getValueAt(row, 3);

                if (tfIdPaciente       != null) tfIdPaciente.setText(vId  != null ? vId.toString()  : "");
                if (tfNombrePaciente   != null) tfNombrePaciente.setText(vNom != null ? vNom.toString() : "");
                if (tfFechaNacPaciente != null) tfFechaNacPaciente.setText(vFec != null ? vFec.toString() : "");
                if (tfTelefonoPaciente != null) tfTelefonoPaciente.setText(vTel != null ? vTel.toString() : "");
            }
        });
        sorterPac = new TableRowSorter<>((DefaultTableModel) tablaPac.getModel());
        tablaPac.setRowSorter(sorterPac);
        conectarFiltroConCombo(
                tfBusquedaPaciente,
                comboPacientes,
                sorterPac,
                1,
                java.util.Map.of(
                        "nombre", 1,
                        "id", 0,
                        "fecha de nacimiento", 2,
                        "teléfono", 3
                )
        );
    }

    private void configurarTablaMedicamentos() {
        if (tablaMed == null) return;

        String[] columnasMed = {"Código", "Nombre", "Presentación"};
        DefaultTableModel modeloMed = new DefaultTableModel(columnasMed, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaMed.setModel(modeloMed);
        tablaMed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaMed.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tablaMed.getSelectedRow();
            if (row >= 0) {
                Object vCod = tablaMed.getValueAt(row, 0);
                Object vNom = tablaMed.getValueAt(row, 1);
                Object vDes = tablaMed.getValueAt(row, 2);

                if (tfCodigoMed  != null) tfCodigoMed.setText(vCod != null ? vCod.toString() : "");
                if (tfNombreMed  != null) tfNombreMed.setText(vNom != null ? vNom.toString() : "");
                if (tfDescMed    != null) tfDescMed.setText(vDes != null ? vDes.toString() : "");
            }
        });
        sorterMed = new TableRowSorter<>((DefaultTableModel) tablaMed.getModel());
        tablaMed.setRowSorter(sorterMed);
        conectarFiltroConCombo(
                tfBusquedaMedicamento,
                comboMedicamentos,
                sorterMed,
                1,
                java.util.Map.of(
                        "nombre", 1,
                        "código", 0, "codigo", 0,
                        "presentación", 2, "presentacion", 2,
                        "descripción", 2, "descripcion", 2
                )
        );

    }

    private void configurarTablaRecetas() {
        String[] columnasRecetas = {"Código", "Medicamento", "Presentación", "Cantidad", "Indicaciones", "Duración en días"};
        modeloTablaRecetas = new DefaultTableModel(columnasRecetas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        if (tablaPrescripcion != null) {
            tablaPrescripcion.setModel(modeloTablaRecetas);
            tablaPrescripcion.getColumnModel().getColumn(0).setMinWidth(0);
            tablaPrescripcion.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaPrescripcion.getColumnModel().getColumn(0).setWidth(0);

            tablaPrescripcion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            tablaPrescripcion.getSelectionModel().addListSelectionListener(e -> {
                if (e.getValueIsAdjusting()) return;
                int viewRow = tablaPrescripcion.getSelectedRow();
                if (viewRow < 0) return;
                int modelRow = tablaPrescripcion.convertRowIndexToModel(viewRow);
                mostrarFilaEnCampos(modelRow);
            });

            tablaPrescripcion.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (e.getClickCount() == 2) editarFilaSeleccionadaEnDialog();
                }
            });
            tablaPrescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                        e.consume();
                        editarFilaSeleccionadaEnDialog();
                    }
                }
            });
        }

        if (tablaDespacho != null) {
            String[] columnasDespacho = {"Código de la receta","ID Paciente", "Fecha Actual", "Fecha de Retiro", "Estado"};
            DefaultTableModel modeloDespacho = new DefaultTableModel(columnasDespacho, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };
            tablaDespacho.setModel(modeloDespacho);
            sorterDesp = new TableRowSorter<>((DefaultTableModel) tablaDespacho.getModel());
            tablaDespacho.setRowSorter(sorterDesp);
        }

        DefaultTableModel md = (DefaultTableModel) tablaDespacho.getModel();
        for (Receta r : controlador.obtenerListaRecetas()) {
            if (r == null) continue;
            md.addRow(new Object[]{
                    r.getCodigo(),
                    (r.getPaciente() != null ? r.getPaciente().getId() : ""),
                    (r.getFecha_confeccion() != null ? r.getFecha_confeccion().format(formatoFecha) : ""),
                    (r.getFecha_retiro() != null ? r.getFecha_retiro().format(formatoFecha) : ""),
                    r.getEstado()
            });
        }
        conectarFiltroConCombo(
                tfRecetasDespacho,
                comboRecetaDespacho,
                sorterDesp,
                0,
                java.util.Map.of("código", 0, "codigo", 0)
        );
    }

    private TableRowSorter<DefaultTableModel> sorterHistorico;

    private void configurarTablaHistorico() {
        if (tablaHistorico != null) {
            String[] columnasHistorico = {"Código Receta", "ID Paciente", "Nombre Paciente", "Medicamentos", "Fecha Confección", "Fecha de Retiro", "Estado"};
            DefaultTableModel modeloHistorico = new DefaultTableModel(columnasHistorico, 0) {
                @Override public boolean isCellEditable(int row, int column) { return false; }
            };
            tablaHistorico.setModel(modeloHistorico);

            sorterHistorico = new TableRowSorter<>(modeloHistorico);
            tablaHistorico.setRowSorter(sorterHistorico);

            conectarFiltroConCombo(
                    tfHistorico,
                    comboCodigoHist,
                    sorterHistorico,
                    0,
                    java.util.Map.of("código", 0, "codigo", 0)
            );
        }
    }

    private void configurarTablaEstados() {
        if (tablaEstadosDashboard != null) {
            String[] columnasEstados = {"Estado", "Cantidad de Recetas"};
            DefaultTableModel modeloEstados = new DefaultTableModel(columnasEstados, 0) {
                @Override public boolean isCellEditable(int row, int column) { return false; }
            };
            tablaEstadosDashboard.setModel(modeloEstados);
        }
    }

    private void configurarTablaDashboard() {
        if (tablaMesAnioDashboard != null) {
            String[] columnasDashboard = { "Mes/Año", "Cantidad de Recetas" };
            DefaultTableModel modeloDashboard = new DefaultTableModel(columnasDashboard, 0) {
                @Override public boolean isCellEditable(int row, int column) { return false; }
            };
            tablaMesAnioDashboard.setModel(modeloDashboard);
        }
    }
    // ---------------------------------- FILTROS DE BÚSQUEDA -----------------------------------

    private void conectarFiltroConCombo(JTextField campo,
                                        JComboBox<?> combo,
                                        TableRowSorter<DefaultTableModel> sorter,
                                        int defaultCol,
                                        java.util.Map<String, Integer> etiquetaACol) {
        if (campo == null || sorter == null) return;

        Runnable aplicar = () -> {
            String t = (campo.getText() == null) ? "" : campo.getText().trim();
            if (t.isEmpty()) { sorter.setRowFilter(null); return; }

            int col = defaultCol;
            if (combo != null && combo.getSelectedItem() != null) {
                String key = combo.getSelectedItem().toString().trim().toLowerCase();
                Integer m = etiquetaACol.get(key);
                if (m != null) col = m;
            }

            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(t), col));
        };

        campo.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { aplicar.run(); }
            @Override public void removeUpdate(DocumentEvent e) { aplicar.run(); }
            @Override public void changedUpdate(DocumentEvent e) { aplicar.run(); }
        });

        if (combo != null) combo.addActionListener(e -> aplicar.run());
    }

    // --------------------------- HELPERS: FORMULARIO MÉDICOS ----------------------------------

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

    private void actualizarMedicoEnTabla(Medico m) {
        if (tabloMedicos == null || m == null) return;
        DefaultTableModel model = (DefaultTableModel) tabloMedicos.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object idCell = model.getValueAt(i, 0);
            String idTabla = idCell != null ? idCell.toString() : "";
            if (idTabla.equals(m.getId())) {
                model.setValueAt(m.getNombre(), i, 1);
                model.setValueAt(m.getEspecialidad(), i, 2);
                break;
            }
        }
    }

    private String obtenerIdDesdeFormularioOSel() {
        String id = (tfIdMedico != null) ? tfIdMedico.getText().trim() : "";
        if ((id == null || id.isEmpty()) && tabloMedicos != null && tabloMedicos.getSelectedRow() >= 0) {
            int sel = tabloMedicos.getSelectedRow();
            Object val = tabloMedicos.getValueAt(sel, 0); // col 0 = ID
            id = (val != null) ? val.toString() : "";
        }
        return (id != null) ? id : "";
    }

    private void eliminarMedicoDeTablaPorId(String id) {
        if (tabloMedicos == null || id == null) return;
        DefaultTableModel model = (DefaultTableModel) tabloMedicos.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object idCell = model.getValueAt(i, 0);
            String idTabla = (idCell != null) ? idCell.toString() : "";
            if (id.equals(idTabla)) {
                model.removeRow(i);
                break;
            }
        }
    }

    // ------------------------- HELPERS: FORMULARIO FARMACEUTAS --------------------------------

    private void limpiarCamposFarmaceuta() {
        if (tfIdFarma != null) tfIdFarma.setText("");
        if (tfNombreFarma != null) tfNombreFarma.setText("");
        if (tfIdFarma != null) tfIdFarma.requestFocus();
    }

    private boolean validarCamposFarmaceuta() {
        String id  = tfIdFarma != null ? tfIdFarma.getText().trim() : "";
        String nom = tfNombreFarma != null ? tfNombreFarma.getText().trim() : "";
        if (id.isEmpty() || nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete ID y Nombre del Farmacéuta.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void agregarFarmaceutaATabla(Modelo.entidades.Farmaceuta f) {
        if (tablaFarma == null || f == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaFarma.getModel();
        model.addRow(new Object[]{ f.getId(), f.getNombre() });
    }

    private void actualizarFarmaceutaEnTabla(Modelo.entidades.Farmaceuta f) {
        if (tablaFarma == null || f == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaFarma.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String idTabla = String.valueOf(model.getValueAt(i, 0));
            if (idTabla.equals(f.getId())) {
                model.setValueAt(f.getNombre(), i, 1);
                break;
            }
        }
    }

    private void eliminarFarmaceutaDeTablaPorId(String id) {
        if (tablaFarma == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaFarma.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String idTabla = String.valueOf(model.getValueAt(i, 0));
            if (idTabla.equals(id)) {
                model.removeRow(i);
                break;
            }
        }
    }

    // -------------------------- HELPERS: FORMULARIO PACIENTES ---------------------------------

    private void limpiarCamposPaciente() {
        if (tfIdPaciente != null) tfIdPaciente.setText("");
        if (tfNombrePaciente != null) tfNombrePaciente.setText("");
        if (tfFechaNacPaciente != null) tfFechaNacPaciente.setText("");
        if (tfTelefonoPaciente != null) tfTelefonoPaciente.setText("");
        if (tfIdPaciente != null) tfIdPaciente.requestFocus();
    }

    private boolean validarCamposPacienteParaGuardar() {
        String id  = tfIdPaciente != null ? tfIdPaciente.getText().trim() : "";
        String nom = tfNombrePaciente != null ? tfNombrePaciente.getText().trim() : "";
        String fec = tfFechaNacPaciente != null ? tfFechaNacPaciente.getText().trim() : "";
        String tel = tfTelefonoPaciente != null ? tfTelefonoPaciente.getText().trim() : "";
        if (id.isEmpty() || nom.isEmpty() || fec.isEmpty() || tel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete ID, Nombre, Fecha de Nacimiento y Teléfono.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try { Integer.parseInt(id); } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID del paciente debe ser numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validarIdPacientePresente() {
        String id  = tfIdPaciente != null ? tfIdPaciente.getText().trim() : "";
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ID del paciente o seleccione una fila.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try { Integer.parseInt(id); } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID del paciente debe ser numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void agregarPacienteATabla(Modelo.entidades.Paciente p) {
        if (tablaPac == null || p == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaPac.getModel();
        String fechaStr = p.getFecha_nacimiento() != null ? p.getFecha_nacimiento().format(formatoFecha) : "";
        model.addRow(new Object[]{ p.getId(), p.getNombre(), fechaStr, p.getTelefono() });
    }

    private void actualizarPacienteEnTabla(Modelo.entidades.Paciente p) {
        if (tablaPac == null || p == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaPac.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String idTabla = String.valueOf(model.getValueAt(i, 0));
            if (idTabla.equals(String.valueOf(p.getId()))) {
                model.setValueAt(p.getNombre(), i, 1);
                model.setValueAt(p.getFecha_nacimiento() != null ? p.getFecha_nacimiento().format(formatoFecha) : "", i, 2);
                model.setValueAt(p.getTelefono(), i, 3);
                break;
            }
        }
    }

    private void eliminarPacienteDeTablaPorId(int id) {
        if (tablaPac == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaPac.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String idTabla = String.valueOf(model.getValueAt(i, 0));
            if (idTabla.equals(String.valueOf(id))) {
                model.removeRow(i);
                break;
            }
        }
    }

    private void seleccionarFechaPara(JTextField destino) {
        LocalDate inicial = null;
        if (destino != null) {
            String txt = destino.getText() != null ? destino.getText().trim() : "";
            if (!txt.isEmpty()) {
                try { inicial = LocalDate.parse(txt, formatoFecha); } catch (Exception ignored) {}
            }
        }

        DialogSeleccionarFecha dialog = new DialogSeleccionarFecha();
        dialog.setFechaInicial(inicial);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        LocalDate seleccionada = dialog.getFechaSeleccionada();
        if (seleccionada != null && destino != null) {
            destino.setText(seleccionada.format(formatoFecha));
            destino.requestFocus();
        } else if (destino != null && destino.getText().trim().isEmpty()) {
            destino.setText("(sin fecha)");
        }
    }

    private void seleccionarFechaParaLabel(JLabel destino) {
        LocalDate inicial = null;
        if (destino != null) {
            String txt = destino.getText() != null ? destino.getText().trim() : "";
            try { inicial = LocalDate.parse(txt, formatoFecha); } catch (Exception ignored) {}
        }

        DialogSeleccionarFecha dialog = new DialogSeleccionarFecha();
        dialog.setFechaInicial(inicial);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        LocalDate seleccionada = dialog.getFechaSeleccionada();
        if (seleccionada != null && destino != null) {
            destino.setText(seleccionada.format(formatoFecha));
            destino.requestFocus();
        }
    }

    private LocalDate leerFechaNacDelForm() {
        String txt = (tfFechaNacPaciente != null) ? tfFechaNacPaciente.getText().trim() : "";
        if (txt.isEmpty()) return null;
        try {
            return LocalDate.parse(txt, formatoFecha); // dd/MM/yyyy
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Fecha inválida. Usa el formato dd/MM/yyyy.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            if (tfFechaNacPaciente != null) tfFechaNacPaciente.requestFocus();
            return null;
        }
    }
    // ------------------------- HELPERS: FORMULARIO MEDICAMENTOS -------------------------------

    private void limpiarCamposMedicamento() {
        if (tfCodigoMed != null) tfCodigoMed.setText("");
        if (tfNombreMed != null) tfNombreMed.setText("");
        if (tfDescMed != null) tfDescMed.setText("");
        if (tfCodigoMed != null) tfCodigoMed.requestFocus();
    }

    private boolean validarCamposMedicamentoParaGuardar() {
        String cod = tfCodigoMed != null ? tfCodigoMed.getText().trim() : "";
        String nom = tfNombreMed != null ? tfNombreMed.getText().trim() : "";
        String des = tfDescMed != null ? tfDescMed.getText().trim() : "";
        if (cod.isEmpty() || nom.isEmpty() || des.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete Código, Nombre y Descripción.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try { Integer.parseInt(cod); } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El código del medicamento debe ser numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validarCodigoMedicamentoPresente() {
        String cod = tfCodigoMed != null ? tfCodigoMed.getText().trim() : "";
        if (cod.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del medicamento o seleccione una fila.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try { Integer.parseInt(cod); } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El código del medicamento debe ser numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void agregarMedicamentoATabla(Modelo.entidades.Medicamento m) {
        if (tablaMed == null || m == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaMed.getModel();
        model.addRow(new Object[]{ m.getCodigo(), m.getNombre(), m.getDescripcion() });
    }

    private void actualizarMedicamentoEnTabla(Modelo.entidades.Medicamento m) {
        if (tablaMed == null || m == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaMed.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String codTabla = String.valueOf(model.getValueAt(i, 0));
            if (codTabla.equals(String.valueOf(m.getCodigo()))) {
                model.setValueAt(m.getNombre(), i, 1);
                model.setValueAt(m.getDescripcion(), i, 2);
                break;
            }
        }
    }

    private void eliminarMedicamentoDeTablaPorCodigo(int codigo) {
        if (tablaMed == null) return;
        DefaultTableModel model = (DefaultTableModel) tablaMed.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String codTabla = String.valueOf(model.getValueAt(i, 0));
            if (codTabla.equals(String.valueOf(codigo))) {
                model.removeRow(i);
                break;
            }
        }
    }

    // ------------------------------------- HELPERS: RECETA-------------------------------------

    private void recargarTablaIndicacionesDesdeControlador() {
        if (modeloTablaRecetas == null || controlador == null) return;
        modeloTablaRecetas.setRowCount(0);
        java.util.List<Indicacion> lista = controlador.obtenerListaIndicaciones();
        if (lista == null) return;
        for (Indicacion in : lista) {
            if (in == null || in.getMedicamento() == null) continue;
            Medicamento m = in.getMedicamento();
            String presentacion = (m.getDescripcion() != null) ? m.getDescripcion()
                    : (m.getPresentacion() != null ? m.getPresentacion() : "");
            modeloTablaRecetas.addRow(new Object[]{
                    m.getCodigo(),
                    m.getNombre(),
                    presentacion,
                    in.getCantidad(),
                    in.getDescripcion() != null ? in.getDescripcion() : "",
                    in.getDuracion()
            });
        }
    }
    private void limpiarIndicacionesDelModelo() {
        if (controlador == null) return;
        java.util.List<Indicacion> copia = new java.util.ArrayList<>(controlador.obtenerListaIndicaciones());
        for (Indicacion in : copia) {
            try {
                if (in != null && in.getMedicamento() != null) {
                    controlador.eliminarIndicacion(in.getMedicamento().getCodigo());
                }
            } catch (Exception ignored) {}
        }
    }


    private void abrirDialogMedicamentoYAgregar(Integer filaAActualizar) {
        DialogBuscarMedicamento dialog = new DialogBuscarMedicamento(controlador);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(this);

        if (filaAActualizar != null && filaAActualizar >= 0 && filaAActualizar < modeloTablaRecetas.getRowCount()) {
            Integer cod   = Integer.valueOf(String.valueOf(modeloTablaRecetas.getValueAt(filaAActualizar, 0)));
            String ind    = String.valueOf(modeloTablaRecetas.getValueAt(filaAActualizar, 4));
            Integer cant  = Integer.valueOf(String.valueOf(modeloTablaRecetas.getValueAt(filaAActualizar, 3)));
            Integer dias  = Integer.valueOf(String.valueOf(modeloTablaRecetas.getValueAt(filaAActualizar, 5)));
            dialog.setValoresIniciales(cod, cant, dias, ind);
        }

        dialog.setVisible(true);

        Object codSel = dialog.getCodigoSeleccionado();
        if (codSel == null) return;

        int codigo = Integer.parseInt(String.valueOf(codSel));
        Integer cantidad = dialog.getCantidadElegida();
        Integer duracion = dialog.getDuracionElegida();
        String indicaciones = dialog.getIndicacionesTexto();

        Medicamento med = controlador.buscarMedicamentoPorCodigo(codigo);
        if (med == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el medicamento seleccionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = med.getNombre();
        String presentacion = (med.getDescripcion() != null) ? med.getDescripcion() :
                (med.getPresentacion() != null ? med.getPresentacion() : "");

        Integer codigoOld = null;
        if (filaAActualizar != null && filaAActualizar >= 0 && filaAActualizar < modeloTablaRecetas.getRowCount()) {
            codigoOld = Integer.valueOf(String.valueOf(modeloTablaRecetas.getValueAt(filaAActualizar, 0)));
        }

        try {
            if (filaAActualizar != null && codigoOld != null && !codigoOld.equals(codigo)) {
                try { controlador.eliminarIndicacion(codigoOld); } catch (Exception ignored) {}
            }
            try { controlador.eliminarIndicacion(codigo); } catch (Exception ignored) {}

            controlador.agregarIndicacion(med, cantidad, indicaciones, duracion);

            recargarTablaIndicacionesDesdeControlador();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo registrar la indicación: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }


    private void editarFilaSeleccionadaEnDialog() {
        if (tablaPrescripcion == null || tablaPrescripcion.getSelectedRow() < 0) return;
        int viewRow = tablaPrescripcion.getSelectedRow();
        int modelRow = tablaPrescripcion.convertRowIndexToModel(viewRow);
        abrirDialogMedicamentoYAgregar(modelRow);
    }

    private void guardarRecetaDesdeUI() {
        if (pacienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un paciente antes de guardar la receta.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.util.List<Indicacion> indicaciones = controlador.obtenerListaIndicaciones();
        if (indicaciones == null || indicaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Agregue al menos un medicamento a la receta.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate fechaConf;
        try {
            String f = (labelFechaActualPresc != null) ? labelFechaActualPresc.getText().trim() : "";
            if (f.isEmpty() || f.equalsIgnoreCase("(sin fecha)")) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar una fecha de confección.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            fechaConf = LocalDate.parse(f, formatoFecha);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Fecha de confección inválida. Usa el formato dd/MM/yyyy.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        LocalDate fechaRet = null;
        try {
            String r = (labelFechaRetiroPresc != null) ? labelFechaRetiroPresc.getText().trim() : "";
            if (!r.isEmpty() && !r.equalsIgnoreCase("(sin fecha)")) {
                fechaRet = LocalDate.parse(r, formatoFecha);
                if (fechaRet.isBefore(fechaConf)) {
                    JOptionPane.showMessageDialog(this,
                            "La fecha de retiro no puede ser anterior a la de confección.",
                            "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Fecha de retiro inválida. Usa el formato dd/MM/yyyy.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoIngresado = leerCodigoPrescripcion();
        if (codigoIngresado.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese el código de la receta.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (controlador.buscarRecetaPorCodigo(codigoIngresado) != null) {
            JOptionPane.showMessageDialog(this,
                    "Ya existe una receta con código: " + codigoIngresado,
                    "Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Receta receta = controlador.agregarReceta(
                    codigoIngresado,
                    controlador.obtenerListaIndicaciones(),
                    pacienteSeleccionado.getId(),
                    fechaConf,
                    fechaRet
            );

            limpiarIndicacionesDelModelo();
            recargarTablaIndicacionesDesdeControlador();
            recetaEnPantalla = null;
            escribirCodigoPrescripcion("");

            if (tablaDespacho != null && tablaDespacho.getModel() instanceof DefaultTableModel) {
                DefaultTableModel md = (DefaultTableModel) tablaDespacho.getModel();
                md.addRow(new Object[]{
                        receta.getCodigo(),
                        pacienteSeleccionado.getId(),
                        receta.getFecha_confeccion() != null ? receta.getFecha_confeccion().format(formatoFecha) : "",
                        receta.getFecha_retiro() != null ? receta.getFecha_retiro().format(formatoFecha) : "",
                        receta.getEstado()
                });
            }

            cargarHistoricoEnTabla();

            JOptionPane.showMessageDialog(this,
                    "Receta guardada correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar la receta. Verifique el código y los datos.\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limpiarPrescripcionUI() {
        if (tablaPrescripcion != null) tablaPrescripcion.clearSelection();

        if (modeloTablaRecetas != null) modeloTablaRecetas.setRowCount(0);

        limpiarIndicacionesDelModelo();

        pacienteSeleccionado = null;
        recetaEnPantalla = null;

        if (labelNomPaciente != null) labelNomPaciente.setText("(sin paciente seleccionado)");
        if (labelFechaActualPresc != null) labelFechaActualPresc.setText(LocalDate.now().format(formatoFecha));
        if (labelFechaRetiroPresc != null) labelFechaRetiroPresc.setText("(sin fecha)");
        if (tfCodPresc != null) tfCodPresc.setText("");
    }



    private void descartarIndicacionActual() {
        if (tablaPrescripcion == null || modeloTablaRecetas == null) return;

        int viewRow = tablaPrescripcion.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un medicamento para descartar.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int modelRow = tablaPrescripcion.convertRowIndexToModel(viewRow);

        int ok = JOptionPane.showConfirmDialog(this, "¿Quitar el medicamento seleccionado?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok != JOptionPane.YES_OPTION) return;

        try {
            Object codObj = modeloTablaRecetas.getValueAt(modelRow, 0);
            if (codObj != null) {
                int codMed = Integer.parseInt(String.valueOf(codObj));
                controlador.eliminarIndicacion(codMed);
            }
        } catch (Exception ignored) {}

        recargarTablaIndicacionesDesdeControlador();

    }

    private String leerCodigoPrescripcion() {
        return (tfCodPresc != null && tfCodPresc.getText() != null)
                ? tfCodPresc.getText().trim()
                : "";
    }

    private void escribirCodigoPrescripcion(String codigo) {
        if (tfCodPresc != null) tfCodPresc.setText(codigo != null ? codigo : "");
    }

    private void cargarRecetaInicialEnPrescripcion() {
        if (controlador == null || modeloTablaRecetas == null) return;

        java.util.List<Receta> recetas = controlador.obtenerListaRecetas();
        if (recetas == null || recetas.isEmpty()) return;

        Receta r = recetas.stream()
                .filter(java.util.Objects::nonNull)
                .sorted(java.util.Comparator.comparing(
                        Receta::getFecha_confeccion,
                        java.util.Comparator.nullsLast(java.util.Comparator.naturalOrder())
                ).reversed())
                .findFirst()
                .orElse(null);

        if (r != null) {
            pintarRecetaEnUI(r);
            escribirCodigoPrescripcion("");
        }
    }


    private void pintarRecetaEnUI(Receta r) {
        this.recetaEnPantalla = r;
        if (r == null || modeloTablaRecetas == null) return;

        modeloTablaRecetas.setRowCount(0);
        java.util.List<Indicacion> inds = r.obtenerListaIndicaciones();
        if (inds != null) {
            for (Indicacion in : inds) {
                if (in == null || in.getMedicamento() == null) continue;

                Medicamento m = in.getMedicamento();
                String presentacion = (m.getDescripcion() != null) ? m.getDescripcion() : "";

                modeloTablaRecetas.addRow(new Object[]{
                        m.getCodigo(),
                        m.getNombre(),
                        presentacion,
                        in.getCantidad(),
                        (in.getDescripcion() != null ? in.getDescripcion() : ""),
                        in.getDuracion()
                });
            }
        }

        if (labelFechaActualPresc != null) {
            labelFechaActualPresc.setText(
                    (r.getFecha_confeccion() != null)
                            ? r.getFecha_confeccion().format(formatoFecha)
                            : java.time.LocalDate.now().format(formatoFecha)
            );
        }
        if (labelFechaRetiroPresc != null) {
            labelFechaRetiroPresc.setText(
                    (r.getFecha_retiro() != null)
                            ? r.getFecha_retiro().format(formatoFecha)
                            : "(sin fecha)"
            );
        }

        pacienteSeleccionado = r.getPaciente();
        if (labelNomPaciente != null) {
            if (pacienteSeleccionado != null && pacienteSeleccionado.getNombre() != null) {
                labelNomPaciente.setText(pacienteSeleccionado.getNombre() + " (ID " + pacienteSeleccionado.getId() + ")");
            } else {
                labelNomPaciente.setText("(sin paciente seleccionado)");
            }
        }

        escribirCodigoPrescripcion(r.getCodigo());
    }

    private boolean recetaTieneMedicamento(Receta r, int codigoMed) {
        if (r == null) return false;
        java.util.List<Indicacion> inds = r.obtenerListaIndicaciones();
        if (inds == null) return false;
        for (Indicacion in : inds) {
            Medicamento m = (in != null) ? in.getMedicamento() : null;
            if (m != null && m.getCodigo() == codigoMed) return true;
        }
        return false;
    }

    private void mostrarFilaEnCampos(int modelRow) {
        if (recetaEnPantalla == null) return;

        LocalDateAdapter lda = new LocalDateAdapter();

        if (tfCodPresc != null) {
            String cod = recetaEnPantalla.getCodigo();
            tfCodPresc.setText(cod != null ? cod : "");
        }

        try {
            LocalDate fc = recetaEnPantalla.getFecha_confeccion();
            String fechaConfStr = (fc != null) ? lda.marshal(fc) : lda.marshal(LocalDate.now());
            if (labelFechaActualPresc != null) labelFechaActualPresc.setText(fechaConfStr);
        } catch (Exception ex) {
            if (labelFechaActualPresc != null) labelFechaActualPresc.setText(LocalDate.now().format(formatoFecha));
        }

        try {
            LocalDate fr = recetaEnPantalla.getFecha_retiro();
            String fechaRetStr = (fr != null) ? lda.marshal(fr) : "(sin fecha)";
            if (labelFechaRetiroPresc != null) labelFechaRetiroPresc.setText(fechaRetStr);
        } catch (Exception ex) {
            if (labelFechaRetiroPresc != null) labelFechaRetiroPresc.setText("(sin fecha)");
        }

        pacienteSeleccionado = recetaEnPantalla.getPaciente();
        if (labelNomPaciente != null) {
            if (pacienteSeleccionado != null) {
                labelNomPaciente.setText(
                        pacienteSeleccionado.getNombre() + " (ID " + pacienteSeleccionado.getId() + ")"
                );
            } else {
                labelNomPaciente.setText("(sin paciente seleccionado)");
            }
        }
    }
    // --------------------------------- HELPERS DASHBOARD --------------------------------------

    private void refrescarDashboard() {
        if (controlador == null) return;

        LocalDate desde = null, hasta = null;
        try {
            if (formattedTextFieldDesdeDash != null && !formattedTextFieldDesdeDash.getText().trim().isEmpty()) {
                desde = LocalDate.parse(formattedTextFieldDesdeDash.getText().trim(), formatoFecha);
            }
            if (formattedTextFieldHastaDash != null && !formattedTextFieldHastaDash.getText().trim().isEmpty()) {
                hasta = LocalDate.parse(formattedTextFieldHastaDash.getText().trim(), formatoFecha);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa dd/MM/yyyy", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Si no se dieron fechas, ponemos un rango por defecto: mes actual
        if (desde == null && hasta == null) {
            YearMonth ymNow = YearMonth.now();
            desde = ymNow.atDay(1);
            hasta = ymNow.atEndOfMonth();
        } else if (desde == null && hasta != null) {
            // si solo hay 'hasta', ponemos desde al primer día del mismo mes
            desde = YearMonth.from(hasta).atDay(1);
        } else if (desde != null && hasta == null) {
            // si solo hay 'desde', ponemos hasta al último día del mismo mes
            hasta = YearMonth.from(desde).atEndOfMonth();
        }

        // sanity: si desde > hasta no hacemos nada
        if (desde.isAfter(hasta)) {
            JOptionPane.showMessageDialog(this, "'Desde' no puede ser posterior a 'Hasta'.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String medSeleccionado = null;
        if (comboBox1 != null && comboBox1.getSelectedItem() != null) {
            medSeleccionado = comboBox1.getSelectedItem().toString();
        }

        // ---------------- TABLA MESES ----------------
        Map<YearMonth, Integer> acumuladoMeses = new java.util.HashMap<>();

        if (medSeleccionado != null && medSeleccionado.equals("Todos")) {
            // Si quieres la suma por medicamento (cada serie por medicamento) -> iteramos, pero evitamos duplicados:
            Set<String> medsVistos = new HashSet<>();
            for (int i = 0; i < comboBox1.getItemCount(); i++) {
                String med = comboBox1.getItemAt(i).toString();
                if ("Todos".equals(med) || medsVistos.contains(med)) continue;
                medsVistos.add(med);

                Map<YearMonth, Integer> datos = controlador.DashboardMedicamentosPorMes(desde, hasta, med);
                for (Map.Entry<YearMonth, Integer> entry : datos.entrySet()) {
                    acumuladoMeses.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
            }
        } else if (medSeleccionado != null) {
            acumuladoMeses = controlador.DashboardMedicamentosPorMes(desde, hasta, medSeleccionado);
        }

        if (tablaMesAnioDashboard != null) {
            DefaultTableModel model = (DefaultTableModel) tablaMesAnioDashboard.getModel();
            model.setRowCount(0);

            // Ordenamos las claves por fecha
            List<YearMonth> clavesOrdenadas = new ArrayList<>(acumuladoMeses.keySet());
            clavesOrdenadas.sort(Comparator.naturalOrder());

            DateTimeFormatter formatoMesAnio = DateTimeFormatter.ofPattern("MM/yyyy");

            for (YearMonth ym : clavesOrdenadas) {
                model.addRow(new Object[]{ym.format(formatoMesAnio), acumuladoMeses.getOrDefault(ym, 0)});
            }
        }

        // ---------------- TABLA ESTADOS ----------------
        Map<String, Long> acumuladoEstados = new java.util.HashMap<>();

        if (medSeleccionado != null && medSeleccionado.equals("Todos")) {
            Set<String> medsVistos = new HashSet<>();
            for (int i = 0; i < comboBox1.getItemCount(); i++) {
                String med = comboBox1.getItemAt(i).toString();
                if ("Todos".equals(med) || medsVistos.contains(med)) continue;
                medsVistos.add(med);

                Map<String, Long> datos = controlador.DashboardRecetasPorEstado(desde, hasta, med);
                for (Map.Entry<String, Long> entry : datos.entrySet()) {
                    acumuladoEstados.merge(entry.getKey(), entry.getValue(), Long::sum);
                }
            }
        } else if (medSeleccionado != null) {
            acumuladoEstados = controlador.DashboardRecetasPorEstado(desde, hasta, medSeleccionado);
        }

        if (tablaEstadosDashboard != null) {
            DefaultTableModel model = (DefaultTableModel) tablaEstadosDashboard.getModel();
            model.setRowCount(0);
            for (Map.Entry<String, Long> entry : acumuladoEstados.entrySet()) {
                model.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }
        }

        // ---------------- GRÁFICO DE LÍNEAS ----------------
        if (panelLineasDashboard != null) {
            panelLineasDashboard.removeAll();

            DefaultCategoryDataset datasetLineas = new DefaultCategoryDataset();
            DateTimeFormatter formatoMesAnio = DateTimeFormatter.ofPattern("MM/yyyy");

            if (medSeleccionado != null && medSeleccionado.equals("Todos")) {
                // una serie por cada medicamento
                Set<String> medsVistos = new HashSet<>();
                for (int i = 0; i < comboBox1.getItemCount(); i++) {
                    String med = comboBox1.getItemAt(i).toString();
                    if ("Todos".equals(med) || medsVistos.contains(med)) continue;
                    medsVistos.add(med);

                    Map<YearMonth, Integer> datos = controlador.DashboardMedicamentosPorMes(desde, hasta, med);
                    if (datos == null || datos.isEmpty()) continue;

                    List<YearMonth> clavesOrdenadas = new ArrayList<>(datos.keySet());
                    Collections.sort(clavesOrdenadas);

                    for (YearMonth ym : clavesOrdenadas) {
                        int val = datos.getOrDefault(ym, 0);
                        // aquí usamos el nombre del medicamento como serie (rowKey)
                        datasetLineas.addValue(val, med, ym.format(formatoMesAnio));
                    }
                }
            } else if (medSeleccionado != null) {
                // una sola serie
                Map<YearMonth, Integer> datos = controlador.DashboardMedicamentosPorMes(desde, hasta, medSeleccionado);
                if (datos != null && !datos.isEmpty()) {
                    List<YearMonth> clavesOrdenadas = new ArrayList<>(datos.keySet());
                    Collections.sort(clavesOrdenadas);

                    for (YearMonth ym : clavesOrdenadas) {
                        int val = datos.getOrDefault(ym, 0);
                        // usamos siempre el nombre del medicamento como serie
                        datasetLineas.addValue(val, medSeleccionado, ym.format(formatoMesAnio));
                    }
                }
            }

            // Crear el chart
            final DefaultCategoryDataset finalDataset = datasetLineas;
            SwingUtilities.invokeLater(() -> {
                JFreeChart chartLineas = ChartFactory.createLineChart(
                        "Medicamentos por Mes",
                        "Mes",
                        "Cantidad",
                        finalDataset,
                        PlotOrientation.VERTICAL,
                        true,
                        true,
                        false
                );

                ChartPanel chartPanelLineas = new ChartPanel(chartLineas);
                if (panelLineasDashboard.getWidth() <= 0 || panelLineasDashboard.getHeight() <= 0) {
                    chartPanelLineas.setPreferredSize(new Dimension(700, 300));
                } else {
                    chartPanelLineas.setPreferredSize(new Dimension(panelLineasDashboard.getWidth(), panelLineasDashboard.getHeight()));
                }

                panelLineasDashboard.setLayout(new BorderLayout());
                panelLineasDashboard.removeAll();
                panelLineasDashboard.add(chartPanelLineas, BorderLayout.CENTER);
                panelLineasDashboard.revalidate();
                panelLineasDashboard.repaint();
            });
        }

        // ---------------- GRÁFICO DE PASTEL ----------------
        if (panelPastelDashboard != null) {
            panelPastelDashboard.removeAll();

            org.jfree.data.general.DefaultPieDataset<String> datasetPastel = new org.jfree.data.general.DefaultPieDataset<>();
            for (Map.Entry<String, Long> entry : acumuladoEstados.entrySet()) {
                datasetPastel.setValue(entry.getKey(), entry.getValue());
            }

            org.jfree.chart.JFreeChart chartPastel = org.jfree.chart.ChartFactory.createPieChart(
                    "Recetas",
                    datasetPastel,
                    true, true, false);

            org.jfree.chart.ChartPanel chartPanelPastel = new org.jfree.chart.ChartPanel(chartPastel);
            chartPanelPastel.setPreferredSize(new Dimension(panelPastelDashboard.getWidth(), panelPastelDashboard.getHeight()));

            panelPastelDashboard.setLayout(new BorderLayout());
            panelPastelDashboard.add(chartPanelPastel, BorderLayout.CENTER);
            panelPastelDashboard.validate();
        }

        JOptionPane.showMessageDialog(this, "Dashboard actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }


    private void limpiarDashboardUI() {
        if (formattedTextFieldDesdeDash != null) formattedTextFieldDesdeDash.setText("");
        if (formattedTextFieldHastaDash != null) formattedTextFieldHastaDash.setText("");
        if (comboBox1 != null) comboBox1.setSelectedIndex(-1);

        if (tablaMesAnioDashboard != null) {
            DefaultTableModel model = (DefaultTableModel) tablaMesAnioDashboard.getModel();
            model.setRowCount(0);
        }

        if (tablaEstadosDashboard != null) {
            DefaultTableModel model = (DefaultTableModel) tablaEstadosDashboard.getModel();
            model.setRowCount(0);
        }

        if (panelLineasDashboard != null) {
            panelLineasDashboard.removeAll();
            panelLineasDashboard.revalidate();
            panelLineasDashboard.repaint();
        }

        if (panelPastelDashboard != null) {
            panelPastelDashboard.removeAll();
            panelPastelDashboard.revalidate();
            panelPastelDashboard.repaint();
        }

        JOptionPane.showMessageDialog(this, "Dashboard limpiado.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }


    private void cargarMedicamentosEnComboDashboard() {
        if (comboBox1 == null || controlador == null) return;

        comboBox1.removeAllItems();
        comboBox1.addItem("Todos");

        for (Medicamento m : controlador.obtenerListaMedicamentos()) {
            if (m != null) {
                comboBox1.addItem(m.getNombre());
            }
        }

        comboBox1.setSelectedIndex(0);
    }


// ------------------------------------- HELPERS: ESTILO + ICONOS -------------------------------------
    private final Font  FNT_BTN_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    private final Font  FNT_BTN_PLAIN = new Font("Segoe UI", Font.PLAIN, 13);

    private void estiloPrimario(JButton b, Color primary) {
        if (b == null) return;
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(primary);
        b.setForeground(Color.WHITE);
        b.setFont(FNT_BTN_BOLD);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.putClientProperty("JButton.buttonType", "roundRect"); // Hover FlatLaf
    }

    private void estiloSecundario(JButton b, Color bg, Color fg) {
        if (b == null) return;
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(FNT_BTN_PLAIN);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.putClientProperty("JButton.buttonType", "roundRect");
    }

    private void aplicarIconoYEstilo(JButton boton, FontAwesomeSolid icono, boolean primario) {
        if (boton == null) return;

        // Crear ícono
        FontIcon icon = FontIcon.of(icono, 16, primario ? Color.WHITE : PRIMARY);
        boton.setIcon(icon);

        if (primario) {
            estiloPrimario(boton, PRIMARY);
        } else {
            estiloSecundario(boton, SECOND, PRIMARY);
        }
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

    // ------------------------------------- ACERCA DE -------------------------------------

    private void configurarPanelAcerca() {
        if (panelacerca == null) return;

        panelacerca.setLayout(new BorderLayout(10, 10));

        JLabel iconoLabel = new JLabel();
        iconoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        FontIcon infoIcon = FontIcon.of(FontAwesomeSolid.INFO_CIRCLE, 100, new Color(66, 133, 244));
        iconoLabel.setIcon(infoIcon);

        JPanel panelIcono = new JPanel(new BorderLayout());
        panelIcono.add(iconoLabel, BorderLayout.CENTER);
        panelIcono.setBorder(new EmptyBorder(20, 20, 10, 20));

        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));

        if (lblTitulo != null) {
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
            lblTitulo.setForeground(PRIMARY);
            lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelTexto.add(lblTitulo);
        }

        if (lblDescripcion != null) {
            lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            lblDescripcion.setForeground(Color.DARK_GRAY);
            lblDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelTexto.add(Box.createVerticalStrut(10));
            panelTexto.add(lblDescripcion);
        }

        panelacerca.removeAll();
        panelacerca.add(panelIcono, BorderLayout.NORTH);
        panelacerca.add(panelTexto, BorderLayout.CENTER);
        panelacerca.revalidate();
        panelacerca.repaint();
    }

    // ------------------------------------------ MAIN ------------------------------------------
    private void mostrarLogin() {
        LoginVista loginVista = new LoginVista();
        JDialog loginDialog = loginVista.createDialog(this);

        loginDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        loginDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                cerrarAplicacion();
            }
        });

        loginVista.setOnIngresar(e -> {
            String id = loginVista.getUsuarioId();
            String clave = new String(loginVista.getClave());

            try {
                int token = controlador.devolverToken(id, clave);
                controlador.devolverId(id, clave);
                aplicarPermisos(token);
                loginDialog.dispose();

                JOptionPane.showMessageDialog(this,
                        "Bienvenido ",
                        "Login correcto",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (SecurityException ex) {
                JOptionPane.showMessageDialog(loginDialog,
                        "Usuario o contraseña incorrectos",
                        "Error de login",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        loginVista.setOnCambiarContrasena(e -> {
            CambiarClaveVista cambiarVista = new CambiarClaveVista();
            final JDialog cambiarDialog = cambiarVista.createDialog(loginDialog);

            cambiarVista.setOnAceptar(ev -> {
                String id = cambiarVista.getUsuarioId();
                String actual = new String(cambiarVista.getClaveActual());
                String nueva = new String(cambiarVista.getClaveNueva());
                String confirmar = new String(cambiarVista.getClaveConfirmacion());

                try {
                    controlador.cambiarClave(id, actual, nueva, confirmar);
                    JOptionPane.showMessageDialog(cambiarDialog,
                            "Contraseña cambiada con éxito",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    cambiarDialog.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(cambiarDialog,
                            ex.getMessage(),
                            "Error de validación",
                            JOptionPane.WARNING_MESSAGE);
                } catch (SecurityException ex) {
                    JOptionPane.showMessageDialog(cambiarDialog,
                            "Error de seguridad: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            cambiarVista.setOnCancelar(ev -> cambiarDialog.dispose());
            cambiarDialog.setVisible(true);
        });
        loginDialog.setVisible(true);
    }
    public void aplicarPermisos(int token) {
        if (tabbedPanePrincipal == null) return;

        switch (token) {
            case 0: // Admin
                for (int i = 0; i < tabbedPanePrincipal.getTabCount(); i++) {
                    tabbedPanePrincipal.setEnabledAt(i, true);
                }
                break;
            case 1: // Médico
                for (int i = 0; i < tabbedPanePrincipal.getTabCount(); i++) {
                    tabbedPanePrincipal.setEnabledAt(i, false);
                }
                tabbedPanePrincipal.setEnabledAt(0, true);
                tabbedPanePrincipal.setEnabledAt(1, true);
                tabbedPanePrincipal.setEnabledAt(7, true);
                tabbedPanePrincipal.setEnabledAt(8, true);
                break;
            case 2: // Farmacéuta
                for (int i = 0; i < tabbedPanePrincipal.getTabCount(); i++) {
                    tabbedPanePrincipal.setEnabledAt(i, false);
                }
                tabbedPanePrincipal.setEnabledAt(0, true);
                tabbedPanePrincipal.setEnabledAt(2, true);
                tabbedPanePrincipal.setEnabledAt(7, true);
                tabbedPanePrincipal.setEnabledAt(8, true);
                break;
            default:
                for (int i = 0; i < tabbedPanePrincipal.getTabCount(); i++) {
                    tabbedPanePrincipal.setEnabledAt(i, false);
                }
        }
    }

    public void init() {
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Error iniciando FlatLaf: " + ex.getMessage());
        }
        SwingUtilities.invokeLater(() -> {
            this.setVisible(false);
            mostrarLogin();
            this.setVisible(true); // <-- se muestra solo después del login
        });
    }
}