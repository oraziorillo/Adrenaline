package model;

import enums.PcColourEnum;
import enums.SquareColourEnum;
import model.squares.Square;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Support class for game
 */
class GameBoard {

    private int rows, columns;
    private ArrayList<Square> squares;
    private ArrayList<Square> spawnPoints;
    private KillShot[] killShotTrack;
    private int currentKillShotTrackIndex;
    
    GameBoard(int rows, int columns, ArrayList<Square> squares, int[] doors) {
        this.rows = rows;
        this.columns = columns;
        this.squares = squares;
        this.spawnPoints = new ArrayList<>();
        squares.stream()
                .parallel()
                .filter(Square::isSpawnPoint)
                .forEach(s -> spawnPoints.add(s));

        for (Square s : squares) {

            //initialize an ArrayList of visible colours
            ArrayList<SquareColourEnum> visibleColours = new ArrayList<>();
            visibleColours.add(s.getColour());

            int sId = s.getX() + s.getY() * columns;

            for (int j = 0; j < doors.length; j = j + 2)
                if (doors[j] == sId)
                    visibleColours.add(getSquare(
                            sId / columns,
                            sId % columns
                    ).getColour());

            //then add all squares whose colour is contained in visibleColours to the list of i's visible squares
            squares.stream()
                    .parallel()
                    .filter(x -> visibleColours.contains(x.getColour()))
                    .forEach(s::addVisible);
        }
    }


    /*TODO: se non ci serve, togliere, se ci serve sistemare
     // I paramteri che il metodo riceve sono così strutturati:
     // numero di righe, numero di colonne
     // array di SquareColourEnum che definisce il colore di ogni Square della mappa. Se un dato Square è null, lo sarà anche nell'array
     // array di int per ogni Square della mappa: se int vale 0 corrisponde ad un tile null, se vale 1 corrisponde ad un ammoTile, se vale 2 ad uno spawnTile
     // array di int dove, per ogni tile, per ogni porta che possiede, c'è una coppia di numeri consecutivi che indica il tile corrente e il tile a cui è collegato tramite porta
    void initMap(int rows, int columns, SquareColourEnum[] colourOfMapTile, int[] typeOfTile, int[] doorsInMap){
        ArrayList<SquareColourEnum> tileColourList;
        ArrayList<SquareColourEnum> tempList = new ArrayList<>();
        ArrayList<Integer> doorsList, typeOfTileList;
        tileColourList = (ArrayList<SquareColourEnum>) Arrays.asList(colourOfMapTile);
        doorsList = (ArrayList<Integer>) Arrays.stream(doorsInMap).boxed().collect(Collectors.toList());
        typeOfTileList = (ArrayList<Integer>) Arrays.stream(typeOfTile).boxed().collect(Collectors.toList());
        if(tileColourList.size() != rows*columns || typeOfTileList.size() != rows*columns){
            throw new IllegalArgumentException("This list doesn't have the right dimension");
        }
        map = new Square[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                if (typeOfTileList.getPlayer(i*rows+j) == 1) {
                    map[i][j] = new AmmoSquare(i, j, tileColourList.getPlayer(i*rows+j), ammoDeck);
                }
                else if(typeOfTileList.getPlayer(i*rows+j) == 2){
                    map[i][j] = new SpawnPoint(i, j, tileColourList.getPlayer(i*rows+j), weaponsDeck);
                    spawnPoints.add(map[i][j]);
                }
                else {
                    map[i][j] = null;   //istruzione inserita per chiarezza, ma omissibile
                }
            }
        }
        for(int i = 0; i < rows; i++){
            for( int j = 0; j < columns; j++){
                if(map[i][j] != null) {
                    tempList.add(map[i][j].getColour());
                    while (doorsList.contains(i * rows + j) && doorsList.indexOf(i * rows + j) % 2 == 0) {
                        int k = doorsList.getPlayer(i * rows + j + 1);
                        tempList.add(map[k / rows][k % columns].getColour());
                        doorsList.remove(i * rows + j);
                        doorsList.remove(i * rows + j);
                    }
                }
                for(int m = 0; m < rows; m++){
                    for(int n = 0; n < columns; n++){
                        if(map[m][n] != null && tempList.contains(map[m][n].getColour())){
                            map[i][j].addVisible(map[m][n]);
                        }
                    }
                }
                tempList.clear();
            }
        }
    }
    */
    
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


    Square getSquare(int x, int y){
        if (x >= rows || y >= columns)
            return null;
        Optional<Square> currSquare = squares.stream()
                .parallel()
                .filter(s -> (x == s.getX() && y == s.getY()))
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
