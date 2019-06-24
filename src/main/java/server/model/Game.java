package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import common.enums.PcColourEnum;
import common.enums.SquareColourEnum;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;
import server.model.deserializers.GameBoardDeserializer;
import server.model.squares.Square;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static common.Constants.FINAL_FRENZY;

/**
 * This class represents an ADRENALINE game
 */
public class Game {

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private GameBoard gameBoard;
    private Set<Pc> pcs;
    private Deck<WeaponCard> weaponsDeck;
    private Deck<PowerUpCard> powerUpsDeck;
    private Deck<AmmoTile> ammoDeck;
    private boolean finalFrenzy;


    private Game() {
        this.pcs = new HashSet<>();
        this.weaponsDeck = new Deck<>();
        this.powerUpsDeck = new Deck<>();
        this.ammoDeck = new Deck<>();
    }


    public static Game getGame(){
        Game game = new Game();
        game.initDecks();
        return game;
    }


    /**
     * Loads a map given the index
     * @param numberOfMap the index of the map
     */
    public void initMap(int numberOfMap) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(GameBoard.class, new GameBoardDeserializer());
            Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

            JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/weapons.json"));
            JsonArray gameBoards = customGson.fromJson(reader, JsonArray.class);
            gameBoard = customGson.fromJson(gameBoards.get(numberOfMap), GameBoard.class);

