package model;

import exceptions.HoleInMapException;

import java.util.HashSet;

public abstract class TargetChecker {
    //TODO togliere attributo currGame e metterlo nel costruttore
    public Game game;

    public void Game(Game game) {
        this.game = game;
    }

    abstract HashSet<Square> validSquares(Square referenceSquare);

}


class EmptyChecker extends TargetChecker{

    //sto metodo era sbagliato, in teoria adesso dovrebbe essere giusto
    //funziona così: cicla la mappa e se getTile solleva l'eccezione la catcha
    //               e in teoria va avanti con la successiva iterazione del ciclo
    public HashSet<Square> validSquares(Square referenceSquare){
        HashSet<Square> temp;
        temp = new HashSet<>();
        for(int i = 0; i < game.map.length; i++){
            for(int j = 0; j < game.map[0].length; j++){
                try {
                    temp.add(game.getTile(i, j));
                } catch (HoleInMapException e) {
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }
}

