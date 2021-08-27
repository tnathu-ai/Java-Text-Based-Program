package Covid19;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static Covid19.ReadWriteCsvData.replaceNullCsv;

public class UserInitialInput {
    String pathToNewCsv;
    String location;
    String chosenDate;
    int dayAway;
    String endInputDate;
    int groupingOption;

    public UserInitialInput(String pathToNewCsv, String location, String chosenDate, int dayAway, int groupingOption) {
        this.pathToNewCsv = pathToNewCsv;
        this.location = location;
        this.chosenDate = chosenDate;
        this.dayAway = dayAway;
        this.groupingOption = groupingOption;
    }

    public UserInitialInput(String endInputDate) {
        this.endInputDate = endInputDate;
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
            endInputDate = EndInputDateValidate();
        } else {
            dayAway = dayAwayValidate();
        }
        // choose type of grouping methods:
        int groupingOption = groupOptionValidate();

        if (DataChoice == 1) {
            UserInitialInput userDataInput = new UserInitialInput(endInputDate);
        }
        UserInitialInput userDataInput = new UserInitialInput(pathToNewCSV, location, chosenDate, dayAway,
                groupingOption);
        return userDataInput;
    }


    public static String EndInputDateValidate() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nPlease enter your desired end date in format MM/dd/yyyy: ");
        while (true) {
            try {
                return input.nextLine();
            } catch (Exception e) {
                input.next();
                System.out.println("Invalid Input! Please enter again: ");
            }
        }
    }

    public static int dayAwayValidate() {
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter the number of days that are away from the date you chose: ");
        while (true) {
            try {
                return input.nextInt();
            } catch (Exception e) {
                input.next();
                System.out.print("Invalid Input! Please enter again: ");
            }
        }
    }

    public static int groupOptionValidate() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nThere are 3 ways you can choose to group your days: ");
        System.out.println("1. No grouping: each day is a separate group.");
        System.out.println("2. Number of groups: the number of days will be divided equally into each group." +
                "\n Your data will be divided as equally as possible. ");
        System.out.println("3. Number of days: The number of days in each divided group" +
                "\n (If it is not possible to divide groups equally, the program will raise an error). ");
        System.out.println();
        int groupingOption;
        do {
            System.out.print("Please enter only the number in those 3 options to choose: ");
            while (true) {
                try {
                    groupingOption = input.nextInt();
                    return groupingOption;
                } catch (Exception e) {
                    input.next();
                    System.out.print("Invalid Input! Please enter again: ");
                }
            }
        } while (groupingOption != 1 && groupingOption != 2 && groupingOption != 3);
    }

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
                System.out.println("There is no location like this in our Database. Please type again! \n");
            }
            System.out.print("Enter the name of a Continent or Country: ");
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
                System.out.println("The date is out of our database time-range. Please type again!");
            }
            System.out.println("\nChoose a date you want in this format 'M/d/yyyy'");
            Date chosenDateDateFormat = null;
            //Parse Error: occurs when user input data that cannot be converted into date (ex: dsfnui, 234)
            boolean error = true;
            do {
                System.out.print("The date should be in the range from 1/1/2020 to 7/8/2021: ");
                inputChosenDate = input.nextLine();
                try {
                    chosenDateDateFormat = sdf.parse(inputChosenDate);
                    error = false;
                } catch (Exception e) {
                    System.out.println("\nPlease enter the date in this format 'M/d/yyyy' !!!");
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

