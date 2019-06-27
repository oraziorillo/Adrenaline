package server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import common.dto_model.AmmoTileDTO;
import common.dto_model.GameBoardDTO;
import common.dto_model.SquareDTO;
import common.dto_model.WeaponCardDTO;
import common.enums.PcColourEnum;
import common.enums.SquareColourEnum;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import server.controller.CustomizedModelMapper;
import server.model.deserializers.GameBoardDeserializer;
import server.model.squares.AmmoSquare;
import server.model.squares.SpawnPoint;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CustomizedModelMapperTest {

    CustomizedModelMapper customizedModelMapperTest = new CustomizedModelMapper();

    ModelMapper modelMapper = customizedModelMapperTest.getModelMapper();


    @Test
    public void modelMapperWorksFineWithAmmoTileDTO(){
        short[] ammo = new short[]{1, 2, 0};
        AmmoTile ammoTile = Mockito.mock(AmmoTile.class);
        when(ammoTile.getAmmo()).thenReturn(ammo);
        when(ammoTile.containsPowerUp()).thenReturn(false);
        AmmoTileDTO ammoTileDTO = modelMapper.map(ammoTile, AmmoTileDTO.class);
        System.out.println("{ " + ammoTileDTO.getAmmo()[0] + ", " + ammoTileDTO.getAmmo()[1] + ", "  + ammoTileDTO.getAmmo()[2] + " }");
    }


    @Test
    public void modelMapperWorksFineFromSpawnPointToSquareDTO(){
        Pc pc1 = Mockito.mock( Pc.class);
        Pc pc2 = Mockito.mock( Pc.class);
        Deck<WeaponCard> deck = Mockito.mock(Deck.class);
        WeaponCard weaponCard1 = Mockito.mock(WeaponCard.class);
        WeaponCard weaponCard2 = Mockito.mock(WeaponCard.class);
        WeaponCard weaponCard3 = Mockito.mock(WeaponCard.class);
        when(deck.draw()).thenReturn(weaponCard1).thenReturn(weaponCard2).thenReturn(weaponCard3);
        when(weaponCard1.getName()).thenReturn("Lock Rifle");
        when(weaponCard2.getName()).thenReturn("Machine Gun");
        when(weaponCard3.getName()).thenReturn("Tractor Beam");
        when( pc1.getColour()).thenReturn( PcColourEnum.BLUE);
        when( pc2.getColour()).thenReturn( PcColourEnum.YELLOW);
        SpawnPoint spawnPoint = new SpawnPoint(2, 2, SquareColourEnum.RED);
        spawnPoint.init(deck, null);
        spawnPoint.addPc(pc1); spawnPoint.addPc(pc2);
        spawnPoint.setTargetable(true);
        SquareDTO squareDTO = modelMapper.map(spawnPoint, SquareDTO.class);
        assertTrue(squareDTO.isTargetable());
        assertTrue(squareDTO.getCol() == 2);
        assertTrue(squareDTO.getRow() == 2);
        assertEquals(squareDTO.getPcs().size(), spawnPoint.getPcs().size());
        assertTrue(squareDTO.getPcs().contains(PcColourEnum.BLUE));
        assertTrue(squareDTO.getPcs().contains(PcColourEnum.YELLOW));
        assertEquals(spawnPoint.getWeapons()[0].getName(), "Lock Rifle");
        assertEquals(spawnPoint.getWeapons()[1].getName(), "Machine Gun");
        assertEquals(spawnPoint.getWeapons()[2].getName(), "Tractor Beam");
    }


    @Test
    public void modelMapperWorksFineFromAmmoSquareToSquareDTO() {
        short[] ammo = new short[]{1, 2, 0};
        AmmoTile ammoTile = new AmmoTile(ammo, false);
        AmmoSquare ammoSquare = new AmmoSquare(1, 2, SquareColourEnum.GREEN);
        Deck<AmmoTile> deck = Mockito.mock(Deck.class);
        Pc pc1 = Mockito.mock( Pc.class);
        Pc pc2 = Mockito.mock( Pc.class);
        when(deck.draw()).thenReturn(ammoTile);
        when( pc1.getColour()).thenReturn( PcColourEnum.BLUE);
        when( pc2.getColour()).thenReturn( PcColourEnum.YELLOW);
        ammoSquare.init(null, deck);
        ammoSquare.addPc(pc1); ammoSquare.addPc(pc2);
        ammoSquare.refill();
        SquareDTO squareDTO = modelMapper.map(ammoSquare, SquareDTO.class);
        assertFalse(squareDTO.isTargetable());
        assertArrayEquals(squareDTO.getAmmoTile().getAmmo(), ammo);
        assertEquals(squareDTO.getPcs().size(), ammoSquare.getPcs().size());
        assertTrue(squareDTO.getPcs().contains(PcColourEnum.BLUE));
        assertTrue(squareDTO.getPcs().contains(PcColourEnum.YELLOW));
    }

    @Test
    public void modelMapperWorksFineOnWeaponCard(){
        String weaponName = "Lock Rifle";
        WeaponCard weaponCard = Mockito.mock(WeaponCard.class);
        when(weaponCard.getName()).thenReturn(weaponName);
        WeaponCardDTO weaponCardDTO = modelMapper.map(weaponCard, WeaponCardDTO.class);
        assertEquals(weaponName, weaponCardDTO.getName());
    }


    private GameBoard setupGameBoard() throws FileNotFoundException {
        GameBoard gameBoard;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameBoard.class, new GameBoardDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/gameBoards.json"));

        JsonArray gameBoards = customGson.fromJson(reader, JsonArray.class);
        return gameBoard = customGson.fromJson(gameBoards.get(3), GameBoard.class);
    }

    @Test
    public void modelMapperWorksFineOnGameBoard() throws FileNotFoundException {
        GameBoard gameBoard = setupGameBoard();
        GameBoardDTO gameBoardDTO = modelMapper.map(gameBoard, GameBoardDTO.class);
        assertEquals(gameBoardDTO.getColumns(), gameBoard.getColumns());
        assertEquals(gameBoardDTO.getRows(), gameBoard.getRows());
        assertEquals(gameBoardDTO.getSpawnPoints().size(), gameBoard.getSpawnPoints().size());
        assertEquals(gameBoardDTO.getSpawnPoints().get(2).getCol(), gameBoard.getSpawnPoints().get(2).getCol());
    }


}
