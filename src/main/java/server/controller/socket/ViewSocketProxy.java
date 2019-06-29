package server.controller.socket;

import client.controller.socket.AbstractSocketProxy;
import com.google.gson.Gson;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemoteView;

import java.io.IOException;
import java.net.Socket;

import static common.Constants.REGEX;
import static common.enums.ViewMethodsEnum.*;

public class ViewSocketProxy extends AbstractSocketProxy implements RemoteView, ModelEventListener {


   ViewSocketProxy(Socket socket) throws IOException {
      super(socket);
   }


   @Override
   public void ack(String message) {
      out.println(ACK + REGEX + message.replaceAll(System.lineSeparator(), REGEX));
      out.flush();
   }


   @Override
   public void onGameBoardUpdate(GameBoardEvent event) {
      out.println(ON_GAME_BOARD_UPDATE + REGEX + new Gson().toJson(event));
      out.flush();
   }

   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) {
      out.println(ON_KILL_SHOT_TRACK_UPDATE + REGEX + new Gson().toJson(event));
      out.flush();
   }

   @Override
   public void onPcBoardUpdate(PcBoardEvent event) {
      out.println(ON_GAME_BOARD_UPDATE + REGEX + new Gson().toJson(event));
      out.flush();
   }

   @Override
   public void onPcUpdate(PcEvent event) {
      out.println(ON_PC_UPDATE + REGEX + new Gson().toJson(event));
      out.flush();
   }

   @Override
   public void onSquareUpdate(SquareEvent event) {
      out.println(ON_SQUARE_UPDATE + REGEX + new Gson().toJson(event));
      out.flush();
   }

   @Override
   public ModelEventListener getListener() {
      return this;
   }
}
