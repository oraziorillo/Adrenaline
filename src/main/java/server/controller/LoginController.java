package server.controller;

import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;
import server.exceptions.PlayerAlreadyLoggedInException;
import server.model.Database;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;


public class LoginController extends UnicastRemoteObject implements RemoteLoginController {

   private static LoginController instance;

   private transient Database database = Database.getInstance();

   private transient Lobby newLobby;


   private LoginController() throws IOException {
      super();
   }


   public static LoginController getInstance() throws IOException {
      if (instance == null) {
         instance = new LoginController();
      }
      return instance;
   }


   @Override
   public synchronized UUID register(String username, RemoteView view) throws IOException {
      if (database.isRegistered(username)){
         view.ack("This username is already used");
         return null;
      }
      UUID token = UUID.randomUUID();
      database.registerPlayer(token, username, new Player(token));
      database.registerView(token, view);
      return token;
   }


   /**
    * returns the player with the corresponding token if registered, null else
    *
    * @param token the token of the player
    * @return the player if registered, null else
    * @throws RemoteException IDK, rmi stuff
    */
   @Override
   public synchronized RemotePlayer login(UUID token) throws IOException {
      database.getView(token).ack("Logging in as @" + database.getUsername(token));
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
   }
}

