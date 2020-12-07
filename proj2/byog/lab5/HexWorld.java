package byog.lab5;

import javafx.geometry.Pos;
import org.junit.Test;

import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 29;
    private static final int HEIGHT = 30;
    private static final long SEED = 1000;
    private static final Random RANDOM = new Random(SEED);

    // hexagon has size s, return with for row i, from i=0 the lowest row
    //when i<=s,return s+2*i, when s<i<2s,return s+2(2s-i-1)
    private static int rowWidth(int s, int i) {
        if (i < s) {
            return s + 2 * i;
        } else {
            return s + 2 * (2 * s - i - 1);
        }
    }

    @Test
    public void testRowWidth5() {
        Assert.assertEquals(5, rowWidth(5, 0));
        Assert.assertEquals(7, rowWidth(5, 1));
        Assert.assertEquals(9, rowWidth(5, 2));
        Assert.assertEquals(11, rowWidth(5, 3));
        Assert.assertEquals(13, rowWidth(5, 4));
        Assert.assertEquals(13, rowWidth(5, 5));
        Assert.assertEquals(11, rowWidth(5, 6));
        Assert.assertEquals(9, rowWidth(5, 7));
        Assert.assertEquals(7, rowWidth(5, 8));
        Assert.assertEquals(5, rowWidth(5, 9));
    }

    @Test
    public void testRowWidth3() {
        assertEquals(3, rowWidth(3, 5));
        assertEquals(5, rowWidth(3, 4));
        assertEquals(7, rowWidth(3, 3));
        assertEquals(7, rowWidth(3, 2));
        assertEquals(5, rowWidth(3, 1));
        assertEquals(3, rowWidth(3, 0));
    }

    @Test
    public void testRowWidth2() {
        assertEquals(2, rowWidth(2, 0));
        assertEquals(4, rowWidth(2, 1));
        assertEquals(4, rowWidth(2, 2));
        assertEquals(2, rowWidth(2, 3));
    }

    private static class Position {
        private int x;
        private int y;

        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Computesrelative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position, e.g.
     * xxxx
     * xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
     * xxxxxx
     * xxxx
     *
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return
     */
    private static int hexRowOffset(int s, int i) {
        if (i < s) {
            return -i;
        } else {
            return -(2 * s - i - 1);
        }
    }

    @Test
    public void testhexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }

    /**
     * Adds a row of the same tile.
     *
     * @param world the world to draw on
     * @param p     the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t     the tile to draw
     */
    private static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int i = 0; i < width; i++) {
            int x = p.x + i;
            int y = p.y;
            world[x][y] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * Adds a hexagon to the world.
     *
     * @param world the world to draw on
     * @param p     the bottom left coordinate of the hexagon
     * @param s     the size of the hexagon
     * @param t     the tile to draw
     */
    private static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        for (int i = 0; i < 2 * s; i++) {
            int x = p.x + hexRowOffset(s, i);
            int y = p.y + i;
            Position newP = new Position(x, y);
            addRow(world, newP, rowWidth(s, i), t);
        }
    }

    private static void drawVerticalHexes(TETile[][] world, Position p, int s, int num) {
        for (int i = 0; i < num; i++) {
            TETile t = randomTile();
            addHexagon(world, p, s, t);
            p = new Position(p.x, p.y + 2 * s);
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0:
                return Tileset.FLOWER;
            case 1:
                return Tileset.MOUNTAIN;
            case 2:
                return Tileset.TREE;
            case 3:
                return Tileset.SAND;
            case 4:
                return Tileset.GRASS;
            default:
                return Tileset.WATER;
        }
    }

    private static Position findRightLowerPosition(Position p, int s) {
        return new Position(p.x + 2 * s - 1, p.y - s);
    }

    private static Position findRightHigherPosition(Position p, int s) {
        return new Position(p.x + 2 * s - 1, p.y + s);
    }

    public static void main(String[] args) {

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        int s = 3;
        Position p = new Position(s, 2 * s);
        drawVerticalHexes(world, p, s, 3);
        p = findRightLowerPosition(p, s);
        drawVerticalHexes(world, p, s, 4);
        p = findRightLowerPosition(p, s);
        drawVerticalHexes(world, p, s, 5);
        p = findRightHigherPosition(p, s);
        drawVerticalHexes(world, p, s, 4);
        p = findRightHigherPosition(p, s);
        drawVerticalHexes(world, p, s, 3);
        ter.renderFrame(world);
    }

}
