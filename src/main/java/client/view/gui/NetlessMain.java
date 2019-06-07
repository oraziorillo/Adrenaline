package client.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NetlessMain extends Application {
   
   public static void main(String[] args) {
      launch( NetlessMain.class,args );
   }
   
   @Override
   public void start(Stage primaryStage) throws Exception {
      FXMLLoader loader = new FXMLLoader( Thread.currentThread().getContextClassLoader().getResource("gui.fxml" ));
      Parent root = loader.load();
      //CardHand<PowerUpCardDTO> controller = loader.getController(); controller.setCard( new PowerUpCardDTO(), 0 );        controller.setCard( new PowerUpCardDTO(), 1 );        controller.setCard( new PowerUpCardDTO(), 2 );
      primaryStage.setTitle( "ADRENALINA" );
      primaryStage.setScene( new Scene( root ) );
      primaryStage.show();
   }
}
