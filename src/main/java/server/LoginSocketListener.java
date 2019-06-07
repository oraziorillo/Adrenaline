package server;


import server.controller.Player;

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

   LoginSocketListener(Socket s) throws IOException {
      this.client = s;
      this.in = new Scanner(s.getInputStream());
      this.out = new PrintWriter(s.getOutputStream());
   }

   @Override
   public void run() {
      while (!client.isClosed()) {
         try {

            switch (in.next()) {
               case "registerWith":
                  out.println(LoginController.getInstance().register(in.next()));
                  out.flush();
                  break;

               case "login":
                  this.player = (Player) LoginController.getInstance().login(UUID.fromString(in.next()));
                  out.println(player.getUsername());
                  out.flush();
                  break;

               /*
               case "join_new_game":
                  Lobby.getInstance().addPlayer(this.player);
                  out.println("Entered a game. There are " + (Lobby.getInstance().size() - 1) + " other players");
                  break;

               case "load_game":
                  //TODO: permanenza
                */

               default:
                  out.println("ILLEGAL COMMAND");
                  out.flush();
            }

         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}
