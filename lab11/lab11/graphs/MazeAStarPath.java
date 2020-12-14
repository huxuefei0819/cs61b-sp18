package lab11.graphs;

import edu.princeton.cs.algs4.IndexMinPQ;

/**
 * @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private IndexMinPQ<Integer> pq;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        pq = new IndexMinPQ<>(maze.V());
    }

    /**
     * Estimate of the distance from v to the target.
     */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /**
     * Finds vertex estimated to be closest to target.
     */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /**
     * Performs an A star search from vertex s.
     */
    private void astar(int s) {
        for (int v = 0; v < maze.V(); v++) {
            pq.insert(v, distTo[v]);
        }

        while (!pq.isEmpty()) {
            int v = pq.delMin();

            if (v == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }

            for (int w : maze.adj(v)) {
                marked[w] = true;
                relax(w, v);
            }
        }
    }

    private void relax(int w, int v) {
        if (distTo[w] > distTo[v] + 1) {
            distTo[w] = distTo[v] + 1;
            edgeTo[w] = v;
            if (pq.contains(w)) {
                pq.decreaseKey(w, distTo[w] + h(w));
            }
            announce();
        }

    }

    /**
     * Performs more efficient A star search from vertex s.
     */
    private void astar1(int s) {
        pq.insert(s, 0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            marked[v] = true;
            announce();

            if (v == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
            for (int w : maze.adj(v)) {
                if (!marked[w] && distTo[v] + 1 < distTo[w]) {
                    if (distTo[w] != Integer.MAX_VALUE) { //relax neighbor w to have better weight
                        distTo[w] = distTo[v] + 1;
                        edgeTo[w] = v;
                        pq.decreaseKey(w, distTo[w] + h(w));
                    } else { //first time to update check neighbor w
                        distTo[w] = distTo[v] + 1;
                        edgeTo[w] = v;
                        pq.insert(w, distTo[w] + h(w));
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        astar(s);
    }

}


