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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
    private JFormattedTextField tfFechaNacPaciente;
    private JTextField tfTelefonoPaciente;
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

    private DefaultTableModel modeloTablaRecetas;
    private Controlador controlador;
    private int token;
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ------------------------------------------------------------------------------------------
    // ------------------------------------- CONSTRUCTOR ----------------------------------------
    // ------------------------------------------------------------------------------------------

    public MenuVista() {
        this.controlador = new Controlador();
        controlador.setToken(0);


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

        if (elegirFechaButton5 != null) {
            elegirFechaButton5.addActionListener(e -> seleccionarFechaPara(tfFechaNacPaciente));
        }

        // ------------------------- LISTENER: GUARDAR MÉDICO -------------------------
        if (guardarButton2 != null) {
            guardarButton2.addActionListener(new ActionListener() { // Médicos
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!validarCamposMedico()) return;

                    if (controlador == null) {
                        JOptionPane.showMessageDialog(MenuVista.this,
                                "No se puede guardar.",
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

                        Medico nuevo = new Medico(nom, id, esp);
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

        // ------------------------- LISTENER: MODIFICAR MÉDICO -------------------------
        if (modificarButton != null) {
            modificarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (controlador == null) {
                        JOptionPane.showMessageDialog(MenuVista.this,
                                "No hay controlador inicializado.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String id = obtenerIdDesdeFormularioOSel();
                    if (id.isEmpty()) {
                        JOptionPane.showMessageDialog(MenuVista.this,
                                "Ingrese el ID o seleccione un médico en la tabla.",
                                "Falta ID", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    try {
                        Medico existente = controlador.buscarMedicoPorId(id);
                        if (existente == null) {
                            JOptionPane.showMessageDialog(MenuVista.this,
                                    "No existe un médico con ID: " + id,
                                    "No encontrado", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Si nombre/especialidad vienen vacíos en el form, conservar los del existente
                        String nom = (tfNombreMedico != null) ? tfNombreMedico.getText().trim() : "";
                        String esp = (tfEspMedico != null) ? tfEspMedico.getText().trim() : "";

                        if (nom.isEmpty()) nom = existente.getNombre();
                        if (esp.isEmpty()) esp = existente.getEspecialidad();

                        Medico actualizado = new Medico(nom, id, esp); // constructor (nombre, id, especialidad)
                        Medico resultado   = controlador.actualizarMedico(actualizado);

                        // Refrescar tabla
                        actualizarMedicoEnTabla(resultado);

                        // Limpiar formulario
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


        // ------------------------- LISTENER: BORRAR MÉDICO -------------------------

        if (borrarButton != null) {
            borrarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (controlador == null) {
                        JOptionPane.showMessageDialog(MenuVista.this,
                                "No hay controlador inicializado.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Tomar ID del formulario o de la fila seleccionada
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

                    // Confirmación
                    int opc = JOptionPane.showConfirmDialog(
                            MenuVista.this,
                            "¿Desea eliminar al médico con ID: " + id + "?",
                            "Confirmar borrado",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (opc != JOptionPane.YES_OPTION) return;

                    try {
                        // Verificar existencia
                        Medico existente = controlador.buscarMedicoPorId(id);
                        if (existente == null) {
                            JOptionPane.showMessageDialog(MenuVista.this,
                                    "No existe un médico con ID: " + id,
                                    "No encontrado", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Eliminar en modelo
                        controlador.eliminarMedico(id);

                        // Refrescar UI (tabla + formulario)
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
            }
        });

        // ------------------------- LISTENER: GUARDAR FARMACÉUTA -------------------------
        if (guardarFarm != null) {
            guardarFarm.addActionListener(e -> {
                // 1) Validación de UI
                if (!validarCamposFarmaceuta()) return;
                if (controlador == null) {
                    JOptionPane.showMessageDialog(MenuVista.this, "No se puede guardar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 2) Datos del formulario
                String id  = tfIdFarma.getText().trim();
                String nom = tfNombreFarma.getText().trim();

                try {
                    // 3) Verificar duplicado
                    if (controlador.buscarFarmaceutaPorId(id) != null) {
                        JOptionPane.showMessageDialog(MenuVista.this, "Ya existe un farmacéuta con ID: " + id, "Duplicado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 4) Crear entidad y persistir
                    Modelo.entidades.Farmaceuta f = new Modelo.entidades.Farmaceuta();
                    f.setId(id);
                    f.setNombre(nom);

                    Modelo.entidades.Farmaceuta agregado = controlador.agregarFarmaceuta(f);

                    // 5) Refrescar UI
                    agregarFarmaceutaATabla(agregado);
                    limpiarCamposFarmaceuta();

                    JOptionPane.showMessageDialog(MenuVista.this, "Farmacéuta guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // ------------------------- LISTENER: MODIFICAR FARMACÉUTA -------------------------
        if (modificarFarm != null) {
            modificarFarm.addActionListener(e -> {
                if (controlador == null) {
                    JOptionPane.showMessageDialog(MenuVista.this, "No hay controlador inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 1) ID desde form o fila seleccionada
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
                    // 2) Buscar existente
                    Modelo.entidades.Farmaceuta existente = controlador.buscarFarmaceutaPorId(id);
                    if (existente == null) {
                        JOptionPane.showMessageDialog(MenuVista.this, "No existe un farmacéuta con ID: " + id, "No encontrado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 3) Actualizar campos no vacíos
                    String nom = (tfNombreFarma != null) ? tfNombreFarma.getText().trim() : "";
                    if (!nom.isEmpty()) existente.setNombre(nom);

                    // 4) Persistir y refrescar UI
                    Modelo.entidades.Farmaceuta actualizado = controlador.actualizarFarmaceuta(existente);
                    actualizarFarmaceutaEnTabla(actualizado);
                    limpiarCamposFarmaceuta();

                    JOptionPane.showMessageDialog(MenuVista.this, "Farmacéuta modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // ------------------------- LISTENER: BORRAR FARMACÉUTA -------------------------
        if (borrarFarm != null) {
            borrarFarm.addActionListener(e -> {
                if (controlador == null) {
                    JOptionPane.showMessageDialog(MenuVista.this, "No hay controlador inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 1) ID desde form o seleccion
                String id = (tfIdFarma != null) ? tfIdFarma.getText().trim() : "";
                if (id.isEmpty() && tablaFarma != null && tablaFarma.getSelectedRow() >= 0) {
                    Object v = tablaFarma.getValueAt(tablaFarma.getSelectedRow(), 0);
                    id = (v != null) ? v.toString() : "";
                }
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Ingrese el ID o seleccione un farmacéuta en la tabla.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 2) Confirmación
                int opc = JOptionPane.showConfirmDialog(
                        MenuVista.this,
                        "¿Desea eliminar al farmacéuta con ID: " + id + "?",
                        "Confirmar borrado",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (opc != JOptionPane.YES_OPTION) return;

                try {
                    // 3) Verificar existencia
                    if (controlador.buscarFarmaceutaPorId(id) == null) {
                        JOptionPane.showMessageDialog(MenuVista.this, "No existe un farmacéuta con ID: " + id, "No encontrado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 4) Eliminar y refrescar UI
                    controlador.eliminarFarmaceuta(id);
                    eliminarFarmaceutaDeTablaPorId(id);
                    limpiarCamposFarmaceuta();

                    JOptionPane.showMessageDialog(MenuVista.this, "Farmacéuta eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // ------------------------- LISTENER: LIMPIAR FARMACÉUTA -------------------------
        if (limpiarFarm != null) {
            limpiarFarm.addActionListener(e -> {
                if (tablaFarma != null) tablaFarma.clearSelection(); // quitar selección
                limpiarCamposFarmaceuta();                            // limpiar form
            });
        }

        // --------------------------- LISTENER: GUARDAR PACIENTE ---------------------------
        if (guardarPaciente != null) {
            guardarPaciente.addActionListener(e -> {
                if (!validarCamposPacienteParaGuardar()) return;
                if (controlador == null) {
                    JOptionPane.showMessageDialog(MenuVista.this, "No se puede guardar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id, tel;
                try {
                    id  = Integer.parseInt(tfIdPaciente.getText().trim());
                    tel = Integer.parseInt(tfTelefonoPaciente.getText().trim());
                } catch (NumberFormatException exN) {
                    JOptionPane.showMessageDialog(MenuVista.this, "ID y Teléfono deben ser numéricos.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                java.time.LocalDate fecha = leerFechaNacDelForm();
                if (fecha == null) return; // si es obligatoria y está mal, se aborta

                try {
                    if (controlador.buscarPacientePorId(id) != null) {
                        JOptionPane.showMessageDialog(MenuVista.this, "Ya existe un paciente con ID: " + id, "Duplicado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String nom = tfNombrePaciente.getText().trim();
                    Modelo.entidades.Paciente p = new Modelo.entidades.Paciente();
                    p.setId(id);
                    p.setNombre(nom);
                    p.setTelefono(tel);
                    p.setFecha_nacimiento(fecha);

                    Modelo.entidades.Paciente agregado = controlador.agregarPaciente(p);

                    agregarPacienteATabla(agregado);
                    limpiarCamposPaciente();

                    JOptionPane.showMessageDialog(MenuVista.this, "Paciente guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // --------------------------- LISTENER: MODIFICAR PACIENTE ---------------------------

        if (modificarPaciente != null) {
            modificarPaciente.addActionListener(e -> {
                if (!validarIdPacientePresente()) return;
                if (controlador == null) {
                    JOptionPane.showMessageDialog(MenuVista.this, "No hay controlador inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id;
                try {
                    id = Integer.parseInt(tfIdPaciente.getText().trim());
                } catch (NumberFormatException exN) {
                    JOptionPane.showMessageDialog(MenuVista.this, "El ID del paciente debe ser numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    Modelo.entidades.Paciente existente = controlador.buscarPacientePorId(id);
                    if (existente == null) {
                        JOptionPane.showMessageDialog(MenuVista.this, "No existe un paciente con ID: " + id, "No encontrado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String nom   = (tfNombrePaciente   != null) ? tfNombrePaciente.getText().trim()   : "";
                    String telSt = (tfTelefonoPaciente != null) ? tfTelefonoPaciente.getText().trim() : "";
                    String fecSt = (tfFechaNacPaciente != null) ? tfFechaNacPaciente.getText().trim() : "";

                    if (!nom.isEmpty()) existente.setNombre(nom);
                    if (!telSt.isEmpty()) {
                        try { existente.setTelefono(Integer.parseInt(telSt)); }
                        catch (NumberFormatException ex2) {
                            JOptionPane.showMessageDialog(MenuVista.this, "El teléfono debe ser numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
                            tfTelefonoPaciente.requestFocus();
                            return;
                        }
                    }
                    if (!fecSt.isEmpty()) {
                        java.time.LocalDate fecha = leerFechaNacDelForm();
                        if (fecha == null) return;
                        existente.setFecha_nacimiento(fecha);
                    }

                    Modelo.entidades.Paciente actualizado = controlador.actualizarPaciente(existente);
                    actualizarPacienteEnTabla(actualizado);
                    limpiarCamposPaciente();

                    JOptionPane.showMessageDialog(MenuVista.this, "Paciente modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // --------------------------- LISTENER: BORRAR PACIENTE ---------------------------
        if (borrarPaciente != null) {
            borrarPaciente.addActionListener(e -> {
                if (controlador == null) {
                    JOptionPane.showMessageDialog(MenuVista.this, "No hay controlador inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

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
                    if (controlador.buscarPacientePorId(id) == null) {
                        JOptionPane.showMessageDialog(MenuVista.this, "No existe un paciente con ID: " + id, "No encontrado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    controlador.eliminarPaciente(id);
                    eliminarPacienteDeTablaPorId(id);
                    limpiarCamposPaciente();

                    JOptionPane.showMessageDialog(MenuVista.this, "Paciente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // --------------------------- LISTENER: LIMPIAR PACIENTE ---------------------------
        if (limpiarPaciente != null) {
            limpiarPaciente.addActionListener(e -> {
                if (tablaPac != null) tablaPac.clearSelection();
                limpiarCamposPaciente();
            });
        }

        // ------------------------ LISTENER: GUARDAR MEDICAMENTO ------------------------
        if (guardarMedicamento != null) {
            guardarMedicamento.addActionListener(e -> {
                // 1) Validación de UI
                if (!validarCamposMedicamentoParaGuardar()) return;
                if (controlador == null) {
                    JOptionPane.showMessageDialog(MenuVista.this, "No se puede guardar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 2) Parse código
                int codigo;
                try { codigo = Integer.parseInt(tfCodigoMed.getText().trim()); }
                catch (NumberFormatException exN) {
                    JOptionPane.showMessageDialog(MenuVista.this, "El código debe ser numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String nom = tfNombreMed.getText().trim();
                String des = tfDescMed.getText().trim();

                try {
                    // 3) Duplicado
                    if (controlador.buscarMedicamentoPorCodigo(codigo) != null) {
                        JOptionPane.showMessageDialog(MenuVista.this, "Ya existe un medicamento con código: " + codigo, "Duplicado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 4) Crear entidad y persistir
                    Modelo.entidades.Medicamento m = new Modelo.entidades.Medicamento();
                    m.setCodigo(codigo);
                    m.setNombre(nom);
                    m.setDescripcion(des);

                    Modelo.entidades.Medicamento agregado = controlador.agregarMedicamento(m);

                    // 5) Refrescar UI
                    agregarMedicamentoATabla(agregado);
                    limpiarCamposMedicamento();

                    JOptionPane.showMessageDialog(MenuVista.this, "Medicamento guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

// ------------------------ LISTENER: MODIFICAR MEDICAMENTO ------------------------
        if (modificarMedicamento != null) {
            modificarMedicamento.addActionListener(e -> {
                // 1) Validar que el código esté presente
                if (!validarCodigoMedicamentoPresente()) return;
                if (controlador == null) {
                    JOptionPane.showMessageDialog(MenuVista.this, "No hay controlador inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 2) Parse código
                int codigo;
                try { codigo = Integer.parseInt(tfCodigoMed.getText().trim()); }
                catch (NumberFormatException exN) {
                    JOptionPane.showMessageDialog(MenuVista.this, "El código debe ser numérico.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    // 3) Buscar existente
                    Modelo.entidades.Medicamento existente = controlador.buscarMedicamentoPorCodigo(codigo);
                    if (existente == null) {
                        JOptionPane.showMessageDialog(MenuVista.this, "No existe un medicamento con código: " + codigo, "No encontrado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 4) Actualizar solo campos no vacíos
                    String nom = (tfNombreMed != null) ? tfNombreMed.getText().trim() : "";
                    String des = (tfDescMed   != null) ? tfDescMed.getText().trim()   : "";
                    if (!nom.isEmpty()) existente.setNombre(nom);
                    if (!des.isEmpty()) existente.setDescripcion(des);

                    // 5) Persistir y refrescar UI
                    Modelo.entidades.Medicamento actualizado = controlador.actualizarMedicamento(existente);
                    actualizarMedicamentoEnTabla(actualizado);
                    limpiarCamposMedicamento();

                    JOptionPane.showMessageDialog(MenuVista.this, "Medicamento modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // ------------------------ LISTENER: BORRAR MEDICAMENTO ------------------------
        if (borrarMedicamento != null) {
            borrarMedicamento.addActionListener(e -> {
                if (controlador == null) {
                    JOptionPane.showMessageDialog(MenuVista.this, "No hay controlador inicializado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 1) Código desde form o fila seleccionada
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
                    // 3) Verificar existencia
                    if (controlador.buscarMedicamentoPorCodigo(codigo) == null) {
                        JOptionPane.showMessageDialog(MenuVista.this, "No existe un medicamento con código: " + codigo, "No encontrado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 4) Eliminar y refrescar UI
                    controlador.eliminarMedicamento(codigo);
                    eliminarMedicamentoDeTablaPorCodigo(codigo);
                    limpiarCamposMedicamento();

                    JOptionPane.showMessageDialog(MenuVista.this, "Medicamento eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MenuVista.this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // ------------------------ LISTENER: LIMPIAR MEDICAMENTO ------------------------
        if (limpiarMedicamento != null) {
            limpiarMedicamento.addActionListener(e -> {
                if (tablaMed != null) tablaMed.clearSelection(); // quitar selección
                limpiarCamposMedicamento();                       // limpiar form
            });
        }
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
                tfTelefonoPaciente, formattedTextField1, formattedTextField2, formattedTextField3, tfFechaNacPaciente,
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

        JButton[] primarios = {
                guardarButton, guardarButton2,
                guardarFarm, guardarPaciente, guardarMedicamento,
                generarFarma, generarPaciente, generarMedicamento,
                iniciarProcesoButton, aplicarFiltrosButton, exportarButton,
                generarReporteButton, entregarButton, marcarListaButton
        };

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
        if (tabloMedicos == null) return;

        String[] columnasMedicos = {"ID", "Nombre", "Especialidad"};
        DefaultTableModel modeloMedicos = new DefaultTableModel(columnasMedicos, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; } // <- no editable
        };
        tabloMedicos.setModel(modeloMedicos);

        // Selección simple
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
    }

    private void configurarTablaMedicamentos() {
        if (tablaMed == null) return;

        String[] columnasMed = {"Código", "Nombre", "Descripción"};
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
    // --------------------------- HELPERS: FORMULARIO MÉDICOS ----------------------------------
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

    // ------------------------------------------------------------------------------------------
    // ------------------------- HELPERS: FORMULARIO FARMACEUTAS --------------------------------
    // ------------------------------------------------------------------------------------------

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

    // ------------------------------------------------------------------------------------------
    // -------------------------- HELPERS: FORMULARIO PACIENTES ---------------------------------
    // ------------------------------------------------------------------------------------------

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


    // ------------------------------------------------------------------------------------------
    // ------------------------- HELPERS: FORMULARIO MEDICAMENTOS -------------------------------
    // ------------------------------------------------------------------------------------------

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

    // ------------------------------------------------------------------------------------------
    // ------------------------------------- HELPERS: ESTILO-------------------------------------
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
