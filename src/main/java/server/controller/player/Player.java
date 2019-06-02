package server.controller.player;

import server.model.Pc;
import org.jetbrains.annotations.Contract;
import java.util.UUID;

/**
 * Represents an user out-game.
 * @see Pc for in-game representation
 */
public class Player {

    private String username;
    private final UUID uuid;
    private Pc pc;

    @Contract(pure = true)
    public Player(String username, UUID uuid) {
        this.username = username;
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
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

    protected void setUsername(String username) {
        this.username = username;
    }
}

