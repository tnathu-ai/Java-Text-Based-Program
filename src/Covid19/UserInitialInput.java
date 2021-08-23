package Covid19;

import java.util.Scanner;

import static Covid19.ReadWriteCsvData.replaceNullCsv;

public class UserInitialInput {
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

    public static UserInitialInput userInputRequest() {
        String pathToCSV = "Data/covid-data.csv";
        String pathToNewCSV = "Data/covid-data-zero.csv";
        replaceNullCsv(pathToCSV, pathToNewCSV);

        Scanner input = new Scanner(System.in);
        System.out.print("Enter the Continent or Country: ");
        String location = input.nextLine();

        System.out.print("Choose a date you want in this format 'MM/dd/yyyy': ");
        String chosenDate = input.nextLine();

        System.out.print("Enter the number of days that are away from the date you chose: ");
        int dayAway = input.nextInt();

        System.out.println("\nThere are 3 ways you can choose to group your" +
                " days: ");
        System.out.println("1. No grouping: each day is a separate group.");
        System.out.println("2. Choose the number of groups that the number of days will be divided equally into " +
                "each " +
                "group");
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
