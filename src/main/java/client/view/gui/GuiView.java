package client.view.gui;

import client.view.AbstractView;
import client.view.gui.javafx_controllers.view_states.ViewState;
import common.enums.ConnectionMethodEnum;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public class GuiView extends UnicastRemoteObject implements AbstractView, ModelEventListener {

   private transient ViewState currentGui = ViewState.getFirstState();
   protected transient RemotePlayer player;
   protected transient HostServices hostServices;

   public GuiView() throws RemoteException {
      super();
   }

   @Override
   public void error(String msg) throws RemoteException {
      currentGui.error( msg );
   }

   @Override
   public ConnectionMethodEnum acquireConnectionMethod() throws RemoteException {
      return currentGui.acquireConnectionMethod();
   }


   @Override
   public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) throws RemoteException {
      return currentGui.acquireConnection(cme);
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
   public void ack(String message) throws RemoteException {
      currentGui.ack( message );
   }
   
   @Override
   public void chatMessage(String message) throws RemoteException {
      currentGui.chatMessage( message );
   }
   
   @Override
   public ModelEventListener getListener() {
      return currentGui.getListener();
   }

   private void nextState(){
      currentGui = currentGui.nextState();
      currentGui.setPlayer( player );
      currentGui.setHostServices( hostServices );
   }

   public void setPlayer(RemotePlayer player) {
      this.player = player;
      currentGui.setPlayer(player);
   }
   
   public void setHostServices(HostServices hostServices) {
      this.hostServices = hostServices;
      currentGui.setHostServices( hostServices );
   }
   
   @Override
   public void onGameBoardUpdate(GameBoardEvent event) throws IOException {
      currentGui.onGameBoardUpdate( event );
   }

   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) throws IOException {
      currentGui.onKillShotTrackUpdate( event );
   }

   @Override
   public void onPcBoardUpdate(PcBoardEvent event) throws IOException {
      currentGui.onPcBoardUpdate( event );
   }

   @Override
   public void onPcUpdate(PcEvent event) throws IOException {
      currentGui.onPcUpdate( event );
   }
   
   @Override
   public void onSquareUpdate(SquareEvent event) throws IOException {
      currentGui.onSquareUpdate( event );
   }
   
}
