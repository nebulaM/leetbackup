import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 72. Edit Distance [DP]
 *
 * Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.
 *
 * You have the following 3 operations permitted on a word:
 *  1. Insert a character
 *  2. Delete a character
 *  3. Replace a character
 *
 * Example 1:
 *  Input: word1 = "horse", word2 = "ros"
 *  Output: 3
 *  Explanation:
 *  horse -> rorse (replace 'h' with 'r')
 *  rorse -> rose (remove 'r')
 *  rose -> ros (remove 'e')
 *
 * Example 2:
 *  Input: word1 = "intention", word2 = "execution"
 *  Output: 5
 *  Explanation:
 *  intention -> inention (remove 't')
 *  inention -> enention (replace 'i' with 'e')
 *  enention -> exention (replace 'n' with 'x')
 *  exention -> exection (replace 'n' with 'c')
 *  exection -> execution (insert 'u')
 */
public class EditDistance {
    static final int NOTHING = -1;
    static final int DELETE = 0;
    static final int INSERT = 1;
    static final int REPLACE = 2;
    static int[] ACTION_COST = {1, 1, 1};

    public static int minDistance(String srcStr, String destStr) {
        if (srcStr == null && destStr == null) return 0;
        if (srcStr == null || srcStr.length() == 0) return destStr.length() * ACTION_COST[INSERT];
        if (destStr == null || destStr.length() == 0) return srcStr.length() * ACTION_COST[DELETE];
        int[][] memo = new int[srcStr.length()][destStr.length()];
        int[][] action = new int[srcStr.length()][destStr.length()];
        int cost = _minDistance(srcStr.toCharArray(), destStr.toCharArray(), 0, 0, memo, action);
        return cost;
    }

    private static int _minDistance(char[] src, char[] dest, int srcIdx, int destIdx, int[][] memo, int[][] action) {
        if (srcIdx >= src.length)
            return (dest.length - destIdx) * ACTION_COST[INSERT];
        if (destIdx >= dest.length)
            return (src.length - srcIdx) * ACTION_COST[DELETE];

        // Check memo here
        if (memo[srcIdx][destIdx] > 0)
            return memo[srcIdx][destIdx];

        int[] costAction = new int[2];
        if (src[srcIdx] == dest[destIdx])
        {
            costAction[0] = _minDistance(src, dest, srcIdx + 1, destIdx + 1, memo, action);
            costAction[1] = NOTHING;
        }
        else
        {
            int del = _minDistance(src, dest, srcIdx + 1, destIdx, memo, action);
            int ins = _minDistance(src, dest, srcIdx, destIdx + 1, memo, action);
            int rep = _minDistance(src, dest, srcIdx + 1, destIdx + 1, memo, action);
            _getCostAction(del, ins, rep, costAction);
        }
        memo[srcIdx][destIdx] = costAction[0];
        action[srcIdx][destIdx] = costAction[1];
        return memo[srcIdx][destIdx];
    }

    private static void _getCostAction(int del, int ins, int rep, int[] costAction) {
        int cost, action;
        if (del <= ins)
            { if (del <= rep) { action = DELETE; cost = del; } else { action = REPLACE; cost = rep; } }
        else
            { if (ins <= rep) { action = INSERT; cost = ins; } else { action = REPLACE; cost = rep; } }
        costAction[0] = cost + ACTION_COST[action];
        costAction[1] = action;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            String word1 = line;
            line = in.readLine();
            String word2 = line;

            int ret = minDistance(word1, word2);

            String out = String.valueOf(ret);

            System.out.println(out);
        }
    }
}
