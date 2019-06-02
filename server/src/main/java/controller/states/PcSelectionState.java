package controller.states;

import controller.Controller;
import controller.player.Player;
import model.Pc;
import enums.PcColourEnum;

/**
 * Each player selects a Pc
 */
public class PcSelectionState extends State {

    private PcColourEnum pcColour;
    private Player player;

    PcSelectionState(Controller controller) {
        super(controller);
    }
    
    /**
     * A player asks for a pc
     * @param colour the pc colour
     * @param player the player
     */
    @Override
    public void selectPcForPlayer(PcColourEnum colour, Player player) {
        this.pcColour = colour;
        this.player = player;
    }
    
    /**
     * Binds the preselected pc to the preselected colour
     * @return true
     */
    @Override
    public boolean ok() {
        if (pcColour != null && player != null) {
            Pc pc = new Pc(pcColour, controller.getGame());
            player.setPc(pc);
            return true;
        }
        return false;
    }
    
    /**
     * Transition
     * @return FirstTurnState
     */
    @Override
    public State nextState() {
        if (controller.getCurrPlayerIndex() == controller.getPlayers().size() - 1) {
            controller.nextTurn();
            return new FirstTurnState(controller);
        }
        controller.nextTurn();
        return new PcSelectionState(controller);
    }


}
