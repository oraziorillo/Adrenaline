package controller;

import model.enumerations.AmmoEnum;
import model.Pc;
import model.PowerUpCard;
import model.Tile;


import java.util.Optional;

public class FirstTurnState extends State{


    @Override
    public boolean spawnPc(Pc pc, int n){
        PowerUpCard powerUp = pc.getPowerUpCard(n);
        AmmoEnum colour = powerUp.getColour();
        Optional<Tile> t = controller.getGame().
                                      getSpawnTiles().
                                      stream().
                                      filter(elem -> elem.getTileColour().ordinal() == colour.ordinal()).
                                      findFirst();
        pc.respawn(t.get());
        pc.moveTo(t.get());
        pc.discardPowerUp(powerUp);
        t.get().addPc(pc);
        return true;
    }

    @Override
    public void nextState() {
        controller.setCurrState(controller.startTurnState);
        controller.setFirstTurn(false);
    }




    /*

    Game currGame;
    private ArrayList<Player> playersInTheGame;

    public FirstTurnState(Game thisGame, ArrayList<Player> players){
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
        pc.moveTo(t.get());
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

     */
}
