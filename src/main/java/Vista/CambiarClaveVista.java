package Vista;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class CambiarClaveVista extends JDialog {

    // ------------------------------------------------------------------------------------------
    // -------------------------------- ATRIBUTOS DE LA VISTA -----------------------------------
    // ------------------------------------------------------------------------------------------

    private JPanel panel1;
    private JTextField idText;
    private JLabel idLabel;
    private JPasswordField passwordField1; // Contraseña actual
    private JPasswordField passwordField2; // Nueva contraseña
    private JPasswordField passwordField3; // Confirmar nueva
    private JLabel ContraActualField;
    private JLabel nuevaContraField;
    private JLabel confirmarField;
    private JButton aceptarButton;
    private JButton cancelarButton;

    // ------------------------------------------------------------------------------------------
    // ------------------------------------- CONSTRUCTOR ----------------------------------------
    // ------------------------------------------------------------------------------------------

    public CambiarClaveVista() {
        setTitle("Cambiar contraseña");
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 460);
        setLocationRelativeTo(null);

        aplicarEstilosCambiarClave();

        if (getRootPane() != null && aceptarButton != null)
            getRootPane().setDefaultButton(aceptarButton);

        // Acción por defecto de "Cancelar": cerrar ventana
        if (cancelarButton != null) {
            cancelarButton.addActionListener(e -> dispose());
        }
    }
    public JDialog createDialog(Window owner) {
        JDialog dialog = new JDialog(owner, "Cambiar contraseña", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setContentPane(panel1);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(owner);

        // establecer el botón por defecto si existe
        if (dialog.getRootPane() != null && aceptarButton != null) {
            dialog.getRootPane().setDefaultButton(aceptarButton);
        }

        return dialog;
    }
    // ------------------------------------------------------------------------------------------
    // ------------------------------- ESTILOS / LOOK & FEEL ------------------------------------
    // ------------------------------------------------------------------------------------------

    private void aplicarEstilosCambiarClave() {
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

        // Labels
        JLabel[] labels = { idLabel, ContraActualField, nuevaContraField, confirmarField };
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
        JPasswordField[] pass = { passwordField1, passwordField2, passwordField3 };
        for (JPasswordField p : pass) {
            if (p == null) continue;
            p.setFont(FNT_TXT);
            p.setForeground(Color.BLACK);
            p.setBackground(Color.WHITE);
            p.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        }

        // Botones
        if (aceptarButton != null)  estiloPrimario(aceptarButton, PRIMARY, FNT_BTN);
        if (cancelarButton != null) estiloSecundario(cancelarButton, SECOND, PRIMARY, FNT_BTN);

        // Asegurar que ningún botón quede sin estilo
        Set<JButton> pintados = new HashSet<>();
        if (aceptarButton != null) pintados.add(aceptarButton);
        if (cancelarButton != null) pintados.add(cancelarButton);
        estilizarBotonesRestantes(panel1, pintados, SECOND, PRIMARY, FNT_BTN);

        // Tamaños uniformes
        igualarTamanoBotones(240, 36, aceptarButton, cancelarButton);
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
        b.putClientProperty("JButton.buttonType", "roundRect"); // Hover FlatLaf
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

    public char[] getClaveActual() {
        return passwordField1 != null ? passwordField1.getPassword() : new char[0];
    }

    public char[] getClaveNueva() {
        return passwordField2 != null ? passwordField2.getPassword() : new char[0];
    }

    public char[] getClaveConfirmacion() {
        return passwordField3 != null ? passwordField3.getPassword() : new char[0];
    }

    public void limpiarCampos() {
        if (idText != null) idText.setText("");
        if (passwordField1 != null) passwordField1.setText("");
        if (passwordField2 != null) passwordField2.setText("");
        if (passwordField3 != null) passwordField3.setText("");
        if (idText != null) idText.requestFocus();
    }

    public void setOnAceptar(ActionListener l) {
        if (aceptarButton != null) aceptarButton.addActionListener(l);
    }

    public void setOnCancelar(ActionListener l) {
        if (cancelarButton != null) cancelarButton.addActionListener(l);
    }
}
