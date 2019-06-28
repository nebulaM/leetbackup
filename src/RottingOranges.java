import com.eclipsesource.json.JsonArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 994. Rotting Oranges [BFS]
 * In a given grid, each cell can have one of three values:
 * the value 0 representing an empty cell;
 * the value 1 representing a fresh orange;
 * the value 2 representing a rotten orange.
 *
 * Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.
 *
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange.  If this is impossible,
 * return -1 instead.
 *
 * Example 1:
 * Input: [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 * Explanation:
 *       0                   1                  2                 3                   4
 * | X | O | O |      | X | X | O |      | X | X | X |      | X | X | X |      | X | X | X |
 * | O | O | - |  ->  | X | O | - |  ->  | X | X | - |  ->  | X | X | - |  ->  | X | X | - |
 * | - | O | O |      | - | O | O |      | - | O | O |      | - | X | O |      | - | X | X |
 *
 * Example 2:
 * Input: [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1
 * Explanation:
 * The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
 *
 * Example 3:
 * Input: [[0,2]]
 * Output: 0
 * Explanation:
 * Since there are already no fresh oranges at minute 0, the answer is just 0.
 *
 * Example 4:
 * Input: [[2],[1],[1],[1],[2],[1],[1]]
 * Output: 2
 *
 * Note:
 * 1. 1 <= grid.length <= 10
 * 2. 1 <= grid[0].length <= 10
 * 3. grid[i][j] is only 0, 1, or 2.
 *
 */
public class RottingOranges {
    private final int EMPTY = 0;
    private final int GOOD = 1;
    private final int BAD = 2;

    private class Cord {
        public int x = -1;
        public int y = -1;
        public int level = -1;
        public Cord(int x, int y, int level)
        {
            this.x = x;
            this.y = y;
            this.level = level;
        }
    }

    public int orangesRotting(int[][] grid) {
        int h = grid.length, w = grid[0].length;
        List<Cord> starts = new ArrayList<>();
        boolean bGood = false;
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
            {
                if (grid[i][j] == BAD)
                {
                    starts.add(new Cord(i, j, 0));
                }
                else if (grid[i][j] == GOOD)
                    bGood = true;
            }
        if (!bGood) return 0;
        if (starts.isEmpty()) return -1;

        int count = -1;
        boolean[][] visited = new boolean[h][w];
        Queue<Cord> q = new LinkedList<>();
        for (Cord start : starts)
        {
            q.offer(start);
            visited[start.x][start.y] = true;
        }

        while (!q.isEmpty())
        {
            int size = q.size();
            for (int i = 0; i < size; i++)
            {
                Cord node = q.poll();

                List<Cord> adjNodes = getAdjNodes(grid, h, w, node);
                for (Cord adjNode : adjNodes)
                {
                    if (!visited[adjNode.x][adjNode.y])
                    {
                        //System.out.println(adjNode.x + " " + adjNode.y + " for node " + node.x + " " + node.y);
                        visited[adjNode.x][adjNode.y] = true;
                        q.offer(adjNode);
                    }
                }
            }
            count++;
        }

        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
            {
                if (grid[i][j] == GOOD && !visited[i][j])
                {
                    return -1;
                }
            }

        return count;
    }

    public List<Cord> getAdjNodes(int[][] grid, int h, int w, Cord node)
    {
        List<Cord> nodes = new ArrayList<>(4);
        int x = node.x, y = node.y;
        if (x < 0 || x >= h || y < 0 || y >= w)
            return nodes;
        int level = node.level + 1;
        if (x - 1 >= 0 && grid[x - 1][y] != EMPTY) nodes.add(new Cord(x - 1, y, level));
        if (x + 1 <  h && grid[x + 1][y] != EMPTY) nodes.add(new Cord(x + 1, y, level));
        if (y - 1 >= 0 && grid[x][y - 1] != EMPTY) nodes.add(new Cord(x, y - 1, level));
        if (y + 1 <  w && grid[x][y + 1] != EMPTY) nodes.add(new Cord(x, y + 1, level));

        return nodes;
    }

    public static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for(int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }

    public static int[][] stringToInt2dArray(String input) {
        JsonArray jsonArray = JsonArray.readFrom(input);
        if (jsonArray.size() == 0) {
            return new int[0][0];
        }

        int[][] arr = new int[jsonArray.size()][];
        for (int i = 0; i < arr.length; i++) {
            JsonArray cols = jsonArray.get(i).asArray();
            arr[i] = stringToIntegerArray(cols.toString());
        }
        return arr;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        RottingOranges solution = new RottingOranges();
        while ((line = in.readLine()) != null) {
            int[][] grid = stringToInt2dArray(line);

            int ret = solution.orangesRotting(grid);

            String out = String.valueOf(ret);

            System.out.println(out);
        }
    }
}
