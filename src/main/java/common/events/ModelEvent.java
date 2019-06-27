package common.events;

import java.io.Serializable;

public abstract class ModelEvent implements Serializable {

    private boolean privateMessage;


    public boolean isPrivateMessage(){
        return privateMessage;
    }

    public void setPrivateMessage(boolean privateMessage) {
        this.privateMessage = privateMessage;
    }

    abstract Object getNewValue();
}
