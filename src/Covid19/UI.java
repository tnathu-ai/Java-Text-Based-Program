package Covid19;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    public static void main(String[] args) throws ParseException, IOException {
        List<CovidData> extractedData = extractedData();
    }

    public static List<CovidData> extractedData() throws IOException, ParseException {
        List<CovidData> extractedData = new ArrayList<>();
        while (true) {
            Scanner scanner = new Scanner(System.in);

            int DataChoice;
            //Data choice input
            System.out.println("\nChoose the type of Range");
            System.out.println("1. A pair of start date and end date");
            System.out.println("2. A number of days or weeks from particular date");
            System.out.println("3. A number of days or weeks to particular date");

            do {
                System.out.println("Please input your option");
                DataChoice = scanner.nextInt();
                if ((DataChoice < 1) || (DataChoice > 3)) {
                    System.out.println("You have entered the wrong option. Please choose again!");
                }
            } while ((DataChoice < 1) || (DataChoice > 3));

            switch (DataChoice) {
                case 1 -> ReadInclusiveDateRange.main();
                case 2 -> DaysFromAParticularDate.main();
                case 3 -> DaysToAParticularDate.main();
            }
        }

        //Metric ( 3 possible metric: positive cases, deaths, people vaccinated )

        //Way of calculation


        //display summary data

    }
}
