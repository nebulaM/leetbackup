import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 1046. Last Stone Weight [PQ]
 *
 * We have a collection of rocks, each rock has a positive integer weight.
 *
 * Each turn, we choose the two heaviest rocks and smash them together. Suppose the stones have weights x and y with
 * x <= y.  The result of this smash is:
 *
 * If x == y, both stones are totally destroyed;
 * If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
 * At the end, there is at most 1 stone left.  Return the weight of this stone (or 0 if there are no stones left.)
 *
 * Example 1:
 * Input: [2,7,4,1,8,1]
 * Output: 1
 * Explanation:
 * We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
 * we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
 * we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
 * we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of last stone.
 *
 * Note:
 * 1. 1 <= stones.length <= 30
 * 2. 1 <= stones[i] <= 1000
 *
 */
public class LastStoneWeight {
    private static class MyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            return b - a;
        }
    }

    public static int lastStoneWeight(int[] stones) {
        Queue<Integer> q = new PriorityQueue<>(new MyComparator());
        for (int stone : stones)
            q.offer(stone);
        while (q.size() > 1)
        {
            q.offer(q.poll() - q.poll());
        }
        return q.peek();
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
            int[] stones = stringToIntegerArray(line);

            int ret = lastStoneWeight(stones);

            String out = String.valueOf(ret);

            System.out.println(out);
        }
    }
}
