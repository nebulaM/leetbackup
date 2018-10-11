package local;

public class struct {
    public static class Interval {
        public int start;
        public int end;

        public Interval() {
            start = 0;
            end = 0;
        }

        public Interval(int s, int e) {
            assert s <= e : "Expect start <= end, but actual start is " + s + " and actual end is " + e;
            start = s;
            end = e;
        }
    }
}
