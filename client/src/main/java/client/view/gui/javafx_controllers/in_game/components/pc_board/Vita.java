package client.view.gui.javafx_controllers.in_game.components.pc_board;

import common.Constants;
import common.dto_model.KillShotTrackDTO;
import common.dto_model.PcDTO;
import common.enums.PcColourEnum;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;

public class Vita implements MapChangeListener<PcColourEnum, PcDTO>, ChangeListener<KillShotTrackDTO> {
   @FXML private StackPane mainPane;
   @FXML private GridPane marchi;
   @FXML private TilePane danni;
   @FXML private TilePane morti;
   @FXML private ImageView background;
   private PcColourEnum settedColor;
   
   
   public void initialize(){
      danni.prefTileWidthProperty().bind( Bindings.subtract( Bindings.divide( danni.widthProperty(),Constants.LIFE_POINTS+1 ),Bindings.add( danni.hgapProperty(),1 )) );
      danni.prefHeightProperty().bind( danni.prefTileWidthProperty() );
      morti.prefTileWidthProperty().bind( Bindings.subtract( Bindings.divide( morti.widthProperty(),Constants.PC_VALUES.length ),1 ) );
   }
   
   public void setColor(PcColourEnum color){
      this.settedColor = color;
      background.setImage( new Image( PcBoard.DIRECTORY+color.getName().toLowerCase()+"/vita.png",0,
              PcBoard.HEIGHT,true,false ) );
   }
   
   public DoubleProperty fitHeightProperty(){
      return background.fitHeightProperty();
   }
   
   public ReadOnlyDoubleProperty widthProperty(){
      return background.getImage().widthProperty();
   }
   
   @Override
   public void onChanged(Change<? extends PcColourEnum, ? extends PcDTO> change) {
      if(change.wasAdded() && change.getKey().equals( settedColor )){
         updateDamageTrack( change.getValueAdded().getPcBoard().getDamageTrack() );
         updateMarks( change.getValueAdded().getPcBoard().getMarks());
         updateKills( change.getValueAdded().getPcBoard().getNumOfDeaths());
      }
   }
   
   private void updateDamageTrack(PcColourEnum[] track){
      danni.getChildren().clear();
      for(int i=0;i<track.length&&track[i]!=null;i++){
         Circle damage = new Circle(0, Color.valueOf( track[i].toString() ) );
         damage.setStroke( Color.BLACK );
         if(!damage.radiusProperty().isBound())
            damage.radiusProperty().bind( Bindings.divide( danni.prefTileWidthProperty(), 2 ) );   //diameter to radius
         danni.getChildren().add( damage );
      }
   }
   
   private void updateMarks(short[] marks){
      marchi.getChildren().clear();
      int i=0;
      for (PcColourEnum color: PcColourEnum.values()){
         if(!settedColor.equals( color )){
            for(int j=0;j<marks[color.ordinal()];j++){
               Circle circle = new Circle( 0,Color.valueOf( color.toString() ) ) ;
               circle.setStroke( Color.BLACK );
               GridPane.setValignment( circle, VPos.TOP );
               GridPane.setHalignment( circle, HPos.RIGHT );
               circle.radiusProperty().bind(
                   Bindings.min(
                           Bindings.divide(
                                   marchi.widthProperty(),
                                   2*(PcColourEnum.values().length-1)
                           ),
                           Bindings.divide(
                                   marchi.heightProperty(),
                                   4
                           )
                   )
               );
               circle.translateYProperty().bind( Bindings.multiply( -j, Bindings.divide( Bindings.subtract( marchi.heightProperty(),circle.radiusProperty()),Constants.MAX_NUMBER_OF_MARKS_PER_COLOUR )) );
               marchi.add( circle,i,0 );
            }
            i++;
         }
      }
   }
   
   private void updateKills(int killCount){
      morti.getChildren().clear();
      for(int i=0;i<killCount;i++){
         Circle skull = new Circle(0,Color.RED);
         skull.radiusProperty().bind( Bindings.divide( morti.prefTileWidthProperty(),2) );   //diameter to radius
         skull.translateYProperty().bind(
                 Bindings.divide(
                         Bindings.subtract(
                            morti.heightProperty(),
                                 Bindings.multiply(
                                         skull.radiusProperty(),
                                         2
                                 )
                         ),
                         2
                 )
         );
         skull.setStroke( Color.BLACK );
         morti.getChildren().add( skull );
      }
   }
   
   private void test(){
      settedColor = PcColourEnum.GREEN;
      PcColourEnum[] track = new PcColourEnum[Constants.LIFE_POINTS+1];
      Arrays.fill(track,PcColourEnum.YELLOW);
      updateDamageTrack( track );
      updateMarks( new short[]{3,3,3,3,3} );
      updateKills( 6 );
      System.out.println(danni.getChildren());
   }
   
   @Override
   public void changed(ObservableValue<? extends KillShotTrackDTO> obs, KillShotTrackDTO oldV, KillShotTrackDTO newV) {
      if (oldV.getKillShotTrack()[Constants.MAX_KILL_SHOT_TRACK_SIZE].isSkulled() &&   //final frenzy wasn't on
     !newV.getKillShotTrack()[Constants.MAX_KILL_SHOT_TRACK_SIZE].isSkulled() &&  //final frenzy is on now
      danni.getChildren().isEmpty()){   //non ho danni
            background.setImage( new Image( PcBoard.DIRECTORY+settedColor.getName().toLowerCase()+PcBoard.FRENZY_SUBDIR+"vita.png",0, PcBoard.HEIGHT,true,false ) );
            updateKills( 0 ); //if life is swapped, kill count is set to 0
      }
   }
}
