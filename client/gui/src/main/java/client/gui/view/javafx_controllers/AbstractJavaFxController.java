package client.gui.view.javafx_controllers;

import client.gui.view.GuiView;
import common.dto_model.GameDTO;
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
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Alert;

import java.util.List;
import java.util.UUID;

/**
 * superclass for java fx controllers
 */
public abstract class AbstractJavaFxController implements ChangeListener<Number>,ModelEventListener {
   
   /**
    * Needed to show game manuals. Given by the application in which the cli.controller is ran
    */
   protected HostServices hostServices;
   /**
    * the stub for the server-size player
    */
   protected RemotePlayer player;
   /**
    * the actually exported cli.client.view
    */
   protected GuiView topView;
   
   protected AbstractJavaFxController() {
   }
   
   /**
    * utility method to display an error message and exit
    * @param msg the text of the message
    */
   public void error(String msg) {
      Alert errorAlert = new Alert(Alert.AlertType.ERROR);
      errorAlert.setTitle("ERROR");
      errorAlert.setContentText(msg);
      errorAlert.setHeaderText(null);
      errorAlert.showAndWait();
      try {player.quit();} catch ( Exception ignored ) {}
      System.exit(1);
   }
   
   public void setHostServices(HostServices hostServices) {
      this.hostServices = hostServices;
   }
   
   /**
    * To print messages from the server
    * @param message
    */
   public abstract void ack(String message);
   
   /**
    * to print messages from other users
    * @param message
    */
   public abstract void chatMessage(String message);
   
   public void  setPlayer(RemotePlayer player) {
      this.player = player;
   }
   
   public void setTopView(GuiView topView) {
      this.topView = topView;
   }
   
   public abstract void notifyEvent(LobbyEvent event);
   
   public abstract void request(Request request);
   
   public abstract ConnectionMethodEnum acquireConnectionMethod();
   
   public abstract RemoteLoginController acquireConnection(ConnectionMethodEnum cme);
   
   public abstract ControllerMethodsEnum authMethod();
   
   public abstract String acquireUsername();
   
   public abstract UUID acquireToken();
   
   @Override
   public abstract void onPcBoardUpdate(PcBoardEvent event);
   
   @Override
   public abstract void onSquareUpdate(SquareEvent event);
   
   @Override
   public abstract void onGameBoardUpdate(GameBoardEvent event);
   
   @Override
   public abstract void onPcUpdate(PcEvent event);
   
   @Override
   public abstract void onKillShotTrackUpdate(KillShotTrackEvent event);
   
   public abstract void resumeGame(GameDTO game);
   
   public abstract void winners(List<String> gameWinners);
}
