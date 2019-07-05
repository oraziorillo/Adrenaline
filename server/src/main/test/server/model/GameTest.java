package server.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import server.model.squares.Square;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {

    private Game game;

    private static final int N_MAP = 2;
    private static final int N_SKULLS = 6;

    @Mock
    private ModelEventHandler eventHandler;

    @Before
    public void init() {
        game = Game.getGame();
    }

    @Test
    public void initMapAndKillShotTrack() {
        game.initMap(N_MAP);
        game.initKillShotTrack(N_SKULLS);
    }


    @Test
    public void getSquareFine() {
        Square tested = game.getSquare(2, 3);
        assertEquals(3, tested.getCol());
        assertEquals(2, tested.getRow());

    }


    @Test
    public void addPcFine() {
        Pc pc1 = Mockito.mock(Pc.class);
        Pc pc2 = Mockito.mock(Pc.class);

        pc1.addModelEventHandler(eventHandler);
        pc2.addModelEventHandler(eventHandler);
        
        game.addPc(pc1);
        game.addPc(pc2);
    }


    @Test
    public void finalFrenzy(){
        game.setFinalFrenzy(true);
        assertTrue(game.isFinalFrenzy());
    }


}
