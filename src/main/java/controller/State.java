package controller;

public interface State {

    public void execute();

    public void attack();

    public void move();

    public void collect();

    public void quitAndSave();

    //il controller deve gestire bene il respawn del pc
}
