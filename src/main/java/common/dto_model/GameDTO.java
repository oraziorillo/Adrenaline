package common.dto_model;

import common.enums.PcColourEnum;

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
}
