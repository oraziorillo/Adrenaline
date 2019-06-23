package client.view.gui.javafx_controllers.components;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Chat {
   @FXML
   private Double MESSAGGI_HEIGHT;
   @FXML
   private VBox messaggi;
   @FXML
   private TextField input;
   @FXML
   private AnchorPane mainPane;
   private Duration duration= new Duration( 300 );
   private ParallelTransition transition;
   private TranslateTransition translate;
   private ScaleTransition scale = new ScaleTransition(duration);
   private boolean isOpened  = true;
   
   public void initialize(){
      translate = new TranslateTransition( duration,messaggi );
      scale = new ScaleTransition( duration,messaggi );
      transition = new ParallelTransition( scale,translate );
      translate.fromYProperty().bind( Bindings.divide(  messaggi.heightProperty(),2) );
      translate.setToY( 0 );
      scale.setFromY( 0 );
      scale.setToY( 1 );
      input.focusedProperty().addListener( (observableValue, oldFocus, newFocus) -> {
         if(newFocus) appear(); else disappear();
      } );
      transition.play();
   }
   @FXML
   public void appear(){
      if(!isOpened) {
         transition.stop();
         transition.setRate( 1 );
         transition.play();
      }
      isOpened=true;
   }
   @FXML
   public void disappear(){
      if(isOpened) {
         transition.stop();
         transition.setRate( -1 );
         transition.play();
      }
      isOpened = false;
   }
   
   private void test(){
      for(int i=0; i<10;i++){
         Label prova = new Label( "Messaggio n°: "+i );
         prova.getStyleClass().add( "labels" );
         messaggi.getChildren().add( prova );
      }
   }
   
   public synchronized void showServerMessage(String message){
      Label added = new Label(message);
      added.setTextFill( Color.RED );
      messaggi.getChildren().add( added );
   }
   
   public synchronized void showUserMessage(String message){
      Label added = new Label(message);
      added.setTextFill( Color.WHITE );
      messaggi.getChildren().add( added );
   }
   
   @FXML
   private synchronized void sendInput(){
      showUserMessage( input.getText() );
      //TODO: actually send the message
      input.clear();
   }
   
}
