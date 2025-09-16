package Vista;

import com.formdev.flatlaf.FlatLightLaf;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

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
    private JLabel iconoLabel;

    // ------------------------------------------------------------------------------------------
    // ------------------------------------- CONSTRUCTOR ----------------------------------------
    // ------------------------------------------------------------------------------------------

    public LoginVista() {
        try { FlatLightLaf.setup(); } catch (Exception ignored) {}

        setTitle("Ingreso al Sistema");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 500);
        setLocationRelativeTo(null);

        inicializarLayout();
        inicializarIcono();
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

    private void inicializarLayout() {
        panel1 = new JPanel(new BorderLayout(10, 10));
        panel1.setBorder(new EmptyBorder(16, 20, 20, 20));
        setContentPane(panel1);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(Color.WHITE);

        idField = new JLabel("Usuario:");
        idText = new JTextField(15);
        claveField = new JLabel("Contraseña:");
        claveText = new JPasswordField(15);

        ingresarButton = new JButton("Ingresar");
        cambiarContraseñaButton = new JButton("Cambiar Contraseña");
        limpiarButton = new JButton("Limpiar");

        JPanel panelCampos = new JPanel(new GridLayout(4, 1, 8, 8));
        panelCampos.setBackground(Color.WHITE);
        panelCampos.add(idField);
        panelCampos.add(idText);
        panelCampos.add(claveField);
        panelCampos.add(claveText);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(ingresarButton);
        panelBotones.add(cambiarContraseñaButton);
        panelBotones.add(limpiarButton);

        panelCentro.add(panelCampos);
        panelCentro.add(Box.createVerticalStrut(15));
        panelCentro.add(panelBotones);

        panel1.add(panelCentro, BorderLayout.CENTER);
    }

    private void inicializarIcono() {
        iconoLabel = new JLabel();
        iconoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // 👤 Ícono principal
        FontIcon userIcon = FontIcon.of(FontAwesomeSolid.USER, 80, new Color(66, 133, 244));
        iconoLabel.setIcon(userIcon);

        JPanel panelIcono = new JPanel(new BorderLayout());
        panelIcono.setBackground(Color.WHITE);
        panelIcono.add(iconoLabel, BorderLayout.CENTER);
        panelIcono.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel1.add(panelIcono, BorderLayout.NORTH);
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

        panel1.setBackground(Color.WHITE);

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

        // Íconos en los botones
        if (ingresarButton != null) {
            ingresarButton.setIcon(FontIcon.of(FontAwesomeSolid.SIGN_IN_ALT, 18, Color.WHITE));
            estiloPrimario(ingresarButton, PRIMARY, FNT_BTN);
        }
        if (cambiarContraseñaButton != null) {
            cambiarContraseñaButton.setIcon(FontIcon.of(FontAwesomeSolid.KEY, 18, PRIMARY));
            estiloSecundario(cambiarContraseñaButton, SECOND, PRIMARY, FNT_BTN);
        }
        if (limpiarButton != null) {
            limpiarButton.setIcon(FontIcon.of(FontAwesomeSolid.BROOM, 18, PRIMARY));
            estiloSecundario(limpiarButton, SECOND, PRIMARY, FNT_BTN);
        }

        igualarTamanoBotones(240, 36, ingresarButton, cambiarContraseñaButton, limpiarButton);
    }

    private void estiloPrimario(JButton b, Color primary, Font fnt) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(primary);
        b.setForeground(Color.WHITE);
        b.setFont(fnt);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setRolloverEnabled(true);
        b.putClientProperty("JButton.buttonType", "roundRect");
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
