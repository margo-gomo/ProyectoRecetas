package Vista;
import javax.swing.*;
import java.awt.*;

public class PanelMedicamentos extends JFrame {
    public JTable tablaMedicamentos;
    public JTextField txtCodigo, txtNombre, txtPresentacion;
    public JButton btnGuardar, btnEliminar, btnBuscar;

    public PanelMedicamentos() {
        setTitle("Catálogo de Medicamentos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Medicamento"));

        panel.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panel.add(txtCodigo);

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Presentación:"));
        txtPresentacion = new JTextField();
        panel.add(txtPresentacion);

        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar por Código");

        panel.add(btnGuardar);
        panel.add(btnEliminar);

        JPanel botones = new JPanel();
        botones.add(btnBuscar);

        tablaMedicamentos = new JTable();
        JScrollPane scroll = new JScrollPane(tablaMedicamentos);

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(botones, BorderLayout.SOUTH);
    }
}

