package controller;

import common.PlayerController;
import controller.socket_connection.LoginSocketListener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server-side main class
 */
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
   
   /**
    * Reads a json from a file located on the specified path
    * @param filename the path to the file to be read
    * @return The object mapped in the Json file
    * @throws IOException If file isn't readable
    * @throws ParseException If file does not represent a valid Json object
    */
    public static Object readJson(String filename) throws IOException, ParseException {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }
}
