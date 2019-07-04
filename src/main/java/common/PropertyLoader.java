package common;

import java.io.*;
import java.util.Properties;

public abstract class PropertyLoader {
   protected Properties properties = new Properties();
   private static final String[] sharedProps = {"common.rmi.port = 9999","common.socket.port = 10000","common.request.timer = 30"};
   private static final String FILENAME = "config.properties";
   protected PropertyLoader(String... defaultProps) {
      try {
         try (FileInputStream fin = new FileInputStream( FILENAME )) {
            properties.load( fin );
         } catch ( FileNotFoundException fnf ) {
            File newFile = new File( FILENAME );
            PrintWriter propWriter = new PrintWriter( newFile );
            for (String s : sharedProps)
               propWriter.println( s );
            for (String s : defaultProps)
               propWriter.println( s );
            propWriter.close();
            FileInputStream fin = new FileInputStream( FILENAME );
            properties.load( fin );
         }
      }catch ( IOException ignored ){}
   }
   
   public int getSocketPort(){
      return Integer.parseInt( properties.getProperty( "common.socket.port" ) );
      
   }
   
   public int getRmiPort(){
      return Integer.parseInt( properties.getProperty( "common.rmi.port" ) );
   }
   
   public int getRequestTimer(){
      return Integer.parseInt( properties.getProperty( "common.request.timer" ) );
   }

   public String getHostAddress(){
      return properties.getProperty( "common.hostname" );
   }
}
