package Covid19;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static Covid19.ReadWriteCsvData.replaceNullCsv;

public class UserInitialInput {
    private static CovidData dataRow;
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
        System.out.println("Please enter your desired end date in format MM/dd/yyyy: ");
        String endInputDate = input.nextLine();
        return endInputDate;
    }

    public static int getDayAway() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of days that are away from the date you chose: ");
        int dayAway = input.nextInt();
        return dayAway;
    }

    public static int getOption() {
        Scanner input = new Scanner(System.in);
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
        } while (option != 1 && option != 2 && option != 3);

        return option;
    }


    public static UserInitialInput userInputRequest(int DataChoice) throws ParseException {
        String pathToCSV = "Data/covid-data.csv";
        String pathToNewCSV = "Data/covid-data-zero.csv";
        replaceNullCsv(pathToCSV, pathToNewCSV);

        Scanner input = new Scanner(System.in);

        String location;
//        do {
//            System.out.println("Enter the Continent or Country: ");
//            while (!input.hasNextLine()) {
//                System.out.println("That's not a valid location. Please enter another one! ");
//                input.next(); // this is important!
//            }
//            location = input.nextLine();
//
//        } while (dataRow.getContinent().equalsIgnoreCase(location) || dataRow.getLocation().equalsIgnoreCase(location));
//        System.out.println("Thank you! Got " + location);
        System.out.println("Enter the Continent or Country: ");
        location = input.nextLine();


        String chosenDate;
//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//        Date smallestDate = sdf.parse("1/1/2020");
//        Date largestDate = sdf.parse("7/8/2021");
//        Date chosenDateDateFormat = sdf.parse(chosenDate);
//        do {
//            System.out.println("Choose a date you want in this format 'MM/dd/yyyy'" +
//                    "\n The date should be in the range from 1/1/2020 to 9/9/2020: ");
//            while (!input.hasNextLine()) {
//                System.out.println("That's not a valid chosenDate." +
//                        "\n Please enter another one in the correct Date format 'MM/dd/yyyy'" +
//                        "that in the range from 1/1/2020 to 7/8/2021: ");
//                input.next();
//            }
//            chosenDate = input.nextLine();
//        } while (!(dataRow.getDate().equals(chosenDate) || !largestDate.after(chosenDateDateFormat) || !smallestDate.before(chosenDateDateFormat)));
//        System.out.println("Thank you! Got " + chosenDate);
        System.out.print("Enter the day you want (M/D/Y): ");
        chosenDate = input.nextLine();

        String endInputDate = null;
        int dayAway = 0;
        if (DataChoice == 1) {
            endInputDate = getEndInputDate();
        } else {
            dayAway = getDayAway();
        }

        int option = getOption();
        if (DataChoice == 1) {
            UserInitialInput userDataInput = new UserInitialInput(endInputDate);
        }
        UserInitialInput userDataInput = new UserInitialInput(pathToNewCSV, location, chosenDate, dayAway, option);

        return userDataInput;
    }
}