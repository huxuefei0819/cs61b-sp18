import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture p;
    private double[][] energyMatrix;

    public SeamCarver(Picture picture) {
        p = picture;
        energyMatrix = new double[width()][height()];
        calculateEnergyMatrix();
    }

    private void calculateEnergyMatrix() {
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                energyMatrix[i][j] = energy(i, j);
                //System.out.println("energyMatrix[" + i + "][" + j + "]=" + energyMatrix[i][j]);
            }
        }
    }

    public Picture picture() {
        return p;
    }

    public int width() {
        return p.width();
    }

    public int height() {
        return p.height();
    }

    private int xPlusOne(int x) {
        return (x + 1) % p.width();
    }

    private int xMinusOne(int x) {
        return (x - 1 + p.width()) % p.width();
    }

    private int yPlusOne(int y) {
        return (y + 1) % p.height();
    }

    private int yMinusOne(int y) {
        return (y - 1 + p.height()) % p.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IndexOutOfBoundsException();
        }
        int rx = p.get(xPlusOne(x), y).getRed() - p.get(xMinusOne(x), y).getRed();
        int gx = p.get(xPlusOne(x), y).getGreen() - p.get(xMinusOne(x), y).getGreen();
        int bx = p.get(xPlusOne(x), y).getBlue() - p.get(xMinusOne(x), y).getBlue();
        double xGradientSquare = rx * rx + gx * gx + bx * bx;

        int ry = p.get(x, yPlusOne(y)).getRed() - p.get(x, yMinusOne(y)).getRed();
        int gy = p.get(x, yPlusOne(y)).getGreen() - p.get(x, yMinusOne(y)).getGreen();
        int by = p.get(x, yPlusOne(y)).getBlue() - p.get(x, yMinusOne(y)).getBlue();
        double yGradientSquare = ry * ry + gy * gy + by * by;

        return xGradientSquare + yGradientSquare;
    }
    /* Returns energy of pixel at (column, row) */

    /* Returns the previous index to input */
    private static int minusOne(int index, int length) {
        if (index == 0) {
            return length - 1;
        }

        return index - 1;
    }

    private static int plusOne(int index, int length) {
        if (index == length - 1) {
            return 0;
        }

        return index + 1;
    }

    /* Returns matrix of minimum cost path ending at pixel (i,j) */
    private double[][] getMinimumCostMatrix() {
        double[][] minimumPathCost = new double[width()][height()];
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                if (j == 0) {
                    minimumPathCost[i][j] = energyMatrix[i][j];
                } else if (i == 0) {
                    minimumPathCost[i][j] = Math.min(
                            minimumPathCost[i][j - 1],
                            minimumPathCost[plusOne(i, width())][j - 1])
                            + energyMatrix[i][j];

                } else if (i == width() - 1) {
                    minimumPathCost[i][j] = Math.min(
                            minimumPathCost[minusOne(i, width())][j - 1],
                            minimumPathCost[i][j - 1])
                            + energyMatrix[i][j];
                } else {
                    minimumPathCost[i][j] = Math.min(
                            Math.min(minimumPathCost[i - 1][j - 1], minimumPathCost[i][j - 1]),
                            minimumPathCost[i + 1][j - 1])
                            + energyMatrix[i][j];
                }
            }
        }
        return minimumPathCost;
    }

    /* Returns the column of the preceding pixel in the seam */
    private int getSeamPredecessor(double[][] minimumCostMatrix, int column, int row) {
        if (column == 0) {
            if (column == width() - 1) {
                return column;
            }
            if (minimumCostMatrix[column][row - 1] < minimumCostMatrix[column + 1][row - 1]) {
                return column;
            }
            return column + 1;

        } else if (column == width() - 1) {
            if (minimumCostMatrix[column - 1][row - 1] < minimumCostMatrix[column][row - 1]) {
                return column - 1;
            }
            return column;
        } else {
            double minCost = Math.min(
                    Math.min(minimumCostMatrix[column - 1][row - 1],
                            minimumCostMatrix[column + 1][row - 1]),
                    minimumCostMatrix[column][row - 1]);

            for (int i = -1; i <= 1; i++) {
                if (minCost == minimumCostMatrix[column + i][row - 1]) {
                    return column + i;
                }
            }
        }

        return -1;
    }

    /* Return sequence of indices for vertical seam. */
    public int[] findVerticalSeam() {
        int[] verticalSeam = new int[height()];
        double[][] minimumCostMatrix = getMinimumCostMatrix();

        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                //System.out.println("minimumCostMatrix[" + i + "][" + j + "]=" + minimumCostMatrix[i][j]);
            }
        }

        int colOfEndMinimumCostPath = 0;
        for (int i = 0; i < width(); i++) {
            if (minimumCostMatrix[i][height() - 1]
                    < minimumCostMatrix[colOfEndMinimumCostPath][height() - 1]) {
                colOfEndMinimumCostPath = i;
            }
        }

        int column = colOfEndMinimumCostPath;
        int row = height() - 1;
        verticalSeam[row] = colOfEndMinimumCostPath;
        while (row > 0) {
            verticalSeam[row - 1] = getSeamPredecessor(minimumCostMatrix, column, row);
            row--;
            column = verticalSeam[row];
        }

        return verticalSeam;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int newWidth = height();
        int newHeight = width();
        Picture newP = new Picture(newWidth, newHeight);
        for (int col = 0; col < newWidth; col++) {
            for (int row = 0; row < newHeight; row++) {
                newP.setRGB(col, row, p.getRGB(row, col));
            }
        }
        SeamCarver sc = new SeamCarver(newP);
        return sc.findVerticalSeam();
    }

    public void removeHorizontalSeam(int[] seam) {
        for (int i : seam) {
            if (i < 0 || i >= height()) {
                throw new IllegalArgumentException("Invalid index in seam");
            }
        }
        p = SeamRemover.removeHorizontalSeam(p, seam);
        calculateEnergyMatrix();
    }

    public void removeVerticalSeam(int[] seam) {
        for (int i : seam) {
            if (i < 0 || i >= width()) {
                throw new IllegalArgumentException("Invalid index in seam");
            }
        }
        p = SeamRemover.removeVerticalSeam(p, seam);
        calculateEnergyMatrix();
    }
}