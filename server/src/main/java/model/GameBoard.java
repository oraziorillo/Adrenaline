package model;

import com.google.gson.annotations.Expose;
import enums.PcColourEnum;
import enums.SquareColourEnum;
import model.squares.Square;
import java.util.*;

/**
 * Support class for game that handles the map and the kill shot track
 */
public class GameBoard {
    @Expose private int rows;
    @Expose private int columns;
    @Expose private List<Square> squares;
    private List<Square> spawnPoints;
    private KillShot[] killShotTrack;
    private int currentKillShotTrackIndex;

    public GameBoard(int rows, int columns, List<Square> squares, int[] doors) {
        this.rows = rows;
        this.columns = columns;
        this.squares = squares;
        this.spawnPoints = new ArrayList<>();
        for (Square s : squares) {

            if(s.isSpawnPoint())
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
                    .parallel()
                    .filter(x -> visibleColours.contains(x.getColour().ordinal()))
                    .forEach(x -> s.addVisible(x));
        }
    }


    public void assignDecks(Deck<WeaponCard> weaponsDeck, Deck<AmmoTile> ammoDeck) {
        squares.forEach(s -> s.assignDeck(weaponsDeck, ammoDeck));
    }

    
    /**
     * Inits the killshottrack with the given number of skulls
     * @param numberOfSkulls the desired number of skulls
     */
    void initKillShotTrack(int numberOfSkulls){
        this.killShotTrack = new KillShot[numberOfSkulls];
        this.currentKillShotTrackIndex = numberOfSkulls - 1;
        for(int i = 0; i < numberOfSkulls; i++)
            killShotTrack[i] = new KillShot();
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
    
    
    /**
     * Updates the KillshotTrack with the occurred kill
     * @param killerColour the colour of the killer
     * @param overkilled se the game manual
     */
    void killOccured(PcColourEnum killerColour, Boolean overkilled){
        killShotTrack[currentKillShotTrackIndex].killOccured(killerColour, overkilled);
        currentKillShotTrackIndex--;
        if(currentKillShotTrackIndex < 0){
            //call observer to notify state change in finalFrenzy;
        }
    }
}
