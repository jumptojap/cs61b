package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;

public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF unionUF;
    private int numOfOpenSites;
    private boolean isPercolates;
    private List<int[]> fullSites;
    private int[] dx = {-1, 0, 1, 0};
    private int[] dy = {0, -1, 0, 1};

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = -1;
            }
        }
        unionUF = new WeightedQuickUnionUF(N * N);
        numOfOpenSites = 0;
        isPercolates = false;
        fullSites = new ArrayList<>(N);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!(row >= 0 && row <= grid.length - 1 && col >= 0 && col <= grid.length - 1)) {
            throw new IndexOutOfBoundsException();
        }
        grid[row][col] = 0;
        numOfOpenSites++;
        if (row == 0) {
            fullSites.add(new int[]{row, col});
        }
        for (int i = 0; i < 4; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];
            if (newRow >= 0 && newRow < grid.length && newCol >= 0
                    && newCol < grid.length && grid[newRow][newCol] == 0) {
                unionUF.union(getIndex(newRow, newCol), getIndex(row, col));
            }
        }
        if (isFull(row, col)) {
            isPercolates = true;
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
        for (int[] item : fullSites) {
            if (unionUF.connected(getIndex(row, col), getIndex(item[0], item[1]))) {
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
        return isPercolates;
    }

}
