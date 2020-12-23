import edu.princeton.cs.algs4.Queue;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // find max length of all strings in given array
        int max = Integer.MIN_VALUE;
        for (String s : asciis) {
            max = max > s.length() ? max : s.length();
        }

        String[] sortedAsciis = new String[asciis.length];
        //System.arraycopy(asciis, 0, sortedAsciis, 0, asciis.length);

        //pad string on the right with empty values
        for (int i = 0; i < asciis.length; i++) {
            if (asciis[i].length() < max) {
                int underscoreNum = max - asciis[i].length();
                sortedAsciis[i] = asciis[i] + IntStream.range(0, underscoreNum).mapToObj(j -> " ").collect(Collectors.joining(""));
            } else {
                sortedAsciis[i] = asciis[i];
            }
        }

        for (int i = 0; i < sortedAsciis.length; i++) {
            System.out.println(sortedAsciis[i]);
        }

        for (int d = 0; d < max; d++) {
            sortHelperLSD(sortedAsciis, d);
        }
        //trim empty values
        for (int i = 0; i < sortedAsciis.length; i++) {
            sortedAsciis[i] = sortedAsciis[i].trim();
        }

        return sortedAsciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        Queue<String>[] charToStringTable = new Queue[256]; // record every element in asciis array by certain index
        int[] charAsciis = new int[asciis.length];

        for (int i = 0; i < asciis.length; i++) {
            int asciisToCompare; // convert from string to char(0-255) to verify
            if (index >= asciis[i].length()) {
                asciisToCompare = 0;
            } else {
                asciisToCompare = (int) asciis[i].charAt(asciis[i].length() - index - 1);
            }
            if (charToStringTable[asciisToCompare] == null) {
                charToStringTable[asciisToCompare] = new Queue<>();
            }
            charToStringTable[asciisToCompare].enqueue(asciis[i]);
            charAsciis[i] = asciisToCompare;
        }

        charAsciis = CountingSort.betterCountingSort(charAsciis);
        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = charToStringTable[charAsciis[i]].dequeue();
        }

    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String at start)
     * @param end    int for where to end sorting in this method (does not include String at end)
     * @param index  the index of the character the method is currently sorting on
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        String[] test = {"Ä", "Ç"};
        String[] sorted = RadixSort.sort(test);
        for (int i = 0; i < sorted.length; i++) {
            System.out.print(sorted[i] + ",");
        }
    }

}
