package server.model;

import common.enums.SocketEnum;
import org.junit.Test;

import static common.enums.SocketEnum.CHOOSE_MAP;

public class RandomTest {

    @Test
    public void randomTest(){
        System.out.println(CHOOSE_MAP.toString());
        System.out.println(SocketEnum.valueOf(CHOOSE_MAP.toString()));
        String[] commands = "ok".split("-");
        System.out.println(commands[0] + commands[1]);
    }
}