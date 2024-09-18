package lab11.graphs;


import java.util.Objects;
import java.util.PriorityQueue;

/**
 * @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int sourceX;
    private int sourceY;
    private int targetX;
    private int targetY;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    /**
     * Estimate of the distance from v to the target.
     */
    private int h(int v) {
        return -1;
    }

    /**
     * Finds vertex estimated to be closest to target.
     */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /**
     * Performs an A star search from vertex s.
     */
    private class Node implements Comparable<Node> {
        Node parent;
        int pointX;
        int pointY;
        int index;
        double f;
        double h;
        double g;

        public Node(Node parent, int pointX, int pointY) {
            this.parent = parent;
            this.pointX = pointX;
            this.pointY = pointY;
            index = maze.xyTo1D(pointX, pointY);
        }

        public void setCost(double g, int destX, int destY, int soucX, int soucY) {
            this.g = g;
            h = Math.abs(destX - soucX) + Math.abs(destY - soucY);
            f = this.g + this.h;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return pointX == node.pointX && pointY == node.pointY;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pointX, pointY);
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(f, o.f);
        }
    }

    private void astar(int s) {
        if (s == t) {
            return;
        }
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Node fir = new Node(null, sourceX, sourceY);
        fir.setCost(0, targetX, targetY, sourceX, sourceY);
        marked[s] = true;
        edgeTo[s] = s;
        distTo[s] = 0;
        announce();
        pq.add(fir);
        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            if (curr.index == t) {
                return;
            }
            for (int next : maze.adj(curr.index)) {
                if (!marked[next] || distTo[next] > curr.g + 1) {
                    Node temp = new Node(curr, maze.toX(next), maze.toY(next));
                    temp.setCost(curr.g + 1, targetX, targetY, sourceX, sourceY);
                    pq.remove(temp);
                    pq.add(temp);
                    marked[next] = true;
                    edgeTo[next] = curr.index;
                    distTo[next] = (int) temp.g;
                    announce();
                }
            }
        }
        /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    }

    @Override
    public void solve() {
        astar(s);
    }

}

