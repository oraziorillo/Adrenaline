package view.cli.conection.socket;

import view.cli.conection.ClientConnection;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class SocketConnection extends ClientConnection {
    public static final String SERVER_IP="localhost";
    public static final int SERVER_PORT=10000;
    public static final String ERROR="Something went wrong with socket connection";
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    UUID token;

    public SocketConnection(){
        try {
            socket=new Socket(SERVER_IP,SERVER_PORT);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new IllegalStateException(ERROR);
        }
        new Thread(new SocketListener(this)).setPriority(Thread.MIN_PRIORITY);
    }


    @Override
    public String update() {
        try {
            return in.readLine();  //After update the line will be available
        } catch (IOException e) {
            throw new IllegalStateException(ERROR);
        }
    }

    @Override
    public boolean login(UUID token) {
        out.println("login\t"+token);
        try {
            return Boolean.getBoolean(in.readLine());
        } catch (IOException e) {
            throw new IllegalStateException(ERROR);
        }
    }

    @Override
    public UUID register(String username) {
        out.println("register\t"+username);
        try {
            return UUID.fromString(in.readLine());
        } catch (IOException e) {
            throw new IllegalStateException(ERROR);
        }
    }
}
