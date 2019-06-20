package server.model;

import com.google.gson.annotations.Expose;
import common.enums.PcColourEnum;
import common.enums.SquareColourEnum;
import server.model.squares.Square;
import java.util.*;

/**
 * Support class for game that handles the map and the kill shot track
 */
public class GameBoard {
    @Expose private int rows;
    @Expose private int columns;
    @Expose private List<Square> squares;
    private List<Square> spawnPoints;
    private KillShotTrack killShotTrack;

    public GameBoard(int rows, int columns, List<Square> squares, int[] doors) {
        this.rows = rows;
        this.columns = columns;
        this.squares = squares;
        this.spawnPoints = new ArrayList<>();
        initVisibleLists(doors);
    }


    private void initVisibleLists(int[] doors) {
        for (Square s : squares) {

            if (s.isSpawnPoint())
                spawnPoints.add(s);

            //initialize an ArrayList of visible colours
            HashSet<Integer> visibleColours = new HashSet<>();
            visibleColours.add(s.getColour().ordinal());

            int sId = s.getRow() * columns + s.getCol();

            for (int j = 0; j < doors.length; j = j + 2)
                if (doors[j] == sId)
                    visibleColours.add(getSquare(
                            doors[j + 1] / columns,
                            doors[j + 1] % columns
                    ).getColour().ordinal());

            //then add all squares whose colour is contained in visibleColours to the list of i's visible squares
            squares.stream()
                    .filter(x -> visibleColours.contains(x.getColour().ordinal()))
                    .forEach(s::addVisible);
        }
    }


    void assignProperDeckToEachSquare(Deck<WeaponCard> weaponsDeck, Deck<AmmoTile> ammoDeck) {
        squares.forEach(s -> s.assignDeck(weaponsDeck, ammoDeck));
    }

    
    /**
     * Inits the kill shot track with the given number of skulls
     * @param numberOfSkulls the desired number of skulls
     */
    void initKillShotTrack(int numberOfSkulls){
        killShotTrack = new KillShotTrack(numberOfSkulls);
    }


    public KillShot[] getFinalFrenzyKillShotTrack() {
        return killShotTrack.getFinalFrenzyKillShotTrack();
    }


    public KillShot[] getKillShotTrack() {
        return killShotTrack.getKillShotTrack();
    }


    Square getSquare(int row, int col){
        if (row >= rows || col >= columns)
            return null;
        Optional<Square> currSquare = squares.stream()
                .parallel()
                .filter(s -> (col == s.getCol() && row == s.getRow()))
                .findFirst();
        return currSquare.orElse(null);
    }
    

    Square getSpawnPoint(SquareColourEnum requiredColour){
        for (Square s : spawnPoints) {
            if (s.getColour().equals(requiredColour))
                return s;
        }
        return null;
    }


    boolean killOccured(PcColourEnum killerColour, Boolean overkilled){
        return killShotTrack.killOccured(killerColour,overkilled);
    }
}
