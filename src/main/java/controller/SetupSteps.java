package controller;

import model.Enumerations.PcColourEnum;
import model.Game;
import model.Pc;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SetupSteps implements State{
    Game currGame;
    private ArrayList<Player> playersInTheGame;

    public SetupSteps(Game thisGame, ArrayList<Player> players){
        this.currGame = thisGame;
        playersInTheGame = players;
        for (Player p: playersInTheGame) {          //forse da fare dopo
            //creare una view per ogni player
        }
    }

    public void execute(){
        assignPcToPlayer();
        chooseNumberOfSkull();
        currGame.initDecks("jSonName");
        chooseMap("jSonName");      //questo metodo chiama anche initMap che inizializza tutto
    }

    private void chooseMap(String jSonName){
        Player p = playersInTheGame.get(0);
        //usare file Json per avere le configurazioni delle 4 possibili mappe
        p.printOnView("Which map do you want to choose?");
        //qui mostra a schermo le 4 possibili mappe, numerate da 1 a 4
        p.receiveNumber();
        p.printOnView("Good choice. This map is craaaazy!!");
        //una volta ricevuta la risposta, dedurre gli altri parametri (numRighe, numColonne...) dal file jSon
        //currGame.initMap(dati provenienti dal jSon);
    }

    private void chooseNumberOfSkull(){
        int numberOfSkulls;
        Player p = playersInTheGame.get(0);
        p.printOnView("How many skulls do you want to start with?");
        while(true){
            numberOfSkulls = p.receiveNumber();
            if(numberOfSkulls > 4 && numberOfSkulls < 9) break;
        }
        currGame.setKillShotTrack(numberOfSkulls);
    }

    private void assignPcToPlayer(){
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
