package server.model;

import common.enums.PcColourEnum;
import common.enums.SocketEnum;
import org.junit.Test;

import static common.enums.SocketEnum.CHOOSE_MAP;

public class RandomTest {

    @Test
    public void randomTest(){
        System.out.println(CHOOSE_MAP.toString());
        System.out.println(SocketEnum.valueOf(CHOOSE_MAP.toString()));
        String[] commands = "ss-1-2".split("-");
        for (String s: commands) {
            System.out.println(s);
        }
    }

     @Test
    public void pcColorEnumTest(){
        String stringed = "blue";
        for(PcColourEnum e: PcColourEnum.values()){
            if(stringed.trim().equalsIgnoreCase( e.toString())){
                System.out.println(e);
            }
        }
     }

}
