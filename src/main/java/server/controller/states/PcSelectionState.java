package server.controller.states;

import server.controller.Controller;
import common.enums.PcColourEnum;
import server.model.Pc;

import java.io.IOException;

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
            try {
                controller.getCurrPlayer().getView().ack(pcColour + "has been chosen");
            } catch (IOException e) {
                e.printStackTrace();
            }
            controller.getGame().addPc(pc);
            controller.getCurrPlayer().setPc(pc);
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
        controller.nextTurn();
        try {
            controller.getCurrPlayer().getView().ack("Next turn has been called and it turns into Inactive");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new InactiveState(controller, InactiveState.FIRST_TURN_STATE);
    }

}
