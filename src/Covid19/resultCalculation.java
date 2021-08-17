package Covid19;

import java.util.ArrayList;

public class resultCalculation {
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

    //II.1 Calculate new metrics: TOTAL new cases/new deaths/new vaccinated people in a group.
    //for both 3 grouping options
    public static void metricDisplay(int metricOption, ArrayList<CovidData> groupsDaysArr, ArrayList<Long> metricsArr) {
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
