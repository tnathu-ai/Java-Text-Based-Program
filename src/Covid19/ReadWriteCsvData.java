package Covid19;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static Covid19.TimeRelatedFunctions.*;
import static Covid19.DateLocationFiltering.*;

public class ReadWriteCsvData {
    // Read the CSV file
    public static ArrayList<CovidData> readCsvRow(String pathToNewCSV, String location,
                                                  String chosenDate, String endInputDate, int dayAway, int DataChoice)
            throws IOException, ParseException {

        String endDate = null;
        // Store the read data inside bigGroup ArrayList
        ArrayList<CovidData> bigGroup = new ArrayList<CovidData>();
        // Store the Date Range according to the chosen chosen choice inside getDatesBetweenArr ArrayList
        ArrayList<Date> getDatesBetweenArr = new ArrayList<Date>();

        //Get and Print the Date Range according to the chosen chosen choice
        if (DataChoice == 1) {
            getDatesBetweenArr = getDatesBetween(chosenDate, endInputDate, DataChoice);
        } else {
            endDate = displayStartEndDate(chosenDate, dayAway, DataChoice);
            getDatesBetweenArr = getDatesBetween(chosenDate, endDate, DataChoice);
        }
        System.out.println(convertDateToString(getDatesBetweenArr));

        //Start reading 1 row of data & Process immediately
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToNewCSV));
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
            CovidData returnRow;

            if (returnRowLocation != null) {
                if (DataChoice == 1) {
                    returnRow = getDataFromDate(chosenDate, endInputDate, returnRowLocation, DataChoice);
                } else {
                    returnRow = getDataFromDate(chosenDate, endDate, returnRowLocation, DataChoice);
                }
                if (returnRow != null) {
                    bigGroup.add(returnRow);
                }
            }
        }
        return bigGroup;
    }

    // Replace the null values in the original CSV with 0 filled values CSV
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
