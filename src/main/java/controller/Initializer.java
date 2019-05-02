package controller;

import model.Enumerations.PcColourEnum;
import model.Game;
import model.Pc;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Initializer implements State{
    Game currGame;
    view currView;
    private ArrayList<Player> playersInTheGame;
    private State currState;

    public Initializer(Game thisGame, ArrayList<Player> players){
        this.currGame = thisGame;
        playersInTheGame = players;
        for (Player p: playersInTheGame) {
            this.currView = new View(this, newGame);        //costruttore da definire
        }
    }

    public void execute(){
        assignPcToPlayer();
    }


    public void assignPcToPlayer(){
        HashSet<PcColourEnum> colourEnums = new HashSet<>();
        PcColourEnum selectedColour;
        for (PcColourEnum elem: PcColourEnum.values()) {
            colourEnums.add(elem);
        }
        for (Player p: playersInTheGame) {
            selectedColour = chooseColour(p, colourEnums);
            colourEnums.remove(selectedColour);
        }
    }

    private PcColourEnum chooseColour(Player p, HashSet<PcColourEnum> colours){
        Pc character;
        PcColourEnum selectedColour;
        p.printOnView("Choose among different colours");
        //mostra a schermo i 5 possibili giocatori e ne fa scegliere uno al player
        selectedColour = p.receivePcColourEnum();
        character = new Pc(selectedColour, currGame);
        p.setPc(character);
        currGame.addPc(character);
        return selectedColour;
    }
}
