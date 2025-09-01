package Vista;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipalVista extends JFrame {

    public JPanel panelContenido;
    public JButton btnMedicos, btnPacientes, btnMedicamentos, btnDashboard, btnHistorico;

    public MenuPrincipalVista() {
        setTitle("Sistema de Recetas - Hospital");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Menú lateral
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(6, 1));
        panelMenu.setPreferredSize(new Dimension(200, 0));

        btnMedicos = new JButton("Médicos");
        btnPacientes = new JButton("Pacientes");
        btnMedicamentos = new JButton("Medicamentos");
        btnDashboard = new JButton("Dashboard");
        btnHistorico = new JButton("Histórico");

        panelMenu.add(btnMedicos);
        panelMenu.add(btnPacientes);
        panelMenu.add(btnMedicamentos);
        panelMenu.add(btnDashboard);
        panelMenu.add(btnHistorico);

        add(panelMenu, BorderLayout.WEST);

        // Panel de contenido
        panelContenido = new JPanel();
        panelContenido.setLayout(new BorderLayout());
        add(panelContenido, BorderLayout.CENTER);
    }
}

