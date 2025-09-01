package Vista;

import javax.swing.*;
import java.awt.*;

public class PanelFarmaceuta extends JPanel {
    public JTextField txtId, txtNombre;
    public JButton btnGuardar, btnEliminar, btnBuscar;
    public JTable tablaFarmaceutas;

    public PanelFarmaceuta() {
        setLayout(new BorderLayout());

        // Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2));
        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnEliminar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        tablaFarmaceutas = new JTable(); // Se llenará desde el controlador
        add(new JScrollPane(tablaFarmaceutas), BorderLayout.CENTER);

        // Búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Buscar por nombre:"));
        JTextField txtBuscar = new JTextField(15);
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);

        add(panelBusqueda, BorderLayout.SOUTH);
    }

}
