package server.controller;

import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;
import server.exceptions.PlayerAlreadyLoggedInException;
import server.exceptions.PlayerAlreadyRegisteredException;
import server.model.Database;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;


public class LoginController extends UnicastRemoteObject implements RemoteLoginController {

   private static LoginController instance;

   private Database database = null;//Database.getInstance();

   private Lobby newLobby;


   private LoginController() throws IOException, ClassNotFoundException {
      super();
   }


   public static LoginController getInstance() throws IOException, ClassNotFoundException {
      if (instance == null) {
         instance = new LoginController();
      }
      return instance;
   }


   @Override
   public synchronized UUID register(String username) throws IOException {
      /*if (database.isRegistered(username)){
         throw new PlayerAlreadyRegisteredException("This username is already used");
      }*/
      UUID token = UUID.randomUUID();
      database.registerPlayer(token, username, new Player(token));
      return token;
   }


   /**
    * returns the player with the curresponding token if registered, null else
    *
    * @param token the token of the player
    * @return the player if registered, null else
    * @throws RemoteException IDK, rmi stuff
    */
   @Override
   public synchronized RemotePlayer login(UUID token, RemoteView view) throws IOException {
      view.ack("Logging in as @" + database.getUsername(token));
      database.registerView(token, view);
      return database.getPlayer(token);
   }


   /**
    * add the player corresponding to the token to a lobby
    *
    * @param token the unique identifier for a player
    */
   public void joinLobby(UUID token) throws PlayerAlreadyLoggedInException {
      if (database.isRegistered(token)) {
         Lobby currLobby;
         if (database.hasAGameToFinish(token)) {
            //if this player is already present in a started game, add it to the started game
            currLobby = database.getLobby(token);
            currLobby.addPlayer(database.getPlayer(token));
         } else {
            //otherwise it is added to a new game
            if (newLobby == null)
               newLobby = new Lobby();
            newLobby.addPlayer(database.getPlayer(token));
         }
      }
      try {
         database.getView( token ).ack( "Joined game" );
      } catch ( IOException e ) {   //If player is unreachable, kick him
         database.getPlayer( token ).quit();
      }
   }
}

