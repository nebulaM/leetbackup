import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Dynamic program example to find out the order to do homework.
 */
public class KnapsackHomework {
    private static final int UNKNOWN = -1;

    private static class Homework {
        public final String m_name;
        public final int m_point;
        public final int m_time;

        public Homework(String name, int point, int time) {
            m_name = name;
            m_point = point;
            m_time = time;
        }
    }

    private static int _getMaxPoint(List<Homework> paHomework, int choose, int availableTime, int[][] pResults) {
        if (choose <= 0 || availableTime < 0)
            return 0;

        Homework pHomework = paHomework.get(choose - 1);

        if (pResults[choose][availableTime] != UNKNOWN)
            return pResults[choose][availableTime];

        int result;
        if (pHomework.m_time > availableTime)
            result = _getMaxPoint(paHomework, choose - 1, availableTime, pResults);
        else
            result = Math.max(_getMaxPoint(paHomework, choose - 1, availableTime, pResults),
                        pHomework.m_point + _getMaxPoint(paHomework, choose - 1, availableTime - pHomework.m_time, pResults));

        pResults[choose][availableTime] = result;

        return result;
    }

    public static void getMaxPointOrder(List<Homework> paHomework, int[][] pResults, int choose, int availableTime, StringBuilder pSbOrder) {
        if (choose <= 0 || availableTime < 0)
            return;

        if (pResults[choose][availableTime] == pResults[choose - 1][availableTime])
        {
            getMaxPointOrder(paHomework, pResults, choose - 1, availableTime, pSbOrder);
        }
        else
        {
            Homework pHomework = paHomework.get(choose - 1);
            pSbOrder.append(pHomework.m_name + ' ');
            getMaxPointOrder(paHomework, pResults, choose - 1, availableTime - pHomework.m_time, pSbOrder);
        }
    }

    public static int getMaxPoint(List<Homework> paHomework, int availableTime, StringBuilder pSbOrder) {
        int numChoose = paHomework.size();
        int[][] aResults = new int[numChoose + 1][availableTime + 1];
        for (int[] row : aResults) {
            Arrays.fill(row, UNKNOWN);
        }

        int maxPoint = _getMaxPoint(paHomework, numChoose, availableTime, aResults);

        getMaxPointOrder(paHomework, aResults, numChoose, availableTime, pSbOrder);

        return maxPoint;
    }

    public static void main(String[] args) {
        List<Homework> aHomework = new ArrayList<>();
        aHomework.add(new Homework("A", 7, 3));
        aHomework.add(new Homework("B", 9, 4));
        aHomework.add(new Homework("C", 5, 2));
        aHomework.add(new Homework("D", 12, 6));
        aHomework.add(new Homework("E", 14, 7));
        aHomework.add(new Homework("F", 6, 3));
        aHomework.add(new Homework("G", 12, 5));

        int availableHours = 15;

        /*Random rnd = new Random();
        rnd.setSeed(10);
        for (int i = 0; i < 50; i++)
        {
            aHomework.add(new Homework(Integer.toString(i), rnd.nextInt(30), rnd.nextInt(10)));
        }

        int availableHours = 100;*/

        StringBuilder sbOrder = new StringBuilder();

        int maxPoint = getMaxPoint(aHomework, availableHours, sbOrder);

        System.out.println("Max point is " + maxPoint);
        System.out.println("Order to finish is " + sbOrder.toString());
    }
}
