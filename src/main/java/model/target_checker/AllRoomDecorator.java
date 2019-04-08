package model.target_checker;

import model.Character;
import model.Tile;

import java.util.ArrayList;

public class AllRoomDecorator extends TargetConditionDecorator{

    public AllRoomDecorator(TargetCondition decorated) {
        super(decorated);
    }

    @Override
        public boolean isValid(ArrayList<Character> characters, Tile startingTile) {
        return base.isValid(characters,startingTile)&&sameRoom(characters)&&allInRoom(characters);

    }

    private boolean sameRoom(ArrayList<Character> characters){
        boolean sameRoom=true;
        for(Character c:characters){
            if(c.getPosition().getRoomColour()!=characters.get(0).getPosition().getRoomColour()){
                sameRoom=false;
                break;
            }
        }
        return sameRoom;
    }

    private boolean allInRoom(ArrayList<Character> c){
        boolean allInRoom=true;

        for(Character character:c){
            for (Tile tile:character.getPosition().getVisibles()){
                if(tile.getRoomColour()==character.getPosition().getRoomColour()&&!c.containsAll(tile.getCharacters())){
                        allInRoom=false;
                        break;
                }
            }
        }
        return allInRoom;

    }
}
