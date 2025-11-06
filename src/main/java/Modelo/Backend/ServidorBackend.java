package Modelo.Backend;

import java.io.IOException;
import java.net.*;

public class ServidorBackend {
    public static final OnlineRegistry REGISTRY = new OnlineRegistry(); // ya lo tienes
    public static final ConnectionRegistry CONNECTIONS = new ConnectionRegistry(); // nuevo


    public static void main(String[] args) {
        int port = 5050;
        System.out.println("[Backend] Iniciando…");
        try (ServerSocket server = new ServerSocket(
                port, 0, InetAddress.getByName("0.0.0.0"))) {
            System.out.println("[Backend] Escuchando en puerto " + port);
            while (true) {
                Socket s = server.accept();
                System.out.println("[Backend] Cliente conectado: " + s.getRemoteSocketAddress());
                new Thread(() -> {
                    try {
                        new ClientHandler(s, REGISTRY).run();
                    } catch (Exception e) {
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
