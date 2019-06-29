package common.events;

import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;

import java.io.IOException;

public interface ModelEventListener {

    void onGameBoardUpdate(GameBoardEvent event) throws IOException;

    void onKillShotTrackUpdate(KillShotTrackEvent event) throws IOException;

    void onPcBoardUpdate(PcBoardEvent event) throws IOException;

    void onPcUpdate(PcEvent event) throws IOException;

    void onSquareUpdate(SquareEvent event) throws IOException;
}
