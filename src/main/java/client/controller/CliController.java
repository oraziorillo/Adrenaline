package client.controller;

import client.view.AbstractView;
import client.view.cli.CliView;
import client.view.cli.CommandParser;
import common.enums.ControllerMethodsEnum;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import server.exceptions.PlayerAlreadyLoggedInException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.UUID;

public class CliController {

    private RemoteLoginController loginController;
    protected AbstractView view;
    protected RemotePlayer player;


    public CliController() throws IOException {
        this.view = new CliView();
    }


    public void run(){
        try {
            view.printMessage("\t\tWELCOME TO ADRENALINE!");
            UUID token;
            while (this.loginController == null) {
                tryAcquireConnection();
            }
            boolean wantsToRegister = view.wantsToRegister();
            if (wantsToRegister) {
                String username;
                do {
                    username = view.acquireUsername();
                    token = loginController.register(username, view);
                } while (token == null);
                view.printMessage("Signed up as @" + username +
                        "\nThis is your token: " + token +
                        "\nUse it to login next time");
                player = tryLogin(token);
            } else {
                do {
                    token = view.acquireToken();
                    player = tryLogin(token);
                } while (player == null);
            }
            loginController.joinLobby(token);
        } catch (IOException e) {
            view.printMessage("Server unreachable");
        }
        while (isRunnable()) {
            try {
                String[] input = view.nextCommand();
                ControllerMethodsEnum command = ControllerMethodsEnum.parseString(input[0]);
                CommandParser.executeCommand(command, input, player);
            } catch (IOException serverUnreachable) {
                view.printMessage("Server unreachable");
            } catch (IllegalArgumentException unsupportedCommand) {
                view.printMessage(unsupportedCommand.getMessage());
                view.printMessage(ControllerMethodsEnum.help());
            }
        }
    }


    private void tryAcquireConnection(){
        try {
            this.loginController = view.acquireConnection(view.acquireConnectionMethod());
        } catch (IllegalArgumentException e){
            view.printMessage(e.getMessage());
        }
    }


    private RemotePlayer tryLogin(UUID token){
        RemotePlayer tmpPlayer;
        try {
            tmpPlayer = loginController.login(token, view);
            if (tmpPlayer != null)
                view.printMessage("Logging in");
        } catch (PlayerAlreadyLoggedInException e) {
            view.printMessage(e.getMessage());
            tmpPlayer = null;
        } catch (IOException e) {
            e.printStackTrace();
            tmpPlayer = null;
        }
        return tmpPlayer;
    }


    public boolean isRunnable() {
        try {
            return player.isConnected();
        } catch (RemoteException e) {
            return false;
        }
    }
}
