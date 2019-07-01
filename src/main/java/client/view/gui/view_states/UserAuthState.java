package client.view.gui.view_states;

import client.view.gui.javafx_controllers.authentication.UserAuthController;
import common.enums.ConnectionMethodEnum;
import common.remote_interfaces.RemoteLoginController;
import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.UUID;

public class UserAuthState extends ViewState {
   public UserAuthState(Stage stage, HostServices hostServices, ObservableList<String> previousAcks) throws RemoteException {
      super( stage, null, hostServices,previousAcks);
      javafxController = new UserAuthController();
   }
   
   
   @Override
   public ViewState nextState() throws IOException {
      return new SetupState(stage,player,hostServices,previousAcks );
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
   
   @Override
   public void ack(String message) throws RemoteException {
      super.ack( message );
      javafxController.ack( message );
   }
}
