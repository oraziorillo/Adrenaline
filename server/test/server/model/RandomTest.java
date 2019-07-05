package server.model;

import common.enums.ControllerMethodsEnum;
import common.enums.PcColourEnum;
import org.junit.Test;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static common.enums.ControllerMethodsEnum.CHOOSE_MAP;

public class RandomTest {

    @Test
    public void randomTest(){
        System.out.println(CHOOSE_MAP.toString());
        System.out.println(ControllerMethodsEnum.valueOf(CHOOSE_MAP.toString()));
        String[] commands = "ok".split("-");
        System.out.println(PcColourEnum.fromString("blue"));
        System.out.println(ControllerMethodsEnum.valueOf(CHOOSE_MAP.toString()));
        String[] commands1 = "ss-1-2".split("-");
        for (String s: commands1) {
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


     @Test
    public void randomNumbers(){
        Random random = new Random();
        for (int i = 0; i < 20; i++)
            System.out.println(random.nextInt(2));
     }


     @Test
    public void system() {
        boolean mioboolean = true;
        System.out.println(mioboolean);
    }


    @Test
    public void timerTest() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int x = 0;
                System.out.println(++x);
            }
        };
        timerTask.run();
        System.out.println(true);
    }
}
