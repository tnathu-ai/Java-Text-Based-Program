package Covid19;

import java.util.*;

public class ChartDisplay {
    public static void FindPosition(int[] IntArrayMetricForChart, int groups) {

        // Find max min and ratio of each pipe
        int[] IntArrayMetricForChartToSort = IntArrayMetricForChart.clone();
        Arrays.sort(IntArrayMetricForChartToSort);
        int MinMetric = IntArrayMetricForChartToSort[0];
        int MaxMetric = IntArrayMetricForChartToSort[IntArrayMetricForChartToSort.length - 1];
        int ValueOfPipeFinal = 1;
        int ValueOfPipe = (MaxMetric - MinMetric) / 24;

        if (ValueOfPipe != 0) ValueOfPipeFinal = ValueOfPipe;
        //Find the position of each data point
        //Find x value
        int[] PositionY = new int[IntArrayMetricForChart.length];
        for (int i = 0; i < PositionY.length; i++) {
            PositionY[i] = (IntArrayMetricForChart[i] - MinMetric) / ValueOfPipeFinal;
        }

        //Find y value
        int[] PositionX = new int[IntArrayMetricForChart.length];
        int TheSpace = 80 / groups;
        int count = 0;
        for (int i = 0; i < PositionX.length; i++) {
            PositionX[i] = count;
            count += TheSpace;
        }

        int[] Position = new int[PositionX.length + PositionY.length];
        int n = 0, z = 0, e = 0;
        while (n < PositionX.length && z < PositionY.length) {
            Position[e++] = PositionX[n++];
            Position[e++] = PositionY[z++];
        }

        int[][] ChartArrays = new int[Position.length / 2][2];
        int count1 = 0;
        for (int i = 0; i < Position.length; i++) {
            for (int y = 0; y < 2; y++) {
                if (count1 == Position.length)
                    break;

                ChartArrays[i][y] = Position[count1];
                count1++;
            }
        }

        // set max y (23) and max x (79)
        int x = 80;
        int y = 24;
        // loop through all rows
        for (int i = y; i >= 0; i--) {
            // print values of y axis
            System.out.print("|");
            // loop through all targets list and filter targets vc
            ArrayList<int[]> list = new ArrayList<>();
            for (int[] k : ChartArrays) {
                int subY = k[1];
                if (subY == i) list.add(k);
            }

            // loop through each values of the row
            for (int j = 0; j <= x; j++) {
                boolean hasTarget = false;
                for (int[] subList : list) {
                    if (subList[0] == j) {
                        hasTarget = true;
                        break;
                    }
                }

                if (!hasTarget) {
                    System.out.print(" " + " ");
                } else {
                    for (int[] subList : list) {
                        if (subList[0] == j) {
                            if (j > 0 || j == 0 && i < 10)
                                System.out.print("*" + " ");
                            else if (j == 0)
                                System.out.print("*" + " ");
                            break;
                        }
                    }
                }
            }
            // print the x axis
            System.out.println();
            if (i == 0) {
                System.out.print("+");
                for (int j = 0; j <= x; j++) {
                    System.out.print("_" + " ");
                }
            }
        }
    }
}