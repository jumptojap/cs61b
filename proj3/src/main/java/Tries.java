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
    private boolean check(char c) {
        if(c >= 'z' && c <= 'z') {
            return true;
        }
        if(c >= 'A' && c <= 'Z') {
            return true;
        }
        return c == ' ';
    }
    public void insert(String word) {
        Node curr = root;
        String temp = "";
        for (int i = 0; i < word.length(); i++) {
            if (!curr.map.containsKey(word.charAt(i))) {
                curr.map.put(word.charAt(i), new Node(curr.word + temp + word.charAt(i)));
                temp = "";
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
        } else if (c == ' ') {
            return c;
        } else {
            return (char) (c - 'A' + 'a');
        }
    }

    private void dfs(String word, int i, List<Node> list, Node curr) {
        if (i == word.length()) {
            list.add(curr);
            return;
        }
        if (!curr.map.containsKey(word.charAt(i))
                && !curr.map.containsKey(reverseChar(word.charAt(i)))) {
            return;
        }
        if (curr.map.containsKey(word.charAt(i))) {
            dfs(word, i + 1, list, curr.map.get(word.charAt(i)));
        }
        if (curr.map.containsKey(reverseChar(word.charAt(i)))) {
            dfs(word, i + 1, list, curr.map.get(reverseChar(word.charAt(i))));
        }

    }

    public List<String> find(String start) {
        start = toCorrectString(start);
        List<String> res = new ArrayList<String>();
        Node curr = root;
        List<Node> list = new ArrayList<>();
        dfs(start, 0, list, curr);
        Queue<Node> queue = new LinkedList<>();
        for (Node node : list) {
            queue.offer(node);
        }
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
