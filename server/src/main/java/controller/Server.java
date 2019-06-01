/*
package controller;

import common.PlayerController;
import controller.socket_connection.LoginSocketListener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

   public static final WaitingRoom waitingRoom = WaitingRoom.getInstance();
   public static final PlayerController playerController = new PlayerControllerImpl();

    public static void main(String[] args) throws Exception {
        //TODO: inizializza rmi
        ServerSocket serverSocket = new ServerSocket( 10000 );
        while (!serverSocket.isClosed()){
            try (Socket clientSocket = serverSocket.accept()){
               clientSocket.getOutputStream().flush();
               new Thread(new LoginSocketListener(clientSocket) ).start();
            }
        }
    }

    public static Object readJson(String filename) throws IOException, ParseException {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }
}
*/
