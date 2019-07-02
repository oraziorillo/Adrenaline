package client.view.gui.view_states;

import client.view.gui.javafx_controllers.in_game.components.Map;
import common.dto_model.PcDTO;
import common.enums.PcColourEnum;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SetupState extends ViewState {
   
   private GameBoardEvent mapSelection;
   private HashSet<PcColourEnum> choosenColors= new HashSet<>();
   
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
   }
   
   
   @Override
   public void ack(String msg) {
      javafxController.printMessage( msg );
      if("game started".equalsIgnoreCase( msg.trim() ))
         javafxController.setEnabled( true );
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
      if(choosenColors.contains( event.getDTO().getColour() )){
         try {
            topView.nextState();
            topView.onGameBoardUpdate( mapSelection );
            topView.onPcUpdate( event );
         }catch ( RemoteException e ){
            IllegalArgumentException e1 = new IllegalArgumentException( "Cannot load fxml file" );
            e1.setStackTrace( e.getStackTrace() );
            throw e1;
         }
      }
      choosenColors.add( event.getDTO().getColour() );
      
   }
   
   @Override
   public void onGameBoardUpdate(GameBoardEvent event) {
      this.mapSelection = event;
   }
   
   @Override
   public void onSquareUpdate(SquareEvent event) throws RemoteException {
      try {
         topView.nextState();
         topView.onGameBoardUpdate( mapSelection );
         topView.onSquareUpdate( event );
      }catch ( RemoteException e ){
         IllegalArgumentException e1 = new IllegalArgumentException( "Cannot load fxml file" );
         e1.setStackTrace( e.getStackTrace() );
         throw e1;
      }
   }
   
   @Override
   public void onPcBoardUpdate(PcBoardEvent event) throws RemoteException {
      try {
         topView.nextState();
         topView.onGameBoardUpdate( mapSelection );
         topView.onPcBoardUpdate( event );
      }catch ( RemoteException e ){
         IllegalArgumentException e1 = new IllegalArgumentException( "Cannot load fxml file" );
         e1.setStackTrace( e.getStackTrace() );
         throw e1;
      }
   }
}
