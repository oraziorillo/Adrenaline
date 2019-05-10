package controller;

import static controller.Controller.*;

public class StartTurnState extends State {

    @Override
    public boolean changeState(int requestedAction) {
        switch (requestedAction){
            case RUN:
                controller.setCurrState(controller.runState);
                break;
            case GRAB:
                controller.setCurrState(controller.grabState);
                break;
            case SHOOT:
                controller.setCurrState(controller.shootState);
                break;
            default:
                    break;
        }
        return true;
    }

    public void nextState(){}

    /*

    Game currGame;
    private ArrayList<Player> playersInTheGame;
    private int currPlayerIndex;

    public StartTurnState(Game thisGame, ArrayList<Player> players){
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
        Optional<Tile> tile1, tile2;
        Player currPlayer = playersInTheGame.get(currPlayerIndex);
        tile1 = executeAction(currPlayer);
        tile2 = executeAction(currPlayer);
        //Reload(currPlayer);
        //RefillTiles(tile1, tile2);
        currGame.nextTurn();        //bisogna cambiare ogni volta il current character
    }

    private Optional<Tile> executeAction(Player p){
        int action;
        Optional<Tile> tileToRefill = Optional.empty();
        p.printOnView("Which action do you want to perform?");
        //mostra a schermo le tre opzioni: muovi, raccogli, spara
        //possiamo mostrare a schermo le tre opzioni sotto forma di array e in base all'elemento selezionato riceviamo un indice
        action = p.receiveNumber();
        switch(action){
            case 1:
                runAround(p, 3);
                break;
            case 2:
                tileToRefill = Optional.ofNullable(grabStuff(p));
                break;
            case 3:
                //shootPeople(p);
        }
        currGame.decreaseRemainingActions();
        return tileToRefill;
    }

    private void runAround(Player p, int distance){
        Tile destination;
        HashSet<Tile> possibleDestinations = p.getPc().getCurrTile().atDistance(distance);
        p.showPossibleTiles(possibleDestinations);      //mostra a schermo
        destination = p.receiveTile();
        p.getPc().moveTo(destination);
    }

    private Tile grabStuff(Player p){
        Pc currPc = p.getPc();
        int adrenalineLevel = currPc.getAdrenaline();
        if(adrenalineLevel > 0){
            runAround(p, 2);
        }
        else{
            runAround(p, 1);
        }
        //usare un metodo ausiliario per le istruzioni fino a qui?
        collect(p, currPc.getCurrTile());
        return currPc.getCurrTile();
    }


     * per questa azione consideriamo tre possibili modi di esecuzione:
     * 1) usiamo instance of in questo metodo e due metodi diversi in ammotile e spawntile
     * 2) usiamo un metodo nel model che alza un eccezione e a seconda dell'eccezione alzata chiamiamo il metodo giusto nel controller
     * 3) chiamiamo un metodo a vuoto la prima volta che prende in ingresso dei parametri nulli e a seconda dei casi chiamiamo poi quello giusto
     * @param p
     * @param collectTile

    private void collect(Player p, Tile collectTile) {
        if(collectTile instanceof AmmoTile){
            AmmoCard ammo;
            ammo = ((AmmoTile) collectTile).pickAmmo();
            p.getPc().getPcBoard().addAmmos(ammo);
        }
        else{
            int grabIndex;
            int dropIndex;
            SpawnTile tile = (SpawnTile) collectTile;
            p.printOnView("Which weapon do you want to choose?");
            //mostra a schermo o evidenzia le armi nel tile
            grabIndex = p.receiveNumber();
            while(tile.getWeapons()[grabIndex] == null){
                p.printOnView("You didn't make a possible choice!");
                p.printOnView("Which weapon do you want to choose?");
                //mostra a schermo o evidenzia le armi nel tile
                grabIndex = p.receiveNumber();
            }
            if(p.getPc().isFullyArmed()){
                p.printOnView("Which weapon do you want to drop?");
                 dropIndex = p.receiveNumber();
                 while(dropIndex < 0 || dropIndex > 2){
                     p.printOnView("You didn't choose a proper weapon!");
                     p.printOnView("Which weapon do you want to drop?");
                     dropIndex = p.receiveNumber();
                 }
                 p.getPc().collectWeapon(grabIndex, dropIndex);     //fa lo switch e prende la nuova arma
            }
            else{
                p.getPc().collectWeapon(grabIndex);
            }
        }
    }

    private  void shootPeople(Player p){
        p.printOnView("Which weapon do you want to use?");

    }

    */
}
