package Controlador;
import Gestores.GestorFarmaceuta;
import Gestores.GestorMedico;
import Vista.LoginVista;
import Vista.MenuPrincipalVista;
import entidades.Farmaceuta;
import entidades.Medico;
import Gestores.GestorMedico;
import Vista.PanelFarmaceuta;
import Vista.PanelMedicos;

import javax.swing.*;

public class LoginControlador {
    private LoginVista vista;


    private void abrirMenuPrincipal(String rol) {
//        vista.dispose(); // Cierra la ventana de login
//
//        switch (rol) {
//            case "medico":
//                PanelMedicos vistaMedico = new PanelMedicos();
//                new ControladorMedicos(vistaMedico);
//                vistaMedico.setVisible(true);
//                break;
//            case "farmaceuta":
//                PanelFarmaceuta vistaFarmaceuta = new PanelFarmaceuta();
//                new ControladorFarmaceuta(vistaFarmaceuta);
//                vistaFarmaceuta.setVisible(true);
//                break;
//            // Puedes agregar más roles si lo necesitas
//            default:
//                JOptionPane.showMessageDialog(null, "Rol desconocido.");
//        }

        vista.dispose(); // Cierra la ventana de login
        MenuPrincipalVista menuVista = new MenuPrincipalVista();
        new MenuControlador(menuVista);
        menuVista.setVisible(true);


    }


    public LoginControlador(LoginVista vista) {
        this.vista = vista;
        iniciarEventos();
    }

    private void iniciarEventos() {
        vista.btnIngresar.addActionListener(e -> {
            String id = vista.txtId.getText();
            String clave = new String(vista.txtClave.getPassword());

            try {
                // Validación con médicos
                GestorMedico gestorMedico = new GestorMedico();
                gestorMedico.cargarXML();
                Medico medico = gestorMedico.buscarMedicoID(id);
                if (medico != null && medico.getClave().equals(clave)) {
                    //JOptionPane.showMessageDialog(vista, "Bienvenido Médico");
                    abrirMenuPrincipal("medico");
                    return;
                }

                // Validación con farmaceutas
                GestorFarmaceuta gestorFarmaceuta = new GestorFarmaceuta();
                gestorFarmaceuta.cargarXML();
                Farmaceuta farmaceuta = gestorFarmaceuta.buscarFarmaceutaID(id);
                if (farmaceuta != null && farmaceuta.getClave().equals(clave)) {
                    //JOptionPane.showMessageDialog(vista, "Bienvenido Farmaceuta");
                    abrirMenuPrincipal("farmaceuta");
                    return;
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error al cargar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(vista, "ID o clave incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        });


    }
}
