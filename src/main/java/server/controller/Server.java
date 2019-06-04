
package server.controller;

import common.RemoteLoginController;
import common.RemotePlayer;
import server.controller.socket_connection.LoginSocketListener;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server-side main class
 */
public class Server {

   public static final WaitingRoom waitingRoom = WaitingRoom.getInstance();
   public static final RemoteLoginController loginController = LoginController.instance;

    public static void main(String[] args) throws Exception {
        //TODO: inizializza rmi
        try(ServerSocket serverSocket = new ServerSocket( 10000 )) {
           while (!serverSocket.isClosed()) {
              try (Socket clientSocket = serverSocket.accept()) {
                 new Thread( new LoginSocketListener( clientSocket ) ).start();
              }
           }
        }
    }
}