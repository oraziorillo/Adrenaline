package common.remote_interfaces;

import common.dto_model.PcBoardDTO;
import common.enums.PcColourEnum;

public interface ModelChangeListener {

    void onSquareTargetableChange(int row, int col, boolean newValue);

    void onMovement(PcColourEnum pc, int oldRow, int oldCol, int newRow, int newCol);

    void onWeaponCollect(PcColourEnum pc, int droppedWeapon, int grabbedWeapon);

    void onAmmoCollect(PcColourEnum pc);

    void onDrawPowerUp(PcColourEnum pc, int newIndex);

    void onDiscardPowerUp(PcColourEnum pc, int oldIndex);

    void onPcBoardChange(PcBoardDTO newPcBoard);

    void onRefill(int typeOfDeck, int row, int col);

    void onKill(PcColourEnum shooter, PcColourEnum killed, boolean isOverkill);

    void onSpawn(PcColourEnum pc, int newRow, int newCol);

    void onAdrenaline(int level);

    void onFinalFrenzy();
}
