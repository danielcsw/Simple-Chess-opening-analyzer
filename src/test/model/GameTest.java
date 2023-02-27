package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    private Game a;
    private Game b;

    @BeforeEach
    void setUp() {
        a = new Game("white", "queen's gambit", "queen's gambit declined", "won");
        b = new Game("black", "sicilian defense", "king's pawn opening", "lost");
    }


    @Test
    void testGame() {

    }

    @Test
    void testGetColor() {
        assertEquals("white", a.getColor());
        assertEquals("black", b.getColor());
    }

    @Test
    void testGetMyOpening() {
        assertEquals("queen's gambit", a.getMyOpening());
        assertEquals("sicilian defense", b.getMyOpening());
    }

    @Test
    void testGetTheirOpening() {
        assertEquals("queen's gambit declined", a.getTheirOpening());
        assertEquals("king's pawn opening", b.getTheirOpening());
    }

    @Test
    void testGetResult() {
        assertEquals("won", a.getResult());
        assertEquals("lost", b.getResult());
    }






}