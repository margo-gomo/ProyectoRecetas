package org.example;

import Controlador.Controlador;
import Vista.MenuVista;
import com.formdev.flatlaf.FlatLightLaf;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(
                    new FileOutputStream(FileDescriptor.out),
                    true,
                    StandardCharsets.UTF_8.name()
            ));
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (UnsupportedEncodingException
                 | ClassNotFoundException
                 | IllegalAccessException
                 | InstantiationException
                 | UnsupportedLookAndFeelException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }

        SwingUtilities.invokeLater(Main::mostrarInterfaz);

        System.out.println("Aplicación inicializada..");
    }

    private static void mostrarInterfaz() {
        System.out.println("Iniciando interfaz..");
        try {
            FlatLightLaf.setup();

            Controlador controlador = new Controlador();
            controlador.setBackendEndpoint("127.0.0.1", 5050);
            controlador.init();
            MenuVista vistaPrincipal = new MenuVista(controlador);
            vistaPrincipal.init();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Error inicializando base de datos: " + ex.getMessage(),
                    "BD",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Error inesperado: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }
    }
}
