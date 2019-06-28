import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 322. Coin Change [DP]
 * You are given coins of different denominations and a total amount of money amount. Write a function to compute
 * the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by
 * any combination of the coins, return -1.
 *
 * Example 1:
 * Input: coins = [1, 2, 5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 *
 * Example 2:
 * Input: coins = [2], amount = 3
 * Output: -1
 *
 * Note:
 *  You may assume that you have an infinite number of each kind of coin.
 *
 */
 public class CoinChange {

    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        // Impossible if need more than amount of coins to reach amount, as min coin has a value of 1.
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++)
            for (int coin : coins)
            {
                if (coin <= i)
                {
                    // dp[i - coin] stores number of coins used to reach (i - coin) amount of money.
                    // That amount plus coin gives i amount of money, with number of coins increased by 1.
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        return (dp[amount] > amount) ? -1 : dp[amount];
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

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            int[] coins = stringToIntegerArray(line);
            line = in.readLine();
            int amount = Integer.parseInt(line);

            int ret = coinChange(coins, amount);

            String out = String.valueOf(ret);

            System.out.println(out);
        }
    }
}
