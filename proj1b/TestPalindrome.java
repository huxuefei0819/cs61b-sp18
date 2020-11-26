import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    OffByOne obo = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindromeTrue() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
    }

    @Test
    public void testIsPalindromeTrueWithLength0() {
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testIsPalindromeTrueWithLength1() {
        assertTrue(palindrome.isPalindrome("a"));
    }

    @Test
    public void testIsPalindromeFalse() {
        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
    }

    @Test
    public void testIsPalindromeFalseUpperAndLowerCase() {
        assertFalse(palindrome.isPalindrome("aA"));
    }

    @Test
    public void testIsPalindromeTrueOBO() {
        assertTrue(palindrome.isPalindrome("flake", obo));
        assertTrue(palindrome.isPalindrome("spot", obo));
    }

    @Test
    public void testIsPalindromeTrueWithLength0OBO() {
        assertTrue(palindrome.isPalindrome("", obo));
    }

    @Test
    public void testIsPalindromeTrueWithLength1OBO() {
        assertTrue(palindrome.isPalindrome("a", obo));
    }

    @Test
    public void testIsPalindromeFalseOBO() {
        assertFalse(palindrome.isPalindrome("aa", obo));
        assertFalse(palindrome.isPalindrome("aez", obo));
    }

    @Test
    public void testIsPalindromeFalseUpperAndLowerCaseOBO() {
        assertFalse(palindrome.isPalindrome("aB", obo));
    }
}
