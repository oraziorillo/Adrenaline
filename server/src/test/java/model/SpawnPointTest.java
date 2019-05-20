package model;

import enums.SquareColourEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SpawnPointTest {
   
   private SpawnPoint tested;
   private final int x=3;
   private final int y=6;
   private final SquareColourEnum colour = SquareColourEnum.RED;
   @Mock WeaponCard card0;
   @Mock WeaponCard card1;
   @Mock WeaponCard card2;
   
   @Before
   public void setupAndConstructorTest(){
      Deck<WeaponCard> deck = Mockito.mock( Deck.class );
      Mockito.when( deck.draw() ).thenReturn( card0 ).thenReturn( card1 ).thenReturn( card2 );
      tested = new SpawnPoint( x,y,colour,deck );
   }
   
   @Test
   public void pickWeaponWorksFine(){
      assertSame( card0, tested.pickWeapon( 0 ) );
      assertSame( card1,tested.pickWeapon( 1 ) );
      assertSame( card2,tested.pickWeapon( 2 ) );
      assertThrows( NullPointerException.class,()->tested.pickWeapon( 0 ) );
      assertThrows( NullPointerException.class,()->tested.pickWeapon( 1 ) );
      assertThrows( NullPointerException.class,()->tested.pickWeapon( 2 ) );
   }
   
   @Test
   public void refillRefillsTheEntireSquare(){
      emptyTheSquare(tested);
      tested.refill();
      for(int i=0;i<3;i++) {
         assertNotNull( tested.pickWeapon( i ) );
      }
   }
   
   @Test
   public void isEmptyWorksFine(){
      assertFalse( tested.isEmpty() );
      emptyTheSquare( tested );
      assertTrue( tested.isEmpty() );
   }
   
   @Test
   public void switchWeaponWorksFine(){
      WeaponCard switched = Mockito.mock( WeaponCard.class );
      WeaponCard returned = tested.switchWeapon( 0,switched );
      assertSame( returned,card0 );
      assertSame( switched,tested.pickWeapon( 0 ) );
      assertSame( card1,tested.pickWeapon( 1 ) );
      assertSame( card2,tested.pickWeapon( 2 ) );
   }
   
   @Test
   public void getWeaponsReturnedArrayEqualsButNotSame(){
      WeaponCard[] expected = new WeaponCard[]{card0,card1,card2};
      assertEquals( expected,tested.getWeapons() );
      assertNotSame( expected,tested.getWeapons() );
      
   }
   
   private void emptyTheSquare(Square toEmpty){
      for(int i=0; i<3;i++){
         tested.pickWeapon( i );
      }
   }
   
   
   
   
   
}
