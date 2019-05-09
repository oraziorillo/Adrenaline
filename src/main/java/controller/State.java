package controller;

import model.Game;
import model.Pc;
import model.enumerations.PcColourEnum;

abstract class State {

    Game game;

    State(Game game) {
        this.game = game;
    }

    boolean initializeMap(int n) {
        return false;
    }

    boolean setNumberOfSkulls(int n) {
        return false;
    }

    boolean assignPcToPlayer(PcColourEnum colour, Player player){
        return false;
    }

    boolean spawnPc(int n) {
        return false;
    }

    void drawTwoPowerUps(Pc pc) {
    }
}
