import com.eclipsesource.json.JsonArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 752. Open the Lock [BFS]
 *
 * You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots:
 * '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'.
 * The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0' to be '9'.
 * Each move consists of turning one wheel one slot.
 *
 * The lock initially starts at '0000', a string representing the state of the 4 wheels.
 *
 * You are given a list of deadends dead ends, meaning if the lock displays any of these codes, the wheels of the lock
 * will stop turning and you will be unable to open it.
 *
 * Given a target representing the value of the wheels that will unlock the lock, return the minimum total number of
 * turns required to open the lock, or -1 if it is impossible.
 *
 * Example 1:
 * Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
 * Output: 6
 * Explanation:
 * A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
 * Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
 * because the wheels of the lock become stuck after the display becomes the dead end "0102".
 *
 * Example 2:
 * Input: deadends = ["8888"], target = "0009"
 * Output: 1
 * Explanation:
 * We can turn the last wheel in reverse to move from "0000" -> "0009".
 *
 * Example 3:
 * Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
 * Output: -1
 * Explanation:
 * We can't reach the target without getting stuck.
 *
 * Example 4:
 * Input: deadends = ["0000"], target = "8888"
 * Output: -1
 *
 * Note:
 * 1. The length of deadends will be in the range [1, 500].
 * 2. target will not be in the list deadends.
 * 3. Every string in deadends and the string target will be a string of 4 digits from the 10,000 possibilities
 * '0000' to '9999'.
 */
public class OpenTheLock {

    public class MyLock {
        public int[] d;
        public int num;

        MyLock (int num) {
            d = new int[4];
            d[0] = num % 10;
            d[1] = (num % 100) / 10;
            d[2] = (num % 1000) / 100;
            d[3] = (num % 10000) / 1000;
            this.num = num;
        }

        public int rotate(int pos, boolean bUp)
        {
            int digit = d[pos];
            if (bUp)
                digit = (digit == 9) ? 0 : (digit + 1);
            else
                digit = (digit == 0) ? 9 : (digit - 1);
            int numNew = 0;
            int pow = 1;
            for (int i = 0; i < d.length; i++)
            {
                if (i != pos) numNew += d[i] * pow;
                else numNew += digit * pow;
                pow *= 10;
            }
            return numNew;
        }
    }
    public int openLock(String[] deadends, String target) {
        boolean[] visited = new boolean[10000];

        for (String block : deadends)
            visited[Integer.parseInt(block)] = true;
        if (visited[0]) return -1;

        int targetNum = Integer.parseInt(target);
        Queue<MyLock> q = new LinkedList<>();
        q.offer(new MyLock(0));
        int moveCount = 0;
        while (!q.isEmpty())
        {
            int size = q.size();
            for (int i = 0; i < size; i++)
            {
                MyLock myLock = q.poll();
                if (targetNum == myLock.num)
                    return moveCount;
                List<Integer> adjLockArray = getAdjNodes(myLock, visited);
                for (int adjLock : adjLockArray)
                {
                    if (!visited[adjLock])
                    {
                        visited[adjLock] = true;
                        q.offer(new MyLock(adjLock));
                    }
                }
            }
            moveCount++;
        }

        return -1;
    }
    public final boolean[] TF = {true, false};
    public List<Integer> getAdjNodes(MyLock myLock, boolean[] visited)
    {
        List<Integer> adjArray = new ArrayList<>();
        for (int i = 0; i < myLock.d.length; i++)
            for (boolean tf : TF)
            {
                int num = myLock.rotate(i, tf);
                if (num >= 0 && num < visited.length && !visited[num])
                {
                    adjArray.add(num);
                }
            }
        return adjArray;
    }

    public static String[] stringToStringArray(String line) {
        JsonArray jsonArray = JsonArray.readFrom(line);
        String[] arr = new String[jsonArray.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = jsonArray.get(i).asString();
        }
        return arr;
    }

    public static String stringToString(String input) {
        return JsonArray.readFrom("[" + input + "]").get(0).asString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        OpenTheLock solution = new OpenTheLock();
        while ((line = in.readLine()) != null) {
            String[] deadends = stringToStringArray(line);
            line = in.readLine();
            String target = stringToString(line);

            int ret = solution.openLock(deadends, target);

            String out = String.valueOf(ret);

            System.out.println(out);
        }
    }
}
