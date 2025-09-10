package Vista;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class LoginVista extends JFrame {

    // ------------------------------------------------------------------------------------------
    // -------------------------------- ATRIBUTOS DE LA VISTA -----------------------------------
    // ------------------------------------------------------------------------------------------

    private JTextField idText;
    private JPanel panel1;
    private JPasswordField claveText;
    private JButton ingresarButton;
    private JButton cambiarContraseñaButton;
    private JLabel idField;
    private JLabel claveField;
    private JButton limpiarButton;

    // ------------------------------------------------------------------------------------------
    // ------------------------------------- CONSTRUCTOR ----------------------------------------
    // ------------------------------------------------------------------------------------------

    public LoginVista() {
        // L&F recomendado para mantener la estética en todo el sistema
        try { FlatLightLaf.setup(); } catch (Exception ignored) {}

        setTitle("Ingreso al Sistema");
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 420);
        setLocationRelativeTo(null);

        // Estilos unificados (celestes) + accesibilidad básica
        aplicarEstilosLogin();

        // Default button al presionar Enter
        if (getRootPane() != null && ingresarButton != null) {
            getRootPane().setDefaultButton(ingresarButton);
        }

        // Acción rápida: limpiar formulario
        if (limpiarButton != null) {
            limpiarButton.addActionListener(e -> {
                if (idText != null) idText.setText("");
                if (claveText != null) claveText.setText("");
                if (idText != null) idText.requestFocus();
            });
        }
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------- ESTILOS / LOOK & FEEL ------------------------------------
    // ------------------------------------------------------------------------------------------

    private void aplicarEstilosLogin() {
        // Paleta del sistema
        final Color PRIMARY = new Color(66, 133, 244);   // celeste principal
        final Color SECOND  = new Color(204, 228, 255);  // celeste claro
        final Color LABEL   = new Color(33, 37, 41);     // texto oscuro
        final Font  FNT_TXT = new Font("Segoe UI", Font.PLAIN, 13);
        final Font  FNT_BTN = new Font("Segoe UI", Font.BOLD, 13);
        final Font  FNT_LBL = new Font("Segoe UI", Font.PLAIN, 12);

        // Fondo general
        if (panel1 != null) panel1.setBackground(Color.WHITE);

        // Labels
        JLabel[] labels = { idField, claveField };
        for (JLabel l : labels) {
            if (l == null) continue;
            l.setFont(FNT_LBL);
            l.setForeground(LABEL);
        }

        // Campos
        if (idText != null) {
            idText.setFont(FNT_TXT);
            idText.setForeground(Color.BLACK);
            idText.setBackground(Color.WHITE);
            idText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        }
        if (claveText != null) {
            claveText.setFont(FNT_TXT);
            claveText.setForeground(Color.BLACK);
            claveText.setBackground(Color.WHITE);
            claveText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        }

        // Botones: primario (Ingresar) + secundarios (Cambiar, Limpiar)
        if (ingresarButton != null) {
            estiloPrimario(ingresarButton, PRIMARY, FNT_BTN);
        }
        JButton[] secundarios = { cambiarContraseñaButton, limpiarButton };
        for (JButton b : secundarios) {
            if (b == null) continue;
            estiloSecundario(b, SECOND, PRIMARY, FNT_BTN);
        }

        // Asegurar que ningún botón quede sin estilizar (secundario por defecto)
        Set<JButton> ya = new HashSet<>();
        if (ingresarButton != null) ya.add(ingresarButton);
        for (JButton b : secundarios) if (b != null) ya.add(b);
        estilizarBotonesRestantes(panel1, ya, SECOND, PRIMARY, FNT_BTN);
    }

    // -- helpers de estilo (mismo criterio usado en MenuVista) --------------------------------

    private void estiloPrimario(JButton b, Color primary, Font fnt) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(primary);
        b.setForeground(Color.WHITE);
        b.setFont(fnt);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void estiloSecundario(JButton b, Color bg, Color fg, Font fnt) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(fnt);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void estilizarBotonesRestantes(Container root, Set<JButton> ya, Color bg, Color fg, Font fnt) {
        if (root == null) return;
        for (Component c : root.getComponents()) {
            if (c instanceof JButton) {
                JButton b = (JButton) c;
                if (!ya.contains(b)) estiloSecundario(b, bg, fg, fnt);
            } else if (c instanceof Container) {
                estilizarBotonesRestantes((Container) c, ya, bg, fg, fnt);
            }
        }
    }

    // ------------------------------------------------------------------------------------------
    // --------------------------------- GETTERS / LISTENERS ------------------------------------
    // ------------------------------------------------------------------------------------------

    public String getUsuarioId() {
        return idText != null ? idText.getText().trim() : "";
    }

    public char[] getClave() {
        return claveText != null ? claveText.getPassword() : new char[0];
    }

    public void setOnIngresar(ActionListener l) {
        if (ingresarButton != null) ingresarButton.addActionListener(l);
    }

    public void setOnCambiarContrasena(ActionListener l) {
        if (cambiarContraseñaButton != null) cambiarContraseñaButton.addActionListener(l);
    }

    public void setOnLimpiar(ActionListener l) {
        if (limpiarButton != null) limpiarButton.addActionListener(l);
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------------------ MAIN ------------------------------------------
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginVista().setVisible(true));
    }
}
