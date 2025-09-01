package Controlador;
import Vista.*;
import javax.swing.*;
import Controlador.MenuControlador;
import Vista.*;
import javax.swing.*;
import java.awt.*;

public class MenuControlador {

    private MenuPrincipalVista vista;

    public MenuControlador(MenuPrincipalVista vista) {
        this.vista = vista;
        iniciarEventos();
    }

    private void iniciarEventos() {
        vista.btnMedicos.addActionListener(e -> {
            PanelMedicos panel = new PanelMedicos();
            new ControladorMedicos(panel); // Conecta lógica
            mostrarVista(panel);
        });

        // Repetir para pacientes, medicamentos, etc.
    }

    private void mostrarVista(JPanel nuevaVista) {
        vista.panelContenido.removeAll();
        vista.panelContenido.add(nuevaVista, BorderLayout.CENTER);
        vista.panelContenido.revalidate();
        vista.panelContenido.repaint();
    }

}
