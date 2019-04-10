package model.target_checker;

import model.Game;
import model.Tile;
import model.Character;

import java.util.HashSet;

public class MinDistanceDecorator extends  TargetCheckerDecorator{
    boolean valid = false;
    public MinDistanceDecorator(Character character){
        this.thisCharacter = character;
    }

    public boolean isValid(Character possibleTargetCharacter, int minDistance) {
        game = new Game();
        boolean valid = false;
        Tile actionTile;
        actionTile = game.getCurrentCharacter().getCurrentTile();
        if(minDistance == 1){
            if(possibleTargetCharacter.getCurrentTile() == actionTile){
                valid == false;
            }
        }
        else if(minDistance == 2){

        }


        return
    }


}
