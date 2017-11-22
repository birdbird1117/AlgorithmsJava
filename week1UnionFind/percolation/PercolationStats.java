import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] x;
    private int T;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n is negative");
        x = new double[trials];
        T = trials;
        for (int i = 0; i < trials; i++) {
            Percolation grid = new Percolation(n);
            while (!grid.percolates()) {
                int j = StdRandom.uniform(1, n + 1);
                int k = StdRandom.uniform(1, n + 1);
                if (!grid.isOpen(j, k)) {
                    grid.open(j, k);
                    x[i]++;
                }
            }
            x[i] = x[i] / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(x);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(x);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - 1.96 * (stddev()) / Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + (1.96 * stddev()) / Math.sqrt(T));
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats grid = new PercolationStats(n, t);

        StdOut.println("mean =                    " + grid.mean());
        StdOut.println("stddev =                  " + grid.stddev());
        StdOut.println("95% confidence interval = " + grid.confidenceLo() + "," + grid.confidenceHi());
    }
}