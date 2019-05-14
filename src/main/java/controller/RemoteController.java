package controller;

import model.enumerations.PcColourEnum;

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

    void chooseFireMode(int n);

    void quit() throws IOException;

}
