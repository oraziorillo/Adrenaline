package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import common.dto_model.PcDTO;
import common.enums.PcColourEnum;
import common.enums.SquareColourEnum;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardSetEvent;
import common.events.kill_shot_track_events.FinalFrenzyEvent;
import common.events.kill_shot_track_events.KillShotTrackSetEvent;
import common.events.pc_events.PcColourChosenEvent;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;
import server.model.deserializers.SquareDeserializer;
import server.model.squares.Square;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static common.Constants.FIRST_MAP;
import static common.Constants.LAST_MAP;

/**
 * This class represents an ADRENALINE game
 */
public class Game {

    private ModelEventHandler events = new ModelEventHandler();

    @Expose private GameBoard gameBoard;
    private Set<Pc> pcs;
    @Expose private Deck<WeaponCard> weaponsDeck;
    @Expose private Deck<PowerUpCard> powerUpsDeck;
    @Expose private Deck<AmmoTile> ammoDeck;
    @Expose private boolean finalFrenzy;
    private GameBoard[] preLoadedGameBoards;


    private Game() {
        this.pcs = new HashSet<>();
        this.weaponsDeck = new Deck<>();
        this.powerUpsDeck = new Deck<>();
        this.ammoDeck = new Deck<>();
        this.finalFrenzy = false;
    }


    public static Game getGame() {
        Game game = new Game();
        game.initDecks();
        game.preLoadGameBoards();
        return game;
    }


    private void initDecks(){

        /* da valutare se cambiarlo
        try {
            JsonReader reader;
            Type deckType = new TypeToken<Deck>(){}.getType();
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(Action.class, new ActionDeserializer())
                    .registerTypeAdapter(deckType, new DeckDeserializer<>())
                    .create();

            reader = new JsonReader(new FileReader("src/main/resources/json/weapons.json"));
            weaponsDeck = gson.fromJson(reader, deckType);
            weaponsDeck.getCards().parallelStream().forEach(WeaponCard::init);
            weaponsDeck.shuffle();
            reader.close();

            reader = new JsonReader(new FileReader("src/main/resources/json/powerUps.json"));
            powerUpsDeck = gson.fromJson(reader, deckType);
            powerUpsDeck.shuffle();
            reader.close();

            reader = new JsonReader(new FileReader("src/main/resources/json/ammoTiles.json"));
            ammoDeck = gson.fromJson(reader, deckType);
            ammoDeck.shuffle();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
         */

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        initWeaponsDeck(customGson);
        initPowerUpsDeck(customGson);
        initAmmoDeck(customGson);
    }


