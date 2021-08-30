package Covid19;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import static Covid19.DayGroupSplitting.splitEqualDays;
import static Covid19.Metrics.metricDisplay;
import static Covid19.Metrics.metricUserInput;
import static Covid19.ReadWriteCsvData.readCsvRow;
import static Covid19.TimeRelatedFunctions.calculateDayAway;

public class GroupingDataPrint {
    UserInitialInput initialInput;

    // setter for initial inputs
    public void setInitialInput(UserInitialInput initialInput) {
        this.initialInput = initialInput;
    }

    // Print first data grouping option
    // A pair of start date and end dates (inclusive)
    public void PrintOption1(int DataChoice) throws IOException, ParseException {
        //filter and get the required Data
        ArrayList<CovidData> bigGroup = readCsvRow(
                initialInput.pathToNewCsv,
                initialInput.location,
                initialInput.chosenDate,
                initialInput.endInputDate,
                initialInput.dayAway,
                DataChoice);

        //get metric option
        int metricOption = metricUserInput();
        //Print Data for users
        System.out.println("\n --- Data List ---");
        for (CovidData data : bigGroup) {
            System.out.println(data.toPrintString());
        }
        //metricsArr for saving the metric result
        ArrayList<Long> metricsArr = new ArrayList<>();
        metricDisplay(metricOption, bigGroup, metricsArr);
        //Display Option and Table
        int DisplayOption = GeneralVisualDisplayOption.GeneralVisualDisplayOption();
        String endDate = TimeRelatedFunctions.displayStartEndDate(initialInput.chosenDate, initialInput.dayAway,
                DataChoice);
        if (DisplayOption == 1)
            new TabularDisplay().TabularPrint(initialInput.chosenDate, endDate, metricsArr, DisplayOption);
        else {
            System.out.println("*");
            System.out.println("\nThere is no ratio for no grouping option so there is no chart");
        }

    }

    // Print second data grouping option
    // A number of days or weeks from a particular date
    public void PrintOption2(int DataChoice) throws IOException, ParseException {
        int groups;
        //filter and get the required Data
        ArrayList<CovidData> bigGroup = readCsvRow(
                initialInput.pathToNewCsv,
                initialInput.location,
                initialInput.chosenDate,
                initialInput.endInputDate,
                initialInput.dayAway,
                DataChoice);
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("\nEnter the number of Groups (smaller than the number of days): ");
            groups = input.nextInt();
        } while (groups > initialInput.dayAway);
        ArrayList<Integer> groupsSplittedArr = new ArrayList<>();

        //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
        if (DataChoice == 1) {
            int dayAway = calculateDayAway(initialInput.chosenDate, initialInput.endInputDate);
            groupsSplittedArr = DayGroupSplitting.splitGroupsEqually(dayAway, groups);
        } else {
            groupsSplittedArr = DayGroupSplitting.splitGroupsEqually(initialInput.dayAway, groups);
        }

