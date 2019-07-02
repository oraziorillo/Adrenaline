package client.view.gui.javafx_controllers;

import client.view.AbstractView;
import client.view.gui.GuiView;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.scene.control.Alert;

import java.rmi.RemoteException;

public abstract class AbstractJavaFxController extends AbstractView {
   
   protected HostServices hostServices;
   protected RemotePlayer player;
   protected GuiView topView;
   
   protected AbstractJavaFxController() throws RemoteException {
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
   }
   
   public void setHostServices(HostServices hostServices) {
      this.hostServices = hostServices;
   }
   
   public void  setPlayer(RemotePlayer player) {
      this.player = player;
   }
   
   @Override
   public ModelEventListener getListener() throws RemoteException {
      return topView;
   }
   
   @Override
   public void ack(String message) {
   
   }
   
   @Override
   public void onSquareUpdate(SquareEvent event) {
   
   }
   
   @Override
   public void onPcUpdate(PcEvent event) {
   
   }
   
   @Override
   public void onPcBoardUpdate(PcBoardEvent event) {
   
   }
   
   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) {
   
   }
   
   @Override
   public void onGameBoardUpdate(GameBoardEvent event) {
   
   }
   
   public void setTopView(GuiView topView) {
      this.topView = topView;
   }
   
   public abstract void setEnabled(boolean enabled);
   
}
