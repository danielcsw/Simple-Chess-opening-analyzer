package persistence;

import model.Game;
import model.GameList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameList gl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGameList.json");
        try {
            GameList gl = reader.read();
            assertEquals("My game list", gl.getName());
            assertEquals(0, gl.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameList.json");
        try {
            GameList gl = reader.read();
            assertEquals("My game list", gl.getName());
            LinkedList<Game> games = gl.getList();
            assertEquals(2, games.size());
            checkGame("white", "queens gambit", "sicilian defense", "won", games.get(0));
            checkGame("black", "caro kann", "kings pawn", "lost", games.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
