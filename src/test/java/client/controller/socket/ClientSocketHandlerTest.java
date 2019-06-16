package client.controller.socket;

import org.junit.Before;
import org.junit.Test;
import server.LaunchServer;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static org.junit.Assert.*;

public class ClientSocketHandlerTest {
    
    private Thread server = new Thread( ()-> { try {LaunchServer.main( null );}catch ( Exception ignored ){}} );
    private ClientSocketHandler tested;
    
    @Before
    public void setUp() throws IOException {
        server.stop();
        server.start();
        tested = new ClientSocketHandler( new Socket("localhost",10000) );
    }
    
    @Test
    public void registerReturnsUUIDWithServerOnAndNewUsername() throws IOException {
        UUID returned = tested.register( "username" );
        assertNotNull( returned );
    }
    
    @Test
    public void registerReturnsNullWithInUseUsername() throws IOException {
        UUID first = tested.register( "username" );
        UUID second = tested.register( "username" );
        assertNull( second );
    }
}