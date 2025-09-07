package Vista;
import javax.swing.*;
import java.awt.*;

public class PanelMedicos
        extends JPanel {
    public JTextField txtId, txtNombre, txtEspecialidad;
    public JButton btnGuardar, btnEliminar, btnBuscar;
    public JTable tablaMedicos;

    public PanelMedicos() {
        setLayout(new BorderLayout());

        // Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2));
        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Especialidad:"));
        txtEspecialidad = new JTextField();
        panelFormulario.add(txtEspecialidad);

        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnEliminar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        tablaMedicos = new JTable(); // Se llenará desde el controlador
        add(new JScrollPane(tablaMedicos), BorderLayout.CENTER);

        // Búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Buscar por Id:"));
        JTextField txtBuscar = new JTextField(15);
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);

        add(panelBusqueda, BorderLayout.SOUTH);
    }

}
