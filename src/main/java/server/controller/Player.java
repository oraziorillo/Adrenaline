package server.controller;

import com.google.gson.annotations.Expose;
import common.enums.CardinalDirectionEnum;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;
import server.controller.states.State;
import server.model.Pc;
import server.model.WeaponCard;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import static common.Constants.*;

/**
 * Represents an user out-game.
 * @see Pc for in-game representation
 */
public class Player extends UnicastRemoteObject implements RemotePlayer {

    @Expose private final UUID token;
    @Expose private Pc pc;
    @Expose private State currState;
    private RemoteView view;
    private transient WeaponCard currWeapon;


    public Player(UUID token) throws RemoteException {
        super();
        this.token = token;
    }


    public RemoteView getView() {           //PER IL DEBUG
        return view;
    }

    public void setView(RemoteView view) {      //PER IL DEBUG
        this.view = view;
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


    public void setAttacked(int playerIndex){
        currState.hasBeenAttacked(playerIndex);
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
    public synchronized void chooseUpgrade(int index) {
        if(index > -1 && index < currWeapon.getUpgrades().size())
            currState.selectUpgrade(currWeapon, index);
    }


    @Override
    public synchronized void chooseAsynchronousEffectOrder(boolean beforeBasicEffect) {
        currState.setAsynchronousEffectOrder(currWeapon, beforeBasicEffect);
    }


    @Override
    public synchronized void chooseDirection(int cardinalDirectionIndex){
        for (CardinalDirectionEnum cardinalDirection: CardinalDirectionEnum.values()) {
            if (cardinalDirection.ordinal() == cardinalDirectionIndex)
                currState.selectDirection(cardinalDirection);
        }
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
    
    @Override
    public boolean isConnected() throws RemoteException{
        return true;
    }
    
    
}

