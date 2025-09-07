package Vista;
import javax.swing.*;
import java.awt.*;

public class PanelPaciente extends JPanel {


    public JTable tablaPacientes;
    public JTextField txtId, txtNombre, txtFechaNacimiento, txtTelefono;
    public JButton btnGuardar, btnEliminar, btnBuscar;

    public PanelPaciente() {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Paciente"));

        panel.add(new JLabel("ID (número):"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Fecha de Nacimiento (dd/MM/yyyy):"));
        txtFechaNacimiento = new JTextField();
        panel.add(txtFechaNacimiento);

        panel.add(new JLabel("Teléfono (número):"));
        txtTelefono = new JTextField();
        panel.add(txtTelefono);

        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar por ID");

        panel.add(btnGuardar);
        panel.add(btnEliminar);

        JPanel botones = new JPanel();
        botones.add(btnBuscar);

        tablaPacientes = new JTable();
        JScrollPane scroll = new JScrollPane(tablaPacientes);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

}

