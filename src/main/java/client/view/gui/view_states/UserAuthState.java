package client.view.gui.view_states;

import client.view.gui.javafx_controllers.authentication.UserAuthController;
import common.enums.ConnectionMethodEnum;
import common.remote_interfaces.RemoteLoginController;

import java.rmi.RemoteException;
import java.util.UUID;

public class UserAuthState extends ViewState {
   UserAuthState() throws RemoteException {
      super();
      setJavafxController( new UserAuthController() );
   }

   @Override
   public ViewState nextState() {
      SetupState nextState;
      try {
         nextState = new SetupState();
      } catch ( RemoteException never ) {
         IllegalStateException e1 = new IllegalStateException( );
         e1.setStackTrace( never.getStackTrace() );
         throw e1;
      }
      return nextState;
   }
   
   @Override
   public UUID acquireToken() {
      return getJavafxController().acquireToken();
   }
   
   @Override
   public String acquireUsername() {
      return getJavafxController().acquireUsername();
   }
   
   @Override
   public ConnectionMethodEnum acquireConnectionMethod() {
      return getJavafxController().acquireConnectionMethod();
   }
   
   @Override
   public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {
      return getJavafxController().acquireConnection( cme );
   }
   
   @Override
   public boolean wantsToRegister() {
      return getJavafxController().wantsToRegister();
   }
   
}
