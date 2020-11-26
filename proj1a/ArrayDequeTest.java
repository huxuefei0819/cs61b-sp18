import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testEmpty() {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        assertTrue(q.isEmpty());
        assertEquals(0, q.size());
        assertNull(q.get(0));
        assertNull(q.removeFirst());
        assertNull(q.removeLast());
    }

    @Test
    public void testAddFirstWithResize() {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            q.addFirst(i);
        }
        assertEquals(10, q.size());
        int actualFirst = q.get(0);
        int actualLast = q.get(9);
        assertEquals(9, actualFirst);
        assertEquals(0, actualLast);
    }

    @Test
    public void testAddLastWithResize() {
        ArrayDeque<String> q = new ArrayDeque<>();
        for (int i = 0; i < 20; i++) {
            q.addLast(String.valueOf(i));
        }
        assertEquals(20, q.size());
        String actualFirst = q.get(0);
        String actualLast = q.get(19);
        assertEquals(String.valueOf(0), actualFirst);
        assertEquals(String.valueOf(19), actualLast);
    }

    @Test
    public void testRemoveFirstWithDownsize() {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < 40; i++) {
            q.addLast(i);
        }
        assertEquals(40, q.size());

        for (int i = 0; i < 9; i++) {
            q.removeFirst();
        }
        assertEquals(31, q.size());

        int actualFirst = q.get(0);
        int actualLast = q.get(30);
        assertEquals(9, actualFirst);
        assertEquals(39, actualLast);
    }

    public void testRemoveLastWithDownsize() {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < 40; i++) {
            q.addLast(i);
        }
        assertEquals(40, q.size());

        for (int i = 0; i < 9; i++) {
            q.removeLast();
        }
        assertEquals(31, q.size());

        int actualFirst = q.get(0);
        int actualLast = q.get(30);
        assertEquals(0, actualFirst);
        assertEquals(30, actualLast);
    }

}
