package controller;

import model.Game;

import java.util.ArrayList;

public class FirstTurn implements State {
    Game currGame;
    private ArrayList<Player> playersInTheGame;

    public FirstTurn(Game thisGame, ArrayList<Player> players){
        this.currGame = thisGame;
        this.playersInTheGame = players;
    }

    public void execute(){

    }
}
