package server.controller;

import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;
import server.exceptions.PlayerAlreadyLoggedInException;
import server.database.DatabaseHandler;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;


public class LoginController extends UnicastRemoteObject implements RemoteLoginController {

   private static LoginController instance;

   private transient DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

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
      if (databaseHandler.isRegistered(username)){
         view.ack("This username is already used");
         return null;
      }
      UUID token = UUID.randomUUID();
      databaseHandler.registerPlayer(token, username, new Player(token));
      databaseHandler.registerView(token, view);
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
      databaseHandler.getView(token).ack("Logging in as @" + databaseHandler.getUsername(token));
      return databaseHandler.getPlayer(token);
   }


   /**
    * add the player corresponding to the token to a lobby
    *
    * @param token the unique identifier for a player
    */
   public void joinLobby(UUID token) throws PlayerAlreadyLoggedInException {
      if (databaseHandler.isRegistered(token)) {
         Lobby currLobby;
         if (databaseHandler.hasPendentGame(token)) {
            //if this player is already present in a started game, add it to the started game
            currLobby = databaseHandler.getMyOldLobby(token);
            currLobby.addPlayer(databaseHandler.getPlayer(token));
         } else {
            //otherwise it is added to a new game
            if (newLobby == null)
               newLobby = new Lobby();
            newLobby.addPlayer(databaseHandler.getPlayer(token));
         }
      }
   }
}

