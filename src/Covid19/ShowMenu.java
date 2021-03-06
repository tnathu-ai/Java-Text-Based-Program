package Covid19;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class ShowMenu {

    // Show a menu that lets users choose data
    public static int getDataChoice() {
        Scanner input = new Scanner(System.in);
        int DataChoice;
        // Lets users choose data (area and range)
        System.out.println("\nChoose the type of Range");
        System.out.println("1. A pair of start date and end date");
        System.out.println("2. A number of days or weeks from particular date");
        System.out.println("3. A number of days or weeks to particular date");
        while (true) {
            try {
                do {
                    System.out.print("\nPlease input your option: ");
                    DataChoice = input.nextInt();
                    if ((DataChoice < 1) || (DataChoice > 3)) {
                        System.out.println("\nYou have entered the wrong option. Please choose again!");
                    }
                } while ((DataChoice < 1) || (DataChoice > 3));
                return DataChoice;
            } catch (Exception e) {
                input.next();
                System.out.println("\nInvalid input. Please enter again!");
            }
        }
    }

    // Call an appropriate method corresponding with the chosen data
    public static void runIndividualProgram(int DataChoice) throws ParseException, IOException {
        //get User Input
        UserInitialInput initialInput = UserInitialInput.userInputRequest(DataChoice);
        //get Grouping Option
        int option = initialInput.groupingOption;
        GroupingDataPrint groupDisplayPrint = new GroupingDataPrint();
        groupDisplayPrint.setInitialInput(initialInput);
        //print Grouping option
        if (option == 1) {
            groupDisplayPrint.PrintOption1(DataChoice);
        } else if (option == 2) {
            groupDisplayPrint.PrintOption2(DataChoice);
        } else if (option == 3) {
            groupDisplayPrint.PrintOption3(DataChoice);
        } else {
            //Just in case sth else happens
            runIndividualProgram(DataChoice);
        }
    }

    // Method that let users can continue the program as many times as they want.
    // OR: They can choose to end the program when they finish.
    public static void FinalChoice() throws ParseException, IOException {

        int FinalChoice = 0;
        Scanner scanner = new Scanner(System.in);
        int DataChoice = getDataChoice();
        runIndividualProgram(DataChoice);
        //Continue or stop the program - Final Choice
        System.out.println("\n" +
                "\n_____________________________________________________________________________________");
        System.out.println("\nThis is the end of the program. Do you want to restart or not? ");
        System.out.println("1. Restart the program");
        System.out.println("2. End the program");
        while (true) {
            try {
                do {
                    System.out.print("Enter your option: ");
                    FinalChoice = scanner.nextInt();
                    if ((FinalChoice < 1) || (FinalChoice > 2)) {
                        System.out.println("You have entered the wrong option. Please choose again!");
                    }
                } while ((FinalChoice < 1) || (FinalChoice > 2));
                if (FinalChoice == 1) {
                    FinalChoice();
                    System.out.println("The program will restart.");
                } else {
                    System.out.println("Good bye! Thank you for using our program!");
                    System.exit(0);
                }
            } catch (Exception e) {
                scanner.next();
                System.out.println("Invalid Input! Please enter again!");
            }
        }
    }
}


