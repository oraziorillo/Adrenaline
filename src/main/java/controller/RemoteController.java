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

    public void shoot();

    public void quit();

    public void selectSquare(int x, int y);

}