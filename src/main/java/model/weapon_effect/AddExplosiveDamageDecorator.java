package model.weapon_effect;

public class AddExplosiveDamageDecorator extends WeaponEffectDecorator{

    public AddExplosiveDamageDecorator(WeaponEffect decorated) {
        super(decorated);
    }

    @Override
    public boolean isExplosiveDamage() {
        return true;
    }
}
