package common.remote_interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface RemotePlayer extends Remote {

    void chooseMap(int n) throws RemoteException;

    void chooseNumberOfSkulls(int n) throws RemoteException;

    void choosePcColour(String colour) throws RemoteException;

    void runAround() throws RemoteException;

    void grabStuff() throws RemoteException;

    void usePowerUp() throws RemoteException;

    void shootPeople() throws RemoteException;

    void chooseSquare(int x, int y) throws RemoteException;

    void choosePowerUp(int index) throws RemoteException;

    void chooseWeaponOnSpawnPoint(int index) throws RemoteException;

    void chooseWeaponOfMine(int index) throws RemoteException;

    void switchFireMode() throws RemoteException;

    void chooseUpgrade(int index) throws RemoteException;

    void chooseAsynchronousEffectOrder(boolean beforeBasicEffect) throws RemoteException;

    void chooseTarget(String pcColour) throws RemoteException;

    void chooseAmmo(String ammoColour) throws RemoteException;

    void chooseDirection(int cardinalDirectionIndex) throws RemoteException;

    void response(String response) throws RemoteException;

    void skip() throws RemoteException;

    void undo() throws RemoteException;

    void ok() throws RemoteException;

    void reload() throws RemoteException;

    void pass() throws RemoteException;

    void quit() throws RemoteException;
    
    boolean isConnected() throws RemoteException;
    
    UUID getToken() throws RemoteException;
    
    void sendMessage(String s) throws RemoteException;
    
}
