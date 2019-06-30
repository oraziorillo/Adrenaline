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

public abstract class ViewState extends AbstractView implements ListChangeListener<String> {
   protected RemotePlayer player;
   protected HostServices hostServices;

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
   
   @Override
   public void error(String msg) {
      Alert errorAlert = new Alert(Alert.AlertType.ERROR);
      errorAlert.setTitle("ERROR");
      errorAlert.setContentText(msg);
      errorAlert.setHeaderText(null);
      errorAlert.showAndWait();
      try {player.quit();} catch ( Exception ignored ) {}
      System.exit(1);
      //TODO: gestisci errori
   }
   
   public void setPlayer(RemotePlayer player){
      this.player=player;
   }
   
   public void setHostServices(HostServices hostServices) {
      this.hostServices = hostServices;
   }

   @Override
   public ConnectionMethodEnum acquireConnectionMethod() {
      return null;
   }

   @Override
   public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {
      return null;
   }

   @Override
   public String acquireUsername(){
      return null;
   }
   
   @Override
   public UUID acquireToken() {
      return null;
   }
   
   @Override
   public void ack(String message) throws RemoteException {
   
   }
   
   @Override
   public boolean wantsToRegister() {
      return false;
   }
   
   @Override
   public String requestString(String message) {
      return null;
   }
   
   @Override
   public ModelEventListener getListener() {
      return this;
   }


   public static ViewState getFirstState() throws RemoteException {
      return new UserAuthState();
   }

}
