import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: Tries
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/21 - 17:26
 * Version: v1.0
 */
public class Tries {
    private class Node {
        Map<Character, Node> mp;
        boolean isEnd;

        public Node() {
            mp = new HashMap<Character, Node>();
            isEnd = false;
        }
    }

    public Tries() {
        root = new Node();
    }

    private Node root;

    public void insert(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            if (!node.mp.containsKey(word.charAt(i))) {
                node.mp.put(word.charAt(i), new Node());
            }
            node = node.mp.get(word.charAt(i));
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        Node temp = root;
        for (int i = 0; i < word.length(); i++) {
            if (!temp.mp.containsKey(word.charAt(i))) {
                return false;
            }
            temp = temp.mp.get(word.charAt(i));
        }
        return temp.isEnd;
    }

    public boolean startsWith(String prefix) {
        Node temp = root;
        for (int i = 0; i < prefix.length(); i++) {
            if (!temp.mp.containsKey(prefix.charAt(i))) {
                return false;
            }
            temp = temp.mp.get(prefix.charAt(i));
        }
        return true;
    }
}
