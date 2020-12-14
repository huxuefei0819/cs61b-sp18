package lab11.graphs;

/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private Maze maze;
    private int[] parent;
    private boolean foundCircle;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = 1;
        edgeTo[s] = s;
        parent = new int[maze.V()];
        foundCircle = false;
    }

    @Override
    public void solve() {
        dfs(s);
        announce();
    }

    private void dfs(int v) {
        for (int w : maze.adj(v)) {
            if (foundCircle) {
                return;
            }

            if (!marked[w]) {
                marked[w] = true;
                parent[w] = v;
                dfs(w);
            } else if (parent[v] != w) {  // w is already visited and is not parent of v
                parent[w] = v;

                //draw circle using parent[]
                int cur = v;
                edgeTo[cur] = parent[cur];
                while (cur != w) {
                    cur = parent[cur];
                    edgeTo[cur] = parent[cur];
                }
                foundCircle = true;
                return;
            }
        }
    }
}

