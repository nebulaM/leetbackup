import java.util.ArrayList;
import java.util.List;

/**
 * 212. Word Search II [DFS]
 * Given a 2D board and a list of words from the dictionary, find all words in the board.
 *
 * Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those
 * horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
 *
 * Example:
 * Input:
 * board = [
 ['o','a','a','n'],
 *   ['e','t','a','e'],
 *   ['i','h','k','r'],
 *   ['i','f','l','v']
 * ]
 * words = ["oath","pea","eat","rain"]
 *
 * Output: ["eat","oath"]
 *
 * Explanation:
 *   ['O','A','a','n'],
 *   ['e','T','A','E'],
 *   ['i','H','k','r'],
 *   ['i','f','l','v']
 *
 */
public class WordSearch2 {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> soln = new ArrayList<>();
        for (String word : words)
        {
            boolean bFound = false;
            for (int i = 0; i < board.length; i++)
            {
                for (int j = 0; j < board[0].length; j++)
                {
                    if (board[i][j] == word.charAt(0) && (bFound = findWord(board, word, i, j, 0)))
                    {
                        soln.add(word);
                        break;
                    }
                }
                if (bFound)
                    break;
            }
        }
        return soln;
    }

    public boolean findWord(char[][] board, String word, int x, int y, int pos)
    {
        if (pos >= word.length()) return true;
        char c;
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || (c = board[x][y]) == '.') return false;
        if (c != word.charAt(pos++)) return false;
        // Mark the location as visited
        board[x][y] = '.';
        boolean bFound = findWord(board, word, x + 1, y, pos) ||
                findWord(board, word, x - 1, y, pos) ||
                findWord(board, word, x, y + 1, pos) ||
                findWord(board, word, x, y - 1, pos);
        // Backtracking
        board[x][y] = c;
        return bFound;
    }
}
