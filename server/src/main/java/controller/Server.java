package controller;

import common.player.Player;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Server {
    public static void main(String[] args) throws Exception {
        WaitingRoom waitingRoom = new WaitingRoom();
        HashMap<UUID, Player> players = new HashMap<>();
        //TODO: inizializza rmi
        ServerSocket serverSocket = new ServerSocket( 10000 );
        while (!serverSocket.isClosed()){
            try (Socket clientSocket = serverSocket.accept()){
                Scanner in = new Scanner( clientSocket.getInputStream() );
               PrintWriter out = new PrintWriter( clientSocket.getOutputStream() );
                UUID token;
                Player player;
                switch (in.next()){ //TODO: rendi register e login metodi del controller
                    case "register":
                        token = UUID.randomUUID();
                        String username = in.next();
                        out.write( token.toString() );
                        out.flush();
                        player = new Player( username, token );
                        players.put( token, player );
                   case "login": //il break è omesso volontariamente: con la registrazione c'è login automatico
                      token = UUID.fromString( in.next() );
                      player = players.get( token );
                      waitingRoom.addPlayer( player );
                      default:
                }
                
            }
        }
    }

    public static Object readJson(String filename) throws IOException, ParseException {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }
}
