package model.weapon_effect;

import model.Pc;


public interface Action {
    void applyOn(Pc pc);
}



class DamageAction implements Action {

    private short damage;

    DamageAction(short n) {
        this.damage = n;
    }

    @Override
    public void applyOn(Pc pc) {
        pc.takeDamage(damage);
    }
}



class MarksAction implements Action {

    private short marks;

    MarksAction(short n) {
        this.marks = n;
    }

    @Override
    public void applyOn(Pc pc) {
        pc.takeMarks(marks);
    }
}



class MovementAction implements Action {

    private short moves;

    MovementAction(short n) {
        this.moves = n;
    }


    @Override
    public void applyOn(Pc pc) {
        for (int i = moves; i > 0; i--) {
            pc.move(pc.game.requestDirection());
        }
    }
}

