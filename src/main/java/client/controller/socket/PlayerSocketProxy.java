package client.controller.socket;

import common.remote_interfaces.RemotePlayer;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.REGEX;
import static common.enums.ControllerMethodsEnum.*;

public class PlayerSocketProxy extends AbstractSocketProxy implements RemotePlayer {

    private UUID token;


    PlayerSocketProxy(Socket socket, UUID token) throws IOException {
        super(socket);
        this.token = token;
    }


    @Override
    public synchronized void chooseMap(int n) {
        out.println(CHOOSE_MAP.toString() + REGEX + n);
        out.flush();
    }


    @Override
    public synchronized void chooseNumberOfSkulls(int n) {
        out.println(CHOOSE_NUMBER_OF_SKULLS.toString() + REGEX + n);
        out.flush();
    }


    @Override
    public synchronized void choosePcColour(String colour) {
        out.println(CHOOSE_PC_COLOUR + REGEX + colour);
        out.flush();
    }


    @Override
    public synchronized void runAround() {
        out.println(RUN_AROUND);
        out.flush();
    }


    @Override
    public synchronized void grabStuff() {
        out.println(GRAB_STUFF);
        out.flush();
    }


    @Override
    public synchronized void usePowerUp() {
        out.println(USE_POWER_UP);
        out.flush();
    }


    @Override
    public synchronized void shootPeople() {
        out.println(SHOOT_PEOPLE);
        out.flush();
    }


    @Override
    public synchronized void chooseSquare(int x, int y) {
        out.println(CHOOSE_SQUARE + REGEX + x + REGEX + y);
        out.flush();
    }


    @Override
    public synchronized void choosePowerUp(int index) {
        out.println(CHOOSE_POWER_UP + REGEX + index);
        out.flush();
    }


    @Override
    public synchronized void chooseWeaponOnSpawnPoint(int n) {
        out.println(CHOOSE_WEAPON_ON_SPAWN_POINT + REGEX + n);
        out.flush();
    }


    @Override
    public synchronized void chooseWeaponOfMine(int n) {
        out.println(CHOOSE_WEAPON_OF_MINE + REGEX + n);
        out.flush();
    }


    @Override
    public synchronized void switchFireMode() {
        out.println(SWITCH_FIRE_MODE);
        out.flush();
    }


    @Override
    public synchronized void chooseUpgrade(int index) {
        out.println(CHOOSE_UPGRADE + REGEX + index);
        out.flush();
    }


    @Override
    public synchronized void chooseAsynchronousEffectOrder(boolean beforeBasicEffect) {
        out.println(CHOOSE_ASYNCHRONOUS_EFFECT_ORDER + REGEX + beforeBasicEffect);
        out.flush();
    }


    @Override
    public void chooseTarget(String pcColour)  {
        out.println(CHOOSE_TARGET + REGEX + pcColour);
        out.flush();
    }


    @Override
    public void chooseAmmo(String ammoColour) {
        out.println(CHOOSE_AMMO + REGEX + ammoColour);
        out.flush();
    }


    @Override
    public synchronized void chooseDirection(int index) {
        out.println(CHOOSE_DIRECTION + REGEX + index);
        out.flush();
    }

    @Override
    public void response(String response){
        out.println(RESPONSE + REGEX + response);
        out.flush();
    }


    @Override
    public synchronized void skip() {
        out.println(SKIP);
        out.flush();
    }


    @Override
    public synchronized void undo() {
        out.println(UNDO);
        out.flush();
    }


    @Override
    public synchronized void ok() {
        out.println(OK);
        out.flush();
    }


    @Override
    public synchronized void reload() {
        out.println(RELOAD);
        out.flush();
    }


    @Override
    public synchronized void pass() {
        out.println(PASS);
        out.flush();
    }


    @Override
    public synchronized void quit() {
        out.println(QUIT);
        out.flush();
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException ignored) {
        } //if already closed it's ok
    }


    @Override
    public void sendMessage(String s) {
        out.println(SEND_MESSAGE + REGEX + s);
        out.flush();
    }


    @Override
    public synchronized boolean isConnected() {
        return !socket.isClosed();
    }


    @Override
    public synchronized UUID getToken() {
        return this.token;
    }

}
