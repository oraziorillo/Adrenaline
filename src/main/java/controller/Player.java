package controller;

import model.Enumerations.PcColourEnum;
import model.Pc;
import model.PowerUpCard;
import model.Tile;
import org.jetbrains.annotations.Contract;

import java.util.HashSet;
import java.util.UUID;

public abstract class Player implements temporaneo{

    private String username;
    private final UUID uuid;
    private Pc pc;

    //@Contract(pure = true)
    Player(String username, UUID uuid){
        this.username=username;
        this.uuid=uuid;
    }

    public String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Pc getPc() {
        return pc;
    }

    protected void setPc(Pc pc) {
        this.pc = pc;
    }
}

class RmiPlayer extends Player{

    RmiPlayer(String username, UUID uuid) {
        super(username, uuid);
    }

    @Override
    public void printOnView(String s) {

    }

    @Override
    public int receiveNumber() {
        return 0;
    }

    @Override
    public PcColourEnum receivePcColourEnum() {
        return null;
    }

    @Override
    public PowerUpCard receivePowerUpCard() {
        return null;
    }

    @Override
    public void showPossibleTiles(HashSet<Tile> tiles) {

    }

    @Override
    public Tile receiveTile() {
        return null;
    }
}

class SocketPlayer extends Player{

    SocketPlayer(String username, UUID uuid) {
        super(username, uuid);
    }

    @Override
    public void printOnView(String s) {

    }

    @Override
    public int receiveNumber() {
        return 0;
    }

    @Override
    public PcColourEnum receivePcColourEnum() {
        return null;
    }

    @Override
    public PowerUpCard receivePowerUpCard() {
        return null;
    }

    @Override
    public void showPossibleTiles(HashSet<Tile> tiles) {

    }

    @Override
    public Tile receiveTile() {
        return null;
    }
}