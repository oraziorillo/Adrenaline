package client.controller;

import client.view.cli.CliView;
import server.controller.RemotePlayer;

import java.io.IOException;
import java.util.UUID;

public class SocketController extends ClientController {

    private SocketLoginController loginClient;
    private SocketPlayer socketPlayer;
    private RemotePlayer player;

    public SocketController(CliView cliView){
        super(cliView);
        init();
    }


    @Override
    public void init() {
        try {
            loginClient = new SocketLoginController();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        getView().login_register();
        getView().listening();

    }


    @Override
    public UUID registerWith(String username) {
        UUID token = null;
        try {
            token = loginClient.register(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }


    @Override
    public void login(UUID token) {
        while (player == null) {
            try {
                player = loginClient.login(token);
                socketPlayer = new SocketPlayer(new String("username"), token);
                //TODO servono i parametri di SocketPlayer?
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessage(String message) {
        //TODO
        return;
    }


}
