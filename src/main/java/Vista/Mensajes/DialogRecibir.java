package Vista.Mensajes;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.*;
import Controlador.Controlador;

public class DialogRecibir extends JDialog {
    // ----- Componentes (del GUI Designer) -----
    private JPanel contentPane;
    private JButton btnRefrescar;
    private JButton btnCerrar;
    private JLabel lblConvoCon;
    private JScrollPane scrollMensajes;
    private JList listMensajes; // usaremos DefaultListModel<MensajeItem> + renderer

    // ----- Estado / dependencias -----
    private Controlador controlador;
    private String otroId;        // id del otro usuario
    private String otroNombre;    // nombre (opcional para el título)

    // ----- Constructores -----
    public DialogRecibir() {
        this(null, null, null, null);
    }

    public DialogRecibir(Frame owner, Controlador controlador, String otroId) {
        this(owner, controlador, otroId, null);
    }

    public DialogRecibir(Frame owner, Controlador controlador, String otroId, String otroNombre) {
        super(owner, "Mensajes", true);
        try { FlatLightLaf.setup(); } catch (Exception ignored) {}

        this.controlador = controlador;
        this.otroId = otroId;
        this.otroNombre = otroNombre;

        setContentPane(contentPane);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(520, 380);
        setLocationRelativeTo(owner);
        setResizable(true);

        aplicarEstilos();
        configurarEventos();

        // Modelo y renderer de la lista
        DefaultListModel<MensajeItem> model = new DefaultListModel<>();
        listMensajes.setModel(model);
        listMensajes.setCellRenderer(new BubbleRenderer());

        actualizarTitulo();
        if (controlador != null && otroId != null) cargarMensajes();
    }

    // ----- API de integración opcional -----
    public void setControlador(Controlador c) {
        this.controlador = c;
        if (otroId != null) cargarMensajes();
    }
    public void setOtroUsuario(String id, String nombre) {
        this.otroId = id;
        this.otroNombre = nombre;
        actualizarTitulo();
        if (controlador != null && id != null) cargarMensajes();
    }

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

        if (lblConvoCon != null) {
            lblConvoCon.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblConvoCon.setForeground(PRIMARY);
        }

        estiloPrimario(btnRefrescar, PRIMARY, FNT_BTN);
        estiloSecundario(btnCerrar, SECOND, PRIMARY, FNT_BTN);
        igualarTamanoBotones(140, 36, btnRefrescar, btnCerrar);

        if (listMensajes != null) {
            listMensajes.setFont(FNT_TXT);
            listMensajes.setBackground(Color.WHITE);
            listMensajes.setFixedCellHeight(48);
            listMensajes.setSelectionBackground(new Color(230, 240, 255));
            listMensajes.setSelectionForeground(Color.BLACK);
        }
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

    private void configurarEventos() {
        btnRefrescar.addActionListener(e -> cargarMensajes());
        btnCerrar.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });

        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void actualizarTitulo() {
        if (lblConvoCon == null) return;
        String etiqueta = (otroNombre != null && !otroNombre.isEmpty())
                ? otroNombre + " (" + otroId + ")"
                : (otroId != null ? otroId : "(sin usuario)");
        lblConvoCon.setText("Conversación con: " + etiqueta);
    }

    // ----- Carga de datos -----
    @SuppressWarnings("unchecked")
    private void cargarMensajes() {
        if (controlador == null) {
            JOptionPane.showMessageDialog(this, "No hay Controlador disponible.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (otroId == null || otroId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para ver la conversación.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultListModel<MensajeItem> model = (DefaultListModel<MensajeItem>) listMensajes.getModel();
        model.clear();

        try {
            // Intentamos obtener "yo" para saber cuál mensaje es propio
            Object yo = (controlador.getUsuario_login() != null) ? controlador.getUsuario_login() : null;
            String miId = (yo == null) ? null : getStringProperty(yo, "getId");

            // Pedimos los mensajes con el otro usuario
            List<?> mensajes = controlador.recibirMensajes(otroId);
            if (mensajes == null) mensajes = Collections.emptyList();

            for (Object m : mensajes) {
                // Flex: intentamos varios getters posibles por reflexión
                // remitente puede ser String o un Usuario con getId()
                Object remitenteObj = tryGetter(m, "getRemitente", "getRemitenteId", "getDe", "getFrom");
                String remitenteId = extractUserId(remitenteObj);

                Object textoObj = tryGetter(m, "getTexto", "getMensaje", "getBody", "getText");
                String texto = (textoObj == null) ? "" : String.valueOf(textoObj);

                boolean esMio = (miId != null && miId.equals(remitenteId));
                model.addElement(new MensajeItem(esMio, (esMio ? "Yo" : remitenteId), texto));
            }

            if (model.size() > 0) listMensajes.ensureIndexIsVisible(model.size() - 1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los mensajes:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ----- Helpers de reflexión -----
    private static Object tryGetter(Object obj, String... names) {
        if (obj == null) return null;
        for (String n : names) {
            try {
                Method m = obj.getClass().getMethod(n);
                return m.invoke(obj);
            } catch (Exception ignored) {}
        }
        return null;
    }

    private static String getStringProperty(Object obj, String getterName) {
        if (obj == null) return null;
        try {
            Method m = obj.getClass().getMethod(getterName);
            Object v = m.invoke(obj);
            return (v == null) ? null : String.valueOf(v);
        } catch (Exception ignored) { return null; }
    }

    private static String extractUserId(Object remitenteObj) {
        if (remitenteObj == null) return null;
        if (remitenteObj instanceof String s) return s;
        // intentar getId()
        String id = getStringProperty(remitenteObj, "getId");
        if (id != null) return id;
        return String.valueOf(remitenteObj);
    }

    // ----- Modelo visual de mensaje + renderer tipo burbuja -----
    private static class MensajeItem {
        final boolean esMio;
        final String quien; // "Yo" o id del otro
        final String texto;

        MensajeItem(boolean esMio, String quien, String texto) {
            this.esMio = esMio;
            this.quien = (quien == null ? "" : quien);
            this.texto = (texto == null ? "" : texto);
        }
    }

    private static class BubbleRenderer extends JPanel implements ListCellRenderer<MensajeItem> {
        private final JLabel lbl;

        BubbleRenderer() {
            setLayout(new BorderLayout());
            lbl = new JLabel();
            lbl.setOpaque(true);
            lbl.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            add(lbl, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends MensajeItem> list,
                                                      MensajeItem value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            String html = "<html><b>" + escape(value.quien) + ":</b> " + escape(value.texto) + "</html>";
            lbl.setText(html);

            // Colores y alineación
            Color mineBg  = new Color(220, 237, 200); // verde suave
            Color otherBg = new Color(236, 239, 241); // gris suave
            Color selBg   = new Color(204, 228, 255);

            lbl.setBackground(isSelected ? selBg : (value.esMio ? mineBg : otherBg));
            lbl.setForeground(Color.BLACK);

            // Alinear: mis mensajes a la derecha
            removeAll();
            if (value.esMio) {
                JPanel spacer = new JPanel(); spacer.setOpaque(false);
                add(spacer, BorderLayout.WEST);
                add(lbl, BorderLayout.EAST);
            } else {
                JPanel spacer = new JPanel(); spacer.setOpaque(false);
                add(spacer, BorderLayout.EAST);
                add(lbl, BorderLayout.WEST);
            }

            setBackground(Color.WHITE);
            return this;
        }

        private static String escape(String s) {
            return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
        }
    }
}
