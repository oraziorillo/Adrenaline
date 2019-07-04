package common.dto_model;

import java.util.HashSet;
import java.util.Set;

public class GameDTO {

    GameBoardDTO gameBoardDTO;
    Set<PcDTO> pcs;


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
}
