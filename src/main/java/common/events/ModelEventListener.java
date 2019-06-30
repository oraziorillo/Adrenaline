package common.events;

import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ModelEventListener extends Remote {

    void onGameBoardUpdate(GameBoardEvent event) throws RemoteException;

    void onKillShotTrackUpdate(KillShotTrackEvent event) throws RemoteException;

    void onPcBoardUpdate(PcBoardEvent event) throws RemoteException;

    void onPcUpdate(PcEvent event) throws RemoteException;

    void onSquareUpdate(SquareEvent event) throws RemoteException;
}
