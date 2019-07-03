package client.view.gui.view_states;

import client.view.gui.javafx_controllers.in_game.components.Map;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.lobby_events.LobbyEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashSet;

public class SetupState extends ViewState {
   
   private transient HashSet<GameBoardEvent> gameBoardToPass = new HashSet<>();
   private transient HashSet<PcEvent> pcToPass = new HashSet<>();
   private transient HashSet<KillShotTrackEvent> killShotToPass = new HashSet<>();
   private transient DoubleProperty stillChoosing = new SimpleDoubleProperty( Double.NEGATIVE_INFINITY );
   private transient DoubleProperty beforeMyTurn = new SimpleDoubleProperty( Double.NEGATIVE_INFINITY );
   
   SetupState() throws RemoteException {
      super();
      try {
         FXMLLoader loader = new FXMLLoader( Map.class.getResource( "/fxml/gameSetup/setup.fxml" ) );
         Parent root = loader.load();
         setJavafxController( loader.getController() );
         stage.setScene( new Scene( root ) );
         stage.show();
      }catch ( IOException e ){
         IllegalArgumentException e1 = new IllegalArgumentException( "Cannot load fxml file" );
         e1.setStackTrace( e.getStackTrace() );
         throw e1;
      }
      stillChoosing.addListener( (obs, oldV, newV) -> {
         if (newV.intValue() == 0)
            topView.nextState();
      } );
      beforeMyTurn.addListener( getJavafxController() );
   }
   
   @Override
   public ViewState nextState() throws RemoteException {
      InGameState returned = new InGameState();
      for(GameBoardEvent e: gameBoardToPass) returned.onGameBoardUpdate( e );
      for (PcEvent e:pcToPass) returned.onPcUpdate( e );
      for(KillShotTrackEvent e:killShotToPass) returned.onKillShotTrackUpdate( e );
      return new InGameState();
   }
   
   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) {
      super.onKillShotTrackUpdate( event );
      killShotToPass.add( event );
   }
   
   @Override
   public void onPcUpdate(PcEvent event) {
      super.onPcUpdate( event );
      stillChoosing.set( stillChoosing.get()-1 );
      beforeMyTurn.set( beforeMyTurn.get()-1 );
      pcToPass.add( event );
   }
   
   @Override
   public void onGameBoardUpdate(GameBoardEvent event) {
      gameBoardToPass.add( event );
   }
   
   @Override
   public void onSquareUpdate(SquareEvent event) {
      throw new IllegalStateException( "Shouldn't be here" );
   }
   
   @Override
   public void onPcBoardUpdate(PcBoardEvent event) {
      throw new IllegalStateException( "Shouldn't be here" );
   }

   @Override
   public void notifyEvent(LobbyEvent event) {
      if(beforeMyTurn.get()==Double.NEGATIVE_INFINITY && event.getDTO().size()>= 3/*MIN_PLAYER_NUMBER*/){
         beforeMyTurn.set((double)(event.getDTO().size()-1));  //excluded yourself
      }
      stillChoosing.set(event.getDTO().size());
   }
}
