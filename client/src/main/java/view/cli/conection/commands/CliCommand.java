package view.cli.conection.commands;

import common.RemoteController;
import org.jetbrains.annotations.NotNull;
import view.InputRequier;
import view.cli.CliInputRequier;
import view.gui.GuiInputRequier;

import java.io.IOException;

import static view.cli.Commands.*;

//TODO: devo dividerla in una superclasse dei vari command e un parser di comandi/builder
public abstract class CliCommand {
    protected final RemoteController controller;
    protected final InputRequier inputRequier;
    protected boolean usingGUI;

    public static CliCommand parseInputString(@NotNull String s, RemoteController controller, boolean gui){
        switch (s.toLowerCase().trim()){
            case MAP_LONG: case MAP_SHORT: case MAP_EXTENDED:
                return new ChooseMapCommand( controller, gui );
            case SKULLS_SHORT: case SKULLS_LONG: case SKULLS_EXTENDED:
                return new ChooseNumberOfSkullsCommand( controller, gui);
            case COLOUR_SHORT: case COLOUR_LONG: case COLOUR_EXTENDED:
                return new ChoosePcColourCommand(controller, gui);
            case DROP_AND_SPAWN_SHORT: case DROP_AND_SPAWN_EXTENDED:
                return new DiscardAndSpawnCommand(controller, gui);
            case COMMENT_SHORT: case COMMENT_LONG:
                return new ShowCommentCommand(controller, gui);
            case RUN_LONG: case RUN_SHORT:
                return new RunAroundCommand(controller, gui);
            case GRAB_STUFF_SHORT: case GRAB_STUFF_LONG:
                return new GrabStuffCommand(controller, gui);
            case SHOOT_LONG: case SHOOT_SHORT:
                return new ShootCommand(controller, gui);
            case SQUARE_LONG: case SQUARE_SHORT:
                return new SelectSquareCommand(controller, gui);
            case GRAB_WEAPON_SHORT: case GRAB_WEAPON_LONG:
                return new ChooseWeaponOnSpawnPointCommand(controller,gui);
            case CHOOSE_WEAPON_SHORT: case CHOOSE_WEAPON_LONG:
                return new ChooseWeaponOfMineCommand(controller,gui);
            case QUIT_LONG: case QUIT_SHORT:
                return new QuitCommand(controller, gui);
                default:
                    System.out.println("Unsupported command");
                    return new UselessCommand();

        }
    }

    CliCommand(RemoteController controller, boolean gui){
        this.controller = controller;
        this.inputRequier = gui? new GuiInputRequier( ) : new CliInputRequier(  );
    }

    public abstract void execute() throws IOException;
}
