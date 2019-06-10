package server;

import common.rmi_interfaces.RemoteLoginController;
import server.controller.Player;
import common.rmi_interfaces.RemotePlayer;
import server.exceptions.PlayerAlreadyLoggedInException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.UUID;

public class LoginController extends UnicastRemoteObject implements RemoteLoginController {

   private static LoginController instance;

   private Lobby newLobby;

   /**
    * hash map containing as key player's UUID and as value the respective player instance (if present)
    */
   private HashMap<UUID, Player> players = new HashMap<>();

   /**
    * hash map containing as key game's UUID and as value the started game instance (if present)
    */
   private HashMap<UUID, Lobby> lobbies = new HashMap<>();

   /**
    * hash map containing as key player's UUID and as value the started game's UUID (if present)
    */
   private HashMap<UUID, UUID> startedGames = new HashMap<>();


   private LoginController() throws RemoteException {
      super();
      //TODO: inizializa variabili
   }


   static LoginController getInstance() throws RemoteException {
      if (instance == null) {
         instance = new LoginController();
      }
      return instance;
   }


   @Override
   public synchronized UUID register(String username) throws RemoteException {
      //TODO controllare se non è già registrato??
      UUID token = UUID.randomUUID();
      players.put(token, new Player(username, token));
      //TODO: sovrascrivi file
      return token;
   }
   
   /**
    * returns the player with the curresponding token if registered, null else
    * @param token the token of the player
    * @return the player if registered, null else
    * @throws RemoteException IDK, rmi stuff
    */
   @Override
   public synchronized RemotePlayer login(UUID token) throws RemoteException {
      return players.get( token );
   }
   
   /**
    * add the player corresponding to the token to a lobby
    * @param token the unique identifier for a player
    */
   public void joinLobby(UUID token) throws PlayerAlreadyLoggedInException, RemoteException {
      if (players.containsKey(token)) {
         Lobby currLobby;
         //if this player is already present in a started game, add it to the started game
         if (startedGames.containsKey(token)) {
            if (lobbies.containsKey(startedGames.get(token)))
               currLobby = lobbies.get(startedGames.get(token));
            else {
               //if the server had crushed and the lobby was gone create a new one
               currLobby = new Lobby(startedGames.get(token));
               lobbies.put(startedGames.get(token), currLobby);
            }
            currLobby.addPlayer(players.get(token));
            //otherwise it is added to a new game
         } else {
            if (newLobby == null)
               newLobby = new Lobby();
            newLobby.addPlayer(players.get(token));
         }
         players.get( token ).getRemoteView().showMessage( "Connected" );
      }
   }


   void addStartedGame(Lobby lobby){
      if (!lobby.isOld()) {
         UUID gameUUID = lobby.getGameUUID();
         lobby.getPlayers().forEach(p -> startedGames.put(p.getToken(), gameUUID));
         //TODO: sovrascrivere il file
         newLobby = null;
      }
   }


   void removeStartedGame(UUID gameUUID){
      startedGames.forEach((k, v) -> {
         if(v.equals(gameUUID))
            startedGames.remove(k);
      });
      lobbies.remove(gameUUID);
      //TODO: sovrascrivere il file
   }
}

