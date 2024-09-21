import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * ClassName: BinaryTrie
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/21 - 19:22
 * Version: v1.0
 */
public class BinaryTrie implements Serializable {
    Node root;
    Map<Character, BitSequence> lookupTable;

    private class Node implements Serializable {
        Node left, right;
        Character value;
        int freq;

        public Node(Node left, Node right, Character value, int freq) {
            this.left = left;
            this.right = right;
            this.value = value;
            this.freq = freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        lookupTable = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<Node>(
                new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o1.freq - o2.freq;
                    }
                });
        for (Character c : frequencyTable.keySet()) {
            pq.add(new Node(null, null, c, frequencyTable.get(c)));
        }
        while (pq.size() >= 2) {
            Node n1 = pq.poll();
            Node n2 = pq.poll();
            Node n3 = new Node(n1, n2, null, n1.freq + n2.freq);
            pq.add(n3);
        }
        root = pq.poll();
        bfs();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        Node temp = root;
        BitSequence res = new BitSequence();
        for (int i = 0; i < querySequence.length(); i++) {
            if (querySequence.bitAt(i) == 0) {
                if (temp.left == null) {
                    break;
                }
                temp = temp.left;
                res = res.appended(0);
            } else {
                if (temp.right == null) {
                    break;
                }
                temp = temp.right;
                res = res.appended(1);
            }
        }
        return new Match(res, temp.value);
    }

    private class Item implements Serializable {
        Node node;
        BitSequence value;

        public Item(Node node, BitSequence value) {
            this.node = node;
            this.value = value;
        }
    }

    private void bfs() {
        Queue<Item> queue = new LinkedList<>();
        Item item = new Item(root, new BitSequence());
        queue.add(item);
        while (!queue.isEmpty()) {
            Item poll = queue.poll();
            if (poll.node.left == null && poll.node.right == null) {
                lookupTable.put(poll.node.value, poll.value);
            }
            if (poll.node.left != null) {
                BitSequence appended = new BitSequence(poll.value).appended(0);
                queue.offer(new Item(poll.node.left, appended));
            }
            if (poll.node.right != null) {
                BitSequence appended = new BitSequence(poll.value).appended(1);
                queue.offer(new Item(poll.node.right, appended));
            }
        }
    }

    public Map<Character, BitSequence> buildLookupTable() {
        return lookupTable;
    }
}
