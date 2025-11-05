package Vista.Mensajes;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.*;
import Controlador.Controlador;

public class DialogEnviar extends JDialog {
    // ----- Componentes (del GUI Designer) -----
    private JPanel contentPane;
    private JButton btnEnviarMensaje;
    private JButton btnCancelarMensaje;
    private JComboBox comboParaUsuarios;
    private JLabel lblPara;
    private JLabel lblMensaje;
    private JLabel lblContador;
    private JScrollPane scrollMensaje;
    private JTextArea taTexto;

    // ----- Estado / dependencias -----
    private Controlador controlador;
    private String preselectId;
    private boolean aceptado = false;
    private static final int LIMITE = 500;

    // ----- Constructores -----
    public DialogEnviar() {
        this(null, null, null);
    }

    public DialogEnviar(Frame owner, Controlador controlador, String preselectId) {
        super(owner, "Enviar mensaje", true);
        try { FlatLightLaf.setup(); } catch (Exception ignored) {}

        this.controlador = controlador;
        this.preselectId = preselectId;

        setContentPane(contentPane);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(420, 340);
        setLocationRelativeTo(owner);
        setResizable(false);

        aplicarEstilos();
        configurarEventos();
        configurarLimiteTexto(LIMITE);

        if (controlador != null) {
            cargarDestinatarios();
            if (preselectId != null) seleccionarDestinatarioPorId(preselectId);
        }

        if (getRootPane() != null && btnEnviarMensaje != null)
            getRootPane().setDefaultButton(btnEnviarMensaje);
    }

    // ----- API de integración opcional -----
    public void setControlador(Controlador c) {
        this.controlador = c;
        cargarDestinatarios();
        if (preselectId != null) seleccionarDestinatarioPorId(preselectId);
    }

    public void setPreselectedDestId(String id) {
        this.preselectId = id;
        if (id != null) seleccionarDestinatarioPorId(id);
    }

    public boolean isAceptado() { return aceptado; }

    // ----- Estilos (basados en DialogBuscarReceta) -----
    private void aplicarEstilos() {
        final Color PRIMARY = new Color(66, 133, 244);
        final Color SECOND  = new Color(204, 228, 255);
        final Font  FNT_TXT = new Font("Segoe UI", Font.PLAIN, 13);
        final Font  FNT_BTN = new Font("Segoe UI", Font.BOLD, 13);

        if (contentPane != null) {
            contentPane.setBackground(Color.WHITE);
            ((JComponent) contentPane).setBorder(new EmptyBorder(12, 12, 12, 12));
            blanquearFondosRec(contentPane);
        }

        if (comboParaUsuarios != null) {
            comboParaUsuarios.setFont(FNT_TXT);
            comboParaUsuarios.setBackground(Color.WHITE);
            comboParaUsuarios.setForeground(Color.BLACK);
        }
        if (taTexto != null) {
            taTexto.setFont(FNT_TXT);
            taTexto.setLineWrap(true);
            taTexto.setWrapStyleWord(true);
        }
        if (lblPara != null) lblPara.setFont(new Font("Segoe UI", Font.BOLD, 13));
        if (lblMensaje != null) lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 13));
        if (lblContador != null) lblContador.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        estiloPrimario(btnEnviarMensaje, PRIMARY, FNT_BTN);
        estiloSecundario(btnCancelarMensaje, SECOND, PRIMARY, FNT_BTN);

        igualarTamanoBotones(140, 36, btnEnviarMensaje, btnCancelarMensaje);
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
        if (b == null) return;
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(primary);
        b.setForeground(Color.WHITE);
        b.setFont(fnt);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.putClientProperty("JButton.buttonType", "roundRect");
    }

    private void estiloSecundario(JButton b, Color bg, Color fg, Font fnt) {
        if (b == null) return;
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(fnt);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

    // ----- Comportamiento -----
    private void configurarEventos() {
        btnEnviarMensaje.addActionListener(e -> onEnviar());
        btnCancelarMensaje.addActionListener(e -> onCancel());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { onCancel(); }
        });

        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // contador caracteres
        if (taTexto != null && lblContador != null) {
            lblContador.setText("0/" + LIMITE);
            taTexto.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                private void upd() {
                    int len = taTexto.getText() == null ? 0 : taTexto.getText().length();
                    lblContador.setText(len + "/" + LIMITE);
                }
                public void insertUpdate(javax.swing.event.DocumentEvent e) { upd(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { upd(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { upd(); }
            });
        }
    }

    private void configurarLimiteTexto(int limite) {
        if (taTexto == null) return;
        AbstractDocument doc = (AbstractDocument) taTexto.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) text = "";
                int cur = fb.getDocument().getLength();
                int newLen = cur - length + text.length();
                if (newLen <= limite) super.replace(fb, offset, length, text, attrs);
                else Toolkit.getDefaultToolkit().beep();
            }
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) string = "";
                int cur = fb.getDocument().getLength();
                if (cur + string.length() <= limite) super.insertString(fb, offset, string, attr);
                else Toolkit.getDefaultToolkit().beep();
            }
        });
    }

    private void cargarDestinatarios() {
        if (comboParaUsuarios == null || controlador == null) return;
        comboParaUsuarios.removeAllItems();
        try {
            // Pedimos todos y filtramos el usuario logueado
            Object yo = (controlador.getUsuario_login() != null) ? controlador.getUsuario_login() : null;
            String miId = (yo == null) ? null : getStringProperty(yo, "getId");

            List<?> usuarios = controlador.obtenerListaUsuarios(); // o controlador.obtenerPosiblesDestinatarios()
            if (usuarios == null) usuarios = Collections.emptyList();

            for (Object u : usuarios) {
                String id = getStringProperty(u, "getId");
                String nombre = getStringProperty(u, "getNombre");
                if (id == null || (miId != null && miId.equals(id))) continue; // no agregue el propio
                comboParaUsuarios.addItem(new UsuarioItem(id, nombre));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar destinatarios:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        comboParaUsuarios.setSelectedIndex(comboParaUsuarios.getItemCount() > 0 ? 0 : -1);
    }

    private void seleccionarDestinatarioPorId(String id) {
        if (id == null || comboParaUsuarios == null) return;
        for (int i = 0; i < comboParaUsuarios.getItemCount(); i++) {
            Object it = comboParaUsuarios.getItemAt(i);
            if (it instanceof UsuarioItem u && id.equals(u.id())) {
                comboParaUsuarios.setSelectedIndex(i);
                break;
            }
        }
    }

    private void onEnviar() {
        UsuarioItem sel = (UsuarioItem) comboParaUsuarios.getSelectedItem();
        String texto = (taTexto != null && taTexto.getText() != null) ? taTexto.getText().trim() : "";

        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un destinatario.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escriba un mensaje.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (controlador == null)
                throw new IllegalStateException("No hay Controlador disponible para enviar el mensaje.");

            controlador.enviarMensaje(sel.id(), texto);
            aceptado = true;
            JOptionPane.showMessageDialog(this, "Mensaje enviado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo enviar el mensaje:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        aceptado = false;
        dispose();
    }

    // ----- Utilidades de reflexión / helpers -----
    private static String getStringProperty(Object obj, String getterName) {
        if (obj == null) return null;
        try {
            Method m = obj.getClass().getMethod(getterName);
            Object v = m.invoke(obj);
            return (v == null) ? null : String.valueOf(v);
        } catch (Exception ignored) { return null; }
    }

    // Combo item (id + nombre)
    public record UsuarioItem(String id, String nombre) {
        @Override public String toString() { return (nombre == null || nombre.isEmpty()) ? id : nombre + " (" + id + ")"; }
    }

}
