package backend;
import java.net.*;
import java.io.*;
import jakarta.xml.bind.*;
import Common.Message;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void send(String xml) throws IOException {
        out.write(xml + "\n");
        out.flush();
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                Message msg = XmlUtil.fromXml(line);
                switch (msg.type) {
                    case "LOGIN_REQUEST":
                        procesarLogin(msg.payload);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Cliente desconectado");
        }
    }

    private void procesarLogin(Message.Payload p) throws Exception {
        UserDAO dao = new UserDAO();
        boolean ok = dao.validarUsuario(p.id, p.clave);
        Message resp = new Message();
        resp.type = "LOGIN_RESPONSE";
        resp.payload = new Message.Payload();
        resp.payload.ok = ok;
        resp.payload.msg = ok ? "Bienvenido " + p.id : "Credenciales incorrectas";
        send(XmlUtil.toXml(resp));
    }
}

