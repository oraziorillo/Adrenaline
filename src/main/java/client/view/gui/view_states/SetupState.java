package client.view.gui.view_states;

import client.view.gui.javafx_controllers.in_game.components.Map;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.lobby_events.LobbyEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class SetupState extends ViewState {
   
   private transient GameBoardEvent mapSelection;
   private transient DoubleProperty stillChoosing = new SimpleDoubleProperty( Double.NEGATIVE_INFINITY );
   private transient DoubleProperty beforeMyTurn = new SimpleDoubleProperty( Double.NEGATIVE_INFINITY );
   
   SetupState(Stage stage, RemotePlayer player, HostServices hostServices, List<String> previousAcks) throws RemoteException {
      super( stage, player, hostServices, previousAcks);
      try {
         FXMLLoader loader = new FXMLLoader( Map.class.getResource( "/fxml/gameSetup/setup.fxml" ) );
         Parent root = loader.load();
         javafxController = loader.getController();
         javafxController.setPlayer( player );
         javafxController.setHostServices( hostServices );
         for (String ack : previousAcks)
            javafxController.ack( ack );
         stage.setScene( new Scene( root ) );
         stage.show();
      }catch ( IOException e ){
         IllegalArgumentException e1 = new IllegalArgumentException( "Cannot load fxml file" );
         e1.setStackTrace( e.getStackTrace() );
         throw e1;
      }
      stillChoosing.addListener( (obs, oldV, newV) -> {
         if (newV.intValue() == 0) {
            topView.nextState();
            try {
               topView.onGameBoardUpdate( mapSelection );
            } catch ( RemoteException ignored ) {}
         }
      } );
      beforeMyTurn.addListener( javafxController );
   }
   
   
   @Override
   public void ack(String msg) {
      javafxController.ack( msg );
   }
   
   @Override
   public ViewState nextState() throws RemoteException {
      return new InGameState(stage,player,hostServices, previousAcks );
   }
   
   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) {
      javafxController.onKillShotTrackUpdate( event );
   }
   
   @Override
   public void onPcUpdate(PcEvent event) {
      javafxController.onPcUpdate( event );
      stillChoosing.set( stillChoosing.get()-1 );
      beforeMyTurn.set( beforeMyTurn.get()-1 );
   }
   
   @Override
   public void onGameBoardUpdate(GameBoardEvent event) {
      this.mapSelection = event;
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
      if(beforeMyTurn.get()==Double.NEGATIVE_INFINITY ){//TODO: &&lobbyDTO.isSetup()
         beforeMyTurn.set((double)(event.getDTO().size()-1));
      }
      stillChoosing.set(event.getDTO().size());
   }
}
