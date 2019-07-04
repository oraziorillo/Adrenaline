package client.view.gui.view_states;

import client.view.gui.javafx_controllers.authentication.UserAuthController;
import common.dto_model.GameDTO;
import common.enums.ConnectionMethodEnum;
import common.enums.ControllerMethodsEnum;
import common.events.requests.Request;
import common.remote_interfaces.RemoteLoginController;

import java.rmi.RemoteException;
import java.util.UUID;

public class UserAuthState extends ViewState {
   UserAuthState() {
      super();
      setJavafxController( new UserAuthController() );
   }

   @Override
   public ViewState nextState() {
      SetupState nextState;
      nextState = new SetupState();
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
   public ControllerMethodsEnum authMethod() {
      return getJavafxController().authMethod();
   }
   
   @Override
   public void resumeGame(GameDTO game) {
      topView.nextState();
      topView.resumeGame( game );
   }
   
}
