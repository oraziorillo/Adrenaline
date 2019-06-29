package common.enums;

import java.util.Arrays;
import java.util.Collection;

public enum ConnectionsEnum {
    SOCKET("s", "socket"),
    RMI("r", "rmi");

    private final Collection<String> recognisedStrings;
    
    ConnectionsEnum(String... recognisedStrings){
        this.recognisedStrings = Arrays.asList( recognisedStrings );
    }
    
    public static ConnectionsEnum parseString(String string){
        for(ConnectionsEnum e: values()){
            if(e.recognisedStrings.contains( string )){
                return e;
            }
        }
        throw new IllegalArgumentException( "Illegal command" );
    }
}
