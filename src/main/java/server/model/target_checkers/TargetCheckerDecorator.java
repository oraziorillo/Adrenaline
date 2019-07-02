package server.model.target_checkers;

public abstract class TargetCheckerDecorator implements TargetChecker {

    TargetChecker base;


    TargetCheckerDecorator(TargetChecker decorated){
        this.base = decorated;
    }


    @Override
    public TargetChecker getbase() {
        return base;
    }


    @Override
    public int propertyValue() {
        return -1;
    }


    @Override
    public String propertyName() {
        return null;
    }


}
