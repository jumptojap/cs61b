package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


import java.util.*;


public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF unionUF;
    private WeightedQuickUnionUF unionUF1;
    private int numOfOpenSites;
    private int[] dx = {-1, 0, 1, 0};
    private int[] dy = {0, -1, 0, 1};
    private int N;
    private Map<Integer, Integer> mp;

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
        mp = new HashMap<>();
        //多出来的两个空间代表顶部和底部
        unionUF = new WeightedQuickUnionUF(N * N + 2);
        unionUF1 = new WeightedQuickUnionUF(N * N);
        for (int i = 0; i < N; i++) {
            unionUF.union(N * N, getIndex(0, i));
            mp.put(i, unionUF1.find(i));
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
        Set<Integer> keySet = mp.keySet();
        List<Integer> ls = new ArrayList<>(N);
        Set<Integer> val = new HashSet<>();
        for (Integer key : keySet) {
            if (!val.contains(mp.get(key))) {
                val.add(mp.get(key));
            } else {
                ls.add(key);
            }
        }
        for (int item : ls) {
            mp.remove(item);
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
        for (int key : mp.keySet()) {
            if (unionUF1.connected(getIndex(row, col), mp.get(key))) {
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
