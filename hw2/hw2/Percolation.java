package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.Assert;

public class Percolation {
    private int n;
    private int openSize;
    private boolean[] openArray;
    private boolean[] connectTop;
    private boolean[] connectBottom;
    private boolean isPercolated;
    private WeightedQuickUnionUF uf;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Indices should be positive number");
        }
        n = N;
        openSize = 0;
        openArray = new boolean[N * N];
        connectTop = new boolean[N * N];
        connectBottom = new boolean[N * N];
        for (int i = 0; i < N * N; i++) {
            openArray[i] = false;
            connectTop[i] = false;
            connectBottom[i] = false;
        }
        uf = new WeightedQuickUnionUF(N * N);

    }

    private int xyTo1D(int x, int y) {
        return n * x + y;
    }

    private void validateXY(int x, int y) {
        if (x < 0 || y < 0 || x >= n || y >= n) {
            throw new IndexOutOfBoundsException("Arguments out of range");
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        int current = xyTo1D(row, col);
        boolean top = false;
        boolean bottom = false;
        validateXY(row, col);
        if (!openArray[current]) {
            if (row == 0) { //first row will connect to top
                connectTop[current] = true;
                top = true; //Be cautious first step open（0，0）will ignore some neighbors union
            }
            if (row == n - 1) { //last row will connect to bottom
                connectBottom[current] = true;
                bottom = true;
            }
            openArray[current] = true;
            openSize += 1;
            //connect to the left element if it exist and is open
            if (col - 1 >= 0 && isOpen(row, col - 1)) {
                int left = xyTo1D(row, col - 1);
                if (connectTop[uf.find(left)] || connectTop[current]) {
                    top = true;
                }
                if (connectBottom[uf.find(left)] || connectBottom[current]) {
                    bottom = true;
                }
                uf.union(current, left);
            }
            //connect to the right element if it exist and is open
            if (col + 1 < n && isOpen(row, col + 1)) {
                int right = xyTo1D(row, col + 1);
                if (connectTop[uf.find(right)] || connectTop[current]) {
                    top = true;
                }
                if (connectBottom[uf.find(right)] || connectBottom[current]) {
                    bottom = true;
                }
                uf.union(current, right);
            }

            //connect to the above element if it exist and is open
            if (row - 1 >= 0 && isOpen(row - 1, col)) {
                int above = xyTo1D(row - 1, col);
                if (connectTop[uf.find(above)] || connectTop[current]) {
                    top = true;
                }
                if (connectBottom[uf.find(above)] || connectBottom[current]) {
                    bottom = true;
                }
                uf.union(current, above);
            }
            //connect to the below element if it exist and is open
            if (row + 1 < n && isOpen(row + 1, col)) {
                int below = xyTo1D(row + 1, col);
                if (connectTop[uf.find(below)] || connectTop[current]) {
                    top = true;
                }
                if (connectBottom[uf.find(below)] || connectBottom[current]) {
                    bottom = true;
                }
                uf.union(current, below);
            }

            connectTop[uf.find(current)] = top;
            connectBottom[uf.find(current)] = bottom;
            if (connectTop[uf.find(current)] && connectBottom[uf.find(current)]) {
                isPercolated = true;
            }

        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateXY(row, col);
        return openArray[xyTo1D(row, col)];
    }

    // A full site is an open site that can be connected to the top
    public boolean isFull(int row, int col) {
        validateXY(row, col);
        return connectTop[uf.find(xyTo1D(row, col))];
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolated;
    }


    public static void main(String[] args) {
        Percolation p4 = new Percolation(4);
        for (int i = 0; i < 16; i++) {
            Assert.assertEquals(false, p4.openArray[i]);
            Assert.assertEquals(false, p4.connectTop[i]);
            Assert.assertEquals(false, p4.connectBottom[i]);
        }
        Assert.assertFalse(p4.isPercolated);
        p4.open(1, 1);
        p4.open(2, 1);
        p4.open(3, 1);
        Assert.assertFalse(p4.isFull(1, 1));
        p4.open(0, 1);
        Assert.assertTrue(p4.isFull(1, 1));
        Assert.assertTrue(p4.percolates());
        p4.open(3, 3);
        Assert.assertFalse(p4.isFull(3, 3));

        Percolation p1 = new Percolation(1);
        p1.open(0, 0);
        Assert.assertTrue(p1.isFull(0, 0));
        Assert.assertTrue(p1.percolates());

        Percolation p2 = new Percolation(2);
        p2.open(1, 1);
        Assert.assertFalse(p2.isFull(1, 1));
        p2.open(0, 1);
        Assert.assertTrue(p2.percolates());
    }
}
