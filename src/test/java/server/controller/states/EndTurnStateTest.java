package server.controller.states;

import server.controller.Controller;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class EndTurnStateTest {
   @Mock
   static Controller controller;
   private EndTurnState  tested = new EndTurnState(controller);
   
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