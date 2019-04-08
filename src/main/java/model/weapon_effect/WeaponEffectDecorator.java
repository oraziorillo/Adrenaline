package model.weapon_effect;

public abstract class WeaponEffectDecorator extends WeaponEffect{
    protected WeaponEffect base;

    //TODO: difficile applicazione dinamica a runtime. Da discutere.

    public WeaponEffectDecorator(WeaponEffect decorated){
        this.base=decorated;
    }

    @Override
    public int getMoveOnMe() {
        return base.getMoveOnMe();
    }

    @Override
    public int getDamage() {
        return base.getDamage();
    }

    @Override
    public int getMarks() {
        return base.getMarks();
    }

    @Override
    public int getMoveOnEnemy() {
        return base.getMoveOnEnemy();
    }

    @Override
    public int getNumberOfTargets() {
        return base.getNumberOfTargets();
    }

    @Override
    public boolean isExplosiveDamage() {
        return base.isExplosiveDamage();
    }

    @Override
    public short[] getCost() {
        return base.getCost();
    }
}
