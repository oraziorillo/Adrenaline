/*
package server.controller;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server-side main class
 *
public class Server {

   public static final WaitingRoom waitingRoom = WaitingRoom.getInstance();
   public static final PlayerController playerController = new PlayerControllerImpl();

    public static void main(String[] args) throws Exception {
        //TODO: inizializza rmi
        try(ServerSocket serverSocket = new ServerSocket( 10000 )) {
           while (!serverSocket.isClosed()) {
              try (Socket clientSocket = serverSocket.accept()) {
                 clientSocket.getOutputStream().flush();
                 new Thread( new LoginSocketListener( clientSocket ) ).start();
              }
           }
        }
    }
}
*/