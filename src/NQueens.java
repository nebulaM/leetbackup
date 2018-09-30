import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 51. N-Queens [DFS, recursion]
 *
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard
 * such that no two queens attack each other.
 *
 * Given an integer n, return all distinct solutions to the n-queens puzzle.
 *
 * Each solution contains a distinct board configuration of the n-queens' placement,
 * where 'Q' and '.' both indicate a queen and an empty space respectively.
 *
 * Examples:
 *         _ _ _ _
 *        |_|Q|_|_|
 *        |_|_|_|Q|
 *        |Q|_|_|_|
 *        |_|_|Q|_|
 *
 * Input: 4
 * Output:
 * [
 *  [".Q..",  // Solution 1
 *   "...Q",
 *   "Q...",
 *   "..Q."],
 *
 *  ["..Q.",  // Solution 2
 *   "Q...",
 *   "...Q",
 *   ".Q.."]
 * ]
 *
 */
public class NQueens {
    private static final int EMPTY = 0;
    private static final int QUEEN = 1;
    private static final int FULL  = 2;
    private static final char[] SYMBOL_ARRAY = {'.', 'Q', '.'};
    
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> solns = new ArrayList<List<String>>();
        _solveNQueens(new int[n][n], 0, solns);
        return solns;
    }
    
    /**
     * Solve N-Queens by DFS
     */
    private static void _solveNQueens(int[][] map, int h, List<List<String>> solns) {
        if (h >= map.length)
        {
            solns.add(_getSolnAsList(map));
            return;
        }

        for (int w = 0; w < map[h].length; ++w)
        {
            if (map[h][w] == EMPTY)
            {
                /*
                ** Create a new map so that the modification on map at next height
                ** does not temper the map at this height.
                */
                int[][] newMap = new int[map.length][map[0].length];
                for (int i = 0; i < map.length; ++i)
                    for (int j = 0; j < map[0].length; ++j)
                        newMap[i][j] = map[i][j];
                _markAsOccupied(newMap, h, w);
                _solveNQueens(newMap, h + 1, solns);
            }
        }
    }
    
    /**
     * Set a Queen at the given Cord, and mark the cords in the Queen's attck range as occupied
     * in the input map.
     */
    private static void _markAsOccupied(int[][] map, int queenCordH, int queenCordW) {
        // Fill all W cords at Queen's height since the Queen can attack horizontally
        Arrays.fill(map[queenCordH], FULL);
        
        /*
         * One W cord of the two diagonals that are in Queen's attack range increases (by 1)
         * as height goes up; the other W cord decreases (by 1) as height goes up.
         */
        int diagAtkIncCordW = queenCordW - queenCordH;
        int diagAtkDecCordW = queenCordW + queenCordH;
        for (int h = 0; h < map.length; ++h)
        {
            // Fill all H cords at Queen's width since the Queen can attack vertically
            map[h][queenCordW] = FULL;
            
            // Fill the cords that can be attacked by Queen diagonally, if the cords are in the map
            if (diagAtkIncCordW >= 0 && diagAtkIncCordW < map[h].length)
                map[h][diagAtkIncCordW] = FULL;
            if (diagAtkDecCordW >= 0 && diagAtkDecCordW < map[h].length)
                map[h][diagAtkDecCordW] = FULL;
            diagAtkIncCordW++;
            diagAtkDecCordW--;
        }
        
        // Finally set Queen at the given position
        map[queenCordH][queenCordW] = QUEEN;
    }
    
    /**
     * Translate a map to a list of strings where each numeric number in the map becomes
     * the character at the numeric number's index in SYMBOL_ARRAY.
     */
    private static List<String> _getSolnAsList(int[][]map) {
        List<String> soln = new ArrayList<String>();
        for (int[] row : map)
        {
            StringBuilder sb = new StringBuilder(row.length);
            for (int n : row)
                sb.append(SYMBOL_ARRAY[n]);
            soln.add(sb.toString());
        }
        return soln;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Enter n for an nxn map to solve N queen. For example 5");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = in.readLine().trim();
        int n = Integer.parseInt(line);
        if (n > 0 && n <= 10)
        {
            List<List<String>> solns = solveNQueens(n);
            int count = 1;
            for (List<String> soln : solns)
            {
                System.out.println("Solution " + count + ":");
                for (String s : soln)
                    System.out.println(s);
                System.out.println();
                count++;
            }
        }
        else
            System.out.println("Expect n between 1 - 10, actual n is " + n);
    }
}
