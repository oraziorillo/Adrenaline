package client.view.gui;

import client.view.AbstractView;
import client.view.gui.view_states.ViewState;
import common.enums.ConnectionMethodEnum;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.lobby_events.LobbyEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.requests.Request;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.UUID;

import static javafx.application.Platform.runLater;

public class GuiView extends AbstractView implements ModelEventListener {

   private transient ViewState currentGui;
   protected transient RemotePlayer player;
   protected transient HostServices hostServices;
   
   
   public GuiView(HostServices hostServices, Stage stage) throws RemoteException {
      super();
      this.hostServices = hostServices;
      currentGui = ViewState.getFirstState(hostServices,stage, this );
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

   @Override
   public void ack(String message) {
      runLater( ()->currentGui.ack( message ));
   }

    @Override
    public void error(String msg) {
      currentGui.error(msg);
    }

   @Override
   public void notifyEvent(LobbyEvent event) {
      currentGui.notifyEvent( event );
   }

   @Override
   public void request(Request request) throws RemoteException {

   }


   @Override
   public void chatMessage(String message) {
      currentGui.chatMessage( message );
   }
   
   @Override
   public ModelEventListener getListener() {
      return currentGui.getListener();
   }

   public void nextState() {
      try {
         currentGui = currentGui.nextState();
      }catch ( RemoteException e ){
         throw new IllegalStateException( "Unexpected RemoteException" );
      }
   }

   public void setPlayer(RemotePlayer player) {
      this.player = player;
      currentGui.setPlayer(player);
   }
   
   @Override
   public void onGameBoardUpdate(GameBoardEvent event) throws RemoteException {
      currentGui.onGameBoardUpdate( event );
   }

   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) throws RemoteException {
      currentGui.onKillShotTrackUpdate( event );
   }

   @Override
   public void onPcBoardUpdate(PcBoardEvent event) throws RemoteException {
      currentGui.onPcBoardUpdate( event );
   }

   @Override
   public void onPcUpdate(PcEvent event) throws RemoteException {
      currentGui.onPcUpdate( event );
   }

   @Override
   public void onSquareUpdate(SquareEvent event) throws RemoteException {
      currentGui.onSquareUpdate( event );
   }
}
