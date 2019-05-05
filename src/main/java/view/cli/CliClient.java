package view.cli;

import view.cli.conection.ClientConnection;
import view.cli.conection.socket.SocketConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class CliClient {

    public static final String NOPE="Invalid Command";

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        ClientConnection connection = null;
        System.out.println("(s)ocket \n (r)mi");
        String cmd;
        UUID token = null;
        do{
            cmd = in.readLine();
            switch (cmd){
                case "s":
                    connection=new SocketConnection();
                    break;
                case "r":
                    //connection=new RmiConnection();
                    break;
                default:
                    System.out.println(NOPE);
                    break;
            }
        }while (connection == null);
        System.out.println("Connected to the server:\n (l)og in \n (s)ign in");
        do{
            cmd=in.readLine();
            switch (cmd){
                case "l":
                    System.out.println("Insert your token");
                    token=UUID.fromString(in.readLine());
                    if(!connection.login(token)){
                        token = null;
                    }
                    break;
                case "s":
                    System.out.println("Choose a username");
                    token = connection.register(in.readLine());
                    System.out.println("This is your token. Use it to access again in the future.");
                    System.out.println(token);
                    break;
                    default:
                        System.out.println(NOPE);
            }
        }while (token == null);
        connection.update();
        //TODO: a questo punto l'utente è ALMENO nella lobby

    }

}
