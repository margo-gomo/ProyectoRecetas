package Vista.Prescripción;

import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JCalendar;
import Adaptador.LocalDateAdapter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class DialogSeleccionarFecha extends JDialog {

    // ------------------------------------------------------------------------------------------
    // -------------------------------- ATRIBUTOS DE LA VISTA -----------------------------------
    // ------------------------------------------------------------------------------------------

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton hoyButton;
    private JButton button2; // botón que abre JCalendar
    private JFormattedTextField formattedTextField1;

    private final LocalDateAdapter adapter = new LocalDateAdapter();

    // ------------------------------------------------------------------------------------------
    // ------------------------------------- CONSTRUCTOR ----------------------------------------
    // ------------------------------------------------------------------------------------------

    public DialogSeleccionarFecha() {
        try { FlatLightLaf.setup(); } catch (Exception ignored) {}

        setTitle("Seleccionar fecha");
        setModal(true);
        setContentPane(contentPane);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(true);

        aplicarEstilos();

        if (getRootPane() != null && buttonOK != null)
            getRootPane().setDefaultButton(buttonOK);

        if (buttonOK != null)     buttonOK.addActionListener(e -> dispose());
        if (buttonCancel != null) buttonCancel.addActionListener(e -> dispose());

        if (hoyButton != null) {
            hoyButton.addActionListener(e -> {
                try {
                    if (formattedTextField1 != null)
                        formattedTextField1.setText(adapter.marshal(LocalDate.now()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        if (button2 != null) {
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JCalendar calendar = new JCalendar();
                    int result = JOptionPane.showConfirmDialog(
                            DialogSeleccionarFecha.this,
                            calendar,
                            "Seleccione una fecha",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );
                    if (result == JOptionPane.OK_OPTION) {
                        LocalDate fecha = calendar.getDate().toInstant()
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDate();
                        try {
                            if (formattedTextField1 != null)
                                formattedTextField1.setText(adapter.marshal(fecha));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }

        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { dispose(); }
        });

        if (contentPane != null) {
            contentPane.registerKeyboardAction(e -> dispose(),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                    JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        }
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------- ESTILOS / LOOK & FEEL ------------------------------------
    // ------------------------------------------------------------------------------------------

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

        if (formattedTextField1 != null) {
            formattedTextField1.setFont(FNT_TXT);
            formattedTextField1.setForeground(Color.BLACK);
            formattedTextField1.setBackground(Color.WHITE);
            formattedTextField1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        }

        JButton[] primarios = { buttonOK };
        JButton[] secundarios = { buttonCancel, hoyButton, button2 };

        for (JButton b : primarios)     if (b != null) estiloPrimario(b, PRIMARY, FNT_BTN);
        for (JButton b : secundarios)   if (b != null) estiloSecundario(b, SECOND, PRIMARY, FNT_BTN);

        igualarTamanoBotones(150, 36, buttonOK, buttonCancel, hoyButton, button2);
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
        b.putClientProperty("JButton.buttonType", "roundRect"); // hover FlatLaf
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
    // -------------------------------------- UTILIDADES ----------------------------------------
    // ------------------------------------------------------------------------------------------

    /** Devuelve la fecha actualmente mostrada en el campo, o null si no es válida. */
    public LocalDate getFechaSeleccionada() {
        try {
            String val = formattedTextField1 != null ? formattedTextField1.getText().trim() : "";
            if (val.isEmpty()) return null;
            return adapter.unmarshal(val);
        } catch (Exception e) {
            return null;
        }
    }

    // ------------------------------------------------------------------------------------------
    // ------------------------------------------ MAIN ------------------------------------------
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DialogSeleccionarFecha dialog = new DialogSeleccionarFecha();
            dialog.setVisible(true);
            System.exit(0);
        });
    }
}
