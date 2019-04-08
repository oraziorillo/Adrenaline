package model.weapon_effect;

public class AddMoveOnEnemyDecorator extends WeaponEffectDecorator{

    private final int moveOnEnemy;

    public AddMoveOnEnemyDecorator(WeaponEffect decorated, int moveOnEnemy) {
        super(decorated);
        this.moveOnEnemy=moveOnEnemy;
    }

    public int getMoveOnEnemy() {
        return base.getMoveOnEnemy()+this.moveOnEnemy;
    }
}
