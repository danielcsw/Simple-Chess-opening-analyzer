package persistence;

import model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkGame(String color, String myOpening, String theirOpening, String result, Game game) {
        assertEquals(color, game.getColor());
        assertEquals(myOpening, game.getMyOpening());
        assertEquals(theirOpening, game.getTheirOpening());
        assertEquals(result, game.getResult());
    }
}
