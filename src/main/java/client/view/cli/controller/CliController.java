package client.view.cli.controller;

import client.socket.ServerListener;
import client.socket.proxies.SocketLoginController;
import client.view.InputReader;
import client.view.cli.CliInputReader;
import common.rmi_interfaces.RemoteLoginController;
import common.rmi_interfaces.RemotePlayer;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.UUID;

public class CliController implements AbstractClientController {
   
   private Scanner inputFromKeyBoard = new Scanner( System.in );
   public static final String NOPE = "Illegal command";
   private final InputReader inputReader;
   
   public CliController( InputReader in) {
      inputReader = in;
   }
   
   @Override
   public RemoteLoginController getLoginController() throws IOException, NotBoundException {
      RemoteLoginController controller;
      String cmd;
      HashSet<String> socketCommands = new HashSet<>( Arrays.asList( "s","socket" ) );
      HashSet<String> rmiCommands = new HashSet<>(Arrays.asList( "r","rmi" ));
      boolean validCommand;
      do {
         cmd = inputReader.requestString( "(s)ocket \n (r)mi" ).toLowerCase();
         validCommand = socketCommands.contains( cmd ) || rmiCommands.contains( cmd );
         if (!validCommand)
            System.out.println( NOPE );
      } while (!validCommand);
      
      if(socketCommands.contains( cmd )){
         Socket socket = new Socket(HOST,SOCKET_PORT);
         controller = new SocketLoginController( socket );
         new Thread(new ServerListener(socket)).start();
      }else {  //if the loop has ended, cmd is an rmi command
         Registry registry = LocateRegistry.getRegistry( HOST,RMI_PORT );
         controller = ( RemoteLoginController ) registry.lookup( "LoginController" );
      }
      return controller;
   }
   
   @Override
   public RemotePlayer loginRegister(RemoteLoginController loginController) throws IOException {
      UUID token = null;
      HashSet<String> yesAnswers = new HashSet<>(Arrays.asList( "y","yes" ));
      HashSet<String> noAnswers = new HashSet<>( Arrays.asList( "n","no","nope" ) );
      do {
         String cmd = inputReader.requestString( "Do you have a login token?" ).toLowerCase();
         if(yesAnswers.contains( cmd )){
            token = UUID.fromString( inputReader.requestString( "Insert your token" ) );
         }else if(noAnswers.contains( cmd )){
            String username;
            do {
               username = inputReader.requestString( "Insert an username" );
            }while (!username.equals( inputReader.requestString( "Are you sure? Retype it to confirm." ) ));
            token = loginController.register( username );
         }else {
            System.out.println( NOPE );
         }
      } while (token == null);
      return loginController.login(token);
   }
}
