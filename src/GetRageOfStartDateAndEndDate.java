import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GetRageOfStartDateAndEndDate {

    public static void main(String[] args) {
        String fileName = "Data/covid-data.csv";
        File file = new File(fileName);

        // Creaete list of list of String - 2dimensional array of strings
        List<List<String>> lines = new ArrayList<>();
        Scanner covidDataStream;

        try {
            covidDataStream = new Scanner(file);

            while (covidDataStream.hasNext()) {
                String line = covidDataStream.next();
                String[] values = line.split(",");
                // Adds the currently parsed line to the 2-dimensional string array
                // Convert String[] into List
                lines.add(Arrays.asList(values));
            }

            covidDataStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Iterate through the 2-dimensional array using advanced for loop
        int rowNumber = 1;
        for (List<String> line : lines) {
            int columnNumber = 1;
            for (String value : line) {
                System.out.println("Row: " + rowNumber + " Column: " + columnNumber + ": " + value);
                columnNumber++;
            }
            rowNumber++;
        }
    }
}