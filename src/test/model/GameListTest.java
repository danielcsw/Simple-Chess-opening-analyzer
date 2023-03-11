package model;

import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class GameListTest {

    private GameList g1;
    private GameList g2;

    private Game a;
    private Game b;
    private Game c;
    private Game d;
    private Game e;
    private Game f;
    private Game g;
    private Game h;

    @BeforeEach
    void setUp() {

        g1 = new GameList("g1 games");
        g2 = new GameList("g2 games");

        a = new Game("white", "queen's gambit", "queen's gambit declined", "won");
        b = new Game("white", "queen's gambit", "queen's gambit accepted", "won");
        c = new Game("white", "king's pawn opening", "open game", "won");
        d = new Game("white", "english opening", "symmetrical opening", "lost");
        e = new Game("black", "scandinavian defense", "king's pawn opening", "lost");
        f = new Game("black", "scandinavian defense", "king's pawn opening", "lost");
        g = new Game("black", "sicilian defense", "king's pawn opening", "lost");
        h = new Game("black", "sicilian defense", "king's pawn opening", "won");

    }

    @Test
    void testGameList() {
        assertTrue(g1.length() == 0);
        g1.addGame(a);
        assertTrue(g1.length() == 1);

    }

    @Test
    void testAddGame() {
        assertTrue(g1.length() == 0);
        g1.addGame(a);
        assertTrue(g1.length() == 1);
        g1.addGame(b);
        g1.addGame(b);
        g1.addGame(b);
        g1.addGame(b);
        g1.addGame(b);
        assertTrue(g1.length() == 6);

    }

    @Test
    void testCheckIndex() {
        g1.addGame(b);
        g1.addGame(b);
        g1.addGame(b);
        assertTrue(g1.checkIndex(2));
        assertFalse(g1.checkIndex(5));
        assertFalse(g1.checkIndex(-1));


    }


    @Test
    void testGetGame() {
        g1.addGame(a);
        g1.addGame(b);
        g1.addGame(c);
        assertEquals(b, g1.getGame(1));

    }

    @Test
    void testRemoveGame() {
        g1.addGame(a);
        g1.addGame(b);
        g1.addGame(c);
        assertTrue(g1.length() == 3);
        assertEquals(b, g1.getGame(1));

        g1.removeGame(1);
        assertTrue(g1.length() == 2);
        assertEquals(c, g1.getGame(1));

    }

    @Test
    void testLength() {
        assertTrue(g1.length() == 0);
        g1.addGame(a);
        g1.addGame(b);
        g1.addGame(c);
        assertTrue(g1.length() == 3);

        assertTrue(g2.length() == 0);
        g2.addGame(c);
        assertTrue(g2.length() == 1);

    }

    @Test
    void testOvrlWinRate() {
        g1.addGame(a);
        g1.addGame(b);
        g1.addGame(c);
        g1.addGame(d);
        assertEquals(75, g1.ovrlWinRate());

        g1.addGame(e);
        g1.addGame(f);
        assertEquals(50, g1.ovrlWinRate());

        g1.addGame(g);
        assertEquals(42.86, g1.ovrlWinRate());

        g1.addGame(h);
        assertEquals(g1.ovrlWinRate(), 50);

        assertEquals(0, g2.ovrlWinRate());

    }

    @Test
    void testMyOpeningWinRate() {
        g1.addGame(a);
        g1.addGame(b);
        g1.addGame(c);
        g1.addGame(d);
        g1.addGame(e);
        g1.addGame(f);
        g1.addGame(g);
        g1.addGame(h);
        assertEquals(100, g1.myOpeningWinRate("queen's gambit"));
        assertEquals(50, g1.myOpeningWinRate("sicilian defense"));

        g1.addGame(g);
        g1.addGame(g);
        g1.addGame(g);
        g1.addGame(g);
        g1.addGame(g);
        assertEquals(14.29, g1.myOpeningWinRate("sicilian defense"));
    }

    @Test
    void testTheirOpeningWinRate() {
        g1.addGame(a);
        g1.addGame(b);
        g1.addGame(c);
        g1.addGame(d);
        g1.addGame(e);
        g1.addGame(f);
        g1.addGame(g);
        g1.addGame(h);
        assertEquals(100, g1.theirOpeningWinRate("queen's gambit declined"));
        assertEquals(25, g1.theirOpeningWinRate("king's pawn opening"));

        g1.addGame(h);
        g1.addGame(h);
        g1.addGame(h);
        g1.addGame(h);
        g1.addGame(h);
        assertEquals(66.67, g1.theirOpeningWinRate("king's pawn opening"));
    }

    @Test
    void testGetList() {
        g1.addGame(a);
        g1.addGame(b);
        g1.addGame(c);

        LinkedList<Game> list = new LinkedList<Game>();
        list.add(a);
        list.add(b);
        list.add(c);

        assertEquals(list, g1.getList());
    }


    void testGetName() {
        assertEquals(g1.getName(), "g1 games");
    }


}
