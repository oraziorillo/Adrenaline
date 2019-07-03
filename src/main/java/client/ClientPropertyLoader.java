package client;

import common.PropertyLoader;

import java.io.IOException;

public class ClientPropertyLoader extends PropertyLoader {
   
   private static ClientPropertyLoader instance;
   
   private ClientPropertyLoader() {
      super("server.host = localhost");
   }
   
   public static ClientPropertyLoader getInstance() {
      if(instance == null) instance = new ClientPropertyLoader();
      return instance;
   }
   
   public String getHostAddress(){
      return properties.getProperty( "server.host" );
   }
}
