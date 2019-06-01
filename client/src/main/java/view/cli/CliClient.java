package view.cli;

import common.RemoteController;
import view.cli.conection.commands.CliCommand;
import view.cli.conection.commands.CommandFactory;
import view.cli.conection.socket.SocketProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class CliClient implements RemoteView{
   
   private static final String NOPE="Invalid Command";
   
   public static void main(String[] args) throws IOException {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      RemoteController connection = null;
      System.out.println("(s)ocket \n (r)mi");
      String cmd;
      UUID token = null;
      do{
         try {
            cmd = in.readLine();
            switch (cmd) {
               case "s":
                  connection = new SocketProxy();
                  break;
               case "r":
                  //connection=new RmiConnection();
                  break;
               default:
                  System.out.println( NOPE );
                  break;
            }
         }catch ( IOException e){
            e.printStackTrace();
         }
      }while (connection == null);
      //TODO: notifica l'utente quando viene inserito nella partita.
      //TODO: gestisci la risposta al metodo eseguito
      while (connection.isOpened()){
         System.out.println( "Insert command" );
         CliCommand command  = CommandFactory.getCommand( in.readLine(),connection,false );
         command.execute();
      }
      
   }
   
}
