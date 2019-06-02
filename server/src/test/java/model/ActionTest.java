package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import model.actions.Action;
import model.deserializers.ActionDeserializer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;

public class ActionTest {

    private Action action;
    @Mock Pc target1;
    @Mock Pc target2;

    @Before
    public void initFine() throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JsonReader reader = new JsonReader(new FileReader("/home/orazio/Documents/ids_progetto/ing-sw-2019-23/json/action.json"));
        action = customGson.fromJson(reader, Action.class);

        target1 = Mockito.mock( Pc.class );
        target2 = Mockito.mock( Pc.class );
    }


    @Test
    public void isCompleteFine(){
        assertFalse(action.isComplete());
        action.selectPc(target1);
        assertTrue(action.isComplete());
    }


    @Test
    public void addTargetFine(){
        action.selectPc(target1);
        assertFalse(action.getTargets().isEmpty());
        Mockito.when(target1.toString()).thenReturn("1");
        Mockito.when(target2.toString()).thenReturn("2");
        assertEquals("1", action.getTargets().get(0).toString());
        action.selectPc(target2);
        assertEquals("2", action.getTargets().get(0).toString());
    }

}
