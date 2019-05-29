package controller;

import common.PlayerController;
import common.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerControllerImpl implements PlayerController {
   private Map<UUID, Player> players = new HashMap<>();
   @Override
   public UUID register(String username) {
      UUID token;
      do{
         token=UUID.randomUUID();
      }while (players.containsKey( token ));
      Player p = new Player( username, token );
      players.put( token,p );
      return token;
   }
   
   @Override
   public Player login(UUID token) {
      return players.get( token );
   }
}
