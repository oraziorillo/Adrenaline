package client.socket;

/*
@RunWith(MockitoJUnitRunner.class)
public class ClientSocketHandlerTest {

    private Thread server;
    private ClientSocketHandler tested;
    @Mock private RemoteView view;
    
    
    @Before
    public void setUp() throws IOException {
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
     */
