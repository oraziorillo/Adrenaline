package client.controller;

import client.view.cli.CliView;
import server.RemoteLoginController;
import server.controller.RemotePlayer;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class RMIController extends ClientController {

    private Registry registry;
    private RemoteLoginController loginClient;
    private RemotePlayer player;

    public RMIController(CliView cliView){
        super(cliView);
        init();
    }

    public void init(){
        try {
            registry = LocateRegistry.getRegistry();
            loginClient = (RemoteLoginController)registry.lookup("LoginController");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        getView().login_register();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }

    @Override
    public void sendMessage(String message) {
        //TODO
        return;
    }


}
