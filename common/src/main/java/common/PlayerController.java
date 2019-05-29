package common;

import common.player.Player;

import java.rmi.Remote;
import java.util.UUID;

public interface PlayerController extends Remote {
   UUID register(String username);
   Player login(UUID token);
}
