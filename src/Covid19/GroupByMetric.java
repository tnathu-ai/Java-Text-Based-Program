package Covid19;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import static Covid19.DaysFromAParticularDate.replaceNullCsv;

public class GroupByMetric {

    public static void main(String[] args) throws IOException, ParseException {
        String pathToCSV = "Data/covid-data.csv";
        String pathToNewCSV = "Data/covid-data-zero.csv";
        replaceNullCsv(pathToCSV, pathToNewCSV);
        Scanner input = new Scanner(System.in);
        System.out.println("There are 3 ways you can choose to group your data by metrics: ");
        System.out.println("1. Positive cases: ");
        System.out.println("2. Deaths: ");
        System.out.println("3. People vaccinated: ");
        int option;
        do {
            System.out.print("Please enter the number in those 3 options to choose: ");
            option = input.nextInt();
        } while (option != 1 && option != 2 && option != 3);

        groupDataArr(pathToNewCSV, UI.extractedData(), option);
    }

    //Grouping condition
    public static void groupDataArr(String pathToNewCSV, List<CovidData> extractedData, int option)
            throws IOException, ParseException {
        if (option == 1) {
            // Declaring a variable to lookup for
            // number of lines in the CSV file
            String headers;
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(pathToNewCSV))) {
                headers = br.readLine();
                while ((line = br.readLine()) != null) {
                    // use split(“,”) method to split row and separate each field.
                    String[] data = line.split(",");
                    CovidData dataRow = new CovidData(data[0], data[1], data[2], data[3],
                            Long.parseLong(data[4]), Long.parseLong(data[5]), Long.parseLong(data[6]), Long.parseLong(data[7]));

                    //Deal with 1 row of data
//                getDataFromLocation(dataRow, location);
//                    CovidData returnRow = (CovidData) UI.extractedData();
//                    System.out.println(returnRow.getNew_cases.toPrintString());
                    // return an array which will be column data
                    for (int i = 0; i < extractedData.size(); i++) {
                        System.out.println(extractedData.get(i).getNew_cases());
                    }
//            System.out.println("Positive Cases: " + data[4]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (option == 2) {
            // Declaring a variable to lookup for
            // number of lines in the CSV file
            String headers;
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(pathToNewCSV))) {
                headers = br.readLine();
                while ((line = br.readLine()) != null) {
                    // use split(“,”) method to split row and separate each field.
                    String[] data = line.split(",");
                    CovidData dataRow = new CovidData(data[0], data[1], data[2], data[3],
                            Long.parseLong(data[4]), Long.parseLong(data[5]), Long.parseLong(data[6]), Long.parseLong(data[7]));

                    //Deal with 1 row of data
//                getDataFromLocation(dataRow, location);
//                    CovidData returnRow = (CovidData) UI.extractedData();
//                    System.out.println(returnRow.getNew_cases.toPrintString());
                    // return an array which will be column data
                    for (int i = 0; i < extractedData.size(); i++) {
                        System.out.println(extractedData.get(i).getNew_deaths());
                    }
//            System.out.println("Positive Cases: " + data[4]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (option == 3) {
            // Declaring a variable to lookup for
            // number of lines in the CSV file
            String headers;
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(pathToNewCSV))) {
                headers = br.readLine();
                while ((line = br.readLine()) != null) {
                    // use split(“,”) method to split row and separate each field.
                    String[] data = line.split(",");
                    CovidData dataRow = new CovidData(data[0], data[1], data[2], data[3],
                            Long.parseLong(data[4]), Long.parseLong(data[5]), Long.parseLong(data[6]), Long.parseLong(data[7]));

                    //Deal with 1 row of data
//                getDataFromLocation(dataRow, location);
//                    CovidData returnRow = (CovidData) UI.extractedData();
//                    System.out.println(returnRow.getNew_cases.toPrintString());
                    // return an array which will be column data
                    for (int i = 0; i < extractedData.size(); i++) {
                        System.out.println(extractedData.get(i).getPeople_vaccinated());
                    }
//            System.out.println("Positive Cases: " + data[4]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
}