            gameBoard.initSquares(weaponsDeck, ammoDeck);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Inits the KillShotTrack with the given number of skulls
     * @param numberOfSkulls The desired number of skulls
     */
    public void initKillShotTrack(int numberOfSkulls){
        gameBoard.initKillShotTrack(numberOfSkulls);
    }


    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }


    public void setFinalFrenzy(boolean finalFrenzy) {

        this.finalFrenzy = finalFrenzy;

        //notify listeners
        changes.firePropertyChange(FINAL_FRENZY, !finalFrenzy, finalFrenzy);
    }


    private void initDecks() {
        initWeaponsDeck();
        initPowerUpsDeck();
        initAmmoDeck();
    }


    private void initWeaponsDeck() {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
            Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

            Type weaponsType = new TypeToken<ArrayList<WeaponCard>>() {
            }.getType();
            JsonReader reader;

            reader = new JsonReader(new FileReader("src/main/resources/json/weapons.json"));
            ArrayList<WeaponCard> weapons = customGson.fromJson(reader, weaponsType);

            weapons.forEach(w -> {
                w.init();
                weaponsDeck.add(w);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void initPowerUpsDeck(){
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
            Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

            Type powerUpType = new TypeToken<ArrayList<PowerUpCard>>() {
            }.getType();
            JsonReader reader;

            reader = new JsonReader(new FileReader("src/main/resources/json/powerUps.json"));
            ArrayList<PowerUpCard> powerUps = customGson.fromJson(reader, powerUpType);

            powerUps.forEach(p -> powerUpsDeck.add(p));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void initAmmoDeck(){
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            Type ammoTileType = new TypeToken<ArrayList<AmmoTile>>(){}.getType();
            JsonReader reader;

            reader = new JsonReader(new FileReader("src/main/resources/json/ammoTiles.json"));
            ArrayList<AmmoTile> ammoTiles = gson.fromJson(reader, ammoTileType);
            ammoTiles.forEach(AmmoTile::setHasPowerUp);
            
            ammoTiles.forEach(a -> ammoDeck.add(a));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Square getSquare(int row, int col){
        return gameBoard.getSquare(row, col);
    }


    public Square getSpawnPoint(SquareColourEnum requiredColour) {
        return gameBoard.getSpawnPoint(requiredColour);
    }


    public void setTargetableSquares(Set<Square> targetableSquares, boolean isTargetable){
        if (targetableSquares.isEmpty())
            return;
        targetableSquares.forEach(s -> s.setTargetable(isTargetable));
    }


    public void addPc(Pc pc) {
        this.pcs.add(pc);
    }


    PowerUpCard drawPowerUp(){
        return powerUpsDeck.draw();
    }


    public boolean scoreDeath(Pc deadPc, boolean doubleKill) {
        scoringPoints(deadPc, doubleKill);
        boolean turnIntoFinalFrenzy = scoreOnKillShotTrack(deadPc);
        if (!isFinalFrenzy())
            deadPc.getPcBoard().increaseNumberOfDeaths();
        return turnIntoFinalFrenzy;
    }


    private void scoringPoints(Pc deadPc, boolean doublekill) {
        PcColourEnum [] deadPcDamageTrack = deadPc.getDamageTrack();
        int [] pcValue = deadPc.getPcBoard().getPcValue();
        int pcValueIndex = 0;
        boolean allPointsAssigned = false;
        int [] numOfDamages = new int [5];
        int max;

        if (!isFinalFrenzy()) {
            pcValueIndex = deadPc.getPcBoard().getNumOfDeaths();

            //assigns the first blood point, only if the board is not flipped
            pcs.stream().filter(pc -> pc.getColour() == deadPcDamageTrack[0]).findFirst().get().increasePoints(1);
        }
        //assigns an extra point, only if the current player gets a doubleKill
        if (doublekill)
            pcs.stream().filter(pc -> pc.getColour() == deadPcDamageTrack[10]).findFirst().get().increasePoints(1);

        //assign points to each Pc who damaged the deadPc
        for (PcColourEnum colour: deadPcDamageTrack) {
            if (colour != null)
                numOfDamages[colour.ordinal()]++;
            else
                break;
        }
        while (!allPointsAssigned) {
            max = 0;
            int maxIndex = 0;
            for (int i = 0; i < 5; i++) {
                if (numOfDamages[i] > max) {
                    max = numOfDamages[i];
                    maxIndex = i;
                }
            }
            if (max != 0) {
                int finalMaxIndex = maxIndex;
                pcs.stream().filter(pc -> pc.getColour().ordinal() == finalMaxIndex).findFirst().get().increasePoints(pcValue[pcValueIndex]);
                if (pcValueIndex != pcValue.length - 1)
                    pcValueIndex++;
                else
                    allPointsAssigned = true;
                numOfDamages[finalMaxIndex] = 0;
            }
            else
                allPointsAssigned = true;
        }
    }

    /**
     * Score the death on The KillShotTrack
     * @param deadPc pc which has received a killshot
     * @return True when the game turns into Final Frenzy mode
     */
    private boolean scoreOnKillShotTrack(Pc deadPc) {
        PcColourEnum [] deadPcDamageTrack = deadPc.getDamageTrack();
        PcColourEnum shooterPcColour = deadPcDamageTrack[10];
        if (gameBoard.killOccurred(shooterPcColour, deadPcDamageTrack[11] != null)) {
            deadPc.flipBoard();
            if (!isFinalFrenzy()) {
                setFinalFrenzy(true);
                return true;
            }
        }
        return false;
    }


    public List<Pc> computeWinner() {
        for (Pc pc: pcs) {
            scoringPoints(pc, false);
        }
        int maxPoints = 0;
        for (Pc pc: pcs) {
            if (pc.getPcBoard().getPoints() > maxPoints)
                maxPoints = pc.getPcBoard().getPoints();
        }
        int finalMaxPoints = maxPoints;
        List<Pc> potentialWinners = pcs.stream().filter(pc -> pc.getPcBoard().getPoints() == finalMaxPoints).collect(Collectors.toList());
        if (potentialWinners.size() != 1){
            HashMap<Pc, Integer> points = new HashMap<>();
            potentialWinners.forEach(pc -> points.put(pc, pointsFromKillShots(pc)));
            int maxPointsOnTrack = 0;
            for (Pc pc: points.keySet()) {
                if (points.get(pc) > maxPointsOnTrack) {
                    maxPointsOnTrack = points.get(pc);
                    potentialWinners = new ArrayList<>();
                    potentialWinners.add(pc);
                } else if (points.get(pc) == maxPointsOnTrack)
                    potentialWinners.add(pc);
            }
        }
        return potentialWinners;
    }


    private Integer pointsFromKillShots(Pc pc){
        return pointsOnKillShotTrack(pc, gameBoard.getKillShotTrack()) + pointsOnKillShotTrack(pc, gameBoard.getFinalFrenzyKillShotTrack());
    }


    private Integer pointsOnKillShotTrack(Pc pc, KillShot[] killShots){
        int points = 0;
        PcColourEnum pcColour = pc.getColour();
        for (KillShot killShot: killShots) {
            if (killShot.getColour() == pcColour){
                points++;
                if(killShot.isOverkilled())
                    points++;
            }
        }
        return points;
    }

    void killOccurred(PcColourEnum killerColour, boolean overkilled) {
        gameBoard.killOccurred(killerColour, overkilled);
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
        gameBoard.addPropertyChangeListener(listener);
        pcs.forEach(pc -> pc.addPropertyChangeListener(listener));
    }

}





