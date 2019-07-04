package client.view.gui;

import common.remote_interfaces.RemotePlayer;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;

public class GuiExceptionHandler implements Thread.UncaughtExceptionHandler {
   
   private final RemotePlayer player;
   
   public GuiExceptionHandler(RemotePlayer player){
      this.player=player;
   }
   
   @Override
   public void uncaughtException(Thread thread, Throwable throwable) {
      Platform.runLater( () -> {
         Alert errorAlert = new Alert(Alert.AlertType.ERROR);
         errorAlert.setTitle("ERROR");
         errorAlert.setContentText(throwable.getMessage());
         errorAlert.setHeaderText(null);
         errorAlert.showAndWait();
      } );
      try {
         player.quit();
      } catch ( IOException ignored ) {}
      System.exit(throwable.hashCode());
   }
}
