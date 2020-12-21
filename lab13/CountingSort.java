/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    public static int[] notbetterCountingSort(int[] arr) {
        // find min & max
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            if (i >= 0) {
                max = max > i ? max : i;
            } else {
                min = min < i ? min : i;
            }
        }

        // gather all the counts for each value
        int[] countP = new int[max + 1];
        int[] countN = new int[1];
        if (min != Integer.MAX_VALUE) {
            countN = new int[Math.abs(min) + 1];
        }
        for (int i : arr) {
            if (i >= 0) {
                countP[i]++;
            } else {
                countN[Math.abs(i)]++;
            }
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = countN.length - 1; i > 0; i -= 1) {
            for (int j = 0; j < countN[i]; j += 1, k += 1) {
                sorted[k] = -i;
            }
        }

        int n = k;
        for (int i = 0; i < countP.length; i += 1) {
            for (int j = 0; j < countP[i]; j += 1, n += 1) {
                sorted[n] = i;
            }
        }

        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param toSort int array that will be sorted
     */
    public static int[] betterCountingSort(int[] toSort) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int num : toSort) {
            if (num < min) {
                min = num;
            }
            if (num > max) {
                max = num;
            }
        }
        int count[] = new int[max - min + 1];
        for (int num : toSort) {
            int arrayPosition = num - min;
            count[arrayPosition]++;
        }

        int[] startingIndices = new int[count.length];
        int startingIndex = 0;
        for (int i = 0; i < startingIndices.length; i++) {
            startingIndices[i] = startingIndex;
            startingIndex = startingIndex + count[i];
        }
        int[] sorted = new int[toSort.length];
        for (int num : toSort) {
            int arrayPosition = num - min;
            sorted[startingIndices[arrayPosition]] = num;
            startingIndices[arrayPosition]++;
        }
        return sorted;
    }

//    public static void main(String[] args) {
//        int[] someNegative = {-99, -95, -4, 2, 1, -2, 5, 3, 0, -2, 3, 1, 1};
//        int[] sortedSomeNegative = CountingSort.betterCountingSort(someNegative);
//        for (int i = 0; i < sortedSomeNegative.length; i++) {
//            System.out.print(sortedSomeNegative[i] + ",");
//        }
//    }

}
