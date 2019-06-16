package common.enums;

import common.enums.interfaces_names.SocketPlayerEnum;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class SocketPlayerEnumTest {
    
    @Test
    public void noRepeatedCommands(){
        for(SocketPlayerEnum s1: SocketPlayerEnum.values()){
            for(String string1: s1.getCliStrings()){
                for(SocketPlayerEnum s2: SocketPlayerEnum.values()){
                    if(!s1.equals( s2 )){
                        for(String string2:s2.getCliStrings()){
                            assertNotEquals( string1,string2 );
                        }
    
                    }
                }
            }
        }
    }
}
