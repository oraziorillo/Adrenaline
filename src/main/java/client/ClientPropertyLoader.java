package client;

import common.PropertyLoader;

public class ClientPropertyLoader extends PropertyLoader {
   
   private static ClientPropertyLoader instance;
   
   private ClientPropertyLoader() {
      super("server.host = localhost");
   }
   
   public static ClientPropertyLoader getInstance() {
      if(instance == null) instance = new ClientPropertyLoader();
      return instance;
   }

    public String getHostAddress() {
        return System.getProperty("client.host");
    }
}
