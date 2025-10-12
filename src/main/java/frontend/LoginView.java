package frontend;

import Common.Message;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame implements BackendProxy.Listener {
    private BackendProxy proxy;
    private JTextField txtId = new JTextField(10);
    private JPasswordField txtClave = new JPasswordField(10);

    public LoginView() throws Exception {
        proxy = new BackendProxy("localhost", 5000, this);
        setTitle("Login");
        setLayout(new GridLayout(3, 2));
        add(new JLabel("ID:"));
        add(txtId);
        add(new JLabel("Clave:"));
        add(txtClave);
        JButton btn = new JButton("Ingresar");
        add(btn);
        btn.addActionListener(e -> enviarLogin());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void enviarLogin() {
        try {
            Message m = new Message();
            m.type = "LOGIN_REQUEST";
            m.payload = new Message.Payload();
            m.payload.id = txtId.getText();
            m.payload.clave = new String(txtClave.getPassword());
            proxy.send(m);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error enviando login");
        }
    }

    @Override
    public void onMessage(Message msg) {
        if ("LOGIN_RESPONSE".equals(msg.type)) {
            JOptionPane.showMessageDialog(this, msg.payload.msg);
        }
    }
}

