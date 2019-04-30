package view.cli;

import view.cli.conection.ClientConnection;
import view.cli.conection.socket.SocketConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class CliClient {

    public static final String NOPE="Comando non valido";

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        ClientConnection connection = null;
        System.out.println("Vuoi usare (s)ocket o (r)mi?");
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
        System.out.println("Connesso al server");
        System.out.println("(a)ccedi o (r)egistrati");
        do{
            cmd=in.readLine();
            switch (cmd){
                case "a":
                    System.out.println("Inserisci il token");
                    token=UUID.fromString(in.readLine());
                    if(!connection.login(token)){
                        token = null;
                    }
                    break;
                case "r":
                    System.out.println("Inserisci uno username");
                    token = connection.register(in.readLine());
                    System.out.println("Questo è il tuo token. usalo per accedere di nuovo");
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
