package Modelo.Proxy;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class SocketProxy {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Consumer<String> onError = msg -> System.err.println("[UI] Error: " + msg);
    private Consumer<String> onMessage = msg -> System.out.println("[UI] MSG: " + msg);

    public void setOnError(Consumer<String> onError) { this.onError = (onError != null) ? onError : this.onError; }
    public void setOnMessage(Consumer<String> onMessage) { this.onMessage = (onMessage != null) ? onMessage : this.onMessage; }

    public void conectar(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

        Thread t = new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("[UI] RX: " + line);
                    try { onMessage.accept(line); } catch (Exception ex) { onError.accept(ex.getMessage()); }
                }
                System.out.println("[UI] Servidor cerró la conexión");
            } catch (Exception ex) {
                onError.accept(ex.getMessage());
            }
        }, "proxy-listener");
        t.setDaemon(true);
        t.start();
    }

    public void enviarLinea(String json) {
        if (out == null) { onError.accept("Proxy no conectado"); return; }
        out.println(json);
    }
    public void cerrar() throws IOException {
        if (socket != null && !socket.isClosed()) socket.close();
    }
}

