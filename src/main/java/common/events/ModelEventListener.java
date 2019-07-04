package common.events;

import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface to get updates from the model.
 */
public interface ModelEventListener extends Remote {
    
    /**
     * Notifies a change of the game board
     * @param event the gameboard event
     * @throws RemoteException because of rmi
     */
    void onGameBoardUpdate(GameBoardEvent event) throws RemoteException;
    
    /**
     * Notifies a change of the KillShotTrack
     * @param event the track event
     * @throws RemoteException because of rmi
     */
    void onKillShotTrackUpdate(KillShotTrackEvent event) throws RemoteException;
    
    /**
     * Notifies a change of a pcBoard
     * @param event the pcBoard event
     * @throws RemoteException because of rmi
     */
    void onPcBoardUpdate(PcBoardEvent event) throws RemoteException;
    
    /**
     * Notifies a change of a Pc
     * @param event the pc event
     * @throws RemoteException
     */
    void onPcUpdate(PcEvent event) throws RemoteException;
    
    /**
     * Notifies a change for a square
     * @param event the square event
     * @throws RemoteException because of rmi
     */
    void onSquareUpdate(SquareEvent event) throws RemoteException;
}
