package common.remote_interfaces;

import java.io.IOException;
import java.rmi.Remote;

public interface RemotePlayer extends Remote {

    void chooseMap(int n) throws IOException;

    void chooseNumberOfSkulls(int n) throws IOException;

    void choosePcColour(String colour) throws IOException;

    void runAround() throws IOException;

    void grabStuff() throws IOException;

    void usePowerUp() throws IOException;

    void shootPeople() throws IOException;

    void chooseSquare(int x, int y) throws IOException;

    void choosePowerUp(int index) throws IOException;

    void chooseWeaponOnSpawnPoint(int index) throws IOException;

    void chooseWeaponOfMine(int index) throws IOException;

    void switchFireMode() throws IOException;

    void chooseUpgrade(int index) throws IOException;

    void chooseAsynchronousEffectOrder(boolean beforeBasicEffect) throws IOException;

    void chooseDirection(int cardinalDirectionIndex) throws IOException;

    void skip() throws IOException;

    void undo() throws IOException;

    void ok() throws IOException;

    void reload() throws IOException;

    void pass() throws IOException;

    void quit() throws IOException;
}
