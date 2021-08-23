package Covid19;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Covid19.Metrics.metricDisplay;


class DateLocationFiltering {
    public static CovidData getDataFromDate(String chosenDate, String endDate, CovidData dataRow) throws ParseException {
        ArrayList<Date> datesInRange = new ArrayList<Date>();
        ArrayList<String> datesInRangeStr = new ArrayList<String>();
        CovidData locationData = null;

        if (dataRow == null) {
            locationData = null;

        } else if (dataRow != null) {
            datesInRange = TimeRelatedFunctions.getDatesBetween(chosenDate, endDate);
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

class TimeRelatedFunctions {
    public static String displayStartEndDate(String chosenDate, int dayAway) {
        //Specifying date format
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Calendar c = new GregorianCalendar();
        try {
            Date date = sdf.parse(chosenDate);
            //Setting the date to the given date
            c.setTime(date);
//            System.out.println(date);
        } catch (ParseException e) {
            System.out.println("invalid input");
            e.printStackTrace();
        }

        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, dayAway);
        //Day after adding the days to the input date
        String endDate = sdf.format(c.getTime());
        //Displaying the new Date after addition of Days
        System.out.println("Date after Addition: " + endDate);
        return endDate;
    }

    public static ArrayList<Date> getDatesBetween(String startDate, String endDate) throws ParseException {
        //convert String to Date
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Date sDate = sdf.parse(startDate);
        Date eDate = sdf.parse(endDate);

        ArrayList<Date> datesInRange = new ArrayList<>();
        Calendar c = new GregorianCalendar();
        c.setTime(sDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(eDate);

        while (c.before((endCalendar))) {
            Date result = c.getTime();
            datesInRange.add(result);
            c.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    public static ArrayList<String> convertDateToString(ArrayList<Date> ArrList) {
        DateFormat df = new SimpleDateFormat("M/d/yyyy");
        ArrayList<String> dateToStrArr = new ArrayList<String>();

        for (int i = 0; i < ArrList.size(); i++) {
            Date DateInArr = ArrList.get(i);
            String dateToString = df.format(DateInArr);
            dateToStrArr.add(dateToString);
        }
        return dateToStrArr;
    }
}

class DayGroupSplitting {
    public static void putDataInGroup(ArrayList<CovidData> bigGroup, ArrayList<Integer> groupsSplittedArr,
                                      String originalStartDate, int metricOption, int DisplayOption) throws ParseException {
        ArrayList<CovidData> groupsDaysArr = new ArrayList<CovidData>();

        int count = 1;
        int k = 0;
        String osd = originalStartDate;
        ArrayList<String> Days = new ArrayList<String>();
        ArrayList<Long> AllMetric = new ArrayList<Long>();

        for (int outerInd = 0; outerInd < groupsSplittedArr.size(); outerInd++) {
            System.out.println("\n--- GROUP " + count + " ---");
            //plusDay : the number of DAYS that need to be added to the StartDay
            int plusDay = groupsSplittedArr.get(outerInd);
            //Get the new endDay (from the plusDay)
            String newEndDate = TimeRelatedFunctions.displayStartEndDate(osd, plusDay);
//            Days.add(osd);
            //Get the new temporary dayRangeStr
            ArrayList<String> dayRangeStr = TimeRelatedFunctions.convertDateToString(
                    TimeRelatedFunctions.getDatesBetween(osd, newEndDate));
            System.out.println(dayRangeStr);
            //put into Days array for Display Table, Chart
            String firstDay = dayRangeStr.get(0);
            String lastday = dayRangeStr.get(dayRangeStr.size() - 1);
            Days.add(firstDay);
            Days.add(lastday);
            //put metricsArr here to save the data for Displaying in Table, Chart
            ArrayList<Long> metricsArr = new ArrayList<Long>();

            for (int innerInd = 0; innerInd < bigGroup.size(); innerInd++) {
                CovidData dateElement = bigGroup.get(k);
                //Accept dataElement if it is in the Range of osd-oed
                CovidData dateData = DateLocationFiltering.getDataFromDate(osd, newEndDate, dateElement);
                if (dateData != null) {
                    groupsDaysArr.add(dateData);
                }
                k += 1;
            }
            k = 0;
            osd = newEndDate;
            for (CovidData gda : groupsDaysArr) {
                //must not null to be able to convert to String
                if (gda != null) {
                    System.out.println(gda.toPrintString());
                } else {
                    ;
                }
            }
            //Display Chosen Metric Result
            metricDisplay(metricOption, groupsDaysArr, metricsArr);
            groupsDaysArr.clear();
            count += 1;
            AllMetric.addAll(metricsArr);

        }
        TabularDisplay2.PutDataIntoTable(AllMetric,Days, DisplayOption);
    }


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




