package Vista.Prescripción;

import Adaptador.LocalDateAdapter;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class DialogSeleccionarFecha extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton hoyButton;
    private JButton button2;
    private JFormattedTextField formattedTextField1;

    private final LocalDateAdapter adapter = new LocalDateAdapter();

    public DialogSeleccionarFecha() {
        setContentPane(contentPane);
        setModal(true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(true);
        getRootPane().setDefaultButton(buttonOK);

        aplicarEstilos();

        buttonOK.addActionListener(e -> dispose());
        buttonCancel.addActionListener(e -> dispose());

        hoyButton.addActionListener(e -> {
            try {
                formattedTextField1.setText(adapter.marshal(LocalDate.now()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

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
                        formattedTextField1.setText(adapter.marshal(fecha));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    private void aplicarEstilos() {
        JButton[] botones = {buttonOK, buttonCancel, hoyButton, button2};
        for (JButton b : botones) {
            if (b != null) {
                b.setFocusPainted(false);
                b.setBorderPainted(false);
                b.setBackground(new Color(66, 133, 244));
                b.setForeground(Color.WHITE);
                b.setFont(new Font("Segoe UI", Font.BOLD, 13));
                b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        if (formattedTextField1 != null) {
            formattedTextField1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            formattedTextField1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        }

        if (contentPane != null) contentPane.setBackground(Color.WHITE);
    }

    public static void main(String[] args) {
        DialogSeleccionarFecha dialog = new DialogSeleccionarFecha();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
