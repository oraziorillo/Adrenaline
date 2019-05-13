package controller;

import model.enumerations.PcColourEnum;

import java.io.IOException;
import java.rmi.Remote;

public interface RemoteController extends Remote {

    public void chooseMap(int n) throws IOException;

    public void chooseNumberOfSkulls(int n) throws IOException;

    public void choosePcColour(PcColourEnum colour) throws IOException;

    public void discardAndSpawn(int n) throws IOException;

    public void showComment(String comment) throws IOException;

    public void runAround() throws IOException;

    public void grabStuff() throws IOException;

    public void shootPeople() throws IOException;

    public void selectSquare(int x, int y) throws IOException;

    public void grabWeapon(int n) throws IOException;

    public void chooseWeapon(int n) throws IOException;

    public void quit() throws IOException;

}
