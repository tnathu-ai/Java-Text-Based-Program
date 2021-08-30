package Covid19;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static Covid19.UserInitialInput.locationValidate;
import static javax.swing.UIManager.get;

public class Metrics {
    //GET CALCULATED COLUMNS INPUT
    public static int metricUserInput() {
        // Get user input for getting the calculated result
        System.out.println("\nEnter one of the number below to calculate an additional metric:");
        System.out.println("1. Total positive cases. ");
        System.out.println("2. Total deaths. ");
        System.out.println("3. Total people vaccinated. ");

        Scanner input = new Scanner(System.in);
        int metricOption;
        System.out.print("Please enter the number in those 3 options to choose: ");
        while (true) {
            try {
                do {
                    metricOption = input.nextInt();
                    return metricOption;
                } while (metricOption != 1 && metricOption != 2 && metricOption != 3);
            } catch (NumberFormatException e) {
                input.next();
                System.out.print("\nInvalid Input! Please enter again: ");
            }
        }
    }

    //METRICS TOTAL-CALCULATING METHODS
    //is new data for each date
    public static long totalNewCases(ArrayList<CovidData> groupDataArr) {
        long sum = 0;
        for (int i = 0; i < groupDataArr.size(); i++) {
            sum += groupDataArr.get(i).getNew_cases();
        }
        return sum;
    }

    //is new data for each date
    public static long totalNewDeaths(ArrayList<CovidData> groupDataArr) {
        long sum = 0;
        for (int i = 0; i < groupDataArr.size(); i++) {
            sum += groupDataArr.get(i).getNew_deaths();
        }
        return sum;
    }

    //is an accumulated values up to a date
    public static long totalNewVaccinated(ArrayList<CovidData> groupDataArr, ArrayList<String> dayRangeStr) throws IOException {
        String pathToNewCSV = "Data/covid-data-zero.csv";
        long upTo = 0;
        String location = locationValidate(pathToNewCSV);
        do {
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToNewCSV));
            String row = "";
            csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);
                if ((data[1].equalsIgnoreCase(location))) {
                    upTo = groupDataArr.get(groupDataArr.size() - 1).getPeople_vaccinated();
                } else {
                    for (int i = 0; i < groupDataArr.size(); i++) {
                        if (groupDataArr.get(i).getDate().equals(dayRangeStr.get(dayRangeStr.size() - 1)))
                            if (groupDataArr.get(i).getDate().equals(dayRangeStr.get(dayRangeStr.size() - 1))) {
                                upTo += groupDataArr.get(i).getPeople_vaccinated();
                            }
                    }
                }
            }
        } while (location == null);
        return upTo;
    }

    //DISPLAY CALCULATED RESULT
    public static void metricDisplay(int metricOption, ArrayList<CovidData> groupsDaysArr,
                                     ArrayList<Long> metricsArr, ArrayList<String> dayRangeStr) throws IOException {
        // Using Array List to print
        switch (metricOption) {
            //option 1: totalNewCases
            case 1:
                long totalNewCases = totalNewCases(groupsDaysArr);
                metricsArr.add(totalNewCases);
                for (long l : metricsArr) {
                    System.out.println("__TOTAL NEW CASES__: " + totalNewCases);
                }
                break;

            //option 2: totalNewDeaths
            case 2:
                long totalNewDeaths = totalNewDeaths(groupsDaysArr);
                metricsArr.add(totalNewDeaths);
                for (long l : metricsArr) {
                    System.out.println("__TOTAL NEW DEATHS__: " + totalNewDeaths);
                }
                break;

            //option 3: totalNewVaccinated
            case 3:
                long totalNewVaccinated = totalNewVaccinated(groupsDaysArr, dayRangeStr);
                metricsArr.add(totalNewVaccinated);
                for (long l : metricsArr) {
                    System.out.println("__TOTAL NEW VACCINATED__: " + totalNewVaccinated);
                }
                break;
        }
    }
}
