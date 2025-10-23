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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(
                    new FileOutputStream(FileDescriptor.out), true,
                    StandardCharsets.UTF_8.name()));
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (UnsupportedEncodingException
                 | ClassNotFoundException
                 | IllegalAccessException
                 | InstantiationException
                 | UnsupportedLookAndFeelException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }

        new Main().init();

        System.out.println("Aplicación inicializada..");
    }

    private void init() {       SwingUtilities.invokeLater(() -> {
            mostrarInterfaz();
        });
    }

    private void mostrarInterfaz() throws SQLException {
        System.out.println("Iniciando interfaz..");

        Controlador controlador = new Controlador();
        controlador.init();
        FlatLightLaf.setup();
        MenuVista vistaPrincipal = new MenuVista(controlador);
        vistaPrincipal.init();
    }
}