        //get Metric option
        int metricOption = metricUserInput();
        //Display Option and Table
        int DisplayOption = GeneralVisualDisplayOption.GeneralVisualDisplayOption();
        //Put data into groups and print out
        putDataInGroup(bigGroup, groupsSplittedArr, initialInput.chosenDate, metricOption, DisplayOption, DataChoice, groups);
    }

    // Print third grouping data option
    // A number of days or weeks to a particular date
    public void PrintOption3(int DataChoice) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        int daysPerGroup;
        //filter and get the required Data
        ArrayList<CovidData> bigGroup = readCsvRow(
                initialInput.pathToNewCsv,
                initialInput.location,
                initialInput.chosenDate,
                initialInput.endInputDate,
                initialInput.dayAway, DataChoice);
        do {
            System.out.print("\nEnter the number of Days per Group (LARGER than 1): ");
            daysPerGroup = sc.nextInt();
        } while (daysPerGroup > initialInput.dayAway && daysPerGroup > 1);
        //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
        ArrayList<Integer> groupsSplittedArr;
        if (DataChoice == 1) {
            int dayAway = calculateDayAway(initialInput.chosenDate, initialInput.endInputDate);
            groupsSplittedArr = splitEqualDays(dayAway, daysPerGroup);
            if (groupsSplittedArr == null) {
                do {
                    System.out.println("Please choose another Start Date / End Date to divide equally!!!");
                    //new Start Date
                    initialInput.chosenDate = UserInitialInput.chosenDateValidate(initialInput.pathToNewCsv);
                    System.out.println();
                    //new End Date
                    initialInput.endInputDate = UserInitialInput.EndInputDateValidate(initialInput.pathToNewCsv);
                    dayAway = calculateDayAway(initialInput.chosenDate, initialInput.endInputDate);
                    do {
                        System.out.print("Enter the number of Days per Group (LARGER than 1 & SMALLER or EQUAL to the DAY AWAY): ");
                        daysPerGroup = sc.nextInt();
                    } while (daysPerGroup > initialInput.dayAway && daysPerGroup > 1);
                    groupsSplittedArr = splitEqualDays(dayAway, daysPerGroup);
                } while (groupsSplittedArr == null);
            }
        } else {
            groupsSplittedArr = splitEqualDays(initialInput.dayAway, daysPerGroup);
            if (groupsSplittedArr == null) {
                do {
                    System.out.println("\nPlease choose another number of DAYS AWAY to divide EQUALLY! ");
                    System.out.print("1.Enter the number of Days that are Away from the date you chose: ");
                    initialInput.dayAway = sc.nextInt();
                    do {
                        System.out.print("2.Enter the number of DAYS per GROUP (LARGER than 1 & SMALLER or EQUAL to the DAY AWAY): ");
                        daysPerGroup = sc.nextInt();
                    } while (daysPerGroup > initialInput.dayAway && daysPerGroup > 1);
                    groupsSplittedArr = splitEqualDays(initialInput.dayAway, daysPerGroup);
                } while (groupsSplittedArr == null);
            }
        }
        //get Metric option
        int metricOption = metricUserInput();
        //Display Option and Table
        int DisplayOption = GeneralVisualDisplayOption.GeneralVisualDisplayOption();
        //Put data into groups and print out
        putDataInGroup(bigGroup, groupsSplittedArr, initialInput.chosenDate, metricOption, DisplayOption, DataChoice,
                groupsSplittedArr.size());
    }

    // function for grouping data base on user choice for all the data extracting processes
    public void putDataInGroup(ArrayList<CovidData> bigGroup, ArrayList<Integer> groupsSplittedArr,
                               String originalStartDate, int metricOption, int DisplayOption, int DataChoice, int groups) throws ParseException {
        ArrayList<CovidData> groupsDaysArr = new ArrayList<>();
        int count = 1;
        int k = 0;
        String osd = originalStartDate;
        ArrayList<String> Days = new ArrayList<>();
        ArrayList<Long> AllMetric = new ArrayList<>();

        //outerInd: group{3,5,7} -> group.size = 3
        for (int outerInd = 0; outerInd < groupsSplittedArr.size(); outerInd++) {
            System.out.println("\n--- GROUP " + count + " ---");
            //plusDay : the number of DAYS that need to be added to the StartDay
            int plusDay = groupsSplittedArr.get(outerInd);
            //Get the "new endDay" from the plusDay (th end date of the divided group)
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

            //innerInd: group{3,5,7} -> bigGroup.size = 3; bigGroup.size = 5, bigGroup.size = 7
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
                    ;
                }
            }
            //Display Chosen Metric Result
            metricDisplay(metricOption, groupsDaysArr, metricsArr);
            groupsDaysArr.clear();
            count += 1;
            AllMetric.addAll(metricsArr);
        }
        TabularDisplay.PutDataIntoTable(AllMetric, Days, DisplayOption, groups);
    }
}
