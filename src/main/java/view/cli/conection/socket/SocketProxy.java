package view.cli.conection.socket;

import common.RemoteController;
import server.enums.PcColourEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static server.enums.SocketCommandsEnum.*;

public class SocketProxy implements RemoteController {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public SocketProxy(String ip, int port ) throws IOException {
        this(new Socket( ip, port));
    }

    public SocketProxy(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter( socket.getOutputStream() );
        out.flush();
        in = new BufferedReader( new InputStreamReader(  socket.getInputStream() ));
    }

    public SocketProxy() throws IOException {
        this("localhost",10000);
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
    public void choosePcColour(PcColourEnum colour) {
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
    
    @Override
    public boolean isOpened() {
        return !socket.isClosed();
    }
}
