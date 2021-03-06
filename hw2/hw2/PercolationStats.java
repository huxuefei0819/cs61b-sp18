package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private PercolationFactory pf;
    private int T;
    private int N;
    private double[] threshold;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Arguments should be positive numbers");
        }
        this.pf = pf;
        this.N = N;
        this.T = T;
        updateThresholdArray();
    }

    private double runOnePercolation() {
        Percolation p = pf.make(N);
        while (!p.percolates()) {
            int row = StdRandom.uniform(N);
            int col = StdRandom.uniform(N);
            p.open(row, col);
        }
        return (double) p.numberOfOpenSites() / (N * N);
    }

    private void updateThresholdArray() {
        threshold = new double[T];
        for (int i = 0; i < T; i++) {
            threshold[i] = runOnePercolation();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

//    public static void main(String[] args){
//        PercolationFactory pf = new PercolationFactory();
//        PercolationStats ps = new PercolationStats(20,3000,pf);
//        System.out.println(ps.threshold[0]);
//        System.out.println(ps.mean());
//        System.out.println(ps.confidenceLow());
//        System.out.println(ps.confidenceHigh());
//    }
}
