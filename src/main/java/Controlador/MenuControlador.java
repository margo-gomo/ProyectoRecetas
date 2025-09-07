package Controlador;
import Vista.*;
import javax.swing.*;
import Controlador.MenuControlador;
import Vista.*;
import javax.swing.*;
import java.awt.*;

public class MenuControlador {

//menu
    private MenuPrincipalVista vista;

    public MenuControlador(MenuPrincipalVista vista) {
        this.vista = vista;
        iniciarEventos();
    }

    private void iniciarEventos() {
        vista.btnMedicos.addActionListener(e -> {
            PanelMedicos panel = new PanelMedicos();
            new ControladorMedicos(panel); // Aquí se configuran los eventos
            mostrarVista(panel);
        });
        //================
        vista.btnFarmaceutas.addActionListener(e -> {
            PanelFarmaceuta panel = new PanelFarmaceuta();
            new ControladorFarmaceuta(panel); // Aquí se configuran los eventos
            mostrarVista(panel);
        });
        //=====================
        vista.btnPacientes.addActionListener(e -> {
            PanelPaciente panel = new PanelPaciente();
            new ControladorPacientes(panel); // Aquí se configuran los eventos
            mostrarVista(panel);
        });
        //=====================

        vista.btnMedicamentos.addActionListener(e -> {
            PanelMedicamentos panel = new PanelMedicamentos();
            new ControladorMedicamentos(panel); // Aquí se configuran los eventos
            mostrarVista(panel);
        });
        // Agrega los demás paneles cuando estén listos
    }

    private void mostrarVista(JPanel nuevaVista) {
        vista.panelContenido.removeAll();
        vista.panelContenido.add(nuevaVista, BorderLayout.CENTER);
        vista.panelContenido.revalidate();
        vista.panelContenido.repaint();
    }


}
