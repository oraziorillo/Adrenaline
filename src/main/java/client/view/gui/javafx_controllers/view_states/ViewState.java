package client.view.gui.javafx_controllers.view_states;

import client.view.AbstractView;
import common.enums.ConnectionMethodEnum;
import common.events.ModelEventListener;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Abstract view state class.
 * Most methods just throw IllegalStateException, which is non-blocking, to notify unexpected calls
 * Concrete states will override those methods so exception is no longer thrown
 */
public abstract class ViewState extends AbstractView implements ListChangeListener<String> {
   protected RemotePlayer player;
   protected HostServices hostServices;
   private static final String UNEXPECTED_CALL = "You are not supposed to call this method from there";

   public abstract ViewState nextState() throws RemoteException;
   
   public ViewState(String... previousAcks) throws RemoteException {
      super();
      for(String ack: previousAcks){
         try {
            this.ack( ack );
         }catch ( IOException shouldntHappen ){
            IllegalStateException neverThrown = new IllegalStateException( "Connection exception thrown on netless operation" );
            neverThrown.setStackTrace( shouldntHappen.getStackTrace() );
            throw neverThrown;
         }
      }
   }
   
   @Override
   public void onChanged(Change<? extends String> change) {
      if(change.wasAdded()){
         for(String ack: change.getAddedSubList()){
            try {
               this.ack( ack );
            }catch ( IOException shouldntHappen ){
               IllegalStateException neverThrown = new IllegalStateException( "Connection exception thrown on netless operation" );
               neverThrown.setStackTrace( shouldntHappen.getStackTrace() );
               throw neverThrown;
            }
         }
      }
   }
   
   /**
    * Utility method for not manageable errors (Such as server unreachable).
    * Displays an error dialog and quits (performing a soft quit if possible)
    * @param msg the text for the error dialog
    */
   @Override
   public void error(String msg) {
      Alert errorAlert = new Alert(Alert.AlertType.ERROR);
      errorAlert.setTitle("ERROR");
      errorAlert.setContentText(msg);
      errorAlert.setHeaderText(null);
      errorAlert.showAndWait();
      try {player.quit();} catch ( Exception ignored ) {}
      System.exit(1);
   }
   
   public void setPlayer(RemotePlayer player){
      this.player=player;
   }
   
   public void setHostServices(HostServices hostServices) {
      this.hostServices = hostServices;
   }

   @Override
   public ConnectionMethodEnum acquireConnectionMethod() {
      throw new IllegalStateException( UNEXPECTED_CALL );
   }

   @Override
   public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {throw new IllegalStateException( UNEXPECTED_CALL );}

   @Override
   public String acquireUsername(){
      throw new IllegalStateException( UNEXPECTED_CALL );
   }
   
   @Override
   public UUID acquireToken() {
      throw new IllegalStateException( UNEXPECTED_CALL );
   }
   
   @Override
   public void ack(String message) throws RemoteException {throw new IllegalStateException( UNEXPECTED_CALL );}
   
   @Override
   public void chatMessage(String message) throws RemoteException {throw new IllegalStateException( UNEXPECTED_CALL );}
   
   @Override
   public boolean wantsToRegister() {
      throw new IllegalStateException( UNEXPECTED_CALL );
   }
   
   @Override
   public String requestString(String message) {
      throw new IllegalStateException( UNEXPECTED_CALL );
   }
   
   @Override
   public ModelEventListener getListener() {
      return this;
   }


   public static ViewState getFirstState() throws RemoteException {
      return new UserAuthState();
   }
   
}
