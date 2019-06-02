/*
package server.controller.socket_connection;

import server.controller.player.Player;
import server.controller.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class LoginSocketListener implements Runnable {
   
   private final Socket client;
   private final Scanner in;
   private final PrintWriter out;
   private Player player;
   
   public LoginSocketListener(Socket s) throws IOException {
      this.client = s;
      this.in = new Scanner( s.getInputStream() );
      this.out = new PrintWriter( s.getOutputStream() );
   }
   
   @Override
   public void run() {
      while (!client.isClosed()){
         switch (in.next()){
            case "register":
               out.println( Server.playerController.register( in.next() ) );
               out.flush();
               break;
            case "getPlayer":
               //TODO: il server.controller sarà spostato in player, che non viene trasferito interamente a solo l'UUID
               //this.player = Server.playerController.getPlayer( UUID.fromString( in.next() ) );
               out.println( player.getUsername() );
               out.flush();
               break;
            case "join_game":
               Server.waitingRoom.addPlayer( this.player );
               out.println( "Entered a game. There are "+(Server.waitingRoom.size()-1)+" other players" );
            case "load_game":
               //TODO
               
         }
      }
   }
}
*/
