package server;

import server.controller.Player;
import server.model.Game;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.UUID;

public class LoginController extends UnicastRemoteObject implements RemoteLoginController {

   private static LoginController instance;

   private HashMap<UUID, Player> players = new HashMap<>();
   private HashMap<UUID, String> gamePaths = new HashMap<>();


   private LoginController() throws RemoteException {
      super();
   }


   static LoginController getInstance() throws RemoteException {
      if (instance == null) {
         instance = new LoginController();
      }
      return instance;
   }


   @Override
   public UUID register(String username) throws RemoteException {
      UUID token = UUID.randomUUID();
      players.put(token, new Player(username, token));
      return token;
   }

   
   @Override
   public synchronized boolean containsPlayer(UUID token) {
      return players.containsKey(token);
   }


   @Override
   public synchronized RemotePlayer login(UUID token) {
      return players.get(token);
   }


   @Override
   public synchronized void continueOldGame(UUID gameId) {
      //TODO
   }


   @Override
   public synchronized void saveGame(Game game) {
      //TODO
   }
}
