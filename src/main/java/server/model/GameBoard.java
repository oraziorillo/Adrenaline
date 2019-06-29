package server.model;

import com.google.gson.annotations.Expose;
import common.enums.PcColourEnum;
import common.enums.SquareColourEnum;
import server.model.squares.Square;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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


    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public List<Square> getSpawnPoints() {
        return spawnPoints;
    }

    /**
     * assigns the proper deck to each square and the list of listeners
     * @param weaponsDeck deck of weapons
     * @param ammoDeck deck of ammoTile
     */
    void initSquares(Deck<WeaponCard> weaponsDeck, Deck<AmmoTile> ammoDeck) {
        squares.forEach(s -> s.init(weaponsDeck, ammoDeck));
    }

    
    /**
     * Inits the kill shot track with the given number of skulls
     * @param numberOfSkulls the desired number of skulls
     */
    void initKillShotTrack(int numberOfSkulls){
        killShotTrack = new KillShotTrack(numberOfSkulls);
    }

    public KillShotTrack getKillShotTrack() {
        return killShotTrack;
    }

    public KillShot[] getFinalFrenzyKillShotTrackArray() {
        return killShotTrack.getFinalFrenzyKillShotTrack();
    }


    public KillShot[] getKillShotTrackArray() {
        return killShotTrack.getKillShotTrack();
    }

    Square getSquare(int row, int col){
        if (row >= rows || row < 0 || col >= columns || col < 0)
            return null;
        Optional<Square> currSquare = squares.stream()
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


    /**
     * Updates the KillShotTrack with the occurred kill
     * @param killerColour the colour of the killer
     * @param overkilled true if the player was overkilled, see the manual
     * @return True if the game turns into or already is in Final Frenzy mode
     */
    boolean killOccurred(PcColourEnum killerColour, Boolean overkilled){
        return killShotTrack.killOccured(killerColour,overkilled);
    }


    void addModelEventHandler(ModelEventHandler events) {
        squares.forEach(s -> s.addModelEventHandler(events));
    }
}
