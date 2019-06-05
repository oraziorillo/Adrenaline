package server.controller.states;

import server.controller.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FirstTurnStateTest {
   
   @Mock
   static Player player;
   private FirstTurnState tested = new FirstTurnState( player );
   
   
   @Test
   public void nextStateReturnsStartTurnState() {
      assertTrue( tested.nextState() instanceof StartTurnState );
   }
}