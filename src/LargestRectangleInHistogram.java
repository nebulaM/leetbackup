import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * 84. Largest Rectangle in Histogram [DP, stack]
 * 
 * Given n non-negative integers representing the histogram's bar height where
 * the width of each bar is 1, find the area of largest rectangle in the histogram.
 *
 * Examples:     _
 *             _| |                            _
 *            |#|#|                  _        | |
 *            |#|#|  _              | |    _  | |
 *         _  |#|#|_| |             | |_  | |_|_|
 *        | |_|#|#| | |             | | | |#|#|#|
 *        |_|_|#|#|_|_|             |_|_|_|#|#|#|
 * Input: [2,1,5,6,2,3]      Input: [4,2,0,3,2,5]
 * Output: 10                Output: 6
 *
 */
class Solution {
    public static int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        Stack<Integer> st = new Stack<Integer>();

        int idx = 0;

        while(idx < heights.length)
            // Push idx to the stack in ascending order
            if (st.isEmpty() || heights[idx] >= heights[st.peek()])
                st.push(idx++);
            else
                maxArea = _maxArea(heights, idx, st, maxArea);

        // Perform the logic again in case we still have indices left on the stack
        while (!st.isEmpty())
            maxArea = _maxArea(heights, idx, st, maxArea);

        return maxArea;
    }

    private static int _maxArea(int[] heights, int idx, Stack<Integer> st, int currMax) {
        int idxCurr = st.pop();
        /*
         * All values on the stack are smaller than heights[idxCurr],
         * idx is the index just next to the local max value from the RHS,
         * and st.peek() is the index to the next smaller value to heights[idxCurr] on the stack.
         * Note that "index to the next smaller" is not necessarily equal to (idxCurr - 1),
         * example corner case is [4,2,0,3,2,5], where indices "2, 4, 5" for values "0, 2, 5"
         * can on the stack at the same time, while index "3" for value "3" had been popped
         * out from the stack.
         *
         * So (i - 1) is the index to local max, and ((i - 1) - st.peek())
         * is the distance between "index to local min" and "index to local max" for idxCurr.
         *
         * When the stack is empty, this value is just (i - 1) - (0) + 1) = i.
         * +1 because index starts from 0, but the distance between the first column and the
         * second column is 1.
         */
        /*if(st.isEmpty())
            System.out.println("currMax " + currMax + " idxCurr " + idxCurr + " heights[idxCurr] " + heights[idxCurr] + " i " + i);
        else
            System.out.println("currMax " + currMax + " idxCurr " + idxCurr + " heights[idxCurr] " + heights[idxCurr] + " i " + i + " st.peek() " + st.peek());*/
        return Math.max(currMax, heights[idxCurr] * (st.isEmpty()? idx : idx - 1 - st.peek()));
    }
}

public class LargestRectangleInHistogram {
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
        while (true)
        {
            System.out.println("Given an input such as [1,2,3,4,5]");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line;
            if ((line = in.readLine()) != null) {
                if (line.equals("exit"))
                    return;

                int[] heights = stringToIntegerArray(line);

                int ret = new Solution().largestRectangleArea(heights);

                String out = String.valueOf(ret);

                System.out.println(out);
            }
        }
    }
}
