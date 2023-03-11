package persistence;

import model.Game;
import model.GameList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads GameList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GameList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses GameList from JSON object and returns it
    private GameList parseWorkRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        GameList gl = new GameList(name);
        addGames(gl, jsonObject);
        return gl;
    }

    // MODIFIES: gl
    // EFFECTS: parses Games from JSON object and adds them to GameList
    private void addGames(GameList gl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Games");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(gl, nextGame);
        }
    }

    // MODIFIES: gl
    // EFFECTS: parses Game from JSON object and adds it to GameList
    private void addGame(GameList gl, JSONObject jsonObject) {

        String color = jsonObject.getString("color");
        String myOpening = jsonObject.getString("my opening");
        String theirOpening = jsonObject.getString("opponents opening");
        String result = jsonObject.getString("result");

        Game game = new Game(color, myOpening, theirOpening, result);
        gl.addGame(game);
    }
}

