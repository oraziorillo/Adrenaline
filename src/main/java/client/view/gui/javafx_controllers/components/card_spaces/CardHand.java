package client.view.gui.javafx_controllers.components.card_spaces;

import common.dto_model.AbstractCardDTO;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class CardHand <T extends AbstractCardDTO>{
   @FXML
   StackPane mainPane;
   @FXML
   HBox cardBox;
   @FXML
   ImageView card0;
   @FXML
   ImageView card1;
   @FXML ImageView card2;
   private ImageView[] cardsImages;
   private TranslateTransition[] transitions = new TranslateTransition[3];
   private Duration duration = new Duration(300);
   @FXML Double hiddenFraction;
   //TODO: inizia a usare general weapon
   private AbstractCardDTO[] cards= new AbstractCardDTO[3];
   
   public void initialize(){
      cardsImages = new ImageView[]{card0,card1,card2};
      for( int i = 0; i<3; i++ ){
         int j=i;
         TranslateTransition transition = new TranslateTransition( duration );
         transitions[i] = transition;
         transition.setFromY( 0 );
         cardsImages[i].imageProperty().addListener( (obs, old, newImg) -> transition.toYProperty().bind( Bindings.multiply( newImg.heightProperty(), -hiddenFraction )) );
         cardsImages[i].setOnMouseEntered( e->appear(  j ) );
         cardsImages[i].setOnMouseExited( e->disappear( j ) );
      }
   }
   
   private void appear(int cardIndex){
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
      if(newCards==null) return;
      if(newCards.length!=cardsImages.length){
         throw new IllegalArgumentException( "setting "+newCards.length+" cards in a "+cardsImages.length+" sized space" );
      }
      for(int i = 0; i<newCards.length;i++){
         ImageView cardImage = cardsImages[i];
         T newCard = newCards[i];   //TODO: add support for null values
         cards[i] = newCard;
         cardImage.setImage( new Image( newCard.getImagePath(),true) );
         cardImage.getImage().heightProperty().addListener( (observableValue, aDouble, t1) ->{
            mainPane.setTranslateY( t1.doubleValue() * hiddenFraction );
            mainPane.setMaxWidth( (card0.getImage().getWidth() * cards.length) + (cardBox.getSpacing() * cards.length-1 ) );
         } );
         transitions[i].setNode( cardImage );
      }
   }
   
   
}

