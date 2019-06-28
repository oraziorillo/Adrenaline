package common.events;

import common.dto_model.DTO;

import java.io.Serializable;

public abstract class ModelEvent implements Serializable {

    private boolean privateMessage;


    public boolean isPrivateMessage(){
        return privateMessage;
    }

    public void setPrivateMessage(boolean privateMessage) {
        this.privateMessage = privateMessage;
    }

    public abstract DTO getNewValue();

    public abstract String getPropertyName();
}
