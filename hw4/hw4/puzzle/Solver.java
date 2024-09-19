package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

/**
 * ClassName: Solver
 * Package: hw4.puzzle
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/18 - 18:23
 * Version: v1.0
 */
public class Solver {
    private WorldState initialState;
    private Deque<WorldState> results;
    private int numOfMove;
    private Map<WorldState, Integer> dis;

    public Solver(WorldState initial) {
        initialState = initial;
        numOfMove = -1;
        results = null;
        dis = new HashMap<>();
        solution();
    }

    public int moves() {
        return numOfMove;
    }

    private class Node implements Comparable<Node> {
        @Override
        public int compareTo(Node o) {
            int temp1, temp2;
            if (dis.containsKey(this.state)) {
                temp1 = dis.get(this.state);
            } else {
                temp1 = this.state.estimatedDistanceToGoal();
            }
            if (dis.containsKey(o.state)) {
                temp2 = dis.get(o.state);
            } else {
                temp2 = o.state.estimatedDistanceToGoal();
            }
            return this.moves + temp1 - temp2 - o.moves;
        }

        WorldState state;
        int moves;
        Node parent;

        public Node(WorldState state, int moves, Node parent) {
            this.state = state;
            this.moves = moves;
            this.parent = parent;
        }
    }

    public Iterable<WorldState> solution() {
        if (results != null) {
            return results;
        }
        Node first = new Node(initialState, 0, null);
        MinPQ<Node> pq = new MinPQ<>();
        Map<WorldState, Integer> mp = new HashMap<>();

        pq.insert(first);
        mp.put(initialState, 0);
        results = new LinkedList<>();
        while (!pq.isEmpty()) {
            Node cur = pq.delMin();
            if (cur.state.isGoal()) {
                numOfMove = cur.moves;
                Node temp = cur;
                while (!temp.state.equals(initialState)) {
                    results.addFirst(temp.state);
                    temp = temp.parent;
                }
                return results;
            }
            for (WorldState next : cur.state.neighbors()) {
                Node temp = new Node(next, cur.moves + 1, cur);
                if (!mp.containsKey(next) || mp.get(next) > temp.moves) { //|| mp.get(next) > temp.moves
                    mp.put(next, temp.moves);
                    pq.insert(temp);
                }

            }
        }
        return null;
    }
}
