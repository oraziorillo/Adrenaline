package client.controller;

import client.AbstractSocketProxy;
import server.RemoteLoginController;
import server.controller.RemotePlayer;
import java.io.IOException;
import java.util.UUID;

import static server.enums.SocketLoginEnum.*;

public class SocketLoginController extends AbstractSocketProxy implements RemoteLoginController {

   public SocketLoginController() throws IOException {}

   @Override
   public UUID register(String username) throws IOException {
      out.println( REGISTER );
      out.println( username );
      out.flush();
      return UUID.fromString( in.readLine() );
   }


   @Override
   public RemotePlayer login(UUID token) throws IOException {
      out.println( LOGIN );
      out.println( token );
      String username = in.readLine();
      return new SocketPlayer( username,token );
   }

}
