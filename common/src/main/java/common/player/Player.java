package common.player;

import model.Pc;
import org.jetbrains.annotations.Contract;

import java.util.UUID;

public class Player{

    private String username;
    private final UUID uuid;
    private Pc pc;

    @Contract(pure = true)
    public Player(String username, UUID uuid){
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

