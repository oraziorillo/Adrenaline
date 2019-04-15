package model.weapon_effect;

import model.MapIterator;
import model.Pc;
import model.Tile;
import model.TargetChecker;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public abstract class Action {
    WeaponEffect effect;
    TargetChecker targetChecker;
    LinkedList<Pc> targets;

    /**
     * executes the action
     */
    abstract void apply();

    /**
     * selects the targets which are valid for the current action
     *
     * @return a Set of all possible target Tiles
     */
    public Set<Tile> validTargetTiles() {
        HashSet<Tile> validTargetTiles = new HashSet<>();
        Tile currTile = this.effect.getCard().getDeck().getCurrGame().map[0][0];
        for (MapIterator itr = new MapIterator(currTile); itr.hasNext(); )
            if (targetChecker.isValid(currTile)) {
                validTargetTiles.add(currTile);
                currTile = itr.next();
            }
        if (targetChecker.isValid(currTile)) {
            validTargetTiles.add(currTile);
        }
        return validTargetTiles;
    }
}




class DamageMarksAction extends Action {
    private short damage;
    private short marks;

    DamageMarksAction(short damage, short marks, TargetChecker targetChecker, WeaponEffect effect){
        super();
        this.effect = effect;
        this.targetChecker = targetChecker;
        this.targets = new LinkedList<Pc>();
        this.damage = damage;
        this.marks = marks;
    }

    public Set<Pc> validTargets(){
        HashSet<Pc> validTargets = new HashSet<>();
        for (Tile t : validTargetTiles()) {
            validTargets.addAll(t.getPcs());
        }
        return validTargets;
    }


    @Override
    public void apply() {
        for (Pc pc: targets) {
            if (damage != 0)
                pc.takeDamage(damage);
            if (marks != 0)
                pc.takeMarks(marks);
        }
        targets.clear();
    }

    /**
     * add a set of Pc to the list of the targets of the action
     * @param pcs list containing Pcs on which damage/marks must be applied
     */
    public void addTargets(List<Pc> pcs){
        targets.addAll(pcs);
    }
}



class MovementAction extends Action {
    private int maxDist;
    private Tile destination;

    MovementAction(int maxDist, TargetChecker targetChecker, WeaponEffect effect) {
        super();
        this.effect = effect;
        this.targetChecker = targetChecker;
        this.targets = new LinkedList<>();
        this.maxDist = maxDist;
        this.destination = null;
    }

    /**
     * sets the destination of the move action
     * @param destination tile of destination
     */
    public void setDestination(Tile destination){
        this.destination = destination;
    }

    @Override
    public void apply() {
        for (Pc pc : targets) {
            pc.move(destination.getX(), destination.getY(), maxDist);
        }
        targets.clear();
    }
}

