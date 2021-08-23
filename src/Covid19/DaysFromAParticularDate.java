package Covid19;

import java.io.*;
import java.util.*;
import java.text.ParseException;

import static Covid19.UserInput.userInputRequest;
import static Covid19.ReadWriteCsvData.readCsvRow;
import static Covid19.Metrics.*;

class DaysFromAParticularDate {
    public static void Process() throws ParseException, IOException {
        UserInput userDataInput = userInputRequest();

        String pathToNewCSV = userDataInput.pathToNewCsv;
        String location = userDataInput.location;
        String chosenDate = userDataInput.chosenDate;
        int dayAway = userDataInput.dayAway;
        int option = userDataInput.option;

        if (option == 1) {
            //read Data and get Metric
            ArrayList<CovidData> bigGroup = readCsvRow(pathToNewCSV, location, chosenDate, dayAway);
            int metricOption = metricUserInput();
            //Print Data for users
            System.out.println("\n--- Data List ---");
            for (CovidData data : bigGroup) {
                System.out.println(data.toPrintString());
            }
            //metricsArr for saving the metric result
            ArrayList<Long> metricsArr = new ArrayList<Long>();
            metricDisplay(metricOption, bigGroup, metricsArr);
            //Choose a display
            System.out.println("\nChoose one way to display");
            System.out.println("1. Tabular display ");
            System.out.println("2. Chart display ");
            int DisplayOption;

//            do {
//                Scanner input = new Scanner(System.in);
//                System.out.print("Please enter the way you want: ");
//                DisplayOption = input.nextInt();
//            } while (DisplayOption != 1 && DisplayOption != 2 );
//            if (DisplayOption == 1)
//                new TabularDisplay(chosenDate,endDate,metricsArr);

        } else if (option == 2) {
            int groups;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter the number of groups (smaller than the number of days): ");
                groups = input.nextInt();
            } while (groups > dayAway);
            //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
            ArrayList<Integer> groupsSplittedArr = DayGroupSplitting.splitGroupsEqually(dayAway, groups);
            //read Data and get Metric
            ArrayList<CovidData> bigGroup = readCsvRow(pathToNewCSV, location, chosenDate, dayAway);
            int metricOption = metricUserInput();
//            //Choose a display
            System.out.println("\nChoose one way to display");
            System.out.println("1. Tabular display ");
            System.out.println("2. Chart display ");
            int DisplayOption;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Please enter the way you want: ");
                DisplayOption = input.nextInt();
            } while (DisplayOption != 1 && DisplayOption != 2 );
            //Put data into groups and print out
            DayGroupSplitting.putDataInGroup(bigGroup, groupsSplittedArr, chosenDate, metricOption, DisplayOption);

        } else if (option == 3) {
            int daysPerGroup;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter the number of days in each group (larger than 1): ");
                daysPerGroup = input.nextInt();
            } while (daysPerGroup > dayAway && daysPerGroup > 1);
            //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
            ArrayList<Integer> groupsSplittedArr = DayGroupSplitting.splitEqualDays(dayAway, daysPerGroup);
            //read Data and get Metric
            ArrayList<CovidData> bigGroup = readCsvRow(pathToNewCSV, location, chosenDate, dayAway);
            int metricOption = metricUserInput();
            //Choose way of displaying
            System.out.println("\nChoose one way to display");
            System.out.println("1. Tabular display ");
            System.out.println("2. Chart display ");
            int DisplayOption;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Please enter the way you want: ");
                DisplayOption = input.nextInt();
            } while (DisplayOption != 1 && DisplayOption != 2 );

            //Put data into groups and print out
            DayGroupSplitting.putDataInGroup(bigGroup, groupsSplittedArr, chosenDate, metricOption, DisplayOption);

        } else {
            //Just in case sth else happens
            Process();
        }
    }
}


