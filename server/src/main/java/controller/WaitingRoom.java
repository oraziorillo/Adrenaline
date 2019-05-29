package controller;

import common.player.Player;
import model.Game;

import javax.swing.*;
import java.util.ArrayList;

public class WaitingRoom {
    private ArrayList<Player> waitingPlayers;
    private Timer timer;
    private static final int TIME = 1000*60*3;

    public WaitingRoom() {
        waitingPlayers = new ArrayList<>();
        timer= new Timer( TIME, actionEvent -> startGame());
        timer.stop();
    }

    public void addPlayer(Player p){
        waitingPlayers.add(p);
        if(waitingPlayers.size() > 2){
            timer.restart();
        }
        if(waitingPlayers.size() == 5){
            timer.stop();
            startGame();
        }
    }
    
    private void startGame(){
        Game g = new Game();
        //add Pcs
        waitingPlayers.clear();
        timer.stop();
    }
}

