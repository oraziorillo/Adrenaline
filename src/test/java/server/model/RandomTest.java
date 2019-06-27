package server.model;

import common.enums.interfaces_names.SocketEnum;
import org.junit.Test;

import static common.enums.interfaces_names.SocketEnum.CHOOSE_MAP;

public class RandomTest {

    @Test
    public void randomTest(){
        System.out.println(CHOOSE_MAP.toString());
        System.out.println(SocketEnum.valueOf(CHOOSE_MAP.toString()));
    }
}