    private void initWeaponsDeck(Gson gson) {
        try {

            Type weaponArrayListType = new TypeToken<ArrayList<WeaponCard>>() {
            }.getType();

            JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/weapons.json"));
            ArrayList<WeaponCard> weapons = gson.fromJson(reader, weaponArrayListType);

            weapons.forEach(w -> {
                w.init();
                weaponsDeck.add(w);
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void initPowerUpsDeck(Gson gson){
        try {

            Type powerUpArrayListType = new TypeToken<ArrayList<PowerUpCard>>() {
            }.getType();

            JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/powerUps.json"));
            ArrayList<PowerUpCard> powerUps = gson.fromJson(reader, powerUpArrayListType);

            powerUps.forEach(p -> powerUpsDeck.add(p));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void initAmmoDeck(Gson gson){
        try {

            Type ammoTileArrayListType = new TypeToken<ArrayList<AmmoTile>>(){}.getType();

            JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/ammoTiles.json"));
            ArrayList<AmmoTile> ammoTiles = gson.fromJson(reader, ammoTileArrayListType);

            ammoTiles.forEach(a -> ammoDeck.add(a));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void preLoadGameBoards(){
        preLoadedGameBoards = new GameBoard[LAST_MAP];
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Square.class, new SquareDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        try {
            for (int numberOfMap = FIRST_MAP; numberOfMap <= LAST_MAP; numberOfMap++) {
                JsonReader reader = new JsonReader(
                        new FileReader("src/main/resources/json/game_boards/gameBoard" + numberOfMap + ".json"));
                preLoadedGameBoards[numberOfMap - 1] = gson.fromJson(reader, GameBoard.class);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a map given the index
     * @param numberOfMap the index of the map
     */
    public void initMap(int numberOfMap) {
        gameBoard = preLoadedGameBoards[numberOfMap - 1];
        preLoadedGameBoards = null;

        gameBoard.init(weaponsDeck, ammoDeck);
        gameBoard.addModelEventHandler(events);

        //notify map set
        events.fireEvent(new GameBoardSetEvent(gameBoard.convertoTo(numberOfMap), numberOfMap));
    }


    public void restore(){
        gameBoard.init(weaponsDeck, ammoDeck);
        gameBoard.addModelEventHandler(events);
        for (Square s : gameBoard.getSquares()) {
            for (Pc pc : s.getPcs()){
                pcs.add(pc);
                pc.setCurrSquare(s);
            }
        }
        pcs.forEach(p -> {
            events.addListenerColour(p.getColour());
            p.addModelEventHandler(events);
            p.setCurrGame(this);
        });

    }

    /**
     * Inits the KillShotTrack with the given number of skulls
     * @param numberOfSkulls The desired number of skulls
     */
    public void initKillShotTrack(int numberOfSkulls){
        gameBoard.initKillShotTrack(numberOfSkulls);

        //notify kill shot track set
        events.fireEvent(new KillShotTrackSetEvent(gameBoard.getKillShotTrack().convertToDTO()));
    }


    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }


    public void setFinalFrenzy(boolean finalFrenzy) {

        this.finalFrenzy = finalFrenzy;

        //notify listeners
        events.fireEvent(new FinalFrenzyEvent(gameBoard.getKillShotTrack().convertToDTO()));
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
        events.addListenerColour(pc.getColour());
        pc.addModelEventHandler(events);

        //notify colour chosen
        PcDTO pcDTO = pc.convertToDTO();
        events.fireEvent(new PcColourChosenEvent(pcDTO));

        pcs.add(pc);
    }


    public Pc getPc(PcColourEnum colour){
        return pcs.stream().filter(pc -> pc.getColour() == colour).findFirst().orElse(null);
    }


    PowerUpCard drawPowerUp(){
        return powerUpsDeck.draw();
    }


    public boolean scoreDeath(Pc deadPc, boolean doubleKill) {
        scoringPoints(deadPc, doubleKill);
        boolean turnIntoFinalFrenzy = scoreOnKillShotTrack(deadPc);
        if (!isFinalFrenzy())
            deadPc.increaseNumberOfDeaths();
        return turnIntoFinalFrenzy;
    }


    private void scoringPoints(Pc deadPc, boolean doubleKill) {
        PcColourEnum [] deadPcDamageTrack = deadPc.getDamageTrack();
        int [] pcValue = deadPc.getPcBoard().getPcValue();
        int pcValueIndex = 0;
        boolean allPointsAssigned = false;
        int [] numOfDamages = new int [5];
        int max;

        if (!isFinalFrenzy()) {
            pcValueIndex = deadPc.getPcBoard().getNumOfDeaths();

            //assigns the first blood point, only if the board is not flipped
            pcs.stream()
                    .filter(pc -> pc.getColour() == deadPcDamageTrack[0])
                    .findFirst()
                    .ifPresent(p -> p.increasePoints(1));
        }
        //assigns an extra point, only if the current player gets a doubleKill
        if (doubleKill)
            pcs.stream()
                    .filter(pc -> pc.getColour() == deadPcDamageTrack[10])
                    .findFirst()
                    .ifPresent(p -> p.increasePoints(1));

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
                int finalPcValueIndex = pcValueIndex;
                pcs.stream()
                        .filter(pc -> pc.getColour().ordinal() == finalMaxIndex)
                        .findFirst()
                        .ifPresent(p -> p.increasePoints(pcValue[finalPcValueIndex]));
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
        return pointsOnKillShotTrack(pc, gameBoard.getKillShotTrackArray()) + pointsOnKillShotTrack(pc, gameBoard.getFinalFrenzyKillShotTrackArray());
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


    public String preLoadedGameBoardToString(int numOfMap) {
        return preLoadedGameBoards[numOfMap - 1].simplifiedToString();
    }


    void killOccurred(PcColourEnum killerColour, boolean overkilled) {
        gameBoard.killOccurred(killerColour, overkilled);
    }


    public void addModelEventListener(UUID playerID, ModelEventListener listener) {
        events.addModelEventListener(playerID, listener);
    }
}





