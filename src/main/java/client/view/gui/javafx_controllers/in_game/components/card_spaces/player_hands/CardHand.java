package client.view.gui.javafx_controllers.in_game.components.card_spaces.player_hands;

import client.view.gui.ImageCache;
import client.view.gui.javafx_controllers.in_game.components.weapons.GeneralWeapon;
import common.dto_model.AbstractCardDTO;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;

public abstract class CardHand <T extends AbstractCardDTO> {
   @FXML protected HBox mainPane = new HBox(10);
   protected GeneralWeapon[] weaponControllers;
   protected TranslateTransition[] transitions;
   protected final Duration duration = new Duration(300);
   @FXML protected Double hiddenFraction = .62962963;
   protected AbstractCardDTO[] cards;
   protected RemotePlayer player;
   protected PcColourEnum playerColor;
   private static final String FXML = "/fxml/inGame/weapons/general_weapon.fxml";
   
   protected CardHand(int arraySize){
      weaponControllers = new GeneralWeapon[arraySize];
      transitions = new TranslateTransition[arraySize];
      cards = new AbstractCardDTO[arraySize];
      try {
         for (int i = 0; i < arraySize; i++) {
            FXMLLoader loader = new FXMLLoader( getClass().getResource( FXML ) );
            mainPane.getChildren().add( loader.load() );
            weaponControllers[i] = loader.getController();
         }
      } catch ( IOException e ) {
         IOException e1 = new IOException( "Can't load fxml" );
         e1.setStackTrace( e.getStackTrace() );
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e1 );
      }
   }
   
   public void initialize() {
      mainPane.translateYProperty().bind( Bindings.multiply( mainPane.heightProperty(),hiddenFraction ) );
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
   
   protected void appear(int cardIndex){
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
   
   protected void setCards(T[] newCards){
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
            cardImage.background.setImage( ImageCache.loadImage( newCard.getImagePath(), -1 ) );
            transitions[i].setNode( cardImage.mainPane );
         }
      }
   }
   
   public void setPlayer(RemotePlayer player){
      this.player = player;
      for(GeneralWeapon w: weaponControllers)
         if(w!=null)
            w.setPlayer( player );
   }
   
   public AbstractCardDTO[] getCards() {
      return cards;
   }
   
   public Node getNode(){
      return mainPane;
   }
   
   public void deselectAll() {
      for(Node n:mainPane.getChildren())
         n.setEffect( null );
      for(GeneralWeapon gw:weaponControllers)
         gw.deselect();
   }
}

