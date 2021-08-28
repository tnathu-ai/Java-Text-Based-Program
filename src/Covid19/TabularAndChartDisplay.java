package Covid19;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class ChartDisplayOption {
    public static int chartDisplayOption() {
        //Choose way of displaying
        System.out.println("\nChoose one way to display");
        System.out.println("1. Tabular display. ");
        System.out.println("2. Chart display. ");

        Scanner input = new Scanner(System.in);
        int DisplayOption;
        do {
            System.out.print("\nPlease enter only the number in those 3 options to choose: ");
            while (true) {
                try {
                    DisplayOption = input.nextInt();
                    return DisplayOption;
                } catch (Exception e) {
                    input.next();
                }
            }
        } while (DisplayOption != 1 && DisplayOption != 2);
    }
}


class TabularDisplay {
    public static void TabularPrint(String chosenDate, String endDate, ArrayList<Long> metricsArr,
                                    int DisplayOption) {
        boolean leftJustifiedRows = false;
        String listString = "";
        String MetricForChart = "";
        for (Long s : metricsArr) {
            listString += s + " ";
        }
        String[] Metric = listString.split(" ");
        for (Long s : metricsArr) {
            MetricForChart += s + "";
        }

        String[] StringArrayMetricForChart = MetricForChart.split("");
        int size = StringArrayMetricForChart.length;
        int[] IntArrayMetricForChart = new int[size];
        for (int i = 0; i < size; i++) {
            IntArrayMetricForChart[i] = Integer.parseInt(StringArrayMetricForChart[i]);
        }

        String StringDay[] = {chosenDate, endDate};

        String[] DataStringDay = new String[StringDay.length];
        for (int DataCount = 0; DataCount < StringDay.length; DataCount = DataCount + 2) {
            DataStringDay[DataCount] = StringDay[DataCount] + "-" + StringDay[DataCount + 1];
        }
        for (int DataCount = 0; DataCount < Metric.length; DataCount = DataCount + 2) {
            DataStringDay[DataCount + 1] = Metric[DataCount];
        }
        String[][] arrays = new String[((DataStringDay.length) / 2 + 1)][2];

        int count = 0;
        for (int i = 1; i < DataStringDay.length; i++) {
            for (int j = 0; j < 2; j++) {
                if (count == DataStringDay.length) break;

                arrays[i][j] = DataStringDay[count];
                count++;

            }
            arrays[0][0] = "Days";
            arrays[0][1] = "Value";

        }
        Map<Integer, Integer> columnLengths = new HashMap<>();
        Arrays.stream(arrays).forEach(a -> Stream.iterate(0, (i -> i < arrays.length), (i -> ++i)).forEach(i -> {
            if (columnLengths.get(i) == null) {
                columnLengths.put(i, 0);
            }
            if (columnLengths.get(i) < a[i].length()) {
                columnLengths.put(i, a[i].length());
            }
        }));

        final StringBuilder formatString = new StringBuilder("");
        String flag = leftJustifiedRows ? "-" : "";
        columnLengths.entrySet().stream().forEach(e -> formatString.append("| %" + flag + e.getValue() + "s "));
        formatString.append("|\n");

        if (DisplayOption == 1) {
            Stream.iterate(0, (i -> i < arrays.length), (i -> ++i))
                    .forEach(a -> System.out.printf(formatString.toString(), arrays[a]));
        }
    }

    public static void PutDataIntoTable(ArrayList<Long> metricsArr, ArrayList<String> Days, int DisplayOption,
                                        int groups) {
        System.out.println();
        boolean leftJustifiedRows = false;
        //convert Array list Metric into String
        String listString = "";
        String MetricForChart = "";
        for (Long s : metricsArr) {
            listString += s + " " + "-" + " ";
        }
        //put Metric into String
        String[] Metric = listString.split(" ");
        for (Long s : metricsArr) {
            MetricForChart += s + " ";
        }
        String[] StringArrayMetricForChart = MetricForChart.split(" ");
        int size = StringArrayMetricForChart.length;
        int[] IntArrayMetricForChart = new int[size];
        for (int i = 0; i < size; i++) {
            IntArrayMetricForChart[i] = Integer.parseInt(StringArrayMetricForChart[i]);
        }
        String[] StringDay = new String[Days.size()];

        //Convert Days from Arraylist into array StringDay
        StringDay = Days.toArray(StringDay);
        String[] DataStringDay = new String[StringDay.length];
        for (int DataCount = 0; DataCount < StringDay.length; DataCount = DataCount + 2) {
            DataStringDay[DataCount] = StringDay[DataCount] + "-" + StringDay[DataCount + 1];
        }
        for (int DataCount = 0; DataCount < Metric.length; DataCount = DataCount + 2) {
            DataStringDay[DataCount + 1] = Metric[DataCount];
        }
        String[][] arrays = new String[((DataStringDay.length) / 2) + 1][2];
        arrays[0][0] = "Days";
        arrays[0][1] = "Value";
        int count = 0;
        for (int i = 1; i < DataStringDay.length; i++) {
            for (int j = 0; j < 2; j++) {
                if (count == DataStringDay.length) break;

                arrays[i][j] = DataStringDay[count];
                count++;
            }

        }
        Map<Integer, Integer> columnLengths = new HashMap<>();
        Arrays.stream(arrays).forEach(a -> Stream.iterate(0, (i -> i < a.length), (i -> ++i)).forEach(i -> {
            if (columnLengths.get(i) == null) {
                columnLengths.put(i, 0);
            }
            if (columnLengths.get(i) < a[i].length()) {
                columnLengths.put(i, a[i].length());
            }
        }));

        final StringBuilder formatString = new StringBuilder("");
        String flag = leftJustifiedRows ? "-" : "";
        columnLengths.entrySet().stream().forEach(e -> formatString.append("| %" + flag + e.getValue() + "s "));
        formatString.append("|\n");

        if (DisplayOption == 1) {
            Stream.iterate(0, (i -> i < arrays.length), (i -> ++i))
                    .forEach(a -> System.out.printf(formatString.toString(), arrays[a]));
        } else {
            ChartDisplay.FindPosition(IntArrayMetricForChart, groups);
        }
    }
}


class ChartDisplay {
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

