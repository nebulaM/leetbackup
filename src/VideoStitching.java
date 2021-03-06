import com.eclipsesource.json.JsonArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 1024. Video Stitching [Greedy]
 *
 * You are given a series of video clips from a sporting event that lasted T seconds.  These video clips can be
 * overlapping with each other and have varied lengths.
 *
 * Each video clip clips[i] is an interval: it starts at time clips[i][0] and ends at time clips[i][1]. We can cut
 * these clips into segments freely: for example, a clip [0, 7] can be cut into segments [0, 1] + [1, 3] + [3, 7].
 *
 * Return the minimum number of clips needed so that we can cut the clips into segments that cover the entire sporting
 * event ([0, T]).  If the task is impossible, return -1.
 *
 * Example 1:
 * Input: clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], T = 10
 * Output: 3
 * Explanation:
 * We take the clips [0,2], [8,10], [1,9]; a total of 3 clips.
 * Then, we can reconstruct the sporting event as follows:
 * We cut [1,9] into segments [1,2] + [2,8] + [8,9].
 * Now we have segments [0,2] + [2,8] + [8,10] which cover the sporting event [0, 10].
 *
 * Example 2:
 * Input: clips = [[0,1],[1,2]], T = 5
 * Output: -1
 * Explanation:
 * We can't cover [0,5] with only [0,1] and [0,2].
 *
 * Example 3:
 * Input: clips = [[0,1],[6,8],[0,2],[5,6],[0,4],[0,3],[6,7],[1,3],[4,7],[1,4],[2,5],[2,6],[3,4],[4,5],[5,7],[6,9]], T = 9
 * Output: 3
 * Explanation:
 * We can take clips [0,4], [4,7], and [6,9].
 *
 * Example 4:
 * Input: clips = [[0,4],[2,8]], T = 5
 * Output: 2
 * Explanation:
 * Notice you can have extra video after the event ends.
 *
 * Note:
 * 1. 1 <= clips.length <= 100
 * 2. 0 <= clips[i][0], clips[i][1] <= 100
 * 3. 0 <= T <= 100
 *
 */
public class VideoStitching {

    public static int videoStitching(int[][] clips, int T) {
        Arrays.sort(clips, Comparator.comparingInt(a -> a[0]));
        int count = 0;
        int currEnd = 0;
        for (int i = 0; i < clips.length && clips[i][0] <= currEnd;)
        {
            if (currEnd >= T) return count;
            int oldEnd = currEnd;
            for (; i < clips.length && clips[i][0] <= oldEnd; i++)
            {
                //System.out.println("checked [" + clips[i][0] + "," + clips[i][1] + "] oldEnd " + oldEnd);
                currEnd = Math.max(currEnd, clips[i][1]);
            }
            count++;
            //System.out.println("i " + i + " currEnd " + currEnd);
        }
        return (currEnd >= T) ? count : -1;
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
        while ((line = in.readLine()) != null) {
            int[][] clips = stringToInt2dArray(line);
            line = in.readLine();
            int T = Integer.parseInt(line);

            int ret = videoStitching(clips, T);

            String out = String.valueOf(ret);

            System.out.println(out);
        }
    }
}
