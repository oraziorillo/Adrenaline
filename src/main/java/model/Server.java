package model;

import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Server {
    public static void main(String[] args) throws Exception {
        //TODO
    }

    public static Object readJson(String filename) throws Exception {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }
}
