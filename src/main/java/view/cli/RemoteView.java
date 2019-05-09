package view.cli;

import model.observers.GameObserver;
import model.observers.PlayerObserver;
import model.observers.SquareObserver;

import java.rmi.Remote;

public interface RemoteView extends Remote, GameObserver, PlayerObserver, SquareObserver {

}
