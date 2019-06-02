package server.controller.states;

import server.controller.Controller;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FirstTurnStateTest {
   
   @Mock
   static Controller controller;
   private FirstTurnState tested = new FirstTurnState( controller );
   
   
   @Test
   public void nextStateReturnsStartTurnState() {
      assertTrue( tested.nextState() instanceof StartTurnState );
   }
}