package server.controller.socket;

import com.google.gson.Gson;
import common.AbstractSocketProxy;
import common.dto_model.GameDTO;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.lobby_events.LobbyEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.requests.Request;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemoteView;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

import static common.Constants.ARGS_SEPARATOR;
import static common.enums.ViewMethodsEnum.*;

public class ViewSocketProxy extends AbstractSocketProxy implements RemoteView, ModelEventListener {

   private Gson gson = new Gson();


   ViewSocketProxy(Socket socket) throws IOException {
      super(socket);
   }


   @Override
   public void ack(String msg) {
      out.println(ACK + ARGS_SEPARATOR + msg.replaceAll(System.lineSeparator(), ARGS_SEPARATOR));
      out.flush();
   }


   @Override
   public void notifyEvent(LobbyEvent event) {
      out.println(NOTIFY_EVENT + ARGS_SEPARATOR + gson.toJson(event, LobbyEvent.class));
      out.flush();
   }

   @Override
   public void request(Request request) {
      out.println(REQUEST + ARGS_SEPARATOR + gson.toJson(request));
      out.flush();
   }


   @Override
   public void chatMessage(String message) {
      out.println(CHAT_MESSAGE + ARGS_SEPARATOR + message.replaceAll(" ", ARGS_SEPARATOR));
      out.flush();
   }
   
   
   @Override
   public void onGameBoardUpdate(GameBoardEvent event) {
      out.println(ON_GAME_BOARD_UPDATE + ARGS_SEPARATOR + gson.toJson(event));
      out.flush();
   }


   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) {
      out.println(ON_KILL_SHOT_TRACK_UPDATE + ARGS_SEPARATOR + gson.toJson(event));
      out.flush();
   }


   @Override
   public void onPcBoardUpdate(PcBoardEvent event) {
      out.println(ON_PC_BOARD_UPDATE + ARGS_SEPARATOR + gson.toJson(event));
      out.flush();
   }


   @Override
   public void onPcUpdate(PcEvent event) {
      out.println(ON_PC_UPDATE + ARGS_SEPARATOR + gson.toJson(event));
      out.flush();
   }


   @Override
   public void onSquareUpdate(SquareEvent event) {
      out.println(ON_SQUARE_UPDATE + ARGS_SEPARATOR + gson.toJson(event));
      out.flush();
   }


   @Override
   public ModelEventListener getListener() {
      return this;
   }
   
   @Override
   public void resumeGame(GameDTO game) {
      out.println( RESUME_GAME + ARGS_SEPARATOR + gson.toJson( game ) );
      out.flush();
   }

   @Override
   public void winners(List<String> winners) throws RemoteException {
      out.println(WINNERS + ARGS_SEPARATOR + gson.toJson(winners));
      out.flush();
   }

   @Override
   public void close() throws RemoteException {
      out.println(CLOSE);
      out.flush();
   }
   
   
}
