package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int N;
    private WeightedQuickUnionUF conn;
    private WeightedQuickUnionUF full;
    private int num;
    private int[] dx = {-1, 1, 0, 0};
    private int[] dy = {0, 0, -1, 1};

    private int pointToIndex(int row, int col) {
        return row * N + col;
    }

    private boolean isVaild(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new int[N][N];
        this.N = N;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = -1;
            }
        }
        conn = new WeightedQuickUnionUF(N * N + 2);
        full = new WeightedQuickUnionUF(N * N + 1);
        num = 0;
        //N*N代表顶部，N*N+1代表底部
        for (int i = 0; i < N; i++) {
            conn.union(i, N * N);
            conn.union(pointToIndex(N - 1, i), N * N + 1);
            full.union(i, N * N);
        }
    }             // create N-by-N grid, with all sites initially blocked

    public void open(int row, int col) {
        if (!isVaild(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (grid[row][col] == 1) {
            return;
        }
        grid[row][col] = 1;
        num++;
        for (int i = 0; i < 4; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];
            if (isVaild(newRow, newCol) && grid[newRow][newCol] == 1) {
                full.union(pointToIndex(newRow, newCol), pointToIndex(row, col));
                conn.union(pointToIndex(newRow, newCol), pointToIndex(row, col));
            }
        }


    }       // open the site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        return grid[row][col] == 1;
    }  // is the site (row, col) open?

    public boolean isFull(int row, int col) {
        if (grid[row][col] == -1) {
            return false;
        }
        return full.connected(pointToIndex(row, col), N * N);
    }  // is the site (row, col) full?

    public int numberOfOpenSites() {
        return num;
    }           // number of open sites

    public boolean percolates() {
        return conn.connected(N * N, N * N + 1);
    }              // does the system percolate?

    public static void main(String[] args) {

    }   // use for unit testing (not required)
}
