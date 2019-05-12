package controller;

import model.enumerations.PcColourEnum;

import java.rmi.Remote;

public interface RemoteController extends Remote {

    public void chooseMap(int n);

    public void chooseNumberOfSkulls(int n);

    public void choosePcColour(PcColourEnum colour);

    public void discardAndSpawn(int n);

    public void showComment(String comment);

    public void runAround();

    public void grabStuff();

    public void shootPeople();

    public void selectSquare(int x, int y);

    public void grabWeapon(int n);

    public void chooseWeapon(int n);

    public void quit();

}
