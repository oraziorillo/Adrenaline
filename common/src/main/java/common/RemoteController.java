package common;


import enums.PcColourEnum;

import java.io.IOException;
import java.rmi.Remote;

public interface RemoteController extends Remote {

    void chooseMap(int n) throws IOException;

    void chooseNumberOfSkulls(int n) throws IOException;

    void choosePcColour(PcColourEnum colour) throws IOException;

    void discardAndSpawn(int n) throws IOException;

    void showComment(String comment) throws IOException;

    void runAround() throws IOException;

    void grabStuff() throws IOException;

    void shootPeople() throws IOException;

    void chooseSquare(int x, int y) throws IOException;

    void grabWeapon(int n) throws IOException;

    void chooseWeapon(int n) throws IOException;

    void switchFiremode() throws IOException;

    void upgrade() throws IOException;

    void chooseAsynchronousEffectOrder(boolean beforeBasicEffect) throws IOException;

    void ok() throws IOException;

    void reload() throws IOException;

    void pass() throws IOException;

    void quit() throws IOException;

}
