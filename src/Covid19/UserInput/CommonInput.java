package Covid19.UserInput;

import Covid19.CovidData;
import Covid19.UserInitialInput;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static Covid19.ReadWriteCsvData.replaceNullCsv;

public class CommonInput {
    private static CovidData dataRow;
    String pathToNewCsv;
    String location;
    String chosenDate;
    int option;

    public CommonInput(String pathToNewCsv, String location, String chosenDate, int option) {
        this.pathToNewCsv = pathToNewCsv;
        this.location = location;
        this.chosenDate = chosenDate;
        this.option = option;
    }

    public CommonInput(String location, String chosenDate) {
        this.location = location;
        this.chosenDate = chosenDate;
    }

    public CommonInput(int option) {
        this.option = option;
    }

    public CommonInput getCommonInput(String pathToNewCsv, String location, String chosenDate, int option) throws ParseException {
        String pathToCSV = "Data/covid-data.csv";
        String pathToNewCSV = "Data/covid-data-zero.csv";
        replaceNullCsv(pathToCSV, pathToNewCSV);
        CommonInput getLocationAndChosenDate = inputRequest(location, chosenDate);
        CommonInput userDataInput = new CommonInput(pathToNewCSV, location, chosenDate, option);
        return userDataInput;
    }

    public CommonInput inputRequest(String location, String chosenDate) throws ParseException {
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Enter the Continent or Country: ");
            while (!input.hasNextLine()) {
                System.out.println("That's not a valid location. Please enter another one! ");
                input.next();
            }
            location = input.nextLine();
        } while (dataRow.getContinent().equalsIgnoreCase(location) || dataRow.getLocation().equalsIgnoreCase(location));
        System.out.println("We've Got " + location);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date smallestDate = sdf.parse("1/1/2020");
        Date largestDate = sdf.parse("7/8/2021");
        Date chosenDateDateFormat = sdf.parse(chosenDate);
        do {
            System.out.println("Choose a date you want in this format 'MM/dd/yyyy'" +
                    "\n The date should be in the range from 1/1/2020 to 9/9/2020: ");
            while (!input.hasNextLine()) {
                System.out.println("That's not a valid chosenDate." +
                        "\n Please enter another one in the correct Date format 'MM/dd/yyyy'" +
                        "that in the range from 1/1/2020 to 7/8/2021: ");
                input.next();
            }
            chosenDate = input.nextLine();
        } while (!(dataRow.getDate().equals(chosenDate) || !largestDate.after(chosenDateDateFormat) || !smallestDate.before(chosenDateDateFormat)));
        System.out.println("We've Got " + chosenDate);
        CommonInput userDataInput = new CommonInput(location, chosenDate);
        return userDataInput;
    }

    public static CommonInput inputOption(int option) {
        Scanner input = new Scanner(System.in);

        // choose type of grouping methods:
        System.out.println("\n There are 3 ways you can choose to group your days: ");
        System.out.println("1. No grouping: each day is a separate group.");
        System.out.println("2. Number of groups: the number of days will be divided equally into each group." +
                "\n Your data will be divided as equally as possible. ");
        System.out.println("3. Number of days: The number of days in each divided group" +
                "\n (If it is not possible to divide groups equally, the program will raise an error). ");
        System.out.println();
        do {
            System.out.print("Please enter only the number in those 3 options to choose: ");
            option = input.nextInt();
        } while (option != 1 && option != 2 && option != 3);
        CommonInput userDataInput = new CommonInput(option);
        return userDataInput;
    }
}