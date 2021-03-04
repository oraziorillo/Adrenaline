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
        StringBuilder gameString = new StringBuilder("Game resumed!" + System.lineSeparator());
        for (SquareDTO s : gameBoardDTO.getSquares())
            gameString.append(s.toString());
        gameString.append(System.lineSeparator()).append("Killshot track:").append(System.lineSeparator()).append(killShotTrackDTO.toString());
        pcs.parallelStream().filter(p -> !p.isCensored()).findFirst().ifPresent(p -> {
            gameString.append(System.lineSeparator()).append("Your character is:").append(System.lineSeparator()).append(p.getName())
                    .append(System.lineSeparator()).append("> points:\t").append(p.getPcBoard().getPoints())
                    .append(System.lineSeparator()).append("> position:\t").append(p.squareToString())
                    .append(System.lineSeparator()).append("> ammo:\t");
            appendPcBoard(gameString, p);
            gameString.append(System.lineSeparator())
                    .append(System.lineSeparator()).append("> weapons:\t").append(Arrays.toString(p.getWeapons()))
                    .append(System.lineSeparator()).append("> power ups\t").append(Arrays.toString(p.getPowerUps().toArray()));
        });
        gameString.append(System.lineSeparator()).append(System.lineSeparator());
        pcs.parallelStream().filter(PcDTO::isCensored).forEach(p -> {
            gameString.append(p.getName())
                    .append(System.lineSeparator()).append("> points:\t").append(p.getPcBoard().getPoints())
                    .append(System.lineSeparator()).append("> position:\t").append(p.squareToString())
                    .append(System.lineSeparator()).append("> ammo:\t");
            appendPcBoard(gameString, p);
            gameString.append(System.lineSeparator())
                    .append(System.lineSeparator()).append("> weapons:\t").append(Arrays.toString(p.getWeapons())).append(System.lineSeparator()).append(System.lineSeparator());
        });
        return gameString.toString();
    }

    private void appendPcBoard(StringBuilder gameString, PcDTO p) {
        for (int i = 0; i < AmmoEnum.values().length; i++)
            if (p.getPcBoard().getAmmo()[i] != 0) {
                gameString.append(p.getPcBoard().getAmmo()[i]).append(" ").append(PcColourEnum.values()[i]).append("\t");
            }
        gameString.append(System.lineSeparator())
                .append(System.lineSeparator()).append("> damage track:\t").append(Arrays.toString(p.getPcBoard().getDamageTrack()))
                .append(System.lineSeparator()).append("> marks:\t");
        for (int i = 0; i < PcColourEnum.values().length; i++) {
            if (p.getPcBoard().getMarks()[i] != 0) {
                gameString.append(p.getPcBoard().getMarks()[i]).append(" ").append(PcColourEnum.values()[i]).append("\t");
            }
        }
    }
}
