package Modelo.Backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ServidorBackend {
    public static void main(String[] args) {
        int port = 5050;
        System.out.println("[Backend] Iniciando…");
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("[Backend] Escuchando en puerto " + port);
            while (true) {
                Socket s = server.accept();
                System.out.println("[Backend] Cliente conectado: " + s.getRemoteSocketAddress());
                new Thread(() -> {
                    try {
                        new ClientHandler(s).run();
                    } catch (SQLException e) {
                        System.out.println("[Backend] Error al crear ClientHandler: " + e.getMessage());
                        try { s.close(); } catch (IOException ignored) {}
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println("[Backend] Error servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
