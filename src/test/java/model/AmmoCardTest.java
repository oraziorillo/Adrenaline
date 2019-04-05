package model;


import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;
import static org.junit.Assume.*;

@RunWith(Theories.class)
public class AmmoCardTest {
    @DataPoints
    public static final AmmoEnum[] colori={AmmoEnum.BLUE,AmmoEnum.RED,AmmoEnum.YELLOW};
    @DataPoints
    public static final boolean[] booleans={true,false};
    @DataPoints
    public static final short[][] ammos={{1,1,1},{0,0,0},{1,2,3}};
    public static final int MAXMUNIZIONI=3;
    public int totalAmmos;

    @Theory
    public void constructionTest(AmmoEnum colore, short[] ammos, boolean powerup){
        for(short s:ammos){
            totalAmmos+=s;
        }
        int limit=MAXMUNIZIONI;
        if(powerup) {
            limit--;
        }
        assumeFalse("Numero munizioni errato", totalAmmos>limit);
        AmmoCard carta=new AmmoCard(colore,ammos,powerup);
        assertSame("Colore diverso",carta.getColour(),colore);
        assertSame("containsPowerup diverso",carta.containsPowerup(),powerup);
        assertSame("Munizioni diverse",carta.getAmmos(),ammos);
    }


}
