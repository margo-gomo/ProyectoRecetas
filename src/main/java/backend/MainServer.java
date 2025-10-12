package backend;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class MainServer {
    private int port = 5000;
    private ServerSocket serverSocket;
    private ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        new MainServer().start();
    }

    public void start() throws Exception {
        serverSocket = new ServerSocket(port);
        System.out.println("Servidor Backend escuchando en puerto " + port);

        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("Cliente conectado");
            pool.submit(new ClientHandler(client));
        }
    }
}

