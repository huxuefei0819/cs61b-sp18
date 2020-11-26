/**
 * This class outputs all palindromes in the words file in the current directory.
 */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in;
        // In in = new In("../library-sp18/data/words.txt");
        Palindrome palindrome = new Palindrome();

//        while (!in.isEmpty()) {
//            String word = in.readString();
//            if (word.length() >= minLength && palindrome.isPalindrome(word)) {
//                System.out.println(word);
//            }
//        }
//        System.out.println("--------------");

//        OffByN obn = new OffByN(5);
//        while (!in.isEmpty()) {
//            String word = in.readString();
//            if (word.length() >= minLength && palindrome.isPalindrome(word, obn)) {
//                System.out.println(word);
//            }
//        }

        //just for more fun
        int maxCount = 0;
        int maxN = 0;
        for (int i = 1; i < 26; i = i + 1) {
            OffByN obi = new OffByN(i);
            int count = 0;
            in = new In("../library-sp18/data/words.txt");
            while (!in.isEmpty()) {
                String word = in.readString();
                if (palindrome.isPalindrome(word, obi)) {
                    count += 1;
                }
            }
            if (count > maxCount) {
                maxN = i;
                maxCount = count;
            }

        }
        System.out.println(maxN + " has the most palindromes in English");
        System.out.println("And the count is " + maxCount);
    }
}
