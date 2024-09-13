/**
 * ClassName: Palindrome
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/13 - 13:25
 * Version: v1.0
 */
public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (char c : word.toCharArray()) {
            deque.addLast(c);
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return recurseDif0(deque);
    }

    private boolean recurseDif0(Deque<Character> deque) {
        if (deque.isEmpty() || deque.size() == 1) {
            return true;
        }
        return deque.removeFirst() == deque.removeLast() && recurseDif0(deque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        return recurseDif1(deque, cc);
    }

    private boolean recurseDif1(Deque<Character> deque, CharacterComparator cc) {
        if (deque.isEmpty() || deque.size() == 1) {
            return true;
        }
        return cc.equalChars(deque.removeFirst(), deque.removeLast()) && recurseDif1(deque, cc);
    }
}
