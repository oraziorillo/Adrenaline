package model.weapon_effect;

public class IncreaseDamageDecorator extends WeaponEffectDecorator{
    private final int damage;
    private final int marks;
    public IncreaseDamageDecorator(WeaponEffect decorated,int damage,int marks) {
        super(decorated);
        this.damage=damage;
        this.marks=marks;
    }

    @Override
    public int getDamage() {
        return base.getDamage()+this.damage;
    }

    @Override
    public int getMarks() {
        return base.getMarks()+this.marks;
    }
}
