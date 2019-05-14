package controller.states;

import controller.Controller;

public class StartTurnState extends State {

    private State nextState;

    StartTurnState(Controller controller) {
        super(controller);
    }

    public boolean runAround(){
        nextState = new RunAroundState(controller);
        return true;
    }

    public boolean grabStuff(){
        nextState = new GrabStuffState(controller);
        return true;
    }

    public boolean shootPeople(){
        nextState = new ShootPeopleState(controller);
        return true;
    }

    @Override
    public State nextState() {
        return nextState;
    }

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

    public void chooseSquare(){
        Optional<Square> tile1, tile2;
        Player currPlayer = playersInTheGame.get(currPlayerIndex);
        tile1 = executeAction(currPlayer);
        tile2 = executeAction(currPlayer);
        //Reload(currPlayer);
        //RefillTiles(tile1, tile2);
        currGame.nextTurn();        //bisogna cambiare ogni volta il current character
    }

    private Optional<Square> executeAction(Player p){
        int action;
        Optional<Square> tileToRefill = Optional.empty();
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
        Square destination;
        HashSet<Square> possibleDestinations = p.getPc().getCurrSquare().atDistance(distance);
        p.showPossibleTiles(possibleDestinations);      //mostra a schermo
        destination = p.receiveTile();
        p.getPc().moveTo(destination);
    }

    private Square grabStuff(Player p){
        Pc currPc = p.getPc();
        int adrenalineLevel = currPc.getAdrenaline();
        if(adrenalineLevel > 0){
            runAround(p, 2);
        }
        else{
            runAround(p, 1);
        }
        //usare un metodo ausiliario per le istruzioni fino a qui?
        collect(p, currPc.getCurrSquare());
        return currPc.getCurrSquare();
    }


     * per questa azione consideriamo tre possibili modi di esecuzione:
     * 1) usiamo instance of in questo metodo e due metodi diversi in ammotile e spawntile
     * 2) usiamo un metodo nel model che alza un eccezione e a seconda dell'eccezione alzata chiamiamo il metodo giusto nel controller
     * 3) chiamiamo un metodo a vuoto la prima volta che prende in ingresso dei parametri nulli e a seconda dei casi chiamiamo poi quello giusto
     * @param p
     * @param collectTile

    private void collect(Player p, Square collectTile) {
        if(collectTile instanceof AmmoTile){
            AmmoTile ammo;
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
