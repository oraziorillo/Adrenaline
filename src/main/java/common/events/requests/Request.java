package common.events.requests;

import java.util.List;

public class Request {

    private String request;
    private List<String> choices;


    public Request(String request, List<String> choices){
        this.request = request;
        this.choices = choices;
    }


    public List<String> getChoices() {
        return choices;
    }


    @Override
    public String toString() {
        return request;
    }
}
