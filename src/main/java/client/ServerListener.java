package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerListener implements Runnable {
   
   private final Socket server;
   private final Scanner in;
   
   ServerListener(Socket server) {
      this.server = server;
      try {
         in = new Scanner( server.getInputStream() );
      } catch ( IOException e ) {
         throw new IllegalStateException("Invalid socket");
      }
   }
   @Override
   public void run() {
      while (!server.isClosed()){
         System.out.println(in.next());
      }
   }
}

