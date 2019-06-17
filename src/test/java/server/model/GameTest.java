package server.model;

import org.junit.Before;
import org.junit.Test;
import server.model.Game;

public class GameTest {

    private Game tested;
    
    @Before
    private void setUp(){
        tested = new Game();
    }
    
    @Test
    public void contructorWorksFine(){
        //When some other test will be added, this can be removed
    }
}
