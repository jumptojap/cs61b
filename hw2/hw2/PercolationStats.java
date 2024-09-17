package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] percolationThreshold;
    private int N;
    private int T;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        percolationThreshold = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation item = pf.make(N);
            while (!item.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!item.isOpen(row, col)) {
                    item.open(row, col);
                }
            }
            percolationThreshold[i] = (double) item.numberOfOpenSites() / (double) (N * N);
        }
    }   // perform T independent experiments on an N-by-N grid

    public double mean() {
        return StdStats.mean(percolationThreshold);
    }                                          // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(percolationThreshold);
    }                                         // sample standard deviation of percolation threshold

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }                                  // low endpoint of 95% confidence interval

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
