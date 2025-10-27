/*package frontend;

import java.net.*;
import java.io.*;
import jakarta.xml.bind.*;
import Common.Message;
import backend.XmlUtil;

public class BackendProxy {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public interface Listener {
        void onMessage(Message msg);
    }
    private Listener listener;

    public BackendProxy(String host, int port, Listener listener) throws IOException {
        this.listener = listener;
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        new Thread(this::listen).start();
    }

    private void listen() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                Message msg = XmlUtil.fromXml(line);
                listener.onMessage(msg);
            }
        } catch (Exception e) {
            System.out.println("Desconectado del servidor");
        }
    }

    public void send(Message msg) throws Exception {
        out.write(XmlUtil.toXml(msg) + "\n");
        out.flush();
    }
}*/

