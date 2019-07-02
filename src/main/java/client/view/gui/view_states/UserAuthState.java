package client.view.gui.view_states;

import client.view.gui.javafx_controllers.authentication.UserAuthController;
import common.enums.ConnectionMethodEnum;
import common.events.lobby_events.LobbyEvent;
import common.remote_interfaces.RemoteLoginController;
import javafx.application.HostServices;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public class UserAuthState extends ViewState {
   public UserAuthState(Stage stage, HostServices hostServices, List<String> previousAcks) throws RemoteException {
      super( stage, null, hostServices,previousAcks);
      javafxController = new UserAuthController();
   }
   
   @Override
   public void ack(String message) {
   
   }

   @Override
   public void notifyEvent(LobbyEvent event) {

   }

   @Override
   public ViewState nextState() {
      SetupState nextState = null;
      try {
         nextState = new SetupState(stage,player,hostServices,previousAcks );
      } catch ( RemoteException never ) {
         IllegalStateException e1 = new IllegalStateException( );
         e1.setStackTrace( never.getStackTrace() );
         throw e1;
      }
      nextState.setTopView( topView );
      return nextState;
   }
   
   @Override
   public UUID acquireToken() {
      return javafxController.acquireToken();
   }
   
   @Override
   public String acquireUsername() {
      return javafxController.acquireUsername();
   }
   
   @Override
   public ConnectionMethodEnum acquireConnectionMethod() {
      return javafxController.acquireConnectionMethod();
   }
   
   @Override
   public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {
      return javafxController.acquireConnection( cme );
   }
   
   @Override
   public boolean wantsToRegister() {
      return javafxController.wantsToRegister();
   }
   
   //TODO: ricevi un qualche dto e cambia stato
}
