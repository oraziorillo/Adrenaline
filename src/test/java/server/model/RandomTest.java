package server.model;

import common.enums.interfaces_names.SocketPlayerEnum;
import org.junit.Test;

import static common.enums.interfaces_names.SocketPlayerEnum.CHOOSE_MAP;

public class RandomTest {

    @Test
    public void randomTest(){
        System.out.println(CHOOSE_MAP.toString());
        System.out.println(SocketPlayerEnum.valueOf(CHOOSE_MAP.toString()));
    }
}
