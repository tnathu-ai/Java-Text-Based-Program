package Covid19;

import java.text.ParseException;
import java.util.*;

import static Covid19.Metrics.metricDisplay;

class DateLocationFiltering {
    public static CovidData getDataFromDate(String chosenDate, String endDate, CovidData dataRow, int DataChoice) throws ParseException {
        ArrayList<Date> datesInRange = new ArrayList<Date>();
        ArrayList<String> datesInRangeStr = new ArrayList<String>();
        CovidData locationData = null;

        if (dataRow == null) {
            locationData = null;

        } else if (dataRow != null) {
            datesInRange = TimeRelatedFunctions.getDatesBetween(chosenDate, endDate, DataChoice);
            datesInRangeStr = TimeRelatedFunctions.convertDateToString(datesInRange);

            for (String d : datesInRangeStr) {
                String inputDate = d;

                if ((dataRow.getDate().equals(inputDate))) {
                    locationData = dataRow;
                    break;
                } else {
                    locationData = null;
                }
            }
        }
        return locationData;
    }


    public static CovidData getDataFromLocation(CovidData dataRow,
                                                String inputlocation) {
        if (dataRow.getContinent().equalsIgnoreCase(inputlocation) == false
                && dataRow.getLocation().equalsIgnoreCase(inputlocation) == false) {
            dataRow = null;
        }
        return dataRow;
    }
}

class DayGroupSplitting {
    public static ArrayList<Integer> splitGroupsEqually(int x, int n) {
        //x: dayAway
        //n: groups
        ArrayList<Integer> groupsSplittedArr = new ArrayList<Integer>();
        int g;
        // If x % n == 0 then the minimum
        // difference is 0 and all
        // numbers are x / n
        if (x % n == 0) {
            for (int i = 0; i < n; i++) {
                g = x / n;
                groupsSplittedArr.add(g);
            }
        } else {
            // upto n-(x % n) the values will be x / n
            // after that the values will be x / n + 1
            int num1 = n - (x % n);
            int num2 = x / n;
            for (int i = 0; i < n; i++) {
                if (i >= num1) {
                    g = num2 + 1;
                    groupsSplittedArr.add(g);
                } else {
                    g = num2;
                    groupsSplittedArr.add(g);
                }
            }
        }
        return groupsSplittedArr;
    }

    public static ArrayList<Integer> splitEqualDays(int x, int d) {
        //x: dayAway
        //n: groups
        //d: daysPerGroup
        ArrayList<Integer> groupsSplittedArr = new ArrayList<Integer>();
        if (d == 1) {
            for (int i = 0; i <= x; i++) {
                groupsSplittedArr.add(d);
            }
        } else {
            for (int n = 2; n < x; n++) {
                if (x % d == 0) {
                    System.out.println("You can divide into " + (x / d) + " groups");
                    for (int i = 0; i < x / d; i++) {
                        groupsSplittedArr.add(x / (x / d));
                    }
                    break;
                } else if (x % n == 0) {
                    for (int i = 0; i < n; i++) {
                        groupsSplittedArr.add(x / n);
                    }
                } else {
                    System.out.println("It is not possible to divide equally!!!");
                    break;
                }
            }
        }
        return groupsSplittedArr;
    }
}





