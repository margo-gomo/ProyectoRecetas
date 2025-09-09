package Vista.Prescripción;

import javax.swing.*;
import java.awt.event.*;

public class DialogBuscarPaciente extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTable table1;

    public DialogBuscarPaciente() {
        setContentPane(contentPane);
        setModal(true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // Llama a onCancel() cuando se cierra con la cruz
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Llama a onCancel() al presionar ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
                                               public void actionPerformed(ActionEvent e) {
                                                   onCancel();
                                               }
                                           }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // Código a ejecutar al presionar OK
        dispose();
    }

    private void onCancel() {
        // Código a ejecutar al presionar Cancelar o cerrar
        dispose();
    }

    public static void main(String[] args) {
        DialogBuscarPaciente dialog = new DialogBuscarPaciente();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
