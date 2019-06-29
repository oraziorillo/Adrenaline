package server.controller;

import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;
import server.database.DatabaseHandler;
import server.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;

public class LoginController extends UnicastRemoteObject implements RemoteLoginController {

   private static LoginController instance;

   private transient DatabaseHandler databaseHandler;

   private transient List<Lobby> lobbies;

   private transient Lobby newLobby;


   private LoginController() throws IOException {
      super();
      databaseHandler = DatabaseHandler.getInstance();
      this.lobbies = databaseHandler.getLobbies();
      this.newLobby = new Lobby();
   }


   public static LoginController getInstance() throws IOException {
      if (instance == null) {
         instance = new LoginController();
      }
      return instance;
   }


   @Override
   public synchronized UUID register(String username, RemoteView view) throws RemoteException {
      if (databaseHandler.isRegistered(username)){
         view.ack("This username is already used");
         return null;
      }
      UUID token = UUID.randomUUID();
      databaseHandler.registerPlayer(token, username, new Player(token));
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
   public synchronized RemotePlayer login(UUID token, RemoteView view) throws RemoteException {
      view.ack("Logging in as @" + databaseHandler.getUsername(token));
      databaseHandler.getPlayer(token).setView(view);    //PER IL DEBUG
      //databaseHandler.registerView( token,view );      NON PIÙ NECESSARIO
      return databaseHandler.getPlayer(token);
   }


   /**
    * add the player corresponding to the token to a lobby
    *
    * @param token the unique identifier for a player
    */
   public synchronized void joinLobby(UUID token) throws RemoteException, PlayerAlreadyLoggedInException {
      if (databaseHandler.isRegistered(token)) {
         if (databaseHandler.hasPendentGame(token)) {

            Lobby lobby;
            UUID incompleteGame = databaseHandler.getGameUUID(token);
            lobby = lobbies
                    .stream()
                    .filter(l -> l.getGameUUID() == incompleteGame)
                    .findFirst()
                    .orElse(null);
            if (lobby != null)
               lobby.addPlayer(databaseHandler.getPlayer(token));
            else
               throw new IllegalStateException("Old " + databaseHandler.getPlayer(token) + "'s lobby was not restored successfully");

         } else {

            //otherwise it is added to a new game
            if (!newLobby.isAvailable()) {
               lobbies.add(newLobby);
               newLobby = new Lobby();

            }
            newLobby.addPlayer(databaseHandler.getPlayer(token));
         }
      }
   }
}

