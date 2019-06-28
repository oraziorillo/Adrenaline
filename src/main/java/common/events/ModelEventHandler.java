package common.events;

import java.util.LinkedList;
import java.util.List;

public class ModelEventHandler {

    private List<ModelEventListener> listeners;


    public ModelEventHandler(){
        this.listeners = new LinkedList<>();
    }


    public synchronized void addModelEventListener(ModelEventListener listener){
        listeners.add(listener);
    }


    public synchronized void fireEvent(ModelEvent event){
        listeners.parallelStream().forEach(l -> l.modelEvent(event));
    }
}
