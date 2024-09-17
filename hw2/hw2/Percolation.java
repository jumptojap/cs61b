package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF unionUF;
    private WeightedQuickUnionUF unionUF1;
    private int numOfOpenSites;
    private int[] dx = {-1, 0, 1, 0};
    private int[] dy = {0, -1, 0, 1};
    private int N;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = -1;
            }
        }
        //多出来的两个空间代表顶部和底部
        unionUF = new WeightedQuickUnionUF(N * N + 2);
        unionUF1 = new WeightedQuickUnionUF(N * N);
        for (int i = 0; i < N; i++) {
            unionUF.union(N * N, getIndex(0, i));
        }
        for (int i = 0; i < N; i++) {
            unionUF.union(N * N + 1, getIndex(N - 1, i));
        }

        numOfOpenSites = 0;

    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        //System.out.println("haha");
        if (!(row >= 0 && row <= grid.length - 1 && col >= 0 && col <= grid.length - 1)) {
            throw new IndexOutOfBoundsException();
        }

        if (grid[row][col] == 0) {
            return;
        }
        grid[row][col] = 0;
        numOfOpenSites++;
        for (int i = 0; i < 4; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];
            if (newRow >= 0 && newRow < grid.length && newCol >= 0
                    && newCol < grid.length && grid[newRow][newCol] == 0) {
                unionUF.union(getIndex(newRow, newCol), getIndex(row, col));
                unionUF1.union(getIndex(newRow, newCol), getIndex(row, col));

            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!(row >= 0 && row <= grid.length - 1 && col >= 0 && col <= grid.length - 1)) {
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col] == 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (grid[row][col] == -1) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            if (unionUF1.connected(i, getIndex(row, col))) {
                return true;
            }
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    private int getIndex(int row, int col) {
        return grid.length * row + col;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionUF.connected(N * N + 1, N * N);
    }

    public static void main(String[] args) {

    }   // use for unit testing (not required)

}
