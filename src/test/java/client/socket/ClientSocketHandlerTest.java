package client.socket;

import common.remote_interfaces.RemoteView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import server.LaunchServer;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ClientSocketHandlerTest {
    
    private Thread server;
    private ClientSocketHandler tested;
    @Mock private RemoteView view;
    
    
    @Before
    public void setUp() throws IOException, ClassNotFoundException {
        server = new Thread( ()-> {
            try {
                LaunchServer.main( null );
            } catch ( IOException e ) {
                e.printStackTrace();
            } catch ( ClassNotFoundException e ) {
                e.printStackTrace();
            }
        } );
        server.start();
        tested = new ClientSocketHandler( new Socket("localhost",10000) );
        new Thread( tested ).start();
        Mockito.doNothing().when( view).ack( any(String.class) );
    }
    
    @Test
    public void registerReturnsUUIDWithServerOnAndNewUsername() throws IOException {
        UUID returned = tested.register( "username",view );
        assertNotNull( returned );
    }
    
    @Test
    public void registerReturnsNullWithInUseUsername() throws IOException {
        UUID first = tested.register( "username",view );
        UUID second = tested.register( "username" ,view);
        assertNull( second );
    }
}