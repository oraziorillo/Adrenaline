package server;

import client.controller.socket.ClientSocketHandler;
import server.controller.LoginController;
import server.controller.socket.LoginSocketListener;
import server.controller.socket.ServerSocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LaunchServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Registry registry = LocateRegistry.createRegistry(9999);
        registry.rebind("LoginController", LoginController.getInstance());

        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(10000)) {
            System.out.println("Server listening");
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                pool.submit(new ServerSocketHandler(clientSocket));
            }

        }

    }
}
