package client.socket.proxies;

import common.rmi_interfaces.RemoteLoginController;
import common.rmi_interfaces.RemotePlayer;
import server.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.enums.SocketLoginEnum.*;

public class SocketLoginController extends AbstractSocketProxy implements RemoteLoginController {
   
   public SocketLoginController(String host, int port) throws IOException {
      super( host, port );
   }
   
   public SocketLoginController(Socket socket) throws IOException {
      super(socket);
   }
   
   @Override
   public UUID register(String username) throws IOException {
      out.println( REGISTER );
      out.println( username );
      out.flush();
      return UUID.fromString( in.readLine() );
   }


   @Override
   public RemotePlayer login(UUID token) throws IOException {
      RemotePlayer player = new SocketPlayer( socket );
      out.println( LOGIN );
      out.println( token );
      out.flush();
      return player;
   }
   
   @Override
   public void joinLobby(UUID token) throws IOException, PlayerAlreadyLoggedInException {
      out.println( JOIN_LOBBY );
      out.println( token );
      out.flush();
   }
   
}
