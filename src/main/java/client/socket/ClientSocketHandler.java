package client.socket;

import common.enums.interfaces_names.RemoteViewEnum;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import static common.enums.interfaces_names.SocketLoginEnum.*;
import static common.enums.interfaces_names.SocketPlayerEnum.*;

public class ClientSocketHandler implements Runnable, RemoteLoginController, RemotePlayer {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private RemoteView view;
    private BlockingQueue<String[]> buffer = new PriorityBlockingQueue<>( 10, (a1, a2) -> a1[0].compareToIgnoreCase( a2[0] ) );
    private UUID token;
    
    public ClientSocketHandler(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter( socket.getOutputStream() );
        in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
    }
    
    @Override
    public void run() {
        while (!socket.isClosed()) {
            String[] args = null;
            try {
                args = in.readLine().split( "," );
                parseRemoteView( args );
            }catch ( IllegalArgumentException notAViewMethod ){
                buffer.add( args );
            } catch ( IOException e ) {
                try {
                    quit();
                    socket.close();
                    out.close();
                    in.close();
                } catch ( IOException ignore ) {}
            }
    
        }
    }
    
    private void parseRemoteView(String[] args) throws IOException {
        switch (RemoteViewEnum.valueOf( args[0] )) {
            case ACK:
                view.ack( args[1] );
        }
    }
    
    //LoginController
    @Override
    public synchronized UUID register(String username,RemoteView view) throws IOException {
        this.view = view;
        out.println( REGISTER.toString() + "," + username );
        out.flush();
        String stringToken;
        try {
            stringToken = buffer.take()[0];
            return UUID.fromString( stringToken );
        } catch ( InterruptedException | IllegalArgumentException invalidToken ) {
            return null;
        }
    }
    
    
    @Override
    public synchronized RemotePlayer login(UUID token, RemoteView view) {
        this.view = view;
        this.token = token;
        new Thread( this ).start();
        out.println( LOGIN.toString() + "," + token );
        out.flush();
        //TODO: check if the login was successful
        return this;
        
    }
    
    
    @Override
    public synchronized void joinLobby(UUID token) {
        out.println( JOIN_LOBBY.toString() + "," + token );
        out.flush();
    }
    
    @Override
    public void setRemoteView(RemoteView view, UUID token) throws IOException {
        this.view = view;
    }
    
    //Player
    
    @Override
    public void chooseMap(int n) {
        out.print( CHOOSE_MAP.toString() + "," + n );
        out.flush();
    }
    
    
    @Override
    public void chooseNumberOfSkulls(int n) {
        out.println( CHOOSE_NUMBER_OF_SKULLS.toString() + "," + n );
        out.flush();
    }
    
    
    @Override
    public void choosePcColour(String colour) {
        out.println( CHOOSE_PC_COLOUR + "," + colour );
        out.flush();
    }
    
    
    @Override
    public void runAround() {
        out.println( RUN_AROUND );
        out.flush();
    }
    
    
    @Override
    public void grabStuff() {
        out.println( GRAB_STUFF );
        out.flush();
    }
    
    
    @Override
    public void usePowerUp() {
        out.println( USE_POWERUP );
        out.flush();
    }
    
    
    @Override
    public void shootPeople() {
        out.println( SHOOT_PEOPLE );
        out.flush();
    }
    
    
    @Override
    public void chooseSquare(int x, int y) {
        out.println( SELECT_SQUARE + "," + x + "," + y );
        out.flush();
    }
    
    
    @Override
    public void choosePowerUp(int index) {
        out.println( CHOOSE_POWERUP + "," + index );
        out.flush();
    }
    
    
    @Override
    public void chooseWeaponOnSpawnPoint(int n) {
        out.println( GRAB_WEAPON_ON_SPAWNPOINT + "," + n );
        out.flush();
    }
    
    
    @Override
    public void chooseWeaponOfMine(int n) {
        out.println( CHOOSE_WEAPON_OF_MINE + "," + n );
        out.flush();
    }
    
    
    @Override
    public void switchFireMode() {
        out.println( SWITCH_FIREMODE );
        out.flush();
    }
    
    
    @Override
    public void chooseUpgrade(int index) {
        out.println( CHOOSE_UPGRADE + "," + index);
        out.flush();
    }
    
    
    @Override
    public void chooseAsynchronousEffectOrder(boolean beforeBasicEffect) {
        out.println( CHOOSE_ASYNCH_EFFECT_ORDER + "," + beforeBasicEffect);
        out.flush();
    }


    @Override
    public void chooseDirection(int index) {
        out.println( CHOOSE_DIRECTION + "," + index);
        out.flush();
    }
    
    
    @Override
    public void skip() {
        out.println( SKIP );
        out.flush();
    }
    
    
    @Override
    public void undo() {
        out.println( UNDO );
        out.flush();
    }
    
    
    @Override
    public void ok() {
        out.println( OK );
        out.flush();
    }
    
    
    @Override
    public void reload() {
        out.println( RELOAD );
        out.flush();
    }
    
    
    @Override
    public void pass() {
        out.println( PASS );
        out.flush();
    }
    
    
    @Override
    public void quit(){
        out.println( QUIT );
        try {
            out.close();
            in.close();
            socket.close();
        }catch ( IOException ignored ){} //if already closed it's ok
    }
    
    @Override
    public boolean isConnected() {
        return !socket.isClosed();
    }
    
    @Override
    public UUID getToken() throws RemoteException {
        return this.token;
    }
}
