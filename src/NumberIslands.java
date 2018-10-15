/**
 *
 * 200. Number of Islands [DFS, recursion]
 *
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 *
 * Example 1:
 *
 * Input:
 * 11110
 * 11010
 * 11000
 * 00000
 *
 * Output: 1
 *
 * Example 2:
 *
 * Input:
 * 11000
 * 11000
 * 00100
 * 00011
 *
 * Output: 3
 */

public class NumberIslands {
    public static int numIslands(char[][] grid) {
        // Deep copy grid so the original grid will not be modified
        char[][] map = grid.clone();
        int count = 0;

        for (int i = 0; i < map.length; ++i)
        {
            for (int j = 0; j < map[0].length; ++j)
            {
                if (map[i][j] == '1')
                {
                    _markAsVisited(map, i, j);
                    count++;
                }
            }
        }

        return count;
    }

    private static void _markAsVisited(char[][] map, int x, int y) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length || map[x][y] != '1')
            return;
        map[x][y] = '0';
        _markAsVisited(map, x + 1, y);
        _markAsVisited(map, x - 1, y);
        _markAsVisited(map, x, y + 1);
        _markAsVisited(map, x, y - 1);
    }

}
