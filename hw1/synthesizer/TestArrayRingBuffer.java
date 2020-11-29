package synthesizer;

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
        int expectValue1 = 0;
        for (int i : arb) {
            Assert.assertEquals(expectValue1, i);
            expectValue1 += 1;
        }

        for (int i = 0; i < 9; i += 1) {
            int removedValue = arb.dequeue();
            Assert.assertEquals(i, removedValue);
        }
        Assert.assertEquals(9, (int) arb.peek());
    }

    /**
     * Calls tests for ArrayRingBuffer.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
