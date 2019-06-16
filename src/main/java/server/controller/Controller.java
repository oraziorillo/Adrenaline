package server.controller;

import common.enums.PcColourEnum;
import org.modelmapper.ModelMapper;
import server.model.Game;
import server.model.Pc;
import server.model.WeaponCard;
import server.model.squares.Square;

import java.util.*;
import java.util.stream.Collectors;

public class Controller{

    public static final int FIRST_MAP = 1;
    public static final int LAST_MAP = 4;
    public static final int MIN_KILL_SHOT_TRACK_SIZE = 5;
    public static final int MAX_KILL_SHOT_TRACK_SIZE = 8;
    private static final int ACTIONS_PER_TURN = 2;
    private static final int ACTIONS_PER_FRENZY_TURN_AFTER_FIRST_PLAYER = 1;

    private Game game;
    private int currPlayerIndex;
    private int lastPlayerIndex;
    private int remainingActions;
    private List<Player> players;
    private Set<PcColourEnum> availablePcColours;
    private Set<Square> squaresToRefill;
    private LinkedList<Player> deadPlayers;
    private ModelMapper modelMapper;


    public Controller(List<Player> players) {
        super();
        this.game = new Game();
        this.players = players;
        this.squaresToRefill = new HashSet<>();
        this.deadPlayers = new LinkedList<>();
        this.availablePcColours = Arrays.stream(PcColourEnum.values()).collect(Collectors.toSet());
        this.players.addAll(players);
        this.currPlayerIndex = 0;
        this.lastPlayerIndex = -1;
        modelMapper = new ModelMapper();
    }

    public boolean isFinalFrenzy() {
        return game.isFinalFrenzy();
    }


    public Game getGame() {
        return game;
    }


    public List<Player> getPlayers() {
        return players;
    }


    public Player getCurrPlayer(){
        return players.get(currPlayerIndex);
    }


    public Pc getCurrPc() {
        return players.get(currPlayerIndex).getPc();
    }


    public void addDeadPlayer(Player deadPlayer){
        deadPlayers.add(deadPlayer);
    }


    public LinkedList<Player> getDeadPlayers() {
        return deadPlayers;
    }


    public int getCurrPlayerIndex() {
        return currPlayerIndex;
    }


    public int getRemainingActions() {
        return remainingActions;
    }


    public WeaponCard getCurrWeapon() {
        return getCurrPlayer().getCurrWeapon();
    }


    public Set<Square> getSquaresToRefill(){
        return squaresToRefill;
    }


    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setLastPlayerIndex(int index) {
        lastPlayerIndex = index;
    }


    public boolean beforeFirstPlayer(int playerIndex) {
        return playerIndex > lastPlayerIndex;
    }


    public void setCurrWeapon(WeaponCard weapon) {
        getCurrPlayer().setCurrWeapon(weapon);
    }


    public void decreaseRemainingActions() {
        this.remainingActions--;
    }


    public void resetRemainingActions() {
        if (!isFinalFrenzy() || beforeFirstPlayer(getCurrPlayerIndex()))
            this.remainingActions = ACTIONS_PER_TURN;
        else
            this.remainingActions = ACTIONS_PER_FRENZY_TURN_AFTER_FIRST_PLAYER;
    }


    public boolean checkAvailableColour(String pcColour) {
        return availablePcColours.contains(PcColourEnum.valueOf(pcColour));
    }


    public void removeAvailableColour(String pcColour) {
        availablePcColours.remove(PcColourEnum.valueOf(pcColour));
    }


    public void addSquareToRefill(Square s) {
        squaresToRefill.add(s);
    }


    public void resetSquaresToRefill() {
        squaresToRefill.clear();
    }


    public void nextTurn() {
        if (deadPlayers.isEmpty()) {
            increaseCurrPlayerIndex();
            getCurrPlayer().setActive();
            if (currPlayerIndex == lastPlayerIndex)
                game.computeWinner();
                //TODO gestire il valore di ritorno del metodo precedente e implementare la fine della partita chiudendo connessioni..
        } else {
            deadPlayers.get(0).hasToRespawn();
        }
    }

    public void increaseCurrPlayerIndex(){
        if (currPlayerIndex == players.size() - 1)
            currPlayerIndex = 0;
        else
            currPlayerIndex++;
    }


    public boolean isNextOnDuty(Player player){
        return currPlayerIndex < players.size() - 1 && players.indexOf(player) == currPlayerIndex + 1 ||
                currPlayerIndex == players.size() - 1 && players.indexOf(player) == 0;
    }
}
