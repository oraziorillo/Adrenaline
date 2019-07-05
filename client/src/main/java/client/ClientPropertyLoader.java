package client;

import common.PropertyLoader;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class ClientPropertyLoader extends PropertyLoader {
   
   private static ClientPropertyLoader instance;
   
   private ClientPropertyLoader() {
      super();
   }
   
   public static ClientPropertyLoader getInstance() {
      if(instance == null) instance = new ClientPropertyLoader();
      return instance;
   }

   public String getMyIPAddress() {
      Enumeration e = null;
      try {
         e = NetworkInterface.getNetworkInterfaces();
      } catch (SocketException ex) {
         return null;
      }
      while(e.hasMoreElements())
      {
         NetworkInterface n = (NetworkInterface) e.nextElement();
         Enumeration ee = n.getInetAddresses();
         while (ee.hasMoreElements())
         {
            InetAddress i = (InetAddress) ee.nextElement();
            if (i.isSiteLocalAddress())
               return i.getHostAddress();
         }
      }
      return null;
   }

}
