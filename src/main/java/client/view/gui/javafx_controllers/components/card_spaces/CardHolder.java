package client.view.gui.javafx_controllers.components.card_spaces;

import common.dto_model.SquareDTO;
import common.dto_model.WeaponCardDTO;
import common.remote_interfaces.RemotePlayer;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import common.enums.AmmoEnum;
import common.enums.CardinalDirectionEnum;

import java.io.IOException;


public class CardHolder implements MapChangeListener <SquareDTO,SquareDTO>{
   @FXML
   public AnchorPane mainPane;
   @FXML
   private FlowPane weaponBox;
   @FXML
   private ImageView background;
   @FXML Double CARD_TRANSLATION;
   private ParallelTransition[] showing = new ParallelTransition[3];
   private RotateTransition[] rotations = new RotateTransition[3];
   private ImageView[] cards = new ImageView[3];
   @FXML
   private final ObjectProperty<CardinalDirectionEnum> cornerProperty = new SimpleObjectProperty<>(CardinalDirectionEnum.NORTH);
   private RemotePlayer player;
   
   public void initialize(){
      Duration duration = new Duration( 500 );
      for(int i=0; i<3;i++){
         int cardIndex = i;
         ImageView card = ( ImageView ) weaponBox.getChildren().get( i );
         cards[i]=card;
         card.setOnMouseEntered( e-> appear(  cardIndex ) );
         card.setOnMouseExited( e-> disappear(cardIndex ) );
         card.setOnMouseClicked( e-> {
            try {
               player.chooseWeaponOnSpawnPoint( cardIndex );
            } catch ( IOException ex ) {
               try {
                  player.quit();
               } catch ( IOException ignored ){}
            }
         } );
         TranslateTransition trans = new TranslateTransition( duration );
         rotations[i] = new RotateTransition( duration );
         trans.setToY( -CARD_TRANSLATION );
         trans.setFromY( card.getTranslateY() );
         rotations[i].setFromAngle( card.getRotate() );
         rotations[i].setToAngle( -mainPane.getRotate() );
         showing[i] = new ParallelTransition( card, trans, rotations[i] );
      }
      test();
   }
   
   public void setBackgroundColor(AmmoEnum color){
      String path = "/images/card_holders/"+color.name().toLowerCase()+".png";
      Image img = new Image( path,true );
      background.setImage( img );
   }
   
   private void disappear(int cardIndex){
      Transition current = showing[cardIndex];
      cards[cardIndex].setViewOrder( 0 );
      current.stop();
      current.setRate( -1 );
      current.play();
   }
   
   private void appear(int cardIndex){
      Transition current = showing[cardIndex];
      cards[cardIndex].setViewOrder( -1 );
      current.stop();
      current.setRate( 1 );
      current.play();
   }
   
   public void setCorner(CardinalDirectionEnum corner) {
      double rotation;
      double transXMult = 0;
      double transYMult = 0;
      mainPane.translateXProperty().unbind();
      switch (corner){
         case WEST:
            rotation = 270;
            transXMult = -1;
            break;
         case EAST:
            rotation= 90;
            transXMult = 1;
            break;
         case SOUTH:
            rotation = 180;
            transYMult = 1;
            break;
         case NORTH:
            rotation = 0;
            transYMult = -1;
            break;
            default:
               throw new IllegalArgumentException( "Only 4 cardinal direction exists. "+ corner +" is not one of those" );
      }
      mainPane.setRotate( rotation );
      for(RotateTransition r: rotations){
         double cardRotation = (rotation+180)>360?rotation-180:rotation+180;
         r.setToAngle(cardRotation);
      }
      mainPane.translateXProperty().bind(Bindings.multiply( Bindings.add( mainPane.heightProperty(), -CARD_TRANSLATION ), 0.5*transXMult));
      mainPane.setTranslateY( -CARD_TRANSLATION*transYMult  );
      this.cornerProperty.set( corner );
   }
   
   public void test(){
      setBackgroundColor( AmmoEnum.RED );
      for(ImageView card: cards) {
         card.setImage( new Image( "/images/weapons/martello_ionico.png", true ) );
      }
   }
   
   public void setCards(WeaponCardDTO[] cards){
      if(cards.length!=this.cards.length){
         throw new IllegalArgumentException( "You must set "+this.cards.length+" cards at time" );
      }
      for(int i=0;i<cards.length;i++){
         int forLambda=i;
         ImageView card = this.cards[i];
         card.setImage( new Image( cards[i].getPathImage(),true ) );
         card.setOnMouseClicked( e-> {
            try {
               player.chooseWeaponOnSpawnPoint( forLambda );
            } catch ( IOException ex ) {
               //TODO: call error in InGameState
            }
         } );
      }
   }
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;
   }
   
   @Override
   public void onChanged(Change<? extends SquareDTO, ? extends SquareDTO> change) {
      if (change.wasAdded()) {
         if(true) {//TODO: controlla che lo spawnpoint sia giusto)
            this.setCards( change.getValueAdded().getWeapons() );
         }
      }
   }
}
