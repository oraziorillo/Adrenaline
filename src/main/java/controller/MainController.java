package controller;

import model.Game;

import java.util.ArrayList;

public class MainController {
    Game currGame;
    view currView;          //è necessario creare una view per ogni player..??
    private ArrayList<Player> playersInTheGame;
    private State initialization;
    private State firstTurn;
    private State turns;
    private State finalFrenzy;
    private State selectWinner;
    private State currState;

    public MainController(String jSonName, ArrayList<Player> players){
        this.currGame = new Game(String jSonName);
        this.playersInTheGame = new ArrayList<>();
        playersInTheGame.addAll(players);
        initialization = new Initializer(this.currGame, this.playersInTheGame);
        firstTurn = new FirstTurn(this.currGame, this.playersInTheGame);
        turns = new Turns(this.currGame, this.playersInTheGame);
        finalFrenzy = new FinalFrenzy(this.currGame, this.playersInTheGame);
        selectWinner = new SelectWinner(this.currGame, this.playersInTheGame);
    }
}
