//import java.util.Scanner;
//public class UserInterface {
//    public static void main(String[] args) {
//        while(true) {
//            Scanner scanner = new Scanner(System.in);
//            int DataChoice;
//            int DisplayChoice;
//            int FinalChoice;
//
//            //input name
//            System.out.println("Please input name here");
//            String AreaName = scanner.nextLine();
//            System.out.println("The area is " + AreaName);
//
//            //Data choice input
//            System.out.println("Choose the type of Range");
//            System.out.println("1. A pair of start date and end date");
//            System.out.println("2. A number of days or weeks from particular date");
//            System.out.println("3. A number of days or weeks to particular date");
//
//            do {
//                DataChoice = scanner.nextInt();
//                if ((DataChoice < 1) || (DataChoice > 2)) {
//                    System.out.println("You have entered the wrong option. Please choose again!");
//                }
//            } while ((DataChoice < 1) || (DataChoice > 3));
//
//            switch (DataChoice) {
//                case 1 -> DataOption1();
//                case 2 -> DataOption2();
//                case 3 -> DataOption3();
//            }
//
//            //Grouping condition
//
//            //Metric ( 3 possible metric: positive cases, deaths, people vaccinated )
//
//            //Way of calculation
//
//
//            //display summary data
//
//            System.out.println("Please choose one way to display");
//            System.out.println("1. Tabular Display");
//            System.out.println("2. Chart Display");
//
//            do {
//                DisplayChoice = scanner.nextInt();
//                if ((DisplayChoice < 1) || (DisplayChoice > 2)) {
//                    System.out.println("You have entered the wrong option. Please choose again!");
//                }
//            }
//            while ((DisplayChoice < 1) || (DisplayChoice > 2));
//
//            switch (DisplayChoice) {
//                case 1 -> Tabular();
//                case 2 -> Chart();
//            }
//
//
//            //Continue or stop the program - Final Choice
//            System.out.println("This is the end of the program. Do you want to restart or not? ");
//            System.out.println("1. Restart the program");
//            System.out.println("2. End the program");
//
//            do {
//                FinalChoice = scanner.nextInt();
//                if ((FinalChoice < 1) || (FinalChoice > 2)) {
//                    System.out.println("You have entered the wrong option. Please choose again!");
//                }
//            }
//            while ((FinalChoice < 1) || (FinalChoice > 2));
//
//            switch (FinalChoice) {
//                case 1 -> System.out.println("The program will restart.");
//
//                case 2 -> {
//                    System.out.println("Good bye! Thank you for using our program");
//                    System.exit(0); }
//            }
//        }
//    }
//
//
//    public static void DataOption1() {
//        System.out.println("You have chosen 1 ");
//    }
//
//    public static void DataOption2() {
//        System.out.println("You have chosen 2 ");
//    }
//
//    public static void DataOption3() {
//        System.out.println("You have chosen 3 ");
//    }
//
//    public static void Tabular() {
//        System.out.println("You have chosen Tabular Display ");
//    }
//
//    public static void Chart() {
//        System.out.println("You have chosen Chart Display ");
//    }
//
//}
