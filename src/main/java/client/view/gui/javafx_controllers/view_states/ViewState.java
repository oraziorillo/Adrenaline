package client.view.gui.javafx_controllers.view_states;

import client.view.AbstractView;
import common.events.ModelEvent;
import common.events.ModelEventListener;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.UUID;

public abstract class ViewState implements AbstractView, ListChangeListener<String> {
   protected RemotePlayer player;
   
   public abstract ViewState nextState();
   
   public ViewState(String... previousAcks) {
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
   
   @Override
   public RemoteLoginController acquireConnection() throws RemoteException {
      return null;
   }
   
   @Override
   public String acquireUsername() throws RemoteException {
      nextState();
      return null;
   }
   
   @Override
   public UUID acquireToken() throws RemoteException {
      nextState();
      return null;
   }
   
   @Override
   public void ack(String message) throws IOException {
   
   }
   
   @Override
   public boolean wantsToRegister() throws RemoteException {
      return false;
   }
   
   @Override
   public String requestString(String message) throws RemoteException {
      return null;
   }
   
   @Override
   public void modelEvent(ModelEvent event) {
   
   }
   
   @Override
   public ModelEventListener getListener() {
      return this;
   }
   
   @Override
   public Collection<String> getPendingAcks() throws RemoteException {
      return null;//TODO:rimovibile?
   }
   
   public static ViewState getFirstState(){
      return new UserAuthState();
   }
}
