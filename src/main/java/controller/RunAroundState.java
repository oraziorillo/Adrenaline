package controller;

import model.Pc;
import model.Tile;

public class RunAroundState extends State{

    public boolean move(Pc pc, Tile tile){
        pc.getCurrTile().removePc(pc);
        pc.moveTo(tile);
        tile.addPc(pc);
        return true;
    }

    @Override
    public void nextState() {

    }
}
