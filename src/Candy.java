import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 135. Candy [DP]
 *
 * There are N children standing in a line. Each child is assigned a rating value.
 *
 * You are giving candies to these children subjected to the following requirements:
 *
 *   1. Each child must have at least one candy.
 *   2. Children with a higher rating get more candies than their neighbors.
 *
 * What is the minimum candies you must give?
 *
 * Example 1:
 *
 * Input: [1,0,2]
 * Output: 5
 * Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
 * Example 2:
 *
 * Input: [1,2,2]
 * Output: 4
 * Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
 *              The third child gets 1 candy because it satisfies the above two conditions.
 *
 */
public class Candy {

    public static int candy(int[] ratings) {
        final int len = ratings.length;
        int[] candies = new int[len];
        Arrays.fill(candies, 1);

        // Give 1 more candy to this child if the rating of this child is higher than the previous child.
        for (int i = 1; i < len; ++i)
            if (ratings[i] > ratings[i - 1])
                candies[i] = candies[i - 1] + 1;

        // Note that we init sum with last child's candy.
        int sum = candies[len - 1];
        // Give 1 more candy to previous child if the rating of previous child is higher than this child.
        for (int i = len - 1; i > 0; --i)
        {
            // Need to see if candies[i - 1] already > candies[i] + 1
            // Example ratings = [1,2,3,0,1]
            //         candies = [1,2,3,1,2] <- without Math.max(), this becomes [1,2,*2*,1,2]
            if (ratings[i - 1] > ratings[i])
                candies[i - 1] = Math.max(candies[i - 1], candies[i] + 1);
            sum += candies[i - 1];
        }

        /*for (int candy : candies)
            System.out.print(candy + ",");
        System.out.println();*/

        return sum;
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
            int[] ratings = stringToIntegerArray(line);

            int ret = candy(ratings);

            String out = String.valueOf(ret);

            System.out.println(out);
        }
    }
}
