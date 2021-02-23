package client.cli.view;

import common.enums.CardinalDirectionEnum;
import common.enums.ControllerMethodsEnum;
import common.remote_interfaces.RemotePlayer;

import java.io.IOException;

public interface CommandParser {
    static void executeCommand(ControllerMethodsEnum command, String[] args, RemotePlayer player) throws IOException {
        try {
            switch (command) {
                case CHOOSE_MAP:
                    player.chooseMap(Integer.parseInt(args[0]));
                    break;
                case CHOOSE_NUMBER_OF_SKULLS:
                    player.chooseNumberOfSkulls(Integer.parseInt(args[0]));
                    break;
                case CHOOSE_PC_COLOUR:
                    player.choosePcColour(args[0]);
                    break;
                case RUN_AROUND:
                    player.runAround();
                    break;
                case GRAB_STUFF:
                    player.grabStuff();
                    break;
                case SHOOT_PEOPLE:
                    player.shootPeople();
                    break;
                case USE_POWER_UP:
                    player.usePowerUp();
                    break;
                case CHOOSE_SQUARE:
                    player.chooseSquare(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                    break;
                case CHOOSE_POWER_UP:
                    player.choosePowerUp(Integer.parseInt(args[0]) - 1);
                    break;
                case CHOOSE_WEAPON_ON_SPAWN_POINT:
                    player.chooseWeaponOnSpawnPoint(Integer.parseInt(args[0]) - 1);
                    break;
                case CHOOSE_WEAPON_OF_MINE:
                    player.chooseWeaponOfMine(Integer.parseInt(args[0]) - 1);
                    break;
                case SWITCH_FIRE_MODE:
                    player.switchFireMode();
                    break;
                case CHOOSE_UPGRADE:
                    player.chooseUpgrade(Integer.parseInt(args[0]) - 1);
                    break;
                case CHOOSE_ASYNCHRONOUS_EFFECT_ORDER:
                    if (args[0].equals("before"))
                        player.chooseAsynchronousEffectOrder(true);
                    else if (args[0].equals("after"))
                        player.chooseAsynchronousEffectOrder(false);
                    break;
                case CHOOSE_TARGET:
                    player.chooseTarget(args[0]);
                    break;
                case CHOOSE_AMMO:
                    player.chooseAmmo(args[0]);
                    break;
                case CHOOSE_DIRECTION:
                    CardinalDirectionEnum dir = CardinalDirectionEnum.parseString(args[0]);
                    if (dir != null)
                        player.chooseDirection(dir.ordinal());
                    break;
                case RESPONSE:
                    player.response(args[0]);
                    break;
                case RELOAD:
                    player.reload();
                    break;
                case UNDO:
                    player.undo();
                    break;
                case OK:
                    player.ok();
                    break;
                case PASS:
                    player.pass();
                    break;
                case SKIP:
                    player.skip();
                    break;
                case QUIT:
                    player.quit();
                    break;
                case HELP:
                    player.help();
                    break;
                default:
                    throw new IllegalArgumentException("This command is illegal");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("You missed some info about the command you gave to me");
        }
    }
}
