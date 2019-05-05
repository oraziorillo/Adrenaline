package controller;

import model.AmmoTile;
import model.Game;
import model.Pc;
import model.Tile;

import java.util.ArrayList;
import java.util.HashSet;

public class Turns implements State {
    Game currGame;
    private ArrayList<Player> playersInTheGame;
    private int currPlayerIndex;

    public Turns(Game thisGame, ArrayList<Player> players){
        this.currGame = thisGame;
        this.playersInTheGame = players;
        currPlayerIndex = 0;
    }

    public void setCurrPlayerIndex(int n){              //serve per la persistenza
        if(n < 0 || n > playersInTheGame.size() - 1){
            throw new IllegalArgumentException("This index is not valid!");
        }
        else{
            currPlayerIndex = n;
        }
    }

    public void execute(){
        Player currPlayer = playersInTheGame.get(currPlayerIndex);
        executeAction(currPlayer);
        executeAction(currPlayer);
        //RefillTiles();
        //Reload(currPlayer);
        currGame.nextTurn();        //bisogna cambiare ogni volta il current character
    }

    private void executeAction(Player p){
        int action;
        p.printOnView("Which action do you want to perform?");
        //mostra a schermo le tre opzioni: muovi, raccogli, spara
        //possiamo mostrare a schermo le tre opzioni sotto forma di array e in base all'elemento selezionato riceviamo un indice
        action = p.receiveNumber();
        switch(action){
            case 1:
                runAround(p, 3);
                break;
            case 2:
                grabStuff(p);
                break;
            case 3:
                //shootPeople(p);
        }
        currGame.decreaseRemainingActions();
    }

    private void runAround(Player p, int distance){
        Tile destination;
        HashSet<Tile> possibleDestinations = p.getPc().getCurrTile().atDistance(distance);
        p.showPossibleTiles(possibleDestinations);      //mostra a schermo
        destination = p.receiveTile();
        p.getPc().moveToTile(destination);
    }

    private void grabStuff(Player p){
        Pc currPc = p.getPc();
        int adrenalineLevel = currPc.getAdrenaline();
        HashSet<Tile> possibleTiles;
        if(adrenalineLevel > 0){
            runAround(p, 2);
        }
        else{
            runAround(p, 1);
        }
        //usare un metodo ausiliario per le istruzioni fino a qui?
        collect(p, currPc.getCurrTile());

    }

    /**
     * per questa azione consideriamo tre possibili modi di esecuzione:
     * 1) usiamo instance of in questo metodo e due metodi diversi in ammotile e spawntile
     * 2) usiamo un metodo nel model che alza un eccezione e a seconda dell'eccezione alzata chiamiamo il metodo giusto nel controller
     * 3) chiamiamo un metodo a vuoto la prima volta che prende in ingresso dei parametri nulli e a seconda dei casi chiamiamo poi quello giusto
     * @param p
     * @param collectTile
     */
    private void collect(Player p, Tile collectTile){
        if(collectTile instanceof AmmoTile){
            //pickAmmo()
        }
    }
}
