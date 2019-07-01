package client.view.gui.view_states;

import client.view.AbstractView;
import client.view.gui.GuiView;
import client.view.gui.javafx_controllers.AbstractJavaFxController;
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
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Abstract view state class.
 * Most methods just throw IllegalStateException, which is non-blocking, to notify unexpected calls
 * Concrete states will override those methods so exception is no longer thrown
 */
public abstract class ViewState extends AbstractView {
   protected AbstractJavaFxController javafxController;
   private static final String UNEXPECTED_CALL = "You are not supposed to call this method from there";
   protected RemotePlayer player;
   protected HostServices hostServices;
   protected List<String> previousAcks;
   public static Stage stage;
   protected GuiView topView;
   
   public abstract ViewState nextState() throws IOException;
   
   public ViewState(Stage stage, RemotePlayer player, HostServices hostServices, List<String> observedList) throws RemoteException {
      super();
      this.player = player;
      this.hostServices = hostServices;
      this.stage = stage;
      this.previousAcks = observedList;
   }
   
   public void setJavafxController(AbstractJavaFxController javafxController) {
      this.javafxController = javafxController;
      javafxController.setPlayer( player );
      javafxController.setHostServices( hostServices );
      javafxController.setTopView( topView );
      for(String s: previousAcks){
         try {
            ack( s );
         } catch ( RemoteException ignored ) {}
      }
      
   }
   
   public void setHostServices(HostServices hostServices) {
      this.hostServices = hostServices;
   }
   
   public static ViewState getFirstState(HostServices hostServices, Stage stage, List<String> previousAcks, GuiView topView) throws RemoteException {
      UserAuthState returned = new UserAuthState( stage, hostServices, previousAcks);
      returned.setTopView(topView);
      return returned;
   }
   
   protected void setTopView(GuiView topView){
      this.topView = topView;
      javafxController.setTopView( topView );
   }
   
   /**
    * Utility method for not manageable errors (Such as server unreachable).
    * Displays an error dialog and quits (performing a soft quit if possible)
    * @param msg the text for the error dialog
    */
   @Override
   public void error(String msg) {
      javafxController.error( msg );
   }
   
   public void setPlayer(RemotePlayer player){
      this.player = player;
      javafxController.setPlayer( player );
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
   public void ack(String message) throws RemoteException {
      javafxController.ack( message );
      previousAcks.add( message );
   }
   
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
