import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ReadInclusiveStartEndDateCsv {

    /**
     * Read in a line and process it as a CSV header.
     * Where to read the header from. It needs to be closed by the caller.
     *
     * @return Array of header column names or null on error.
     * @try ParseException
     * Thrown on any parsing problems. If parseError is not null then the error will be added there and an
     * exception should not be thrown.
     * @catchIOException If there are any IO exceptions thrown when reading.
     */

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your desired location or continent: ");
//        String name = scanner.nextLine();
//        System.out.println("Enter your desired start date: ");
//        String date = scanner.next();
//        System.out.println("Enter your desired end date: ");
//        Date date = scanner.next();
        String file = "Data/covid-data.csv";
        String headers;
        String line;
//        ArrayList<String> covidData = null;
        try (BufferedReader br =
                     new BufferedReader(new FileReader(file))) {
            headers = br.readLine();
            while ((line = br.readLine()) != null) {
                // use split(“,”) method to split row and separate each field.
                String[] data = line.split(",");
//                if (continent.contains(data[1])){
//                    System.out.println(continent + " exists");
//                    return;
//                }
//                else{
//                    System.out.println(keyword + " is not in our database");
//                }
//                System.out.println(line);
                // return an array which will be column data
                System.out.println("Location: " + data[0] + " Date: " + data[3]);
            }
//            covidData = (ArrayList<String>) Files.readAllLines(Paths.get("Data/covid-data.csv"));
//            for (String data : covidData) {
//                System.out.println(data + " - ");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//String getHeaderField(int columnNum) throws IOException {
//        return inputCSV.getHeader(columnNum);
//}

// parse a specific column by its header in Java



    // Converts CSV to ArrayList using Split
//    static ArrayList<String> ConvertCsvToArrayList(String covidData) {
//        ArrayList<String> CovidDataResult = new ArrayList<String>();
//
//        if (covidData != null) {
//            String[] splitData = covidData.split("\\s*,\\s*");
//            for (int i = 0; i < splitData.length; i++) {
//                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
//                    CovidDataResult.add(splitData[i].trim());
//                }
//            }
//        }
//
//        return CovidDataResult;
//    }

//    private static List<CovidData> readCovidData(String fileName) {
//        List<CovidData> data = new ArrayList<>();
//        Path pathToFile = Paths.get(fileName);
//
//        // instance of BufferedReader
//        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
//            // read 1st line in the csv
//            String line = br.readLine();
//            // loop until all lines are read
//            while (line != null) {
//                // use string.split to load a string array with the values from
//                // the file, using a comma as the delimiter
//                String[] attributes = line.split(",");
//                // read next line before looping
//                // if reach the end of the csv, line = null
//                line = br.readLine();
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return data;
//    }


}


