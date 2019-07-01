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
        initialize();
    }


    protected void initialize() {
//        try {
//            UUID token;
//            this.loginController = view.acquireConnection(view.acquireConnectionMethod());
//            boolean wantsToRegister = view.wantsToRegister();
//            if (wantsToRegister) {
//                String username;
//                do {
//                    username = view.acquireUsername();
//                    token = loginController.register(username, view);
//                } while (token == null);
//                view.printMessage("Registered as @" + username +
//                        "\n\nThis is your token: " + token + "\nUse it to login next time\n");
//                player = tryLogin(token);
//            } else {
//                do {
//                    token = view.acquireToken();
//                    player = tryLogin(token);
//                } while (player == null);
//            }
//            loginController.joinLobby(token);
//        } catch (IOException e) {
//            view.printMessage("Server unreachable");
//        }
    }


//    private RemotePlayer tryLogin(UUID token){
//        RemotePlayer tmpPlayer;
//        try {
//            tmpPlayer = loginController.login(token, view);
//            if (tmpPlayer != null)
//                view.printMessage("Logging in");
//        } catch (PlayerAlreadyLoggedInException e) {
//            view.printMessage(e.getMessage());
//            tmpPlayer = null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            tmpPlayer = null;
//        }
//        return tmpPlayer;
//    }
}


