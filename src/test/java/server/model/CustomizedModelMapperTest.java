//package server.model;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonArray;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.JsonReader;
//import common.dto_model.*;
//import common.enums.AmmoEnum;
//import common.enums.PcColourEnum;
//import common.enums.SquareColourEnum;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//import org.modelmapper.ModelMapper;
//import server.controller.CustomizedModelMapper;
//import server.model.actions.Action;
//import server.model.deserializers.ActionDeserializer;
//import server.model.deserializers.GameBoardDeserializer;
//import server.model.squares.AmmoSquare;
//import server.model.squares.SpawnPoint;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//public class CustomizedModelMapperTest {
//
//    CustomizedModelMapper customizedModelMapperTest = new CustomizedModelMapper();
//
//    ModelMapper modelMapper = customizedModelMapperTest.getModelMapper();
//    private Deck<WeaponCard> weaponCardDeck;
//    private Deck<PowerUpCard> powerUpCardDeck;
//
//    @Before
//    public void weaponsDeckConstrucionFine() throws FileNotFoundException {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
//        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
//
//        Type weaponsType = new TypeToken<ArrayList<WeaponCard>>(){}.getType();
//
//        JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/weapons.json"));
//        ArrayList<WeaponCard> weapons = customGson.fromJson(reader, weaponsType);
//
//        weaponCardDeck = new Deck<>();
//        weapons.forEach(w -> weaponCardDeck.add(w));
//    }
//
//
//    @Before
//    public void initPowerUpsDeck() throws FileNotFoundException {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
//        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
//
//        Type powerUpType = new TypeToken<ArrayList<PowerUpCard>>() {
//        }.getType();
//        JsonReader reader = null;
//
//        reader = new JsonReader(new FileReader("src/main/resources/json/powerUps.json"));
//        ArrayList<PowerUpCard> powerUps = customGson.fromJson(reader, powerUpType);
//
//        powerUpCardDeck = new Deck<>();
//        powerUps.forEach(p -> powerUpCardDeck.add(p));
//    }
//
//
//    private GameBoard setupGameBoard() throws FileNotFoundException {
//        GameBoard gameBoard;
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(GameBoard.class, new GameBoardDeserializer());
//        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
//
//        JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/gameBoards.json"));
//
//        JsonArray gameBoards = customGson.fromJson(reader, JsonArray.class);
//        return gameBoard = customGson.fromJson(gameBoards.get(3), GameBoard.class);
//    }
//
//
//
//    @Test
//    public void modelMapperWorksFineWithAmmoTileDTO(){
//        short[] ammo = new short[]{1, 2, 0};
//        AmmoTile ammoTile = Mockito.mock(AmmoTile.class);
//        when(ammoTile.getAmmo()).thenReturn(ammo);
//        when(ammoTile.hasPowerUp()).thenReturn(false);
//        AmmoTileDTO ammoTileDTO = modelMapper.map(ammoTile, AmmoTileDTO.class);
//        System.out.println("{ " + ammoTileDTO.getAmmo()[0] + ", " + ammoTileDTO.getAmmo()[1] + ", "  + ammoTileDTO.getAmmo()[2] + " }");
//    }
//
//
//
//
//    @Test
//    public void modelMapperWorksFineFromPowerUpToPowerUpDTO(){
//        PowerUpCard powerUpCard = powerUpCardDeck.draw();
//        PowerUpCardDTO powerUpCardDTO = modelMapper.map(powerUpCard, PowerUpCardDTO.class);
//    }
//
//
//    @Test
//    public void modelMapperWorksFineFromSpawnPointToSquareDTO() {
//        Game game = Mockito.mock(Game.class);
//        Pc pc1 = new Pc(PcColourEnum.BLUE, game);
//        Pc pc2 = new Pc(PcColourEnum.YELLOW, game);
//        SpawnPoint spawnPoint = new SpawnPoint(2, 2, SquareColourEnum.RED);
//        spawnPoint.init(weaponCardDeck, null);
//        spawnPoint.addPc(pc1);
//        spawnPoint.addPc(pc2);
//        SquareDTO squareDTO = modelMapper.map(spawnPoint, SquareDTO.class);
//        assertTrue(squareDTO.getCol() == 2);
//        assertTrue(squareDTO.getRow() == 2);
//        assertEquals(squareDTO.getPcs().size(), spawnPoint.getPcs().size());
//        assertTrue(squareDTO.getPcs().contains(PcColourEnum.BLUE));
//        assertTrue(squareDTO.getPcs().contains(PcColourEnum.YELLOW));
//    }
//
//
//    @Test
//    public void modelMapperWorksFineFromPcBoardToPcBoardDTO(){
//        PcBoard pcBoard = new PcBoard(PcColourEnum.GREEN);
//        PcBoardDTO pcBoardDTO = modelMapper.map(pcBoard, PcBoardDTO.class);
//    }
//
//    @Test
//    public void modelMapperWorksFineFromPcToPCDTO(){
//        Pc pc = new Pc(PcColourEnum.GREEN, null);
//        PcDTO PcDTO = modelMapper.map(pc, PcDTO.class);
//    }
//
//
//    @Test
//    public void modelMapperWorksFineFromAmmoSquareToSquareDTO() {
//        short[] ammo = new short[]{1, 2, 0};
//        AmmoTile ammoTile = new AmmoTile(ammo, false);
//        AmmoSquare ammoSquare = new AmmoSquare(1, 2, SquareColourEnum.GREEN);
//        Deck<AmmoTile> deck = Mockito.mock(Deck.class);
//        Pc pc1 = Mockito.mock( Pc.class);
//        Pc pc2 = Mockito.mock( Pc.class);
//        when(deck.draw()).thenReturn(ammoTile);
//        when( pc1.getColour()).thenReturn( PcColourEnum.BLUE);
//        when( pc2.getColour()).thenReturn( PcColourEnum.YELLOW);
//        ammoSquare.init(null, deck);
//        ammoSquare.addPc(pc1); ammoSquare.addPc(pc2);
//        SquareDTO squareDTO = modelMapper.map(ammoSquare, SquareDTO.class);
//        assertFalse(squareDTO.isTargetable());
//        assertArrayEquals(squareDTO.getAmmoTile().getAmmo(), ammo);
//        assertEquals(squareDTO.getPcs().size(), ammoSquare.getPcs().size());
//        assertTrue(squareDTO.getPcs().contains(PcColourEnum.BLUE));
//        assertTrue(squareDTO.getPcs().contains(PcColourEnum.YELLOW));
//    }
//
//    @Test
//    public void modelMapperWorksFineOnWeaponCard(){
//        WeaponCard weaponCard = weaponCardDeck.draw();
//        WeaponCardDTO weaponCardDTO = modelMapper.map(weaponCard, WeaponCardDTO.class);
//        assertEquals(weaponCard.getName(), weaponCardDTO.getName());
//        assertEquals(weaponCard.getFireModes().size(), weaponCardDTO.getBasicEffects());
//        assertEquals(weaponCard.getUpgrades().size(), weaponCardDTO.getUpgrades());
//    }
//
//
//    @Test
//    public void modelMapperWorksFineOnGameBoard() throws FileNotFoundException {
//        GameBoard gameBoard = setupGameBoard();
//        GameBoardDTO gameBoardDTO = modelMapper.map(gameBoard, GameBoardDTO.class);
//        assertEquals(gameBoardDTO.getColumns(), gameBoard.getColumns());
//        assertEquals(gameBoardDTO.getRows(), gameBoard.getRows());
//        assertEquals(gameBoardDTO.getSpawnPoints().size(), gameBoard.getSpawnPoints().size());
//        assertEquals(gameBoardDTO.getSpawnPoints().get(2).getCol(), gameBoard.getSpawnPoints().get(2).getCol());
//        for (SquareDTO s: gameBoardDTO.getSquares()) {
//            System.out.println(s.getRow() + " " + s.getCol() + " " + s.isTargetable());
//        }
//    }
//
//
//}
