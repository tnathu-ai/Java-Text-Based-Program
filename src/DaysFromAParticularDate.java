import java.io.*;
import java.util.*;

public class DaysFromAParticularDate {
    public static void main(String args[]) {
        readCSV("Data/covid-data.csv");
    }

    public static void readCSV(String pathToCSV) {
        try {
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(pathToCSV));
            String row;

            //put header in an array
            //to skip the 1st line header for reading only data
            String header = csvReader.readLine();
            String[] headerArray = header.split(",");
            System.out.println(Arrays.toString(headerArray));

            //read line by line till the end
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);

                //test reading file
                for (String d : data) {
                    System.out.println(d + "\t");
                }
                System.out.println();
            }
            csvReader.close();
        }
        catch (Exception e) {
            //pinpoint the error in the code
            e.printStackTrace();
        }
    }
}
