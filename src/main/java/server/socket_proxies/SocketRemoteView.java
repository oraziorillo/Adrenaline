package server.socket_proxies;

import client.socket.proxies.AbstractSocketProxy;
import common.rmi_interfaces.RemoteView;

import java.io.IOException;
import java.net.Socket;

import static common.enums.RemoteViewEnum.*;

public class SocketRemoteView extends AbstractSocketProxy implements RemoteView {
   
   public SocketRemoteView(Socket socket) throws IOException {
      super( socket );
   }
   
   @Override
   public void showMessage(String message) {
      out.println( SHOW_MESSAGE );
      out.println( message );
      out.flush();
   }
}
