package Covid19;

import javax.swing.*;
import java.util.*;
class TabularDisplay {
    JFrame f;
    JTable j;
    TabularDisplay(String chosenDate, String endDate, ArrayList<Long> metricsArr) {
        f = new JFrame();
        // Frame Title
        f.setTitle("Tabular Display");
        String[] columnNames = {"Range", "Value"};
            String listString = "";
            for (Long s : metricsArr) {
                listString += s + "\t";
            }
            String[] ob1 = {chosenDate + "-" + endDate, listString};
            String[][] data1 = {
                    ob1
            };
            j = new JTable(data1, columnNames);

        j.setBounds(30, 40, 200, 300);


        JScrollPane sp = new JScrollPane(j);
        f.add(sp);

        f.setSize(500, 200);
        f.setVisible(true);
    }
}
class TabularDisplay2{
    JFrame f;
    JTable j;
    public static void PutDataIntoTable(ArrayList<Long> metricsArr,ArrayList<String> Days, int DisplayOption) {
        String listString = "";
        for (Long s : metricsArr) {
            listString += s + " " + "-" + " ";
        }
        String[] Metric = listString.split(" ");

        String[] StringDay = new String[Days.size()];
        StringDay = Days.toArray(StringDay);
        String[] DataStringDay = new String[StringDay.length];
        for (int DataCount = 0; DataCount < StringDay.length; DataCount = DataCount + 2) {
            DataStringDay[DataCount] = StringDay[DataCount] + "-" + StringDay[DataCount + 1];
        }
        for (int DataCount = 0; DataCount < Metric.length; DataCount = DataCount + 2) {
            DataStringDay[DataCount + 1] = Metric[DataCount];
        }
        String[][] arrays = new String[(DataStringDay.length) / 2][2];
        int count = 0;
        for (int i = 0; i < DataStringDay.length; i++) {
            for (int j = 0; j < 2; j++) {
                if (count == DataStringDay.length) break;

                arrays[i][j] = DataStringDay[count];
                count++;

            }

        }
        if(DisplayOption==1 )
            new TabularDisplay2(arrays);
    }

    TabularDisplay2(String[][] arrays) {
        f = new JFrame();
        f.setTitle("Tabular Display");
        String[] columnNames = {"Range", "Value"};

        j = new JTable(arrays, columnNames);

        j.setBounds(30, 40, 200, 300);


        JScrollPane sp = new JScrollPane(j);
        f.add(sp);

        f.setSize(500, 200);
        f.setVisible(true);

    }

}

//class TabularDisplay3(){
//
//}
