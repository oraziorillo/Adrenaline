package server.model;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AmmoTileTest {


//    @Test
//    public void doesNotAlterCostructionParametersWithHasPowerupFalse() {
//        short[] ammos = new short[]{1,1,1};
//        boolean hasPowerup = false;
//        assumeTrue("Invalid parameters", AmmoTile.validParameters(ammos, hasPowerup));
//        AmmoTile tested = new AmmoTile(ammos, hasPowerup);
//        assertTrue("Different ammos", Arrays.equals( ammos, tested.getAmmo()));
//        assertEquals("Different powerup", hasPowerup, tested.containsPowerup());
//    }
//
//    @Test
//    public void doesNotAlterCostructionParametersWithHasPowerupTrue() {
//        short[] ammos = new short[]{1,1,0};
//        boolean hasPowerup = true;
//        assumeTrue("Invalid parameters", AmmoTile.validParameters(ammos, hasPowerup));
//        AmmoTile tested = new AmmoTile(ammos, hasPowerup);
//        assertTrue("Different ammos", Arrays.equals(ammos, tested.getAmmo()));
//        assertEquals("Different powerup", hasPowerup, tested.containsPowerup());
//    }
//
//    @Test
//    public void throwsExceptionOnInvalidParametersWithHasPowerupFalse() {
//        short[][] ammosArr = new short[][]{{1,1,0},{1,1,2},{1,0,0}};
//        boolean hasPowerup = false;
//        for(short[] ammos : ammosArr) {
//                assumeFalse( "Valid parameters", AmmoTile.validParameters( ammos, hasPowerup ) );
//                assertThrows( IllegalArgumentException.class, () -> new AmmoTile( ammos, hasPowerup ) );
//        }
//    }
//
//    @Test
//    public void throwsExceptionOnInvalidParametersWithHasPowerupTrue() {
//        short[][] ammosArr = new short[][]{{1,1,0},{1,1,2},{1,0,0}};
//        boolean hasPowerup = false;
//        for(short[] ammos : ammosArr) {
//            assumeFalse( "Valid parameters: "+ammos[0]+","+ammos[1]+","+ammos[2]+" "+hasPowerup, AmmoTile.validParameters( ammos, hasPowerup ) );
//            assertThrows(  IllegalArgumentException.class, () -> new AmmoTile( ammos, hasPowerup ) );
//        }
//    }
//
//    @Test
//    public void parameterValidatorAcceptsWellWithHasPowerupFalse() {
//        short[] ammos = new short[]{0,3,0};
//        boolean hasPowerup = false;
//        assertTrue( "Valid arguments refused", AmmoTile.validParameters( ammos,hasPowerup ) );
//    }
//
//    @Test
//    public void parameterValidatorAcceptsWellWithHasPowerupTrue() {
//        short[] ammos = new short[]{0,1,1};
//        boolean hasPowerup = true;
//        assertTrue( "Valid arguments refused", AmmoTile.validParameters( ammos,hasPowerup ) );
//    }
//
//    @Test
//    public void parameterValidatorRefusesNegativeValues() {
//        short[] ammosTrue = new short[]{0,-1,3};
//        short[] ammosFalse = new short[]{0,-1,4};
//        assertFalse( "Valid arguments refused", AmmoTile.validParameters( ammosTrue,true ) );
//        assertFalse( "Valid arguments refused", AmmoTile.validParameters( ammosFalse,false ) );
//    }
//
//    @Test
//    public void parameterValidatorRefusesNot3Sums() {
//        short[][] ammosTrue = new short[][]{{0,0,0},{1,1,1},{3,4,5}};
//        short[][] ammosFalse = new short[][]{{0,0,0},{1,0,0},{3,4,5}};
//        for(short[] ammos: ammosTrue) {
//            assertFalse( "Illegal arguments accepted", AmmoTile.validParameters( ammos, true ) );
//        }
//        for( short[] ammos: ammosFalse) {
//            assertFalse( "Illegal arguments accepted", AmmoTile.validParameters( ammos, false ) );
//        }
//    }

}
