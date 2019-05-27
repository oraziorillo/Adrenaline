package controller;

import controller.player.Player;

import java.util.ArrayList;

public class WaitingRoom {
    private ArrayList<Player> waitingPlayers;
    //private View view;

    public WaitingRoom() {
        waitingPlayers = new ArrayList<>();
        //view = new view();          //da definire
    }

    public void addPlayer(Player p){        //questo metodo può prendere come parametro anche il tipo di connessione del player
        waitingPlayers.add(p);
        if(waitingPlayers.size() > 2){
            //parte il timer; se il timer scade, inizia il gioco
        }
        if(waitingPlayers.size() == 5){
            //fa partire il thread del gioco
            //inizia il gioco
        }

        //se il gioco inizia, waitingPlayers diventa vuoto
    }
    
}
