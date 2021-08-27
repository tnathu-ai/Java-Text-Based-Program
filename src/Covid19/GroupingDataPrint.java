package Covid19;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import static Covid19.Metrics.metricDisplay;
import static Covid19.Metrics.metricUserInput;
import static Covid19.ReadWriteCsvData.readCsvRow;

public class GroupingDataPrint {
    UserInitialInput initialInput;

    public void setInitialInput(UserInitialInput initialInput) {
        this.initialInput = initialInput;
    }

    public void PrintOption1(int DataChoice) throws IOException, ParseException {
        //filter and get the required Data
        ArrayList<CovidData> bigGroup = readCsvRow(
                initialInput.pathToNewCsv,
                initialInput.location,
                initialInput.chosenDate,
                initialInput.dayAway,
                DataChoice);

        //get metric option
        int metricOption = metricUserInput();
        //Print Data for users
        System.out.println("\n--- Data List ---");
        for (CovidData data : bigGroup) {
            System.out.println(data.toPrintString());
        }
        //metricsArr for saving the metric result
        ArrayList<Long> metricsArr = new ArrayList<>();
        metricDisplay(metricOption, bigGroup, metricsArr);
        //Display Option and Table
        int DisplayOption = UserDisplayInput.optionDisplayInput();
        String endDate = TimeRelatedFunctions.displayStartEndDate(initialInput.chosenDate, initialInput.dayAway,
                DataChoice);
        if (DisplayOption == 1)
            new TabularDisplay(initialInput.chosenDate,endDate,metricsArr,DisplayOption);
        else {
            System.out.println("*");
            System.out.println("There is no ratio for no grouping option so there is no chart");
        }
    }

    public void PrintOption2(int DataChoice) throws IOException, ParseException {
        int groups;
        //filter and get the required Data
        ArrayList<CovidData> bigGroup = readCsvRow(
                initialInput.pathToNewCsv,
                initialInput.location,
                initialInput.chosenDate,
                initialInput.dayAway,
                DataChoice);
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the number of groups (smaller than the number of days): ");
            groups = input.nextInt();
        } while (groups > initialInput.dayAway);
        //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
        ArrayList<Integer> groupsSplittedArr = DayGroupSplitting.splitGroupsEqually(initialInput.dayAway, groups);
        //get Metric option
        int metricOption = metricUserInput();
        //Display Option and Table
        int DisplayOption = UserDisplayInput.optionDisplayInput();
        //Put data into groups and print out
        putDataInGroup(bigGroup, groupsSplittedArr, initialInput.chosenDate, metricOption, DisplayOption, DataChoice,groups);
    }

    public void PrintOption3(int DataChoice) throws IOException, ParseException {
        int daysPerGroup;
        //filter and get the required Data
        ArrayList<CovidData> bigGroup = readCsvRow(
                initialInput.pathToNewCsv,
                initialInput.location,
                initialInput.chosenDate,
                initialInput.dayAway, DataChoice);
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the number of days in each group (larger than 1): ");
            daysPerGroup = input.nextInt();
        } while (daysPerGroup > initialInput.dayAway && daysPerGroup > 1);
        //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
        ArrayList<Integer> groupsSplittedArr = DayGroupSplitting.splitEqualDays(initialInput.dayAway, daysPerGroup);
        //get Metric option
        int metricOption = metricUserInput();
        //Display Option and Table
        int DisplayOption = UserDisplayInput.optionDisplayInput();
        //Put data into groups and print out
        putDataInGroup(bigGroup, groupsSplittedArr, initialInput.chosenDate, metricOption, DisplayOption, DataChoice, groupsSplittedArr.size());
    }

    public void putDataInGroup(ArrayList<CovidData> bigGroup, ArrayList<Integer> groupsSplittedArr,
                               String originalStartDate, int metricOption, int DisplayOption, int DataChoice, int groups) throws ParseException {
        ArrayList<CovidData> groupsDaysArr = new ArrayList<>();

        int count = 1;
        int k = 0;
        String osd = originalStartDate;
        ArrayList<String> Days = new ArrayList<>();
        ArrayList<Long> AllMetric = new ArrayList<>();

        for (int outerInd = 0; outerInd < groupsSplittedArr.size(); outerInd++) {
            System.out.println("\n--- GROUP " + count + " ---");
            //plusDay : the number of DAYS that need to be added to the StartDay
            int plusDay = groupsSplittedArr.get(outerInd);
            //Get the "new endDay" (from the plusDay)
            String newEndDate = TimeRelatedFunctions.displayStartEndDate(osd, plusDay, DataChoice);
//            Days.add(osd);
            //Get the new "temporary dayRangeStr"
            ArrayList<String> dayRangeStr = TimeRelatedFunctions.convertDateToString(
                    TimeRelatedFunctions.getDatesBetween(osd, newEndDate, DataChoice));
            System.out.println(dayRangeStr);
            //put into Days array for Display Table, Chart
            String firstDay = dayRangeStr.get(0);
            String lastday = dayRangeStr.get(dayRangeStr.size() - 1);
            Days.add(firstDay);
            Days.add(lastday);
            //put "metricsArr" here to save the data for Displaying in Table, Chart
            ArrayList<Long> metricsArr = new ArrayList<>();

            for (int innerInd = 0; innerInd < bigGroup.size(); innerInd++) {
                CovidData dateElement = bigGroup.get(k);
                //Accept dataElement if it is in the Range of osd-oed
                CovidData dateData = DateLocationFiltering.getDataFromDate(osd, newEndDate, dateElement, DataChoice);
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

                }
            }
            //Display Chosen Metric Result
            metricDisplay(metricOption, groupsDaysArr, metricsArr);
            groupsDaysArr.clear();
            count += 1;
            AllMetric.addAll(metricsArr);

        }
        TabularDisplay2.PutDataIntoTable(AllMetric,Days, DisplayOption,groups);
    }
}
