package client.view.gui;

import client.view.AbstractView;
import client.view.gui.javafx_controllers.view_states.ViewState;
import common.events.ModelEvent;
import common.events.ModelEventListener;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class GuiView extends UnicastRemoteObject implements AbstractView, ModelEventListener {
   
   private ViewState currentGui = ViewState.getFirstState();
   private ObservableList<String> acks = FXCollections.emptyObservableList();
   protected RemotePlayer player;
   
   public GuiView() throws RemoteException {
   }
   
   @Override
   public void error(String msg) throws RemoteException {
      currentGui.error( msg );
   }
   
   @Override
   public RemoteLoginController acquireConnection() throws RemoteException {
      return currentGui.acquireConnection();
   }
   
   @Override
   public boolean wantsToRegister() throws RemoteException {
      return currentGui.wantsToRegister();
   }
   
   @Override
   public String acquireUsername() throws RemoteException {
      String username = currentGui.acquireUsername();
      nextState();
      return username;
   }
   
   @Override
   public UUID acquireToken() throws RemoteException {
      UUID token = currentGui.acquireToken();
      nextState();
      return token;
   }
   
   @Override
   public String requestString(String message) throws RemoteException {
      return currentGui.requestString( message );
   }
   
   @Override
   public Collection<String> getPendingAcks() throws RemoteException {
      //TODO: rimovibile?
      return new HashSet<>( acks );
   }
   
   @Override
   public void modelEvent(ModelEvent event) {
      currentGui.modelEvent( event );
   }
   
   @Override
   public void ack(String message) throws IOException {
      currentGui.ack( message );
   }
   
   @Override
   public ModelEventListener getListener() {
      return currentGui.getListener();
   }
   
   private void nextState(){
      currentGui = currentGui.nextState();
   }
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;
      currentGui.setPlayer(player);
   }
}
