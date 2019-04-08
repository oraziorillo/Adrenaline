package model.weapon_effect;

public class WeaponEffect {
    private final short[] cost;
    private final int damage;
    private final int marks;
    private final int moveOnMe;
    private final int moveEnemy;
    private final boolean explosiveDamage;
    private final int numberOfTargets;

    public WeaponEffect(short[] cost,int damage, int marks, int moveOnMe, int moveEnemy, boolean explosiveDamage, int numberOfTargets){
        this.cost=cost;
        this.damage=damage;
        this.marks=marks;
        this.moveOnMe =moveOnMe;
        this.moveEnemy = moveEnemy;
        this.explosiveDamage=explosiveDamage;
        this.numberOfTargets=numberOfTargets;
    }

    WeaponEffect(){
        this(new short[]{1,0,0},0,0,0,0,false,0);
    }

    public int getDamage() {
        return damage;
    }

    public int getMarks() {
        return marks;
    }

    public int getMoveOnMe() {
        return moveOnMe;
    }

    public int getNumberOfTargets() {
        return numberOfTargets;
    }

    public boolean isExplosiveDamage() {
        return explosiveDamage;
    }

    public int getMoveOnEnemy() {
        return moveEnemy;
    }

    public short[] getCost() {
        return cost;
    }
}
