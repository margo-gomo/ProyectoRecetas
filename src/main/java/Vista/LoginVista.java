package Vista;
import javax.swing.*;
import java.awt.*;

public class LoginVista extends JFrame {
    public JTextField txtId;
    public JPasswordField txtClave;
    public JButton btnIngresar;

    public LoginVista() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(30, 30, 80, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(100, 30, 150, 25);
        add(txtId);

        JLabel lblClave = new JLabel("Clave:");
        lblClave.setBounds(30, 70, 80, 25);
        add(lblClave);

        txtClave = new JPasswordField();
        txtClave.setBounds(100, 70, 150, 25);
        add(txtClave);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(100, 110, 150, 25);
        add(btnIngresar);
    }
}