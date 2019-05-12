package controller;

import model.Pc;
import model.SpawnTile;
import model.WeaponCard;

public class CollectWeaponState extends State {

    private static final int MAX_NUMBER_OF_WEAPONS = 3;
    private boolean haveToDrop;
    int weaponToGrabIndex;

    CollectWeaponState(Controller controller) {
        super(controller);
        this.haveToDrop = false;
    }

    public boolean executeOnWeapon(Pc currPc, int n){
        boolean result;
        if(!(currPc.getWeapons().length == MAX_NUMBER_OF_WEAPONS)){
            try {
                currPc.collectWeapon(n);
                controller.toRefillTiles.add(currPc.getCurrTile());
                result = true;
            }
            catch(NullPointerException exception){
                result = false;
            }
        }
        else{
            if(!haveToDrop){
                weaponToGrabIndex = n;
                haveToDrop = true;
                result = true;
            }
            else {
                try {
                    currPc.collectWeapon(weaponToGrabIndex, n);
                    controller.toRefillTiles.add(currPc.getCurrTile());
                    result = true;
                }
                catch(NullPointerException exception){
                    result = false;
                }
                finally {
                    haveToDrop = false;
                }
            }
        }
        return result;
    }


    @Override
    public void nextState() {
        decreaseRemainingActions();
        if (getRemainingActions() == 0) {
            resetRemainingActions();
            controller.setCurrState(controller.endTurn);
        } else
            controller.setCurrState(controller.startTurnState);
    }
}
