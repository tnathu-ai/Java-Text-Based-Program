package Covid19;

import java.util.ArrayList;
import java.util.HashSet;

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
        long temp_sum;
        long sum = 0;
        if (groupDataArr.size() > 1) {
            for (int i = 0; i < groupDataArr.size() - 1; i++) {
                long vaccinated1 = groupDataArr.get(i).getPeople_vaccinated();
                long vaccinated2 = groupDataArr.get(i + 1).getPeople_vaccinated();
                temp_sum = vaccinated2 - vaccinated1;
                sum += temp_sum;
            }
        } else {
            sum += groupDataArr.get(0).getPeople_vaccinated();
        }
        return sum;
    }

    public static long upToNewCases(ArrayList<CovidData> groupDataArr) {
        long upTo = 0;
        // get the last element minus the first element of the array list to get the upto value
        upTo = groupDataArr.get(groupDataArr.size() - 1).getNew_cases() - groupDataArr.get(0).getNew_cases();
        return upTo;
    }

    public static long upToNewDeaths(ArrayList<CovidData> groupDataArr) {
        long upTo = 0;
        // get the last element minus the first element of the array list to get the upto value
        upTo = groupDataArr.get(groupDataArr.size() - 1).getNew_deaths() - groupDataArr.get(0).getNew_deaths();
        return upTo;
    }

    public static long upToNewVaccinated(ArrayList<CovidData> groupDataArr) {
        long upTo = 0;
        // get the last element minus the first element of the array list to get the upto value
        upTo = groupDataArr.get(groupDataArr.size() - 1).getPeople_vaccinated() - groupDataArr.get(0).getPeople_vaccinated();
        return upTo;
    }


    //II.1 Calculate new metrics: TOTAL new cases/new deaths/new vaccinated people in a group.
    //for both 3 grouping options
    public static void metricDisplay(int metricOption, ArrayList<CovidData> groupsDaysArr,
                                     HashSet<Long> metricsArr) {
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
