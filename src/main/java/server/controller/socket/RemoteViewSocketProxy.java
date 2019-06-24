package server.controller.socket;

import client.controller.socket.AbstractSocketProxy;
import common.remote_interfaces.RemoteView;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.Socket;

import static common.enums.interfaces_names.RemoteViewEnum.ACK;

public class RemoteViewSocketProxy extends AbstractSocketProxy implements RemoteView {
   
   RemoteViewSocketProxy(Socket socket) throws IOException {
      super( socket );
   }
   
   @Override
   public void ack(String message) {
      out.println(ACK + "," + message.replaceAll( System.lineSeparator(),"," ));
      out.flush();
   }

   @Override
   public PropertyChangeListener getListener() {
      //TODO
      return null;
   }

}
