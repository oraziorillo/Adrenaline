package server;

import server.controller.LoginController;
import server.controller.socket.ServerSocketHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LaunchServer {

    public static void main(String[] args) throws IOException {
    
        ServerPropertyLoader propertyLoader = ServerPropertyLoader.getInstance();
        
        Registry registry = LocateRegistry.createRegistry(propertyLoader.getRmiPort());
        registry.rebind("LoginController", LoginController.getInstance());

        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(propertyLoader.getSocketPort())) {
            System.out.println("Server listening");
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                pool.submit(new ServerSocketHandler(clientSocket));
            }
        }
    }
}
