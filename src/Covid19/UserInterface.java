package Covid19;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class UserInterface {
    public static void main(String[] args) throws ParseException, IOException {
        while (true) {
            int FinalChoice;
            Scanner scanner = new Scanner(System.in);
            int DataChoice = getDataChoice();
            runIndividualProgram(DataChoice);

            //Continue or stop the program - Final Choice
            System.out.println("This is the end of the program. Do you want to restart or not? ");
            System.out.println("1. Restart the program");
            System.out.println("2. End the program");

            do {
                FinalChoice = scanner.nextInt();
                if ((FinalChoice < 1) || (FinalChoice > 2)) {
                    System.out.println("You have entered the wrong option. Please choose again!");
                }
            }
            while ((FinalChoice < 1) || (FinalChoice > 2));

            switch (FinalChoice) {
                case 1 -> System.out.println("The program will restart.");

                case 2 -> {
                    System.out.println("Good bye! Thank you for using our program");
                    System.exit(0); }
            }
        }
    }

    public static int getDataChoice() {
        Scanner input = new Scanner(System.in);
        int DataChoice;
        //Data choice input
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
}

