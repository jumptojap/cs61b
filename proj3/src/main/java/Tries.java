import java.util.*;

/**
 * ClassName: Tries
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/22 - 9:52
 * Version: v1.0
 */
public class Tries {
    static class Node {
        Map<Character, Node> map = new HashMap<Character, Node>();
        boolean isEnd = false;
        String word = "";

        public Node(String word) {
            this.word = word;
        }

        public Node() {
        }
    }

    private Node root = new Node();

    public void insert(String word) {
        Node curr = root;
        for (int i = 0; i < word.length(); i++) {
            if (!curr.map.containsKey(word.charAt(i))) {
                curr.map.put(word.charAt(i), new Node(curr.word + word.charAt(i)));
            }
            curr = curr.map.get(word.charAt(i));
        }
        curr.isEnd = true;
    }

    private String toCorrectString(String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) >= 'a' && word.charAt(i) <= 'z') {
                sb.append(word.charAt(i));
            } else if (word.charAt(i) >= 'A' && word.charAt(i) <= 'Z') {
                sb.append(word.charAt(i));
            } else if (word.charAt(i) == ' ') {
                sb.append(" ");
            } else {
                continue;
            }
        }
        return sb.toString();
    }

    private char reverseChar(char c) {
        if (c >= 'a' && c <= 'z') {
            return (char) (c + 'A' - 'a');
        } else {
            return (char) (c - 'A' + 'a');
        }

    }

    public List<String> find(String start) {
        start = toCorrectString(start);
        List<String> res = new ArrayList<String>();
        Node curr = root;
        for (int i = 0; i < start.length(); i++) {
            if (!curr.map.containsKey(start.charAt(i))
                    && !curr.map.containsKey(reverseChar(start.charAt(i)))) {
                return res;
            }
            if (curr.map.containsKey(start.charAt(i))) {
                curr = curr.map.get(start.charAt(i));
            } else {
                curr = curr.map.get(reverseChar(start.charAt(i)));
            }
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(curr);
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            if (poll.isEnd) {
                res.add(poll.word);
            }
            for (Node n : poll.map.values()) {
                if (n.isEnd) {
                    res.add(n.word);
                }
                queue.offer(n);
            }
        }
        return res;

    }
}
