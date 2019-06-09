package client.view.cli;

import client.ServerListener;
import client.socket_proxies.SocketLoginController;
import client.view.InputReader;
import client.view.RemoteView;
import common.RemoteLoginController;
import common.RemotePlayer;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.UUID;

public class CliView implements RemoteView, InputReader {

    private static final String NOPE = "Invalid Command";

    private RemoteLoginController controller;
    private Scanner inputFromKeyBoard;

    public CliView(){
        this.inputFromKeyBoard = new Scanner(System.in);
    }

    /**
     * @param message the text to show to the user
     * @return an integer inserted by the user
     */
    @Override
    public int requestInt(String message) {
        System.out.println(message);
        return inputFromKeyBoard.nextInt();
    }


    @Override
    public String requestString(String message) {
        System.out.println(message);
        return inputFromKeyBoard.next();
    }


    public void setupConnection() throws IOException, NotBoundException {
        String cmd;
        boolean validCommand;
        do {
            cmd = requestString( "(s)ocket \n (r)mi" );
            validCommand = cmd.equals( "s" ) || cmd.equals( "r" );
            if (!validCommand)
                System.out.println( NOPE );
        } while (!validCommand);
    
        if (cmd.equals( "s" )) {
            Socket socket = new Socket("localhost",10000);
            controller = new SocketLoginController( socket );
            new Thread(new ServerListener(socket)).start();
        }else {
            Registry registry = LocateRegistry.getRegistry( "localhost", 9999 );
            controller = ( RemoteLoginController ) registry.lookup( "LoginController" );
        }
    }


    public RemotePlayer login_register() throws IOException {
        UUID token = null;
        System.out.println("Do you have a login token?");
        do {
            switch (inputFromKeyBoard.next().toLowerCase()) {
                case "y":
                case "yes":
                    System.out.println("Insert your token");
                    token = UUID.fromString(inputFromKeyBoard.next());
                    break;
                case "n":
                case "no":
                case "nope":
                    String username;
                    do {
                        System.out.println("Insert an username");
                        username = inputFromKeyBoard.next();
                        System.out.println(username + System.lineSeparator() + "Are you sure? Retype it to confirm.");
                    } while (!inputFromKeyBoard.next().equals(username));
                    token = controller.register(username);
                    System.out.println("This is your access token. Save it to access next time!");
                    break;
                default:
                    System.out.println(NOPE);
            }
        } while (token == null);
        return controller.login(token);
    }
}
