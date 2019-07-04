package client.controller;

import client.view.gui.GuiExceptionHandler;
import client.view.gui.GuiView;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import javafx.application.Application;
import javafx.stage.Stage;
import server.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.util.UUID;

public class GuiController extends Application {

   RemoteLoginController loginController;
   protected GuiView view;
   protected RemotePlayer player;

   public GuiController() {

    }


    @Override
    public void start(Stage stage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler( new GuiExceptionHandler(player) );
        view = new GuiView( getHostServices(),stage );
        UUID token = authUser( stage );
        loginController.joinLobby( token );
    }
    
    private UUID authUser(Stage stage){
        try {
            this.loginController = view.acquireConnection(view.acquireConnectionMethod());
            UUID token;
            if (view.authMethod()) {
                String username = view.acquireUsername();
                token = loginController.register( username, view );
                view.ack( "This is your token"+System.lineSeparator()+token );
            } else {
                token = view.acquireToken();
            }
            player = loginController.login( token, view );
            view.setPlayer( player );
            view.nextState();
           return token;
        }catch ( IOException e ){
           view.error( "Server unreachable" );
        }catch ( PlayerAlreadyLoggedInException alreadyLogged ){
           view.error( "This player is already connected on a different machine" );
        }
        throw new IllegalStateException( "Authentication failed" );
    }
    
}
