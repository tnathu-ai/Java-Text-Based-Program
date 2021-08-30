package Covid19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TabularDisplay {
    // Display summary data in a row and column format.
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

        // the “Range” column shows “date1 – date2,”
        // where date1 and date2 are the first dates and last dates of a group
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
