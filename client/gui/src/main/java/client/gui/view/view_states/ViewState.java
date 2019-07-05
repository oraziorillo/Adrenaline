package client.gui.view.view_states;

import client.gui.view.GuiView;
import client.gui.view.javafx_controllers.AbstractJavaFxController;
import common.dto_model.GameDTO;
import common.enums.ConnectionMethodEnum;
import common.enums.ControllerMethodsEnum;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Abstract cli.client.view state class.
 * Most methods just throw IllegalStateException, which is non-blocking, to notify unexpected calls
 * Concrete states will override those methods so exception is no longer thrown
 */
public abstract class ViewState {
   private static AbstractJavaFxController javafxController;
   private static final String UNEXPECTED_CALL = "You are not supposed to call this method from there";
   protected static transient RemotePlayer player;
   static transient HostServices hostServices;
   private static transient List<String> previousAcks = new ArrayList<>();
   static transient Stage stage;
   static transient GuiView topView;
   
   public abstract ViewState nextState() throws RemoteException;
   
   ViewState() {
      super();
   }
   
   protected void setJavafxController(AbstractJavaFxController javafxController) {
      ViewState.javafxController = javafxController;
      javafxController.setPlayer( player );
      javafxController.setHostServices( hostServices );
      javafxController.setTopView( topView );
      for(String s: previousAcks)
         ack( s );
      
   }
   
   protected AbstractJavaFxController getJavafxController() {
      return javafxController;
   }
   
   public static ViewState getFirstState(HostServices hostServices, Stage stage, GuiView topView) throws RemoteException {
      ViewState returned = new UserAuthState();
      ViewState.stage = stage;
      ViewState.hostServices = hostServices;
      setTopView( topView );
      return returned;
   }
   
   public static void setTopView(GuiView topView) {
      ViewState.topView = topView;
      if(javafxController!=null) javafxController.setTopView( topView );
   }
   
   /**
    * Utility method for not manageable errors (Such as server unreachable).
    * Displays an error dialog and quits (performing a soft quitFromLobby if possible)
    * @param msg the text for the error dialog
    */
   public void error(String msg) {
      javafxController.error( msg );
   }
   
   public void setPlayer(RemotePlayer player){
      ViewState.player = player;
      if(javafxController!=null) javafxController.setPlayer( player );
   }
   
   public ConnectionMethodEnum acquireConnectionMethod() {
      throw new IllegalStateException( UNEXPECTED_CALL );
   }
   
   public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {throw new IllegalStateException( UNEXPECTED_CALL );}
   
   public String acquireUsername(){
      throw new IllegalStateException( UNEXPECTED_CALL );
   }
   
   public UUID acquireToken() {
      throw new IllegalStateException( UNEXPECTED_CALL );
   }
   
   public void ack(String message) {
      previousAcks.add( message );
   }
   
   public void chatMessage(String message) {
      javafxController.chatMessage( message );
   }
   
   public ControllerMethodsEnum authMethod() {
      throw new IllegalStateException( UNEXPECTED_CALL );
   }
   
   public void onGameBoardUpdate(GameBoardEvent event) {
      javafxController.onGameBoardUpdate( event );
   }
   
   public void onKillShotTrackUpdate(KillShotTrackEvent event) {
      javafxController.onKillShotTrackUpdate( event );
   }
   
   public void onPcBoardUpdate(PcBoardEvent event) {
      javafxController.onPcBoardUpdate( event );
   }
   
   public void onPcUpdate(PcEvent event) {
      javafxController.onPcUpdate( event );
   }
   
   public void onSquareUpdate(SquareEvent event) {
      javafxController.onSquareUpdate( event );
   }
   
   public void notifyEvent(LobbyEvent event) {
      javafxController.notifyEvent( event );
   }
   
   public void request(Request request) throws RemoteException {
      javafxController.request( request );
   }
   
   public abstract void resumeGame(GameDTO game);
   
   public void winners(List<String> gameWinners){
      javafxController.winners(gameWinners);
   };
}
