package server.controller.socket;

import client.controller.socket.AbstractSocketProxy;
import common.remote_interfaces.RemoteView;

import java.io.IOException;
import java.net.Socket;

import static common.enums.RemoteViewEnum.*;

public class RemoteViewSocketProxy extends AbstractSocketProxy implements RemoteView {
   
   public RemoteViewSocketProxy(Socket socket) throws IOException {
      super( socket );
   }
   
   @Override
   public void ack(String message) {
      out.println(ACK);
      out.println( message );
      out.flush();
   }
}
