package client.controller.socket;

import common.enums.interfaces_names.RemoteViewEnum;
import common.remote_interfaces.RemoteView;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ViewSocketListener implements Runnable {


   private final Socket server;
   private final Scanner in;
   private final RemoteView view;


   public ViewSocketListener(Socket server, RemoteView view) {
      this.server = server;
      this.view = view;
      try {
         in = new Scanner( server.getInputStream() );
      } catch ( IOException e ) {
         throw new IllegalStateException("Invalid socket");
      }
   }


   @Override
   public void run() {
       while (!server.isClosed()){
          RemoteViewEnum cmd = RemoteViewEnum.valueOf( in.next() );
          switch (cmd){
             case ACK:
                try {
                   view.ack( in.next() );
                } catch ( IOException e ) {
                   e.printStackTrace();
                }
                break;
          }
       }
   }
}

