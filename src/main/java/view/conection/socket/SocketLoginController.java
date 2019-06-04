package view.conection.socket;

import common.RemoteLoginController;
import common.RemotePlayer;
import server.model.Game;

import java.io.IOException;
import java.util.UUID;

import static server.enums.SocketLoginEnum.*;

public class SocketLoginController extends AbstractSocketProxy implements RemoteLoginController {
   public SocketLoginController() throws IOException {
   }
   
   @Override
   public UUID register(String username) throws IOException {
      out.println( REGISTER );
      out.println( username );
      out.flush();
      return UUID.fromString( in.readLine() );
   }
   
   @Override
   public boolean containsPlayer(UUID token) throws IOException {
      out.println( CONTAINS_PLAYER );
      out.println( token.toString() );
      out.flush();
      return Boolean.getBoolean( in.readLine() );
   }
   
   @Override
   public RemotePlayer login(UUID fromString) throws IOException {
      out.println( LOGIN );
      out.println( fromString );
      String username = in.readLine();
      return new SocketPlayer( username,fromString );
      
   }
   
   @Override
   public void enterSavedGame(UUID gameId) throws IOException, ClassNotFoundException {
      //TODO
   }
   
   @Override
   public void saveGame(Game game) {
      //TODO
   }
}
