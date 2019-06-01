package controller.states;

import controller.Controller;
import controller.player.Player;
import model.Pc;
import enums.PcColourEnum;

public class PcSelectionState extends State {

    private PcColourEnum pcColour;
    private Player player;

    PcSelectionState(Controller controller) {
        super(controller);
    }

    @Override
    public void selectPcForPlayer(PcColourEnum colour, Player player) {
        this.pcColour = colour;
        this.player = player;
    }

    @Override
    public boolean ok() {
        if (pcColour != null && player != null) {
            Pc pc = new Pc(pcColour, controller.getGame());
            player.setPc(pc);
            controller.getGame().addPc(pc);
            return true;
        }
        return false;
    }

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
