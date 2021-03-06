package Covid19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static Covid19.DayGroupSplitting.*;
import static Covid19.ReadWriteCsvData.replaceNullCsv;

// User input Object
public class UserInitialInput {
    String pathToNewCsv;
    String location;
    String chosenDate;
    int dayAway;
    String endInputDate;
    int groupingOption;

    public UserInitialInput(String pathToNewCsv, String location, String chosenDate, String endInputDate,
                            int dayAway, int groupingOption) {
        this.pathToNewCsv = pathToNewCsv;
        this.location = location;
        this.chosenDate = chosenDate;
        this.endInputDate = endInputDate;
        this.dayAway = dayAway;
        this.groupingOption = groupingOption;
    }

    public static UserInitialInput userInputRequest(int DataChoice) throws IOException, ParseException {
        String pathToCSV = "Data/covid-data.csv";
        String pathToNewCSV = "Data/covid-data-zero.csv";
        replaceNullCsv(pathToCSV, pathToNewCSV);

        // Validate user input LOCATION, CHOSENDATE
        String location = locationValidate(pathToNewCSV);
        String chosenDate = chosenDateValidate(pathToNewCSV);

        String endInputDate = null;
        int dayAway = 0;
        if (DataChoice == 1) {
            endInputDate = EndInputDateValidate(pathToNewCSV);
        } else {
            dayAway = dayAwayValidate(chosenDate, DataChoice);
        }
        // choose type of grouping methods:
        int groupingOption = groupOptionValidate();
        UserInitialInput userDataInput;
        if (DataChoice == 1) {
            dayAway = calculateDayAway(chosenDate, endInputDate);
            userDataInput = new UserInitialInput(pathToNewCSV, location, chosenDate, endInputDate, dayAway,
                    groupingOption);
            return userDataInput;
        } else {
            userDataInput = new UserInitialInput(pathToNewCSV, location, chosenDate, endInputDate, dayAway,
                    groupingOption);
            return userDataInput;
        }
    }

    // Validate day away from user input
    public static int dayAwayValidate(String chosenDate, int DataChoice) {
        Scanner input = new Scanner(System.in);
        int dayAway;
        String smallestDate = "1/1/2020";
        String largestDate = "7/8/2021";
        //currentDayAway = largestDate - chosenDate

        while (true) {
            try {
                if (DataChoice == 2) {
                    int currentDayAway = TimeRelatedFunctions.calculateDayAwayDiffMsg(chosenDate, largestDate,
                            DataChoice);
                    do {
                        System.out.print("\nEnter the number of DAYS AWAY from the date you chose: ");
                        dayAway = input.nextInt();
                    } while (dayAway > currentDayAway);
                    return dayAway;
                } else if (DataChoice == 3) {
                    int currentDayAway = TimeRelatedFunctions.calculateDayAwayDiffMsg(smallestDate, chosenDate, DataChoice);
                    do {
                        System.out.print("\nEnter the number of DAYS AWAY to the date you chose: ");
                        dayAway = input.nextInt();
                    } while (dayAway > currentDayAway);
                    return dayAway;
                }
            } catch (Exception e) {
                input.next();
                System.out.print("\nInvalid Input! Please enter again!\n ");
            }
        }
    }

