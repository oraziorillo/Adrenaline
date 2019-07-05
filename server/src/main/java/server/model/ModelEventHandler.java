package server.model;

import common.enums.PcColourEnum;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;
import server.database.CustomizedHashMap;
import server.database.DatabaseHandler;

import java.rmi.RemoteException;
import java.util.UUID;

public class ModelEventHandler {


    private CustomizedHashMap<UUID, PcColourEnum, ModelEventListener> listeners;

    ModelEventHandler(){
        this.listeners = new CustomizedHashMap<>();
    }


    synchronized void addModelEventListener(UUID playerID, ModelEventListener listener){
        listeners.put(playerID, listener);
    }


    synchronized void addListenerColour(PcColourEnum colour) {
        listeners.insertSK(colour);
    }


    synchronized void removeListener(UUID token) {
        listeners.removeLine(token);
    }


    synchronized void fireEvent(GameBoardEvent event){
        listeners.values().parallelStream().forEach(l -> {
            try {
                l.onGameBoardUpdate(event);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }


    synchronized void fireEvent(KillShotTrackEvent event){
        listeners.values().parallelStream().forEach(l -> {
            try {
                l.onKillShotTrackUpdate(event);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }


    synchronized void fireEvent(PcBoardEvent event){
        PcColourEnum publisherColour = event.getDTO().getColour();
        try {
            listeners.get(publisherColour).onPcBoardUpdate(event);
            for (PcColourEnum colour : listeners.secondaryKeySet()) {
                if (publisherColour != colour) {
                    listeners.get(colour).onPcBoardUpdate(event.censor());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    public synchronized void fireEvent(PcEvent event) {
        PcColourEnum publisherColour = event.getDTO().getColour();
        try {
            listeners.get(publisherColour).onPcUpdate(event);
            for (ModelEventListener listener : listeners.values()) {
                if (publisherColour != listeners.getSecondaryKey(listener)) {
                    listener.onPcUpdate(event.hideSensibleContent());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public synchronized void fireEvent(SquareEvent event) {
        try {
            UUID aPlayerUUID = listeners.getPrimaryAtIndex(0);
            PcColourEnum publisherColour = DatabaseHandler.getInstance().getCurrPlayerColour(aPlayerUUID);
            listeners.get(publisherColour).onSquareUpdate(event);
            for (PcColourEnum colour : listeners.secondaryKeySet()) {
                if (publisherColour != colour) {
                    listeners.get(colour).onSquareUpdate(event.censor());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void notifyDamaged(PcColourEnum damagedColour){
        DatabaseHandler.getInstance().getPlayer(listeners.getPrimaryKey(damagedColour)).notifyDamaged();
    }
}
