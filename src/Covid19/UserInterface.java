package Covid19;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import static Covid19.ShowMenu.getDataChoice;
import static Covid19.ShowMenu.runIndividualProgram;

// Run the main program here
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
}

