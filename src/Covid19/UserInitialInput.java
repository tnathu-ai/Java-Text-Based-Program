package Covid19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static Covid19.ReadWriteCsvData.replaceNullCsv;

public class UserInitialInput {
    private static CovidData dataRow;
    private static UserInitialInput userDataInput;
    String pathToNewCsv;
    String location;
    String chosenDate;
    int dayAway;
    String endInputDate;
    int option;

    public UserInitialInput(String pathToNewCsv, String location, String chosenDate, int dayAway, int option) {
        this.pathToNewCsv = pathToNewCsv;
        this.location = location;
        this.chosenDate = chosenDate;
        this.dayAway = dayAway;
        this.option = option;
    }

    public UserInitialInput(String endInputDate) {
        this.endInputDate = endInputDate;
    }

    public static String getEndInputDate() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nPlease enter your desired end date in format MM/dd/yyyy: ");
        String endInputDate = input.nextLine();
        return endInputDate;
    }

    public static int getDayAway() {
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter the number of days that are away from the date you chose: ");
        int dayAway = input.nextInt();
        return dayAway;
    }

    public static UserInitialInput userInputRequest(int DataChoice) throws ParseException, IOException {
        String pathToCSV = "Data/covid-data.csv";
        String pathToNewCSV = "Data/covid-data-zero.csv";
        replaceNullCsv(pathToCSV, pathToNewCSV);
        Scanner input = new Scanner(System.in);

        // Validate user input LOCATION, CHOSENDATE
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToNewCSV));
        String row = "";
        csvReader.readLine();

        String location = null;
        String input_location;
        do {
            System.out.print("Enter the name of a Continent or Country: ");
            input_location = input.nextLine();
            if (location == null) {
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",", -1);
                    if ((data[2].equalsIgnoreCase(input_location)) || (data[1].equalsIgnoreCase(input_location))) {
                        location = input_location;
                        break;
                    }
                }
            } else {
                System.out.println("There is no location like this in our Database. Please type again! ");
            }
        } while (location == null);
        System.out.println("We've got '" + location + "' !!!");

        String chosenDate = null;
        String inputChosenDate;
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Date smallestDate = sdf.parse("1/1/2020");
        Date largestDate = sdf.parse("7/8/2021");
        do {
            System.out.print("\nChoose a date you want in this format 'M/d/yyyy'" +
                    "\nThe date should be in the range from 1/1/2020 to 7/8/2021: ");
            inputChosenDate = input.nextLine();
            Date chosenDateDateFormat = sdf.parse(inputChosenDate);

            if (chosenDate == null) {
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",", -1);
                    if (largestDate.after(chosenDateDateFormat) && smallestDate.before(chosenDateDateFormat)) {
                        chosenDate = inputChosenDate;
                        break;
                    }
                }
            } else {
                System.out.println("The date is out of our database time-range. Please type again!");
            }
        } while (chosenDate == null);
        System.out.println("We've got '" + chosenDate + "' !!!");
        //// End of Validation

        String endInputDate = null;
        int dayAway = 0;
        if (DataChoice == 1) {
            endInputDate = getEndInputDate();
        } else {
            dayAway = getDayAway();
        }

        // choose type of grouping methods:
        System.out.println("\nThere are 3 ways you can choose to group your days: ");
        System.out.println("1. No grouping: each day is a separate group.");
        System.out.println("2. Number of groups: the number of days will be divided equally into each group." +
                "\n Your data will be divided as equally as possible. ");
        System.out.println("3. Number of days: The number of days in each divided group" +
                "\n (If it is not possible to divide groups equally, the program will raise an error). ");
        System.out.println();
        int option;
        do {
            System.out.print("Please enter only the number in those 3 options to choose: ");
            option = input.nextInt();
            System.out.println();
        } while (option != 1 && option != 2 && option != 3);

        if (DataChoice == 1) {
            UserInitialInput userDataInput = new UserInitialInput(endInputDate);
        }
        UserInitialInput userDataInput = new UserInitialInput(pathToNewCSV, location, chosenDate, dayAway, option);
        return userDataInput;
    }
}
