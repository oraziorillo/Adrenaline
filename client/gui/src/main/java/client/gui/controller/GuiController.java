package client.gui.controller;

import client.gui.view.GuiView;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import javafx.application.Application;
import javafx.stage.Stage;
import common.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

/**
 * Controller for gui flow, launched by launch gui
 * @see client.gui.LaunchGui
 */
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
        UUID token;
        do {
           token = authUser( stage );
        }while (token==null);
       view.nextState();
        loginController.joinLobby( token );
        stage.setOnCloseRequest( e-> {
           try {
              player.quit();
              UnicastRemoteObject.unexportObject( view,true );
           } catch ( RemoteException ignored ) {
           
           }finally {
              stage.close();
           }
        } );
    }
   
   /**
    * Login protocol
    * @param stage passed to the view
    * @return the token obtained after login
    */
    private UUID authUser(Stage stage){
        try {
            this.loginController = view.acquireConnection(view.acquireConnectionMethod());
            UUID token;
            switch (view.authMethod()){
               case LOG_IN:
                  token = view.acquireToken();
                  break;
               case SIGN_UP:
                  String username = view.acquireUsername();
                  token = loginController.register( username, view );
                   view.ack("This is your token\n" + token);
                  break;
               case QUIT: default:
                  stage.close();
                  throw new IllegalStateException( "Gui allows illegal states" );
            }
            player = loginController.login( token, view );
            view.setPlayer( player );
           return token;
        }catch ( IOException e ){
           view.error( "Server unreachable" );
        }catch ( PlayerAlreadyLoggedInException alreadyLogged ){
           view.error( "This player is already connected on a different machine" );
        }
        throw new IllegalStateException( "Authentication failed" );
    }
    
}
