package server.controller;

import common.RemoteLoginController;
import common.RemotePlayer;
import server.model.Game;

import java.io.*;
import java.rmi.RemoteException;
import java.util.UUID;

public class LoginController implements RemoteLoginController {
   public static final LoginController instance = new LoginController();
   private LoginController(){}
   @Override
   public UUID register(String username) throws RemoteException {
      UUID token = UUID.randomUUID();
      players.put( token, new Player( username, token ) );
      return token;
   }
   
   @Override
   public boolean containsPlayer(UUID token) {
      return players.containsKey( token );
   }
   
   @Override
   public RemotePlayer login(UUID fromString) {
      return players.get( fromString );
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
