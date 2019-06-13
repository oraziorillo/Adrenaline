package server.controller.socket;

import common.enums.SocketLoginEnum;
import common.remote_interfaces.RemotePlayer;
import server.controller.LoginController;
import server.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class LoginSocketListener implements Runnable {

    private final Socket client;
    private final Scanner in;
    private final PrintWriter out;
    private LoginController loginController = LoginController.getInstance();

    public LoginSocketListener(Socket s) throws IOException {
        this.client = s;
        this.in = new Scanner(s.getInputStream());
        this.out = new PrintWriter(s.getOutputStream());
    }

    @Override
    public void run() {
        while (!client.isClosed()) {
            SocketLoginEnum cmd = SocketLoginEnum.valueOf(in.next());
            try {

                switch (cmd) {
                    case REGISTER:
                        out.println(loginController.register(in.next(), new RemoteViewSocketProxy(client)));
                        out.flush();
                        break;

                    case LOGIN:
                        RemotePlayer player = loginController.login(UUID.fromString(in.next()));
                        new Thread(new PlayerSocketListener(client, player)).start();
                        break;

                    case JOIN_LOBBY:
                        loginController.joinLobby(UUID.fromString(in.next()));
                        break;

                    default:
                        out.println("ILLEGAL COMMAND");
                        out.flush();
                }

            } catch (IOException | PlayerAlreadyLoggedInException e) {
                e.printStackTrace();
            }
        }
    }
}
