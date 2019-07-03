package server.controller.states;

import server.controller.Controller;
import common.enums.PcColourEnum;
import server.database.DatabaseHandler;
import server.model.Pc;

/**
 * Each player selects a Pc
 */
public class PcSelectionState extends State {


    private String pcColour;


    PcSelectionState(Controller controller) {
        super(controller);
    }


    /**
     * A player asks for a pc
     * @param pcColour the colour of the pc chosen
     */
    @Override
    public void selectPcColour(String pcColour) {
        if (controller.checkAvailableColour(pcColour)) {
            this.pcColour = pcColour;
            PcColourEnum colourChosen = PcColourEnum.fromString(pcColour);
            if (colourChosen != null)
                controller.ackCurrent("\n" + colourChosen.getName() +
                        "\n" + colourChosen.getDescription() +
                        "\n\nI guess it's what you wanted");
        }
    }

    
    /**
     * Binds the preselected pc to the preselected colour
     * @return true
     */
    @Override
    public boolean ok() {
        if (pcColour != null) {
            controller.removeAvailableColour(pcColour);
            Pc pc = new Pc(PcColourEnum.fromString(pcColour), controller.getGame());
            controller.getGame().addPc(pc);
            controller.getCurrPlayer().setPc(pc);
            controller.ackAll("@" + DatabaseHandler.getInstance().getUsername(controller.getCurrPlayer().getToken()) + " picked " + pc.getName());
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
        if (controller.amITheLast()) {
            controller.ackCurrent("\nGood Choice!");
            controller.ackAll("\nLet's start!");
            controller.nextTurn();
            controller.ackCurrent("\nChoose a power up to discard, you'll spawn on the spawn point of discarded power up's colour");
            DatabaseHandler.getInstance().save(controller);
        } else {
            controller.ackCurrent("\nGood choice! Now wait for the others choosing their character...");
            controller.nextTurn();
            controller.ackCurrent("\nChoose your character, each of them is particular in its own way:\n" +
                    controller.availableColours());
        }
        return new InactiveState(controller, InactiveState.FIRST_TURN_STATE);
    }
}
