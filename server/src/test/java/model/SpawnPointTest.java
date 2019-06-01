package model;

import enums.SquareColourEnum;
import exceptions.EmptySquareException;
import exceptions.NotEnoughAmmoException;
import model.squares.SpawnPoint;
import model.squares.Square;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class SpawnPointTest {
   
   private SpawnPoint tested;
   private final int x=3;
   private final int y=6;
   private final SquareColourEnum colour = SquareColourEnum.RED;
   @Mock WeaponCard card0;
   @Mock WeaponCard card1;
   @Mock WeaponCard card2;
   @Mock Pc pc;


   @Before
   public void setupAndConstructorTest(){
      Deck<WeaponCard> deck = Mockito.mock( Deck.class );
      Mockito.when( deck.draw() ).thenReturn( card0 ).thenReturn( card1 ).thenReturn( card2 );
      tested = new SpawnPoint( x,y,colour);
   }
   
   @Test
   public void pickWeaponWorksFine() throws EmptySquareException, NotEnoughAmmoException {
      for (int i = 0; i < 2; i++) {
         tested.setWeaponToGrabIndex(i);
         assertSame( card0, tested.weaponAtIndex(i));
         tested.collect(pc);
         assertThrows( NullPointerException.class,()->tested.collect( pc ) );
      }
   }
   
   @Test
   public void refillRefillsTheEntireSquare() throws EmptySquareException, NotEnoughAmmoException {
      emptyTheSquare(tested);
      tested.refill();
      for(int i=0;i<3;i++) {
         assertNotNull( tested.weaponAtIndex( i ) );
      }
   }
   
   @Test
   public void isEmptyWorksFine() throws EmptySquareException, NotEnoughAmmoException {
      assertFalse( tested.isEmpty() );
      emptyTheSquare( tested );
      assertTrue( tested.isEmpty() );
   }
   
   @Test
   public void switchWeaponWorksFine(){
      //TODO
   }


   private void emptyTheSquare(Square toEmpty) throws EmptySquareException, NotEnoughAmmoException {
      for (int i = 0; i < 3; i++) {
         toEmpty.setWeaponToGrabIndex(i);
         toEmpty.collect(pc);
      }
   }
   
}
