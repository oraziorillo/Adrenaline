package client.controller;

import client.view.AbstractView;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import javafx.application.Application;
import server.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.UUID;

public abstract class AbstractClientController extends Application {
    
    protected transient AbstractView view;
    protected transient RemoteLoginController loginController;
    protected transient RemotePlayer player;
    
    AbstractClientController(AbstractView view) {
        this.view = view;
        initialize();
    }

    protected void initialize(){
        try {
            UUID token;
            this.loginController = view.acquireConnection();
            boolean wantsToRegister = view.wantsToRegister();
            if (wantsToRegister) {
                do {
                    token = loginController.register( view.acquireUsername(), view );
                } while (token == null);
                view.ack( "This is your token: " + token + "\nUse it to login next time\n" );
                player = loginController.login( token, view );
            } else {
                RemotePlayer tmpPlayer;
                do {
                    token = view.acquireToken();
                    tmpPlayer = loginController.login( token, view );
                } while (tmpPlayer == null);
                player = tmpPlayer;
            }
            loginController.joinLobby( token );
        }catch ( IOException serverUnreachable ){
            try{
                serverUnreachable.printStackTrace();
                view.error( "Server unreachable" );
            }catch ( RemoteException ignored ){
                throw new IllegalStateException( "View should be local" );
            }

        } catch ( PlayerAlreadyLoggedInException e ) {
            e.printStackTrace();
            //TODO
        }
    }
}
