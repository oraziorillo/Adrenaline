package server;

import common.PropertyLoader;

import java.io.IOException;

public class ServerPropertyLoader extends PropertyLoader {
   private static ServerPropertyLoader instance;
   private ServerPropertyLoader() {
      super("server.lobby.timer = 10","server.player.timer = 120");
   }
   
   public static ServerPropertyLoader getInstance() {
      if(instance==null) instance = new ServerPropertyLoader();
      return instance;
   }
   
   public int getLobbyTimer(){
      return Integer.parseInt( properties.getProperty( "server.lobby.timer" ));
   }
   
   public int getPlayerTimer(){
      return Integer.parseInt( properties.getProperty( "server.player.timer" ) );
   }
}
