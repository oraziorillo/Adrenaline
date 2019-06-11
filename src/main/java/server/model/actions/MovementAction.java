package server.model.actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import server.model.*;
import server.model.squares.Square;
import server.model.target_checkers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovementAction extends Action {

    @Expose private boolean selfMovement;
    @Expose private TargetChecker destinationChecker;


    public MovementAction(JsonObject jsonAction) {
        super(jsonAction);
        this.selfMovement = jsonAction.get("selfMovement").getAsBoolean();

        JsonArray json = jsonAction.get("destinationChecker").getAsJsonArray();
        for(JsonElement checker : json) {
            JsonObject jsonChecker = checker.getAsJsonObject();
            switch (jsonChecker.get("type").getAsString()) {
                case "minDistance":
                    this.destinationChecker = new MinDistanceDecorator(destinationChecker, jsonChecker.get("minDistance").getAsInt());
                    break;
                case "maxDistance":
                    this.destinationChecker = new MaxDistanceDecorator(destinationChecker, jsonChecker.get("maxDistance").getAsInt());
                    break;
                case "straightLine":
                    this.destinationChecker = new SimpleStraightLineDecorator(destinationChecker, null);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void selectPc(Pc targetPc) {
        if (targetPc != null)
            //in questo modo non è permesso all'utente cambiare il target se è già stato selezionato in un'azione precedente
            //dovremmo sapere qui se l'azione è stata settata tramite un hasSameTarget
            return;
        if (!selfMovement) {
            //if are selected more Pcs than allowed, the target set becomes empty and adds the new Pc
            if (targets.size() == maxNumberOfTargets)
                targets = new HashSet<>();
            targets.add(targetPc);
        }
    }


    @Override
    public void selectSquare(Square targetSquare) {
        if (!targets.isEmpty() || this.selfMovement) {
            this.targetSquare = targetSquare;
        }
    }


    @Override
    public Set<Square> validDestinations(Square targetSquare) {
        if (selfMovement)
            return targetChecker.validSquares(targetSquare);
        return destinationChecker.validSquares(new ArrayList<>(targets).get(0).getCurrSquare());
    }


    @Override
    public void resetAction() {
        targets.clear();
        targetSquare = null;
    }


    @Override
    public Set<Pc> apply(Pc shooter) {
        if (selfMovement)
            targets.add(shooter);
        if (!isComplete())
            return null;
        new ArrayList<>(targets).get(0).moveTo(targetSquare);
        resetAction();
        return null;
    }


    @Override
    public boolean isComplete() {
        return !isParameterized() ||
                ((selfMovement || !targets.isEmpty()) && targetSquare != null);
    }
}
