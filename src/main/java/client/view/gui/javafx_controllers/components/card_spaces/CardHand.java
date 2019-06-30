package client.view.gui.javafx_controllers.components.card_spaces;

import client.view.gui.javafx_controllers.components.weapons.GeneralWeapon;
import common.dto_model.AbstractCardDTO;
import common.dto_model.PcDTO;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

//TODO: dividi in weaponHand, powerupHand
public class CardHand <T extends AbstractCardDTO> {
   @FXML StackPane mainPane;
   @FXML HBox cardBox;
   @FXML private GeneralWeapon card0Controller, card1Controller,card2Controller;
   private GeneralWeapon[] weaponControllers;
   private TranslateTransition[] transitions = new TranslateTransition[3];
   private Duration duration = new Duration(300);
   @FXML Double hiddenFraction;
   private AbstractCardDTO[] cards= new AbstractCardDTO[3];
   private RemotePlayer player;
   private PcColourEnum playerColor;
   
   public void initialize() {
      weaponControllers = new GeneralWeapon[]{card0Controller,card1Controller,card2Controller};
      for( int i = 0; i<3; i++ ){
         int j=i;
         TranslateTransition transition = new TranslateTransition( duration );
         transitions[i] = transition;
         transition.setFromY( 0 );
         weaponControllers[i].setPlayer( player );
         weaponControllers[i].setEnabled( true );
         weaponControllers[i].background.imageProperty().addListener( (obs, old, newImg) -> transition.toYProperty().bind( Bindings.multiply( newImg.heightProperty(), -hiddenFraction )) );
         weaponControllers[i].mainPane.setOnMouseEntered( e->appear(  j ) );
         weaponControllers[i].mainPane.setOnMouseExited( e->disappear( j ) );
      }
   }
   
   private void appear(int cardIndex){
      choosePowerup( cardIndex );
      Transition current = transitions[cardIndex];
      current.stop();
      current.setRate( 1 );
      current.play();
   }
   
   private void disappear(int cardIndex){
      Transition current = transitions[cardIndex];
      current.stop();
      current.setRate( -1 );
      current.play();
   }
   
   public void setCards(T[] newCards){
      if(newCards==null){
         throw new IllegalArgumentException( "Setting null cards" );
      }
      if(newCards.length!= weaponControllers.length){
         throw new IllegalArgumentException( "setting "+newCards.length+" cards in a "+ weaponControllers.length+" sized space" );
      }
      for(int i = 0; i<newCards.length;i++) {
         if (newCards[i] != null){
            GeneralWeapon cardImage = weaponControllers[i];
            T newCard = newCards[i];
            cards[i] = newCard;
            cardImage.background.setImage( new Image( newCard.getImagePath(), true ) );
            cardImage.background.getImage().heightProperty().addListener( (observableValue, aDouble, t1) -> {
               mainPane.setTranslateY( t1.doubleValue() * hiddenFraction );
               mainPane.setMaxWidth( (weaponControllers[0].background.getImage().getWidth() * cards.length) + (cardBox.getSpacing() * cards.length - 1) );
            } );
            transitions[i].setNode( cardImage.mainPane );
         }
      }
   }
   
   private void choosePowerup(int i){
      try {
         player.choosePowerUp( i );
      } catch ( IOException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
   
   public void setPlayer(RemotePlayer player){
      this.player = player;
      for(GeneralWeapon w: weaponControllers)
         if(w!=null)
            w.setPlayer( player );
   }
}

