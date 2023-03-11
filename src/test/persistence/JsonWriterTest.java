package persistence;

import model.Game;
import model.GameList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            GameList gl = new GameList("My game list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            GameList gl = new GameList("My game list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGameList.json");
            writer.open();
            writer.write(gl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGameList.json");
            gl = reader.read();
            assertEquals("My game list", gl.getName());
            assertEquals(0, gl.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            GameList gl = new GameList("My game list");
            gl.addGame(new Game("white", "queens gambit", "sicilian defense", "won"));
            gl.addGame(new Game("black", "caro kann", "kings pawn", "lost"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGameList.json");
            writer.open();
            writer.write(gl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGameList.json");
            gl = reader.read();
            assertEquals("My game list", gl.getName());
            LinkedList<Game> games = gl.getList();
            assertEquals(2, games.size());
            checkGame("white", "queens gambit", "sicilian defense", "won", games.get(0));
            checkGame("black", "caro kann", "kings pawn", "lost", games.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}