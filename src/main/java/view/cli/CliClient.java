package view.cli;

import common.RemoteLoginController;
import common.RemotePlayer;
import view.cli.commands.CliCommand;
import view.cli.commands.CommandFactory;
import view.conection.socket.SocketLoginController;

import java.io.*;
import java.util.Scanner;
import java.util.UUID;

public class CliClient implements RemoteView{
   
   private static final String NOPE="Invalid Command";
   
   public static void main(String[] args) throws IOException {
      Scanner in = new Scanner(new InputStreamReader(System.in));
      PrintWriter out = new PrintWriter( new OutputStreamWriter( System.out ) );
      UUID token = null;
      RemoteLoginController loginController = getLoginController(in,out);
      RemotePlayer player = login_register( in,out,loginController );
      
      
      //TODO: notifica l'utente quando viene inserito nella partita.
      //TODO: gestisci la risposta al metodo eseguito
      while (true){
         out.println( "Insert command: " );
         CliCommand command  = CommandFactory.getCommand( in.next(),player,false );
         command.execute();
      }
      
   }
   
   private static RemoteLoginController getLoginController(Scanner in, PrintWriter out){
      RemoteLoginController loginController = null;
      String cmd;
      out.println("(s)ocket \n (r)mi");
      do{
         try {
            cmd = in.next();
            switch (cmd) {
               case "s":
                  loginController = new SocketLoginController();
                  break;
               case "r":
                  //connection=new RmiConnection();
                  break;
               default:
                  out.println( NOPE );
                  break;
            }
         }catch ( IOException e){
            e.printStackTrace();
         }
      }while (loginController == null);
      return loginController;
   }
   private static RemotePlayer login_register(Scanner in, PrintWriter out, RemoteLoginController loginController) throws IOException {
      UUID token = null;
      out.println("Do you have a login token?");
      do {
         switch (in.next().toLowerCase()) {
            case "y":
            case "yes":
               out.println( "Insert your token" );
               token = UUID.fromString( in.next() );
               break;
            case "n":
            case "no":
            case "nope":
               String username;
               do {
                  out.println( "Insert an username" );
                  username = in.next();
                  out.println( username + System.lineSeparator() + "Are you sure? Retype it to confirm" );
               } while (!in.next().equals( username ));
               token = loginController.register( username );
               break;
            default:
               out.println( NOPE );
         }
      }while (token == null);
      return loginController.login( token );
   }
}
