package model;

import java.util.HashSet;

public abstract class TargetChecker {
    //TODO togliere attributo game e metterlo nel costruttore
    public Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    abstract HashSet<Tile> validTiles ();

}


class EmptyChecker extends TargetChecker{

    public HashSet<Tile> validTiles (){
        HashSet<Tile> temp;
        temp = new HashSet<Tile>();
        for(int i = 0; i < game.map.length; i++){
            for(int j = 0; j < game.map[0].length; j++){
                if(game.map[i][j] != null){
                    temp.add(game.map[i][j]);
                }
            }
        }
        return temp;
    }
}

