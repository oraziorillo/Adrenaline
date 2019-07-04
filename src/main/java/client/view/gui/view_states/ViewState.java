package client.view.gui.view_states;

import client.view.AbstractView;
import client.view.gui.GuiView;
import client.view.gui.javafx_controllers.AbstractJavaFxController;
import common.enums.ConnectionMethodEnum;
import common.enums.ControllerMethodsEnum;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Abstract view state class.
 * Most methods just throw IllegalStateException, which is non-blocking, to notify unexpected calls
 * Concrete states will override those methods so exception is no longer thrown
 */
public abstract class ViewState extends AbstractView {
   private static AbstractJavaFxController javafxController;
   private static final String UNEXPECTED_CALL = "You are not supposed to call this method from there";
   protected static transient RemotePlayer player;
   static transient HostServices hostServices;
   private static transient List<String> previousAcks = new ArrayList<>();
   static transient Stage stage;
   static transient GuiView topView;
   
   public abstract ViewState nextState() throws RemoteException;
   
   ViewState() throws RemoteException {
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
   @Override
   public void error(String msg) {
      javafxController.error( msg );
   }
   
   public void setPlayer(RemotePlayer player){
      ViewState.player = player;
      if(javafxController!=null) javafxController.setPlayer( player );
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
   public void ack(String message) {
      previousAcks.add( message );
   }
   
   @Override
   public void chatMessage(String message) {
      try {
         javafxController.chatMessage( message );
      } catch ( RemoteException e ) {
         Thread.currentThread().getUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }

   @Override
   public ControllerMethodsEnum authMethod() {
      throw new IllegalStateException( UNEXPECTED_CALL );
   }
   
   @Override
   public String requestString(String message) {
      throw new IllegalStateException( UNEXPECTED_CALL );
   }
   
   
   @Override
   public ModelEventListener getListener() {
      return topView;
   }
   
   @Override
   public void onGameBoardUpdate(GameBoardEvent event) {
      javafxController.onGameBoardUpdate( event );
   }
   
   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) {
      javafxController.onKillShotTrackUpdate( event );
   }
   
   @Override
   public void onPcBoardUpdate(PcBoardEvent event) {
      javafxController.onPcBoardUpdate( event );
   }
   
   @Override
   public void onPcUpdate(PcEvent event) {
      javafxController.onPcUpdate( event );
   }
   
   @Override
   public void onSquareUpdate(SquareEvent event) {
      javafxController.onSquareUpdate( event );
   }
   
   @Override
   public void notifyEvent(LobbyEvent event) {
      try {
         javafxController.notifyEvent( event );
      } catch ( RemoteException e ) {
         IllegalStateException e1 = new IllegalStateException( "RemoteException on netless operation" );
         e1.setStackTrace( e.getStackTrace() );
         throw e1;
      }
   }
   
   @Override
   public void request(Request request) throws RemoteException {
   
   }
   
}
