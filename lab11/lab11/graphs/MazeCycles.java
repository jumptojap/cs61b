package lab11.graphs;


/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] edgeToCp = new int[edgeTo.length];
    private int s;
    private int t;
    private Maze maze;
    private int[] parent = new int[edgeTo.length];
    private boolean flag = false;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = 0;
        distTo[s] = 0;
        edgeToCp[s] = s;
        parent[s] = -1;
    }

    private void dfs(int s) {
        marked[s] = true;
        announce();
        for (int i : maze.adj(s)) {
            if (!marked[i]) {
                distTo[i] = distTo[s] + 1;
                marked[i] = true;
                edgeToCp[i] = s;
                announce();
                parent[i] = s;
                dfs(i);
                if (flag) {
                    return;
                }
            } else {
                if (parent[s] == i) {
                    continue;
                }
                int st = i;
                edgeToCp[st] = s;
                do {
                    edgeTo[st] = edgeToCp[st];
                    st = edgeToCp[st];
                } while (st != i);
                announce();
                flag = true;
                return;
            }
        }
    }

    @Override
    public void solve() {

        dfs(0);
    }

    // Helper methods go here
}

