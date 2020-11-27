import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDequeGold {
    public static void main(String[] args) {
        test();
    }

    @Test
    public static void test() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
        int size = 0;
        String message = "";
        for (int i = 0; i < 500; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.25) {
                sad1.addLast(i);
                ads1.addLast(i);
                size += 1;
                message += "\naddLast(" + i + ")";
            } else if (numberBetweenZeroAndOne < 0.5) {
                sad1.addFirst(i);
                ads1.addFirst(i);
                size += 1;
                message += "\naddFirst(" + i + ")";
            } else if (numberBetweenZeroAndOne < 0.75 && size > 0) {
                Integer student = sad1.removeLast();
                Integer solution = ads1.removeLast();
                size -= 1;
                message += "\nremoveLast()";
                assertEquals(message, solution, student);
            } else if (size > 0) {
                Integer student = sad1.removeFirst();
                Integer solution = ads1.removeFirst();
                size -= 1;
                message += "\nremoveFirst()";
                assertEquals(message, solution, student);
            }

        }
    }
}