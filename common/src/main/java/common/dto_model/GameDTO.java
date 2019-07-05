package common.dto_model;

import common.enums.AmmoEnum;
import common.enums.PcColourEnum;

import java.util.Arrays;
import java.util.Set;

public class GameDTO {

    GameBoardDTO gameBoardDTO;
    Set<PcDTO> pcs;
    KillShotTrackDTO killShotTrackDTO;


    public GameBoardDTO getGameBoardDTO() {
        return gameBoardDTO;
    }

    public void setGameBoardDTO(GameBoardDTO gameBoardDTO) {
        this.gameBoardDTO = gameBoardDTO;
    }

    public Set<PcDTO> getPcs() {
        return pcs;
    }

    public void setPcs(Set<PcDTO> pcs) {
        this.pcs = pcs;
    }

    public void addPc(PcDTO pc) {
        pcs.add(pc);
    }

    public KillShotTrackDTO getKillShotTrackDTO() {
        return killShotTrackDTO;
    }

    public void setKillShotTrackDTO(KillShotTrackDTO killShotTrackDTO) {
        this.killShotTrackDTO = killShotTrackDTO;
    }

    public GameDTO getCensoredDTOFor(PcColourEnum colour) {
        GameDTO censoredGame = new GameDTO();
        setKillShotTrackDTO(killShotTrackDTO);
        setGameBoardDTO(gameBoardDTO);
        for (PcDTO currPc : pcs) {
            if (!currPc.getColour().equals(colour)) {
                PcDTO censoredPc = currPc.getCensoredDTO();
                censoredGame.addPc(censoredPc);
            } else
                censoredGame.addPc(currPc);
        }
        return censoredGame;
    }

    @Override
    public String toString() {
        StringBuilder gameString = new StringBuilder("Game resumed!\n");
        for (SquareDTO s : gameBoardDTO.getSquares())
            gameString.append(s.toString());
        gameString.append("\nKillshot track:\n").append(killShotTrackDTO.toString());
        pcs.parallelStream().filter(p -> !p.isCensored()).findFirst().ifPresent(p -> {
            gameString.append("\nYour character is:\n").append(p.getName())
                    .append("\n> points:\t").append(p.getPcBoard().getPoints())
                    .append("\n> position:\t").append(p.squareToString())
                    .append("\n> ammo:\t");
            appendPcBoard(gameString, p);
            gameString.append("\n")
                    .append("\n> weapons:\t").append(Arrays.toString(p.getWeapons()))
                    .append("\n> power ups\t").append(Arrays.toString(p.getPowerUps().toArray()));
        });
        gameString.append("\n\n");
        pcs.parallelStream().filter(PcDTO::isCensored).forEach(p -> {
            gameString.append(p.getName())
                    .append("\n> points:\t").append(p.getPcBoard().getPoints())
                    .append("\n> position:\t").append(p.squareToString())
                    .append("\n> ammo:\t");
            appendPcBoard(gameString, p);
            gameString.append("\n")
                    .append("\n> weapons:\t").append(Arrays.toString(p.getWeapons()))
                    .append("\n\n");
        });
        return gameString.toString();
    }

    private void appendPcBoard(StringBuilder gameString, PcDTO p) {
        for (int i = 0; i < AmmoEnum.values().length; i++)
            if (p.getPcBoard().getAmmo()[i] != 0) {
                gameString.append(p.getPcBoard().getAmmo()[i]).append(" ").append(PcColourEnum.values()[i]).append("\t");
            }
        gameString.append("\n")
                .append("\n> damage track:\t").append(Arrays.toString(p.getPcBoard().getDamageTrack()))
                .append("\n> marks:\t");
        for (int i = 0; i < PcColourEnum.values().length; i++) {
            if (p.getPcBoard().getMarks()[i] != 0) {
                gameString.append(p.getPcBoard().getMarks()[i]).append(" ").append(PcColourEnum.values()[i]).append("\t");
            }
        }
    }
}
