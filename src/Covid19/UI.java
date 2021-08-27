package Covid19;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class UI {
    public static void main(String[] args) throws ParseException, IOException {
//        List<CovidData> extractedData = extractedData();
        int DataChoice = getDataChoice();
        runIndividualProgram(DataChoice);
    }

    public static int getDataChoice() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            int DataChoice;
            //Data choice input
            System.out.println("\nChoose the type of Range");
            System.out.println("1. A pair of start date and end date");
            System.out.println("2. A number of days or weeks from particular date");
            System.out.println("3. A number of days or weeks to particular date");

            do {
                System.out.print("\nPlease input your option: ");
                DataChoice = scanner.nextInt();
                if ((DataChoice < 1) || (DataChoice > 3)) {
                    System.out.println("You have entered the wrong option. Please choose again!");
                }
            } while ((DataChoice < 1) || (DataChoice > 3));
            return DataChoice;
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

//    public static List<CovidData> extractedData() throws IOException, ParseException {
//        List<CovidData> extractedData = new ArrayList<>();
//        while (true) {
//            Scanner scanner = new Scanner(System.in);
//            int DataChoice;
//            //Data choice input
//            System.out.println("\nChoose the type of Range");
//            System.out.println("1. A pair of start date and end date");
//            System.out.println("2. A number of days or weeks from particular date");
//            System.out.println("3. A number of days or weeks to particular date");
//
//            do {
//                System.out.print("Please input your option:");
//                DataChoice = scanner.nextInt();
//                if ((DataChoice < 1) || (DataChoice > 3)) {
//                    System.out.println("You have entered the wrong option. Please choose again!");
//                }
//            } while ((DataChoice < 1) || (DataChoice > 3));
//
//            switch (DataChoice) {
//                case 1 -> ReadInclusiveDateRange.main();
//                case 2 -> DaysFromAParticularDate.runTypeofRange();
//                case 3 -> DaysToAParticularDate.main();
//            }
//        }
//
//
//
//        //Metric ( 3 possible metric: positive cases, deaths, people vaccinated )
//
//        //Way of calculation
//
//
//        //display summary data
//    }

}

