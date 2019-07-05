package client.gui.view.javafx_controllers.in_game.components.card_spaces;

import client.gui.ImageCache;
import client.gui.view.javafx_controllers.in_game.InGameController;
import common.dto_model.SquareDTO;
import common.dto_model.WeaponCardDTO;
import common.enums.SquareColourEnum;
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
   @FXML public AnchorPane mainPane;
   @FXML private FlowPane weaponBox;
   @FXML private ImageView background;
   @FXML Double CARD_TRANSLATION;
   @FXML private final ObjectProperty<CardinalDirectionEnum> cornerProperty = new SimpleObjectProperty<>(CardinalDirectionEnum.NORTH);
   private ParallelTransition[] showing = new ParallelTransition[3];
   private RotateTransition[] rotations = new RotateTransition[3];
   private ImageView[] cards = new ImageView[3];
   private RemotePlayer player;
   private SquareColourEnum squareColor;

   public void initialize(){
      Duration duration = new Duration( 500 );
      for(int i=0; i<3;i++){
         int cardIndex = i;
         ImageView card = ( ImageView ) weaponBox.getChildren().get( i );
         cards[i]=card;
         card.setTranslateY( CARD_TRANSLATION );
         card.setOnMouseEntered( e-> appear(  cardIndex ) );
         card.setOnMouseExited( e-> disappear(cardIndex ) );
         card.setOnMouseClicked( e-> chooseWeaponOnSpawnPoint( cardIndex ) );
         TranslateTransition trans = new TranslateTransition( duration );
         trans.setToY( -CARD_TRANSLATION );
         trans.setFromY( card.getTranslateY() );
         rotations[i] = new RotateTransition( duration );
         rotations[i].setFromAngle( card.getRotate() );
         rotations[i].setToAngle( -mainPane.getRotate() );
         showing[i] = new ParallelTransition( card, trans, rotations[i] );
      }
   }

   public void setColor(AmmoEnum color){
      String path = "/images/card_holders/"+color.name().toLowerCase()+".png";
      Image img = ImageCache.loadImage( path,-1 );
      background.setImage( img );
      squareColor = SquareColourEnum.valueOf( color.name() );
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
         double cardRotation = ((rotation+180)>360?rotation-180:rotation+180);
         r.setToAngle(cardRotation);
      }
      mainPane.translateXProperty().bind(Bindings.multiply( Bindings.add( mainPane.heightProperty(), -CARD_TRANSLATION ), 0.5*transXMult));
      mainPane.setTranslateY( -CARD_TRANSLATION*transYMult  );
      this.cornerProperty.set( corner );
   }

   public void setCards(WeaponCardDTO[] cards){
      if(cards.length!=this.cards.length){
         throw new IllegalArgumentException( "You must set "+this.cards.length+" cards at time" );
      }
      for(int i=0;i<cards.length;i++){
         ImageView card = this.cards[i];
         if(cards[i]!=null) {
            int forLambda = i;
            card.setImage( new Image( cards[i].getImagePath(), true ) );
            card.setOnMouseClicked( e -> chooseWeaponOnSpawnPoint( forLambda ) );
            card.setVisible( true );
         }else {
            card.setVisible( false );
         }
      }
   }

   public void setPlayer(RemotePlayer player) {
      this.player = player;
   }
   
   private void chooseWeaponOnSpawnPoint(int i){
      try{
          player.chooseWeaponOnSpawnPoint(i + 1);
         cards[i].setEffect( InGameController.selectedObjectEffect );
      } catch ( IOException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }

   @Override
   public void onChanged(Change<? extends SquareDTO, ? extends SquareDTO> change) {
      if (change.wasAdded() && change.getValueAdded().getWeapons()!=null&&change.getValueAdded().getColour().equals(this.squareColor ))
            this.setCards( change.getValueAdded().getWeapons() );
   }
   
   
   public void deselectAll() {
      for(ImageView card:cards)
         card.setEffect( null );
   }
}
