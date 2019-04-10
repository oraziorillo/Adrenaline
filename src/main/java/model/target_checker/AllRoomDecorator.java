package model.target_checker;

import model.Character;
import model.Tile;

import java.util.ArrayList;

public class AllRoomDecorator extends TargetCheckerDecorator {

    public AllRoomDecorator(TargetChecker decorated) {
        super(decorated);
    }

    @Override
        public boolean isValid(ArrayList<Character> characters, Tile startingTile) {
        return base.isValid(characters,startingTile)&&sameRoom(characters)&&allInRoom(characters);

    }

    private boolean sameRoom(ArrayList<Character> characters){
        boolean sameRoom=true;
        for(Character c:characters){
            if(c.getCurrentTile().getRoomColour()!=characters.get(0).getCurrentTile().getRoomColour()){
                sameRoom=false;
                break;
            }
        }
        return sameRoom;
    }

    private boolean allInRoom(ArrayList<Character> c){
        boolean allInRoom=true;

        for(Character character:c){
            for (Tile tile:character.getCurrentTile().getVisibles()){
                if(tile.getRoomColour()==character.getCurrentTile().getRoomColour()&&!c.containsAll(tile.getCharacters())){
                        allInRoom=false;
                        break;
                }
            }
        }
        return allInRoom;

    }
}
