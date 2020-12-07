/* This version use virtual top and virtual bottom, which has backwash issue
package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation1 {
    private int n;
    private int openSize;
    private int[] array;
    private WeightedQuickUnionUF uf;

    public Percolation1(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("Indices should be positive number");
        }
        n = N;
        openSize = 0;
        array = new int[N * N + 2];
        for (int i = 0; i < N * N + 2; i++) {
            array[i] = 0;
        }
        uf = new WeightedQuickUnionUF(N * N + 2);
        //first rwo should connect to the N*N element
        for (int i = 0; i < N; i++) {
            uf.union( N * N,i);
        }
        //last N(N-1) to N*N elements should connect to N*N+1 element
        for (int i = N * (N - 1); i < N * N; i++) {
            uf.union( N * N + 1,i);
        }
    }

    private int xyTo1D(int x, int y) {
        return n * x + y;
    }

    private void unionNeighbors(int x, int y) {
        //connect to left if left element exist&open
        if (y - 1 >= 0 && isOpen(x, y - 1)) {
            uf.union(xyTo1D(x, y), xyTo1D(x, y - 1));
        }
        //connect to right if right element exist
        if (y + 1 < n && isOpen(x, y + 1)) {
            uf.union(xyTo1D(x, y), xyTo1D(x, y + 1));
        }
        //connect to above if above element exist
        if (x - 1 >= 0 && isOpen(x - 1, y)) {
            uf.union(xyTo1D(x, y), xyTo1D(x - 1, y));
        }
        //connect to below if below element exist
        if (x + 1 < n && isOpen(x + 1, y)) {
            uf.union(xyTo1D(x, y), xyTo1D(x + 1, y));
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new java.lang.IndexOutOfBoundsException("Arguments out of range");
        }
        if (array[xyTo1D(row, col)] == 0) {
            array[xyTo1D(row, col)] = 1;
            openSize += 1;
            unionNeighbors(row, col);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new java.lang.IndexOutOfBoundsException("Arguments out of range");
        }
        return array[xyTo1D(row, col)] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new java.lang.IndexOutOfBoundsException("Arguments out of range");
        }
        return uf.connected(xyTo1D(row,col),n*n);
    }
    // number of open sites
    public int numberOfOpenSites(){
        return openSize;
    }
    // does the system percolate?
    public boolean percolates(){
        return uf.connected(n*n,n*n+1);
    }

}
 */
