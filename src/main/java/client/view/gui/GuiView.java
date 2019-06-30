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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.UUID;

public class GuiView extends AbstractView implements ModelEventListener {

   private ViewState currentGui = ViewState.getFirstState();
   private ObservableList<String> acks = FXCollections.emptyObservableList();
   protected RemotePlayer player;


   public GuiView() throws RemoteException {
   }


   @Override
   public ConnectionMethodEnum acquireConnectionMethod() {
      return currentGui.acquireConnectionMethod();
   }


   @Override
   public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {
      return currentGui.acquireConnection(cme);
   }


   @Override
   public boolean wantsToRegister() {
      return currentGui.wantsToRegister();
   }


   @Override
   public String acquireUsername(){
      return currentGui.acquireUsername();
   }


   @Override
   public UUID acquireToken() {
      return currentGui.acquireToken();
   }


   @Override
   public String requestString(String message) {
      return currentGui.requestString( message );
   }


   private void nextState() throws RemoteException {
      currentGui = currentGui.nextState();
   }


   public void setPlayer(RemotePlayer player) {
      this.player = player;
      currentGui.setPlayer(player);
   }


   @Override
   public void ack(String message) throws RemoteException {
      currentGui.ack( message );
   }


   @Override
   public void error(String msg) throws RemoteException {
      currentGui.error( msg );
   }


   @Override
   public ModelEventListener getListener() {
      return currentGui.getListener();
   }


   @Override
   public void onGameBoardUpdate(GameBoardEvent event) throws RemoteException {

   }


   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) throws RemoteException {

   }

   @Override
   public void onPcBoardUpdate(PcBoardEvent event) throws RemoteException {

   }

   @Override
   public void onPcUpdate(PcEvent event) throws RemoteException {

   }

   @Override
   public void onSquareUpdate(SquareEvent event) throws RemoteException {

   }
}
