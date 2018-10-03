import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 40. Combination Sum II [recursion]
 *
 * Given a collection of candidate numbers (candidates) and a target number (target),
 * find all unique combinations in candidates where the candidate numbers sums to target.
 *
 * Each number in candidates may only be used once in the combination.
 *
 * Note:
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 *
 * Example 1:
 *
 * Input: candidates = [10,1,2,7,6,1,5], target = 8,
 * A solution set is:
 * [
 *   [1, 7],
 *   [1, 2, 5],
 *   [2, 6],
 *   [1, 1, 6]
 * ]
 *
 * Example 2:
 *
 * Input: candidates = [2,5,2,1,2], target = 5,
 * A solution set is:
 * [
 *   [1,2,2],
 *   [5]
 * ]
 *
 */
public class CombinationSum2 {

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> solns = new ArrayList<List<Integer>>();
        // Sort array at the beginning is important so that we can easily remove repeated elements without using a set.
        Arrays.sort(candidates);
        _combinationSum2(candidates, target, 0, new ArrayList<>(), solns);
        return solns;
    }

    /**
     * Recursively find all non-repeated combination of numbers in array [nums] that sum to [target].
     * @param nums   An array of numbers sorted in ascending order
     * @param target Target sum of numbers in [soln]
     * @param start  Start index in [nums] to continue search
     * @param soln   One solution contains numbers sum to target
     * @param solns  List of solutions
     * @apiNote Precondition is that input [nums] is sorted.
     */
    private static void _combinationSum2(int[] nums, int target, int start, List<Integer> soln, List<List<Integer>> solns) {
        if (target <= 0)
        {
            if (target == 0)
                solns.add(new ArrayList<>(soln));
            return;
        }

        for (int i = start; i < nums.length; ++i)
        {
            // Skip elements equal to their previous neighbour because they have been added to "soln"  by the recursive
            // call to this function when "start == their index".
            if (i > start && nums[i] == nums[i - 1])
                continue;

            soln.add(nums[i]);
            // i + 1 is the next start index so that the number at this index is not repeatedly used.
            _combinationSum2(nums, target - nums[i], i + 1, soln, solns);
            soln.remove(soln.size() - 1);
        }
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

    public static String integerArrayListToString(List<Integer> nums, int length) {
        if (length == 0) {
            return "[]";
        }

        String result = "";
        for(int index = 0; index < length; index++) {
            Integer number = nums.get(index);
            result += Integer.toString(number) + ", ";
        }
        return "[" + result.substring(0, result.length() - 2) + "]";
    }

    public static String integerArrayListToString(List<Integer> nums) {
        return integerArrayListToString(nums, nums.size());
    }

    public static String int2dListToString(List<List<Integer>> nums) {
        StringBuilder sb = new StringBuilder("[");
        for (List<Integer> list: nums) {
            sb.append(integerArrayListToString(list));
            sb.append(",");
        }

        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.println("Enter array of numbers, for example [1,2,3,4,5]");
        System.out.println("Then Enter target, for example 5");
        while ((line = in.readLine()) != null) {
            int[] candidates = stringToIntegerArray(line);
            line = in.readLine();
            int target = Integer.parseInt(line);

            List<List<Integer>> ret = combinationSum2(candidates, target);

            String out = int2dListToString(ret);

            System.out.println(out);
        }
    }
}
