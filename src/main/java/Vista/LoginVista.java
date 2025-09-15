package Vista;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setTitle("Ingreso al Sistema");
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 420);
        setLocationRelativeTo(null);

        aplicarEstilosLogin();

        if (getRootPane() != null && ingresarButton != null)
            getRootPane().setDefaultButton(ingresarButton);

        if (limpiarButton != null) {
            limpiarButton.addActionListener(e -> {
                if (idText != null) idText.setText("");
                if (claveText != null) claveText.setText("");
                if (idText != null) idText.requestFocus();
            });
        }
    }
    public JDialog createDialog(Frame owner) {
        JDialog dialog = new JDialog(owner, "Ingreso al Sistema", true); // modal
        dialog.setContentPane(panel1);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(getSize());
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(owner);

        if (dialog.getRootPane() != null && ingresarButton != null) {
            dialog.getRootPane().setDefaultButton(ingresarButton);
        }

        return dialog;
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------- ESTILOS / LOOK & FEEL ------------------------------------
    // ------------------------------------------------------------------------------------------

    private void aplicarEstilosLogin() {
        final Color PRIMARY = new Color(66, 133, 244);
        final Color SECOND  = new Color(204, 228, 255);
        final Color LABEL   = new Color(33, 37, 41);
        final Font  FNT_TXT = new Font("Segoe UI", Font.PLAIN, 13);
        final Font  FNT_BTN = new Font("Segoe UI", Font.BOLD, 13);
        final Font  FNT_LBL = new Font("Segoe UI", Font.PLAIN, 12);

        if (panel1 != null) {
            panel1.setBackground(Color.WHITE);
            ((JComponent) panel1).setBorder(new EmptyBorder(16, 20, 20, 20));
            blanquearFondosRec(panel1);
        }

        JLabel[] labels = { idField, claveField };
        for (JLabel l : labels) {
            if (l == null) continue;
            l.setFont(FNT_LBL);
            l.setForeground(LABEL);
        }

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

        if (ingresarButton != null) estiloPrimario(ingresarButton, PRIMARY, FNT_BTN);

        JButton[] secundarios = { cambiarContraseñaButton, limpiarButton };
        for (JButton b : secundarios) if (b != null) estiloSecundario(b, SECOND, PRIMARY, FNT_BTN);

        Set<JButton> pintados = new HashSet<>();
        if (ingresarButton != null) pintados.add(ingresarButton);
        for (JButton b : secundarios) if (b != null) pintados.add(b);
        estilizarBotonesRestantes(panel1, pintados, SECOND, PRIMARY, FNT_BTN);

        igualarTamanoBotones(240, 36, ingresarButton, cambiarContraseñaButton, limpiarButton);
    }

    private void blanquearFondosRec(Container root) {
        if (root == null) return;
        for (Component c : root.getComponents()) {
            if (c instanceof JPanel) {
                c.setBackground(Color.WHITE);
                ((JComponent) c).setOpaque(true);
            }
            if (c instanceof Container) blanquearFondosRec((Container) c);
        }
    }

    private void estiloPrimario(JButton b, Color primary, Font fnt) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(primary);
        b.setForeground(Color.WHITE);
        b.setFont(fnt);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setRolloverEnabled(true);
        b.putClientProperty("JButton.buttonType", "roundRect"); // permite hover FlatLaf
    }

    private void estiloSecundario(JButton b, Color bg, Color fg, Font fnt) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(fnt);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setRolloverEnabled(true);
        b.putClientProperty("JButton.buttonType", "roundRect");
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

    private void igualarTamanoBotones(int w, int h, JButton... btns) {
        Dimension d = new Dimension(w, h);
        for (JButton b : btns) {
            if (b == null) continue;
            b.setMinimumSize(d);
            b.setPreferredSize(d);
            b.setMaximumSize(d);
        }
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------- GETTERS / LISTENERS API ----------------------------------
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
}
