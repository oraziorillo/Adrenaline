package model.weapon_effect;

import model.Character;



public interface Action {
    void applyOn(Character c);
}



class DamageAction implements Action {

    private short damage;

    DamageAction(short n) {
        this.damage = n;
    }

    @Override
    public void applyOn(Character c) {
        c.takeDamage(damage);
    }
}



class MarksAction implements Action {

    private short marks;

    MarksAction(short n) {
        this.marks = n;
    }

    @Override
    public void applyOn(Character c) {
        c.takeMarks(marks);
    }
}



class MovementAction implements Action {

    private short moves;

    MovementAction(short n) {
        this.moves = n;
    }


    @Override
    public void applyOn(Character c) {
        for (int i = moves; i > 0; i--) {
            c.move(c.game.requestDirection());
        }
    }
}

