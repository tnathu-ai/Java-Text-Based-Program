package Covid19;

import java.util.ArrayList;
import java.util.Scanner;

public class Metrics {
    //GET METRIC INPUT
    public static int metricUserInput() {
        System.out.println("\nEnter one of the number below to calculate an additional metric:");
        System.out.println("1. Total positive cases. ");
        System.out.println("2. Total deaths. ");
        System.out.println("3. Total people vaccinated. ");
        int metricOption;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Please enter the number in those 3 options to choose: ");
            metricOption = input.nextInt();
        } while (metricOption != 1 && metricOption != 2 && metricOption != 3);
        return metricOption;
    }

    //METRICS TOTAL-CALCULATING METHODS
    //os new data for each date
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
        if (groupDataArr.size() > 1) {
            upTo = groupDataArr.get(groupDataArr.size() - 1).getPeople_vaccinated() - groupDataArr.get(0).getPeople_vaccinated();
        } else {
            upTo += groupDataArr.get(0).getPeople_vaccinated();
        }
        return upTo;
    }

    //DISPLAY METRICS
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
