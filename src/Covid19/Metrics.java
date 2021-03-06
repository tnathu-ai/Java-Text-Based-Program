package Covid19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
    public static long totalNewVaccinated(ArrayList<CovidData> groupDataArr) {
        long upTo = 0;
        //HashMap "countries": have the latest "people_vaccinated" value
        //1. of every countries in a continent (in case user insert continent as location)
        //2. of a country (user insert country as location)
        HashMap<String, Long> countries = new HashMap<String, Long>();
        for (int i = 0; i < groupDataArr.size(); i++) {
            String location = groupDataArr.get(i).getLocation();
            Long vaccinated = groupDataArr.get(i).getPeople_vaccinated();
            //the location with the latest day will overwrite this location in previous day
            //the latest day won't be able to overwrite if the people_vaccinated = 0
            if (vaccinated != 0) {
                countries.put(location, vaccinated);
            }
        }
        for (String j : countries.keySet()) {
            upTo += countries.get(j);
        }
        return upTo;
    }

    //DISPLAY CALCULATED RESULT
    public static void metricDisplay(int metricOption, ArrayList<CovidData> groupsDaysArr,
                                     ArrayList<Long> metricsArr) {
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
                long totalNewVaccinated = totalNewVaccinated(groupsDaysArr);
                metricsArr.add(totalNewVaccinated);
                for (long l : metricsArr) {
                    System.out.println("__TOTAL NEW VACCINATED__: " + totalNewVaccinated);
                }
                break;
        }
    }
}
