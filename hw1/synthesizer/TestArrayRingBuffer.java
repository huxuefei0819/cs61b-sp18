package synthesizer;

import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        Assert.assertTrue(arb.isEmpty());
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(i);
        }
        Assert.assertTrue(arb.isFull());
        Assert.assertEquals(0, (int) arb.peek());
        for (int i = 0; i < 9; i += 1) {
            arb.dequeue();
        }
        Assert.assertEquals(9, (int) arb.peek());
    }

    @Test
    public void overflowTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(i);
        }
    }

    @Test
    public void underflowTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);
        for (int i = 0; i < 5; i += 1) {
            arb.enqueue(i);
        }
        for (int i = 0; i < 10; i += 1) {
            arb.dequeue();
        }
    }

    /**
     * Calls tests for ArrayRingBuffer.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
