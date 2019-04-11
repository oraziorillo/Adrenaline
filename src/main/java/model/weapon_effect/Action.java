package model.weapon_effect;

import model.Pc;
<<<<<<< Updated upstream


public interface Action {
    void applyOn(Pc pc);
=======



public interface Action {
    void applyOn(Pc c);
>>>>>>> Stashed changes
}



class DamageAction implements Action {

    private short damage;

    DamageAction(short n) {
        this.damage = n;
    }

    @Override
<<<<<<< Updated upstream
    public void applyOn(Pc pc) {
        pc.takeDamage(damage);
=======
    public void applyOn(Pc c) {
        c.takeDamage(damage);
>>>>>>> Stashed changes
    }
}



class MarksAction implements Action {

    private short marks;

    MarksAction(short n) {
        this.marks = n;
    }

    @Override
<<<<<<< Updated upstream
    public void applyOn(Pc pc) {
        pc.takeMarks(marks);
=======
    public void applyOn(Pc c) {
        c.takeMarks(marks);
>>>>>>> Stashed changes
    }
}



class MovementAction implements Action {

    private short moves;

    MovementAction(short n) {
        this.moves = n;
    }


    @Override
<<<<<<< Updated upstream
    public void applyOn(Pc pc) {
=======
    public void applyOn(Pc c) {
>>>>>>> Stashed changes
        for (int i = moves; i > 0; i--) {
            pc.move(/*richiedi direzione*/);
        }
    }
}

