package server.controller.states;

import server.controller.Player;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class EndTurnStateTest {
   @Mock
   static Player player;
   private EndTurnState  tested = new EndTurnState( player );
   
   @BeforeAll
   public static void setUpMocks(){
   }
   
   @Test
   public void nextStateIsReloadIfReloadIsCalled(){
      tested.reload();
      assertTrue( tested.nextState() instanceof ReloadState );
   }
   
   @Test
   public void nextStateIsStartTurnIfReloadIsCalled(){
      assertTrue( tested.nextState() instanceof StartTurnState );
   }
}