package controller;

import model.Pc;

import java.util.UUID;

public abstract class Player{

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

    public void setPc(Pc pc) {
        this.pc = pc;
    }
}

class RmiPlayer extends Player{

    RmiPlayer(String username, UUID uuid) {
        super(username, uuid);
    }
}

class SocketPlayer extends Player{

    SocketPlayer(String username, UUID uuid) {
        super(username, uuid);
    }
}