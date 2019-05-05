package controller;

import model.Enumerations.AmmoEnum;
import model.Enumerations.TileColourEnum;
import model.Game;
import model.Pc;
import model.PowerUpCard;
import model.Tile;


import java.util.ArrayList;
import java.util.Optional;

public class FirstTurn implements State {
    Game currGame;
    private ArrayList<Player> playersInTheGame;

    public FirstTurn(Game thisGame, ArrayList<Player> players){
        this.currGame = thisGame;
        this.playersInTheGame = players;
    }

    public void execute(){
        for (Player p: playersInTheGame) {
            performTurn(p);
            //usando il for qui, potremmo anche eliminare il metodo nextTurn()
        }
    }

    private void performTurn(Player p){
        Pc pc;
        Optional<Tile> t;
        AmmoEnum selectedColour;
        pc = p.getPc();
        pc.drawPowerUp();
        pc.drawPowerUp();
        selectedColour = chooseCardToDiscard(p);
        t = currGame.getSpawnTiles().stream().filter(elem -> elem.getTileColour().ordinal() == selectedColour.ordinal()).findFirst();
        pc.moveToTile(t.get());
        t.get().addPc(pc);
        //qui possiamo inserire la frase che ogni giocatore pronuncia a inizio gioco
    }

    private AmmoEnum chooseCardToDiscard(Player p){
        PowerUpCard powerUp;
        p.printOnView("Which powerUp do want to reveal?");
        //evidenzia le sue due carte powerUp
        powerUp = p.receivePowerUpCard();
        p.getPc().discardPowerUp(powerUp);      //metodo lancia eccezione..da gestire
        return powerUp.getColour();
    }
}
