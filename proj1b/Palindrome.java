public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque lld = new LinkedListDeque();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            lld.addLast(c);
        }
        return lld;
    }

    private boolean isPalindromeHelper(Deque d) {
        if (d.size() <= 1) {
            return true;
        } else {
            if (d.removeFirst().equals(d.removeLast())) {
                return isPalindromeHelper(d);
            } else {
                return false;
            }
        }
    }

    public boolean isPalindrome(String word) {
        Deque d = wordToDeque(word);
        return isPalindromeHelper(d);
    }

    private boolean isPalindromeOBOHelper(Deque d, CharacterComparator cc) {
        if (d.size() <= 1) {
            return true;
        } else {
            if (cc.equalChars((Character) d.removeFirst(), (Character) d.removeLast())) {
                return isPalindromeOBOHelper(d, cc);
            } else {
                return false;
            }
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = wordToDeque(word);
        return isPalindromeOBOHelper(d, cc);
    }
}
