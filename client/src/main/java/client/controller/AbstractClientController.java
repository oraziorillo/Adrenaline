package client.controller;

import client.view.AbstractView;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import javafx.application.Application;

public abstract class AbstractClientController extends Application {
    
    RemoteLoginController loginController;
    protected AbstractView view;
    protected RemotePlayer player;


    AbstractClientController(AbstractView view) {
        this.view = view;
    }


    protected void initialize() {
//        try {
//            UUID token;
//            this.loginController = cli.view.acquireConnection(cli.view.acquireConnectionMethod());
//            boolean authMethod = cli.view.authMethod();
//            if (authMethod) {
//                String username;
//                do {
//                    username = cli.view.acquireUsername();
//                    token = loginController.register(username, cli.view);
//                } while (token == null);
//                cli.view.printMessage("Registered as @" + username +
//                        "\n\nThis is your token: " + token + "\nUse it to login next time\n");
//                player = tryLogin(token);
//            } else {
//                do {
//                    token = cli.view.acquireToken();
//                    player = tryLogin(token);
//                } while (player == null);
//            }
//            loginController.joinLobby(token);
//        } catch (IOException e) {
//            cli.view.printMessage("Server unreachable");
//        }
    }


//    private RemotePlayer tryLogin(UUID token){
//        RemotePlayer tmpPlayer;
//        try {
//            tmpPlayer = loginController.login(token, cli.view);
//            if (tmpPlayer != null)
//                cli.view.printMessage("Logging in");
//        } catch (PlayerAlreadyLoggedInException e) {
//            cli.view.printMessage(e.getMessage());
//            tmpPlayer = null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            tmpPlayer = null;
//        }
//        return tmpPlayer;
//    }
}


