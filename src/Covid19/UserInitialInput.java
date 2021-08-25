package Covid19;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;


import static Covid19.ReadWriteCsvData.replaceNullCsv;

public class UserInitialInput {
    private static CovidData dataRow;
    String pathToNewCsv;
    String location;
    String chosenDate;
    int dayAway;
    int option;

    public UserInitialInput(String pathToNewCsv, String location, String chosenDate, int dayAway, int option) {
        this.pathToNewCsv = pathToNewCsv;
        this.location = location;
        this.chosenDate = chosenDate;
        this.dayAway = dayAway;
        this.option = option;
    }

    public static UserInitialInput userInputRequest() throws ParseException {
        String pathToCSV = "Data/covid-data.csv";
        String pathToNewCSV = "Data/covid-data-zero.csv";
        replaceNullCsv(pathToCSV, pathToNewCSV);

        Scanner input = new Scanner(System.in);

        String location;
        do {
            System.out.println("Enter the Continent or Country: ");
            while (!input.hasNextLine()) {
                System.out.println("That's not a valid location. Please enter another one! ");
                input.next(); // this is important!
            }
            location = input.nextLine();
        } while (dataRow.getContinent().equalsIgnoreCase(location) || dataRow.getLocation().equalsIgnoreCase(location));
        System.out.println("Thank you! Got " + location);

        String chosenDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        //Date date1 = sdf.parse("2009-12-31");
        Date smallestDate = sdf.parse("1/1/2020");
        Date largestDate = sdf.parse("9/9/2020");
        Date chosenDateDateFormat = sdf.parse(chosenDate);

        do {
            System.out.println("Choose a date you want in this format 'MM/dd/yyyy'" +
                    "\n The date should be in the range from 1/1/2020 to 9/9/2020: ");
            while (!input.hasNextLine()) {
                System.out.println("That's not a valid chosenDate." +
                        "\n Please enter another one in the correct Date format 'MM/dd/yyyy'" +
                        "that in the range from 1/1/2020 to 9/9/2020: ");
                input.next(); // this is important!
            }
            chosenDate = input.nextLine();
        } while (!(dataRow.getDate().equals(chosenDate) || !largestDate.after(chosenDateDateFormat) || !smallestDate.before(chosenDateDateFormat)));
        System.out.println("Thank you! Got " + chosenDate);

        System.out.print("Enter the number of days that are away from the date you chose: ");
        int dayAway = input.nextInt();

        System.out.println("\nThere are 3 ways you can choose to group your" +
                " days: ");
        System.out.println("1. No grouping: each day is a separate group.");
        System.out.println("2. Choose the number of groups that the number of days will be divided equally into " +
                "each " + "group");
        System.out.println("3. Choose the number of days in each divided group");
        System.out.println();
        int option;
        do {
            System.out.print("Please enter the number in those 3 options to choose: ");
            option = input.nextInt();
        } while (option != 1 && option != 2 && option != 3);
        UserInitialInput userDataInput = new UserInitialInput(pathToNewCSV, location, chosenDate, dayAway, option);

        return userDataInput;
    }
}
