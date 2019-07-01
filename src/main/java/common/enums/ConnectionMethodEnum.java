package common.enums;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public enum ConnectionMethodEnum {
    SOCKET(":s","socket"),
    RMI(":r","rmi");

    private Collection<String> command = new HashSet<>();
    
    ConnectionMethodEnum(String... command){
        this.command.addAll( Arrays.asList( command ) );
    }
    
    public static ConnectionMethodEnum parseString(String string){
        for(ConnectionMethodEnum e: values()){
            if(e.command.contains(string)){
                return e;
            }
        }
        throw new IllegalArgumentException("Oak's words echoed... There's a time and place for everything, but not now");
    }
}
