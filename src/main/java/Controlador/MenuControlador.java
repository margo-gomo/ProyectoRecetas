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
        vista.btnMedicos.addActionListener(e -> mostrarVista(new PanelMedicos()));
        vista.btnFarmaceutas.addActionListener(e -> mostrarVista(new PanelFarmaceuta()));
        vista.btnPacientes.addActionListener(e -> mostrarVista(new PanelPaciente()));
        vista.btnMedicamentos.addActionListener(e -> mostrarVista(new PanelMedicamentos()));
        // Agrega los demás paneles cuando estén listos
    }

    private void mostrarVista(JPanel nuevaVista) {
        vista.panelContenido.removeAll();
        vista.panelContenido.add(nuevaVista, BorderLayout.CENTER);
        vista.panelContenido.revalidate();
        vista.panelContenido.repaint();
    }


}
