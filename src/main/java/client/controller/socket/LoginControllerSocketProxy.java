package client.controller.socket;

import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.enums.interfaces_names.SocketLoginEnum.*;

public class LoginControllerSocketProxy extends AbstractSocketProxy implements RemoteLoginController {


   public LoginControllerSocketProxy(String host, int port) throws IOException {
      super( host, port );
   }

   
   public LoginControllerSocketProxy(Socket socket) throws IOException {
      super(socket);
   }

   
   @Override
   public UUID register(String username) throws IOException {
      out.println( REGISTER + "," +username);
      out.flush();
      return UUID.fromString( in.readLine() );
   }


   @Override
   public RemotePlayer login(UUID token, RemoteView view) throws IOException {
      RemotePlayer player = new PlayerSocketProxy( socket );
      out.println( LOGIN +"," +token );
      out.flush();
      return player;
   }


   @Override
   public void joinLobby(UUID token) {
      out.println( JOIN_LOBBY +"," +token );
      out.flush();
   }
}
