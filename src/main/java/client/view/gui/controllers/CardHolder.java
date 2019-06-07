package client.view.gui.controllers;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import server.enums.AmmoEnum;
import server.enums.CardinalDirectionEnum;


public class CardHolder {
   @FXML
   public AnchorPane mainPane;
   @FXML
   private FlowPane weapon_box;
   @FXML
   private ImageView background;
   @FXML Double CARD_TRANSLATION;
   private ParallelTransition[] showing = new ParallelTransition[3];
   private RotateTransition[] rotations = new RotateTransition[3];
   private ImageView[] cards = new ImageView[3];
   @FXML
   private final ObjectProperty<CardinalDirectionEnum> cornerProperty = new SimpleObjectProperty<>(CardinalDirectionEnum.NORTH);
   
   public void initialize(){
      Duration duration = new Duration( 500 );
      for(int i=0; i<3;i++){
         int cardIndex = i;
         ImageView card = ( ImageView )weapon_box.getChildren().get( i );
         cards[i]=card;
         card.setOnMouseEntered( (me)-> appear(  cardIndex ) );
         card.setOnMouseExited( (me)-> disappear(cardIndex ) );
         card.onMouseClickedProperty().set( (e)-> System.out.println("clicked") );
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
      String path = "/images/porta_armi_"+color.name().toLowerCase()+".png";
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
            transYMult = -1;
            break;
         case NORTH:
            rotation = 0;
            transYMult = 1;
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
         card.setImage( new Image( "/images/martello_ionico.png", true ) );
      }
   }
}
