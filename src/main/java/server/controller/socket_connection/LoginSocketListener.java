package server.controller.socket_connection;

import server.controller.Player;
import server.controller.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
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
               try {
                  out.println( Server.loginController.register( in.next() ) );
               } catch ( IOException e ) {
                  throw new IllegalStateException("Cannot create a player on server. Something went TERRIBLY wrong");
               }
               out.flush();
               break;
            case "login":
               try {
                  this.player = ( Player ) Server.loginController.login( UUID.fromString( in.next() ));
               } catch ( IOException e ) {
                  e.printStackTrace();
               }
               out.println( player.getUsername() );
               out.flush();
               break;
            case "join_new_game":
               Server.waitingRoom.addPlayer( this.player );
               out.println( "Entered a game. There are "+(Server.waitingRoom.size()-1)+" other players" );
               break;
            case "load_game": case "save_game":
               //TODO: permanenza
               default:
                  out.println( "ILLEGAL COMMAND" );
                  out.flush();
         }
      }
   }
}
