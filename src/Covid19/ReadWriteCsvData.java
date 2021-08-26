package Covid19;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static Covid19.TimeRelatedFunctions.*;
import static Covid19.DateLocationFiltering.*;

public class ReadWriteCsvData {
    public static ArrayList<CovidData> readCsvRow(String pathToNewCSV, String location,
                                  String chosenDate, int dayAway) throws IOException, ParseException {
        ArrayList<CovidData> bigGroup = new ArrayList<CovidData>();

        //Get and Print the Date Range
        String endDate = displayStartEndDate(chosenDate, dayAway);
        ArrayList<Date> getDatesBetweenArr = getDatesBetween(chosenDate, endDate);
        System.out.println(convertDateToString(getDatesBetweenArr));

        //Start reading 1 row of data & Process immediately
        BufferedReader csvReader =
                new BufferedReader(new FileReader(pathToNewCSV));
        String row = "";
        csvReader.readLine();

        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",", -1);
            int i = 0;

            CovidData dataRow = new CovidData(data[i],
                    data[i + 1],
                    data[i + 2],
                    data[i + 3],
                    Long.parseLong(data[i + 4]),
                    Long.parseLong(data[i + 5]),
                    Long.parseLong(data[i + 6]),
                    Long.parseLong(data[i + 7]));


            //Deal with 1 row of data
            CovidData returnRowLocation = getDataFromLocation(dataRow, location);
//                getDataFromLocation(dataRow, location);
            if (returnRowLocation != null) {
                CovidData returnRow = getDataFromDate(chosenDate, endDate, returnRowLocation);
                if (returnRow != null) {
                    bigGroup.add(returnRow);
                }
            }
        }
        return bigGroup;
    }

    public static void replaceNullCsv(String pathToCSV,
                                      String pathToNewCSV) {
        try {
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(pathToCSV));
            BufferedWriter csvWriter =
                    new BufferedWriter(new FileWriter(pathToNewCSV));
            String line = "";

            while ((line = csvReader.readLine()) != null) {
                String[] values = line.split(",", -1);
                //make an array out of string
                String writableString = "";
                ArrayList<String> al = new ArrayList<>();

                //use array to modify and edit
                for (String element : values) {
                    if (element == null || element.length() == 0) {
                        al.add("0");
                    } else {
                        al.add(element);
                    }
                }
                //add commas between elements
                for (String s : al) {
                    writableString = writableString + s + ",";
                }
                //remove last comma
                writableString = writableString.substring(0,
                        writableString.length() - 1);
                csvWriter.write(writableString + "\n");
//                System.out.println(writableString);
            }
            csvWriter.close();
            csvReader.close();
        } catch (Exception e) {
            //pinpoint the error in the code
            e.printStackTrace();
        }
    }
}
