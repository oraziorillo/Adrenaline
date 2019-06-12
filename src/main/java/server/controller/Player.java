package server.controller;

import common.remote_interfaces.RemotePlayer;
import server.controller.states.State;
import server.model.Pc;
import server.model.WeaponCard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import static server.controller.Controller.*;

/**
 * Represents an user out-game.
 * @see Pc for in-game representation
 */
public class Player extends UnicastRemoteObject implements RemotePlayer {

    private final UUID token;
    private Pc pc;
    private State currState;
    private transient WeaponCard currWeapon;


    public Player(UUID token) throws RemoteException {
        super();
        this.token = token;
    }


    public UUID getToken() {
        return token;
    }


    public Pc getPc() {
        return pc;
    }


    public void setCurrState(State currState) {
        this.currState = currState;
    }

    WeaponCard getCurrWeapon(){
        return currWeapon;
    }


    public void setPc(Pc pc) {
        this.pc = pc;
    }


    void setCurrWeapon(WeaponCard currWeapon) {
        this.currWeapon = currWeapon;
    }


    void hasToRespawn(){
        if (currState.isInactive()){
            currState.setHasToRespawn(true);
            currState = currState.nextState();
        }
    }


    void setActive() {
        if (currState.isInactive())
            currState = currState.nextState();
    }


    @Override
    public synchronized void chooseMap(int n) {
        if (n >= FIRST_MAP && n <= LAST_MAP)
            currState.selectMap(n);
    }


    @Override
    public synchronized void chooseNumberOfSkulls(int n) {
        if (n >= MIN_KILL_SHOT_TRACK_SIZE && n <= MAX_KILL_SHOT_TRACK_SIZE)
            currState.selectNumberOfSkulls(n);
    }


    @Override
    public synchronized void choosePcColour(String colour) {
        currState.selectPcColour(colour);
    }


    @Override
    public synchronized void runAround() {
        if (currState.runAround()){
            currState = currState.nextState();
        }
    }


    @Override
    public synchronized void grabStuff() {
        if (currState.grabStuff()){
            currState = currState.nextState();
        }
    }


    @Override
    public synchronized void shootPeople() {
        if (currState.shootPeople()){
            currState = currState.nextState();
        }
    }


    @Override
    public synchronized void usePowerUp() {
        if (currState.usePowerUp()){
            currState = currState.nextState();
        }
    }


    @Override
    public synchronized void chooseSquare(int row, int col) {
        currState.selectSquare(row, col);
    }


    @Override
    public synchronized void choosePowerUp(int index) {
        if (index >= 0 && index <= 3)
            currState.selectPowerUp(index);
    }


    @Override
    public synchronized void chooseWeaponOnSpawnPoint(int index) {
        if (index >= 0 && index <= 2)
            currState.selectWeaponOnBoard(index);
    }


    @Override
    public synchronized void chooseWeaponOfMine(int index) {
        if (index >= 0 && index <= 2)
            currState.selectWeaponOfMine(index);
    }


    @Override
    public synchronized void switchFireMode() {
        if(currWeapon.getFireModes().size() > 1)
            currState.switchFireMode(currWeapon);
    }


    @Override
    public synchronized void upgrade() {
        if(!currWeapon.getUpgrades().isEmpty())
            currState.upgrade(currWeapon);
    }


    @Override
    public synchronized void removeUpgrade() {
        currState.removeUpgrade(currWeapon);
    }


    @Override
    public synchronized void chooseAsynchronousEffectOrder(boolean beforeBasicEffect) {
        currState.setAsynchronousEffectOrder(currWeapon, beforeBasicEffect);
    }


    @Override
    public synchronized void skip() {
        if (currState.skipAction())
            currState = currState.nextState();
    }


    @Override
    public synchronized void undo() {
        if (currState.undo())
            currState = currState.nextState();
    }


    @Override
    public synchronized void ok() {
        if (currState.ok())
            currState = currState.nextState();
    }


    @Override
    public synchronized void reload() {
        if (currState.reload())
            currState = currState.nextState();
    }


    @Override
    public synchronized void pass() {
        if (currState.pass()) {
            currState = currState.nextState();
        }
    }


    @Override
    public synchronized void quit() {
        //TODO: gestire la disconnessione in modo tale da far saltare il turno al giocatore
    }

}

