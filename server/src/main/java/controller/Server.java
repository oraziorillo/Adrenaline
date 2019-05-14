package controller;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Server {
    public static void main(String[] args) throws Exception {
        //TODO
    }

    public static Object readJson(String filename) throws IOException, ParseException {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }
}
