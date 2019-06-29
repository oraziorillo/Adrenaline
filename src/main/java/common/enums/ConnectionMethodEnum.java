package common.enums;

import java.util.Arrays;
import java.util.Collection;

public enum ConnectionMethodEnum {
    SOCKET("s", "socket"),
    RMI("r", "rmi");

    private final Collection<String> recognisedStrings;
    
    ConnectionMethodEnum(String... recognisedStrings){
        this.recognisedStrings = Arrays.asList( recognisedStrings );
    }
    
    public static ConnectionMethodEnum parseString(String string){
        for(ConnectionMethodEnum e: values()){
            if(e.recognisedStrings.contains( string )){
                return e;
            }
        }
        throw new IllegalArgumentException( "Illegal command" );
    }
}
