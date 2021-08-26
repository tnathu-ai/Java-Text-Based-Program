package Covid19;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Covid19.Metrics.metricDisplay;

public class ReadInclusiveDateRange {
    GroupingDisplayDataPrint groupDisplayPrint;

    public static void main() throws ParseException, IOException {
//
//        String pathToCSV = "Data/covid-data.csv";
//        String pathToNewCSV = "Data/covid-data-zero.csv";
//        replaceNullCsv(pathToCSV, pathToNewCSV);
//        // Ask for user inputs
//        Scanner input = new Scanner(System.in);
//        System.out.println("Please enter the name of either a desired location (country) or continent: ");
//        String location = input.nextLine();
//        // set date format that match CSV file
//        System.out.println("Please enter your desired start date in format MM/dd/yyyy: ");
//        String chosenDate = input.next();
//        System.out.println("Please enter your desired end date in format MM/dd/yyyy: ");
//        String endDate = input.next();
//
//        System.out.println("\nThere are 3 ways you can choose to group your" +
//                " days: ");
//        System.out.println("1. No grouping: each day is a separate group.");
//        System.out.println("2. Choose the number of groups that the number of days will be divided equally into " +
//                "each " +
//                "group");
//        System.out.println("3. Choose the number of days in each divided group (Note: The program will choose the" +
//                " " +
//                "most optimal number for grouping equally");
//        System.out.println();
//        int option;
//        do {
//            System.out.print("Please enter the number in those 3 options to choose: ");
//            option = input.nextInt();
//        } while (option != 1 && option != 2 && option != 3);
//
//        getDataFromCSV(pathToNewCSV, location, chosenDate, endDate, option);
//    }
//
//
//    public static List<CovidData> getDataFromCSV(String pathToNewCSV, String location,
//                                                 String chosenDate, String endDate, int option)
//            throws ParseException, IOException {
//        List<CovidData> dataList = new ArrayList<>();
//        ArrayList<Long> metricsArr = new ArrayList<Long>();
//        ArrayList<CovidData> bigGroup = new ArrayList<CovidData>();
//        int dayAway = calculateDayAway(chosenDate, endDate);
//        if (option == 1) {
//
//            // Declaring a variable to lookup for
//            // number of lines in the CSV file
//            String headers;
//            String line;
////        ArrayList<String> covidData = null;
//
//            try (BufferedReader br = new BufferedReader(new FileReader(pathToNewCSV))) {
//                headers = br.readLine();
//                while ((line = br.readLine()) != null) {
//                    // use split(“,”) method to split row and separate each field.
//                    String[] data = line.split(",");
//
//                    CovidData dataRow = new CovidData(data[0], data[1], data[2],
//                            data[3], Long.parseLong(data[4]), Long.parseLong(data[5]), Long.parseLong(data[6]),
//                            Long.parseLong(data[7]));
//
//                    //Deal with 1 row of data
////                getDataFromLocation(dataRow, location);
//                    CovidData returnRow = getInclusiveRageDateFromLocation(location, chosenDate, endDate, dataRow);
//                    System.out.println(returnRow.toPrintString());
//
//                    // return an array which will be column data
////            System.out.println("Continent: " + data[1] + " Location: " + data[2] + " Rage of Dates: " + data[3]);
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //Choose a new metric
//            System.out.println("\n Enter one of the number below to calculate an additional metric:");
//            System.out.println("1. Total positive cases. ");
//            System.out.println("2. Total deaths. ");
//            System.out.println("3. Total people vaccinated. ");
//            int metricOption;
//            do {
//                Scanner input = new Scanner(System.in);
//                System.out.print("Please enter the number in those 3 options to choose: ");
//                metricOption = input.nextInt();
//            } while (metricOption != 1 && metricOption != 2 && metricOption != 3);
//
//            //Print Data
//            System.out.println("\n--- Data List ---");
//            for (CovidData data : bigGroup) {
//                System.out.println(data.toPrintString());
//            }
//            metricDisplay(metricOption, bigGroup, metricsArr);
//            System.out.println("\nChoose one way to display");
//            System.out.println("1. Tabular display ");
//            System.out.println("2. Chart display ");
//            int DisplayOption;
//            do {
//                Scanner input = new Scanner(System.in);
//                System.out.print("Please enter the way you want: ");
//                DisplayOption = input.nextInt();
//            } while (DisplayOption != 1 && DisplayOption != 2 );
//            if (DisplayOption == 1)
//                new TabularDisplay(chosenDate,endDate,metricsArr);
//            return dataList;
//
//        } else if (option == 2) {
//
//            int groups;
//            do {
//                Scanner input = new Scanner(System.in);
//                System.out.print("Enter the number of groups (smaller than the number of days): ");
//                groups = input.nextInt();
//            } while (groups > dayAway);
//
//            //Get and Print the Date Range
////                System.out.println(getDatesBetween(chosenDate, endDate));
//            ArrayList<Date> getDatesBetweenArr = getDatesBetween(chosenDate, endDate);
//            System.out.println(DaysFromAParticularDate.convertDateToString(getDatesBetweenArr));
//
//            //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
//            ArrayList<Integer> groupsSplittedArr = DaysFromAParticularDate.splitGroupsEqually(dayAway, groups);
//            bigGroup = new ArrayList<CovidData>();
//
//            //Start reading 1 row of data & Process immediately
//            BufferedReader csvReader =
//                    new BufferedReader(new FileReader(pathToNewCSV));
//            String row = "";
//            csvReader.readLine();
//            while ((row = csvReader.readLine()) != null) {
//                String[] data = row.split(",", -1);
//                int i = 0;
//
//                CovidData dataRow = new CovidData(data[i],
//                        data[i + 1],
//                        data[i + 2],
//                        data[i + 3],
//                        Long.parseLong(data[i + 4]),
//                        Long.parseLong(data[i + 5]),
//                        Long.parseLong(data[i + 6]),
//                        Long.parseLong(data[i + 7]));
//
//                //Deal with 1 row of data
//                CovidData returnRowLocation = DaysFromAParticularDate.getDataFromLocation(dataRow, location);
////                getDataFromLocation(dataRow, location);
//                if (returnRowLocation != null) {
//                    CovidData returnRow = DaysFromAParticularDate.getDataFromDate(chosenDate, endDate, returnRowLocation);
//                    if (returnRow != null) {
//                        bigGroup.add(returnRow);
//                    }
//                }
//            }
//            //Choose a new metric
//            System.out.println("\nEnter one of the number below to calculate an additional metric:");
//            System.out.println("1. Total positive cases. ");
//            System.out.println("2. Total deaths. ");
//            System.out.println("3. Total people vaccinated. ");
//            int metricOption;
//            do {
//                Scanner input = new Scanner(System.in);
//                System.out.print("Please enter the number in those 3 options to choose: ");
//                metricOption = input.nextInt();
//            } while (metricOption != 1 && metricOption != 2 && metricOption != 3);
//
//            System.out.println("\nChoose one way to display");
//            System.out.println("1. Tabular display ");
//            System.out.println("2. Chart display ");
//            int DisplayOption;
//            do {
//                Scanner input = new Scanner(System.in);
//                System.out.print("Please enter the way you want: ");
//                DisplayOption = input.nextInt();
//            } while (DisplayOption != 1 && DisplayOption != 2 );
//            putDataInGroup(bigGroup, groupsSplittedArr, chosenDate, metricOption, DisplayOption);
//
//        } else if (option == 3) {
//            int daysPerGroup;
//            do {
//                Scanner input = new Scanner(System.in);
//                System.out.print("Enter the number of days in each group (larger than 1): ");
//                daysPerGroup = input.nextInt();
//
//            } while (daysPerGroup > dayAway && daysPerGroup > 1);
//
//            //Get and Print the Date Range
////                System.out.println(getDatesBetween(chosenDate, endDate));
//            ArrayList<Date> getDatesBetweenArr = getDatesBetween(chosenDate, endDate);
//            System.out.println(DaysFromAParticularDate.convertDateToString(getDatesBetweenArr));
//
//            //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
//            ArrayList<Integer> groupsSplittedArr = DaysFromAParticularDate.splitEqualDays(dayAway, daysPerGroup);
//            bigGroup = new ArrayList<CovidData>();
//
//            //Start reading 1 row of data & Process immediately
//            BufferedReader csvReader =
//                    new BufferedReader(new FileReader(pathToNewCSV));
//            String row = "";
//            csvReader.readLine();
//            while ((row = csvReader.readLine()) != null) {
//                String[] data = row.split(",", -1);
//                int i = 0;
//
//                CovidData dataRow = new CovidData(data[i],
//                        data[i + 1],
//                        data[i + 2],
//                        data[i + 3],
//                        Long.parseLong(data[i + 4]),
//                        Long.parseLong(data[i + 5]),
//                        Long.parseLong(data[i + 6]),
//                        Long.parseLong(data[i + 7]));
//
//                //Deal with 1 row of data
//                CovidData returnRowLocation = DaysFromAParticularDate.getDataFromLocation(dataRow, location);
////                getDataFromLocation(dataRow, location);
//                if (returnRowLocation != null) {
//                    CovidData returnRow = DaysFromAParticularDate.getDataFromDate(chosenDate, endDate, returnRowLocation);
//                    if (returnRow != null) {
//                        bigGroup.add(returnRow);
//                    }
//                }
//            }
//            //Choose a new metric
//            System.out.println("\nEnter one of the number below to calculate an additional metric:");
//            System.out.println("1. Total positive cases. ");
//            System.out.println("2. Total deaths. ");
//            System.out.println("3. Total people vaccinated. ");
//            int metricOption;
//            do {
//                Scanner input = new Scanner(System.in);
//                System.out.print("Please enter the number in those 3 options to choose: ");
//                metricOption = input.nextInt();
//            } while (metricOption != 1 && metricOption != 2 && metricOption != 3);
//
//            int DisplayOption;
//            do {
//                Scanner input = new Scanner(System.in);
//                System.out.print("Please enter the way you want: ");
//                DisplayOption = input.nextInt();
//            } while (DisplayOption != 1 && DisplayOption != 2 );
//            putDataInGroup(bigGroup, groupsSplittedArr, chosenDate, metricOption, DisplayOption);
//
//        } else {
//            //Just in case sth else happens
//            getDataFromCSV(pathToNewCSV, location, chosenDate, endDate, option);
//        }
//        return dataList;
//    }
//
//    public static CovidData getInclusiveRageDateFromLocation(String location, String chosenDate, String endDate, CovidData dataRow) throws ParseException {
//        ArrayList<Date> datesInRange = new ArrayList<Date>();
//        ArrayList<String> datesInRangeStr = new ArrayList<String>();
//        CovidData locationData = null;
//        if (dataRow == null) {
//            dataRow = null;
//
//        } else if (dataRow != null &&
//                (dataRow.getContinent().equalsIgnoreCase(location) ||
//                        dataRow.getLocation().equalsIgnoreCase(location))) {
//            datesInRange = getDatesBetween(chosenDate, endDate);
//            datesInRangeStr = CovidData.convertDateToString(datesInRange);
//            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//            Date sd = df.parse(chosenDate);
//            Date ed = df.parse(endDate);
//
//            for (Date d : datesInRange) {
//                Date inputDate = d;
//
//                if (!sd.after(d) && !ed.before(d)) {
//                    locationData = dataRow;
//                    break;
//                } else {
//                    locationData = null;
//                }
//            }
//        }
//        return dataRow;
//    }
//
//    public static ArrayList<Date> getDatesBetween(String chosenDate, String endDate) throws ParseException {
//        //convert String to Date
//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//        Date sDate = sdf.parse(chosenDate);
//        Date eDate = sdf.parse(endDate);
//
//        ArrayList<Date> datesInRange = new ArrayList<>();
//        Calendar c = new GregorianCalendar();
//        c.setTime(sDate);
//
//        Calendar endCalendar = new GregorianCalendar();
//        endCalendar.setTime(eDate);
//
//        while (c.before((endCalendar))) {
//            Date result = c.getTime();
//            datesInRange.add(result);
//            c.add(Calendar.DATE, 1);
//        }
//        return datesInRange;
//    }
//
//    public static void replaceNullCsv(String pathToCSV,
//                                      String pathToNewCSV) {
//        try {
//            BufferedReader csvReader =
//                    new BufferedReader(new FileReader(pathToCSV));
//            BufferedWriter csvWriter =
//                    new BufferedWriter(new FileWriter(pathToNewCSV));
//            String line = "";
//
//            while ((line = csvReader.readLine()) != null) {
//                String[] values = line.split(",", -1);
//                //make an array out of string
//                String writableString = "";
//                ArrayList<String> al = new ArrayList<>();
//
//                //use array to modify and edit
//                for (String element : values) {
//                    if (element == null || element.length() == 0) {
//                        al.add("0");
//                    } else {
//                        al.add(element);
//                    }
//                }
//                //add commas between elements
//                for (String s : al) {
//                    writableString = writableString + s + ",";
//                }
//                //remove last comma
//                writableString = writableString.substring(0,
//                        writableString.length() - 1);
//                csvWriter.write(writableString + "\n");
////                System.out.println(writableString);
//            }
//            csvWriter.close();
//            csvReader.close();
//        } catch (Exception e) {
//            //pinpoint the error in the code
//            e.printStackTrace();
//        }
//    }
//
//    // Function to print difference in
//    // time chosenDate and endDate
//    static int calculateDayAway(String chosenDate, String endDate) {
//        // SimpleDateFormat converts the
//        // string format to date object
//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//        // Try Block
//        long dayAway = 0;
//        try {
//            // parse method is used to parse
//            // the text from a string to
//            // produce the date
//            Date d1 = sdf.parse(chosenDate);
//            Date d2 = sdf.parse(endDate);
//
//            // Calucalte time difference
//            // in time and days
//            long timeDifference
//                    = d2.getTime() - d1.getTime();
//
//            dayAway = (timeDifference
//                    / (1000 * 60 * 60 * 24))
//                    % 365;
//
//            // Print the date difference in days
//            System.out.print("Difference " + "between two dates is: ");
//            System.out.println(dayAway + " days, ");
//
//        }
//
//        // Catch the Exception
//        catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return (int) dayAway;
    }

}