    // Validate grouping options from user input
    public static int groupOptionValidate() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nThere are 3 ways you can choose to group your days: ");
        System.out.println("1. No grouping: each day is a separate group.");
        System.out.println("2. Number of groups: the number of days will be divided equally into each group." +
                "Your data will be divided as equally as possible. ");
        System.out.println("3. Number of days: The number of days in each divided group" +
                "(If it is not possible to divide groups equally, the program will raise an error). ");
        System.out.println();
        int groupingOption;
        while (true) {
            try {
                do {
                    System.out.print("Enter one of the options above: ");
                    groupingOption = input.nextInt();
                } while (groupingOption != 1 && groupingOption != 2 && groupingOption != 3);
                return groupingOption;
            } catch (Exception e) {
                input.next();
                System.out.print("\nInvalid Input! Please enter again! ");
            }
        }
    }

    // Validate geographic area in the dataset from user input
    public static String locationValidate(String pathToNewCSV) throws IOException {
        Scanner input = new Scanner(System.in);
        String location = null;
        String input_location;
        int count = 0;
        do {
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToNewCSV));
            String row = "";
            csvReader.readLine();
            if (count >= 1) {
                System.out.println("\nThere is no location like this in our Database. Please type again! \n");
            }
            System.out.print("\nPlease enter a name of a Continent or Country: ");
            input_location = input.nextLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);
                if ((data[2].equalsIgnoreCase(input_location)) || (data[1].equalsIgnoreCase(input_location))) {
                    location = input_location;
                    break;
                }
            }
            count += 1;
            csvReader.close(); //reset to the 1st line of csvFile
        } while (location == null);
        System.out.println("We've got '" + location + "' !!!");
        return location;
    }

    // Validate end date from user input
    public static String EndInputDateValidate(String pathToNewCSV) throws IOException, ParseException {
        Scanner input = new Scanner(System.in);
        String endDate = null;
        String inputEndDate;
        int count = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Date smallestDate = sdf.parse("1/1/2020");
        Date largestDate = sdf.parse("7/8/2021");
        do {
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToNewCSV));
            String row = "";
            csvReader.readLine();
            if (count >= 1) {
                System.out.println("\nThe date is out of our database time-range or not valid in your chosen " +
                        "location. Please type again!");
            }
            System.out.println("\nPlease enter your desired END DATE in format MM/dd/yyyy: ");
            Date chosenDateDateFormat = null;
            //Parse Error: occurs when user input data that cannot be converted into date (ex: dsfnui, 234)
            boolean error = true;
            do {
                System.out.print("The date should be in the RANGE from 1/1/2020-(0h) to 7/8/2021-(24h): ");
                inputEndDate = input.nextLine();
                try {
                    chosenDateDateFormat = sdf.parse(inputEndDate);
                    error = false;
                } catch (Exception e) {
                    System.out.println("\nPlease enter the date in this FORMAT 'M/d/yyyy' !!!");
                }
            } while (error);

            if (endDate == null) {
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",", -1);
                    if (!smallestDate.after(chosenDateDateFormat) && !largestDate.before(chosenDateDateFormat)) {
                        endDate = inputEndDate;
                        break;
                    }
                }
                count += 1;
                csvReader.close(); //reset to the 1st line of csvFile
            }
        } while (endDate == null);
        System.out.println("We've got '" + endDate + "' !!!");
        return endDate;
    }

    // Validate start date from user input
    public static String chosenDateValidate(String pathToNewCSV) throws IOException, ParseException {
        Scanner input = new Scanner(System.in);
        String chosenDate = null;
        String inputChosenDate;
        int count = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Date smallestDate = sdf.parse("1/1/2020");
        Date largestDate = sdf.parse("7/8/2021");
        do {
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToNewCSV));
            String row = "";
            csvReader.readLine();
            if (count >= 1) {
                System.out.println("\nThe date is out of our database time-range or not valid in your chosen " +
                        "location. " +
                        "Please type again!");
            }
            System.out.println("\nPlease enter a START DATE you want in this format 'M/d/yyyy'");
            Date chosenDateDateFormat = null;
            //Parse Error: occurs when user input data that cannot be converted into date (ex: dsfnui, 234)
            boolean error = true;
            do {
                System.out.print("The date should be in the RANGE from 1/1/2020-(0h) to 7/8/2021-(24h): ");
                inputChosenDate = input.nextLine();
                try {
                    chosenDateDateFormat = sdf.parse(inputChosenDate);
                    error = false;
                } catch (Exception e) {
                    System.out.println("\nPlease enter the date in this FORMAT 'M/d/yyyy' !!!");
                }
            } while (error);
            if (chosenDate == null) {
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",", -1);
                    if (!smallestDate.after(chosenDateDateFormat) && !largestDate.before(chosenDateDateFormat)) {
                        chosenDate = inputChosenDate;
                        break;
                    }
                }
                count += 1;
                csvReader.close(); //reset to the 1st line of csvFile
            }
        } while (chosenDate == null);
        System.out.println("We've got '" + chosenDate + "' !!!");
        return chosenDate;
    }
}

