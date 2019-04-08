package model.weapon_effect;

public class AddMoveOnMeDecorator extends WeaponEffectDecorator{
    private final int moveMe;
    public AddMoveOnMeDecorator(WeaponEffect decorated, int moveMe) {
        super(decorated);
        this.moveMe=moveMe;
    }

    @Override
    public int getMoveOnMe() {
        return base.getMoveOnMe()+this.moveMe;
    }
}
