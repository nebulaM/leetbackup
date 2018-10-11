
import local.struct.Interval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 56. Merge Intervals [sorting, linked list]
 *
 * Given a collection of intervals, merge all overlapping intervals.
 *
 * Example 1:
 *
 * Input: [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
 *
 * Example 2:
 *
 * Input: [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 *
 */
public class MergeIntervals {

    public static List<Interval> merge(List<Interval> intervals) {
        // Sort intervals in ascending order with respect to "start" so that we only have to handle "end" later
        Collections.sort(intervals, new _IntervalComparator());

        LinkedList<Interval> newIntervals = new LinkedList<>();
        for (Interval interval : intervals)
        {
            if (newIntervals.isEmpty() || newIntervals.getLast().end < interval.start)
                newIntervals.add(interval);
            else
                newIntervals.getLast().end = Math.max(newIntervals.getLast().end, interval.end);
        }

        return newIntervals;
    }

    private static class _IntervalComparator implements Comparator<Interval> {
        @Override
        public int compare(Interval a, Interval b) {
            return a.start < b.start ? -1 : a.start == b.start ? 0 : 1;
        }
    }

    private static Pattern _inputFormat = Pattern.compile("^(\\[?)((\\[[0-9]+,[0-9]+],)+)?(\\[[0-9]+,[0-9]+])(]?)$");
    public static List<Interval> stringToIntervals(String str) {
        assert _inputFormat.matcher(str).find() : "Invalid input format: " + str;

        List<Interval> intervals = new LinkedList<>();
        String[] strIntervals =  str.split("],\\[");

        for (String strInterval : strIntervals)
        {
            String[] bounds = strInterval.split(",");
            assert bounds.length == 2 : "Actual bounds length is " + bounds.length + " str being split is " + strInterval;
            if (bounds[0].startsWith("["))
                bounds[0] = bounds[0].replaceAll("\\[","");
            if (bounds[1].endsWith("]"))
                bounds[1] = bounds[1].replaceAll("]","");
            intervals.add(new Interval(Integer.parseInt(bounds[0]), Integer.parseInt(bounds[1])));
        }

        return intervals;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.println("Enter intervals to be merge, for example [[1,3],[2,6],[8,10],[15,18]]");
        while ((line = in.readLine()) != null) {
            List<Interval> intervals = stringToIntervals(line);

            List<Interval> newInterval = merge(intervals);

            boolean first = true;
            System.out.print("Merged intervals: [");
            for (Interval interval : newInterval)
            {
                if (!first)
                    System.out.print(",");
                System.out.print("[" + interval.start + "," + interval.end + "]");

                first = false;
            }
            System.out.print("]\n");
        }
    }
}

