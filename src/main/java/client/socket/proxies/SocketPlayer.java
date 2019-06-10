package client.socket.proxies;

import client.socket.listeners.ViewSocketListener;
import common.rmi_interfaces.RemotePlayer;
import common.rmi_interfaces.RemoteView;

import java.io.IOException;
import java.net.Socket;

import static common.enums.SocketPlayerEnum.*;

public class SocketPlayer extends AbstractSocketProxy implements RemotePlayer {

    private RemoteView remoteView;
    
    public SocketPlayer(Socket socket) throws IOException {
        super(socket);
    }
    
    @Override
    public void chooseMap(int n) {
        out.println( CHOOSE_MAP );
        out.print( n );
        out.flush();
    }
    
    @Override
    public void chooseNumberOfSkulls(int n) {
        out.println( CHOOSE_NUMBER_OF_SKULLS );
        out.write( n );
        out.flush();
    }
    
    @Override
    public void choosePcColour(String colour) throws IOException {
        out.println( CHOOSE_PC_COLOUR );
        out.println( colour );
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
    public void usePowerUp() throws IOException {
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
        out.println( SELECT_SQUARE );
        out.print( x );
        out.print( y );
        out.flush();
    }
    
    @Override
    public void choosePowerUp(int index) throws IOException {
        out.println( CHOOSE_POWERUP );
        out.println( index );
        out.flush();
    }
    
    @Override
    public void chooseWeaponOnSpawnPoint(int n) {
        out.println( GRAB_WEAPON );
        out.print( n );
        out.flush();
    }
    
    @Override
    public void chooseWeaponOfMine(int n) {
        out.println( CHOOSE_WEAPON );
        out.print( n );
        out.flush();
    }
    
    @Override
    public void switchFireMode() {
        out.println( SWITCH_FIREMODE );
        out.flush();
    }
    
    @Override
    public void upgrade() {
        out.println( UPGRADE );
        out.flush();
    }
    
    @Override
    public void removeUpgrade() {
        out.println( REMOVE_UPGRADE );
        out.flush();
    }
    
    
    @Override
    public void chooseAsynchronousEffectOrder(boolean beforeBasicEffect) {
        out.println( CHOOSE_ASYNCH_EFFECT_ORDER );
        out.println( beforeBasicEffect );
        out.flush();
    }
    
    @Override
    public void skip() {
        out.println( SKIP );
        out.flush();
    }
    
    @Override
    public void undo() throws IOException {
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
    public void quit() throws IOException {
        out.println( QUIT );
        out.close();
        in.close();
        socket.close();
    }
    
    /**
     * Informs the listener to create a remote view using this socket
     * @param remoteView useless
     */
    @Override
    public void setRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
        new Thread( new ViewSocketListener( socket,remoteView ) ).start();
    }
    
    @Override
    public RemoteView getRemoteView() {
        return this.remoteView;
    }
    
}
