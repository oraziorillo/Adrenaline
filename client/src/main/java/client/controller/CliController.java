package client.controller;

import client.view.AbstractView;
import client.view.cli.CliView;
import client.view.cli.CommandParser;
import common.enums.ConnectionMethodEnum;
import common.enums.ControllerMethodsEnum;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import static common.Constants.WRONG_TIME;

public class CliController {

    private RemoteLoginController loginController;
    protected AbstractView view;
    protected RemotePlayer player;

    public CliController() throws IOException {
        this.view = new CliView();
    }


    public void run() {
        view.printMessage("\t\tWELCOME TO ADRENALINE!");
        UUID token;
        while (this.loginController == null) {
            try {
                while (this.loginController == null) {
                    if (!tryAcquireConnection()) {
                        view.printMessage("BYE!");
                        return;
                    }
                }
                ControllerMethodsEnum authMethod = view.authMethod();
                switch (authMethod) {
                    case SIGN_UP:
                        String username;
                        do {
                            username = view.acquireUsername();
                            token = loginController.register(username, view);
                        } while (token == null);
                        view.printMessage("Signed up as @" + username +
                                "\nThis is your token: " + token +
                                "\nUse it to login next time");
                        player = tryLogin(token);
                        break;
                    case LOG_IN:
                        do {
                            token = view.acquireToken();
                            player = tryLogin(token);
                        } while (player == null);
                        break;
                    case QUIT:
                        view.printMessage("BYE!");
                        UnicastRemoteObject.unexportObject(view, true);
                        return;
                    default:
                        throw new IllegalArgumentException(WRONG_TIME);
                }
                loginController.joinLobby(token);
            } catch (IllegalArgumentException e) {
                view.printMessage(e.getMessage());
            } catch (IOException e) {
                view.printMessage("Server unreachable");
            }
        }
        view.nextCommand();
        ControllerMethodsEnum command = null;
        while (isRunnable() && command != ControllerMethodsEnum.QUIT) {
            try {
                String[] input = view.nextCommand().split("\\s+");
                command = ControllerMethodsEnum.parseString(input[0]);
                String[] args = new String[input.length - 1];
                System.arraycopy(input, 1, args, 0, input.length - 1);
                CommandParser.executeCommand(command, args, player);
            } catch (IOException serverUnreachable) {
                view.printMessage("Server unreachable");
            } catch (IllegalArgumentException unsupportedCommand) {
                view.printMessage(unsupportedCommand.getMessage());
                view.printMessage(ControllerMethodsEnum.help());
            }
        }
        try {
            view.printMessage("BYE!");
            UnicastRemoteObject.unexportObject(view, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
        //TODO logout
    }


    private boolean tryAcquireConnection(){
        try {
            ConnectionMethodEnum connMethod = view.acquireConnectionMethod();
            if (connMethod == ConnectionMethodEnum.QUIT) {
                UnicastRemoteObject.unexportObject(view, true);
                return false;
            }
            this.loginController = view.acquireConnection(connMethod);
        } catch (IllegalArgumentException e){
            view.printMessage(e.getMessage());
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
        return true;
    }


    private RemotePlayer tryLogin(UUID token){
        RemotePlayer tmpPlayer;
        try {
            tmpPlayer = loginController.login(token, view);
            if (tmpPlayer != null)
                view.printMessage("\nLogging in");
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
