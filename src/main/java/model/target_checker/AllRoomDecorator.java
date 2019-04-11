package model.target_checker;

import model.Pc;
import model.Tile;

import java.util.ArrayList;

public class AllRoomDecorator extends TargetCheckerDecorator {

    public AllRoomDecorator(TargetChecker decorated) {
        super(decorated);
    }

    @Override
        public boolean isValid(ArrayList<Pc> pcs, Tile startingTile) {
        return base.isValid(pcs, startingTile)&&sameRoom(pcs)&&allInRoom(pcs);

    }

    private boolean sameRoom(ArrayList<Pc> pcs){
        boolean sameRoom=true;
        for(Pc c: pcs){
            if(c.getCurrentTile().getRoomColour()!= pcs.get(0).getCurrentTile().getRoomColour()){
                sameRoom=false;
                break;
            }
        }
        return sameRoom;
    }

    private boolean allInRoom(ArrayList<Pc> c){
        boolean allInRoom=true;

        for(Pc pc :c){
            for (Tile tile: pc.getCurrentTile().getVisibles()){
                if(tile.getRoomColour()== pc.getCurrentTile().getRoomColour()&&!c.containsAll(tile.getPcs())){
                        allInRoom=false;
                        break;
                }
            }
        }
        return allInRoom;

    }
}
