package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;
    private Queue<Integer> queue;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        queue = new LinkedList<Integer>();
    }

    /**
     * Conducts a breadth first search of the maze starting at the source.
     */
    private void bfs() {
        marked[s] = true;
        announce();
        if (s == t) {
            return;
        }
        queue.add(s);
        while (!queue.isEmpty()) {
            int prevElemet = queue.poll();
            if (prevElemet == t) {
                return;
            }
            for (int nextElement : maze.adj(prevElemet)) {
                if (!marked[nextElement]) {
                    marked[nextElement] = true;
                    distTo[nextElement] = distTo[prevElemet] + 1;
                    edgeTo[nextElement] = prevElemet;
                    announce();
                    queue.add(nextElement);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

