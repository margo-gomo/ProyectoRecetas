package Vista;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipalVista extends JFrame {


    public JButton btnMedicos, btnFarmaceutas, btnPacientes, btnMedicamentos, btnDashboard, btnHistorico, btnAcerca;
    public JPanel panelContenido;

    public MenuPrincipalVista() {
        setTitle("Menú Principal");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Inicializar botones
        btnMedicos = new JButton("Médicos");
        btnFarmaceutas = new JButton("Farmaceutas");
        btnPacientes = new JButton("Pacientes");
        btnMedicamentos = new JButton("Medicamentos");
        btnDashboard = new JButton("Dashboard");
        btnHistorico = new JButton("Histórico");
        btnAcerca = new JButton("Acerca de");

        JPanel panelBotones = new JPanel(new GridLayout(1, 7));
        panelBotones.add(btnMedicos);
        panelBotones.add(btnFarmaceutas);
        panelBotones.add(btnPacientes);
        panelBotones.add(btnMedicamentos);
        panelBotones.add(btnDashboard);
        panelBotones.add(btnHistorico);
        panelBotones.add(btnAcerca);

        add(panelBotones, BorderLayout.NORTH);

        panelContenido = new JPanel(new BorderLayout());
        add(panelContenido, BorderLayout.CENTER);
    }

}

