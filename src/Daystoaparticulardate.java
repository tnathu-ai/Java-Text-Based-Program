import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


class DataDetail {
    String iso_code;
    String continent;
    String location;
    String date;
    long new_cases;
    long new_deaths;
    long people_vaccinated;
    long population;

    public DataDetail(String iso_code,
                      String continent,
                      String location,
                      String date,
                      long new_cases,
                      long new_deaths,
                      long people_vaccinated,
                      long population) {
        this.iso_code = iso_code;
        this.continent = continent;
        this.location = location;
        this.date = date;
        this.new_cases = new_cases;
        this.new_deaths = new_deaths;
        this.people_vaccinated = people_vaccinated;
        this.population = population;
    }

    public String getIso_code() {
        return iso_code;
    }

    public String getContinent() {
        return continent;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public long getNew_cases() { return new_cases; }

    public long getNew_deaths() {
        return new_deaths;
    }

    public long getPeople_vaccinated() {
        return people_vaccinated;
    }

    public long getPopulation() {
        return population;
    }


public class Daystoaparticulardate {
    public static void main(String[] args) throw IOEXCEPTION {
        string CSV = "Data/covid-data.csv";
        string newCSV = "Data/covid-data-zero.csv";
        replaceCSV(String CSV, String newCSV);
        scanner input = new scanner(System.in);


        System.out.print("Enter a Continent or Country: ");
        String location = input.nextLine();

        System.out.print("Choose a date you want in this format 'MM/dd/yyyy': ");
        String chosenDate = input.nextLine();

        System.out.print("Enter the number of days that are away from the date you chose: ");

        int dayAway = input.nextInt();
        System.out.println("\nThere are 3 ways you can choose to group your" + " days: ");
        System.out.println("1. No grouping: each day is a separate group.");
        System.out.println("2. Choose the number of groups that the number of days will be divided equally into " +
                "each " +
                "group");
        System.out.println("3. Choose the number of days in each divided group (Note: The program will choose the" +
                " " +
                "most optimal number for grouping equally");


        System.out.println();

        int option;

        do {
            System.out.print("Please enter the number in those 3 options to choose: ");
            option = input.nextInt();
        } while (option != 1 && option != 2 && option != 3);

        ProcessData(newCSV, location, chosenDate, dayAway, option);
    }

    public static void ProcessData(String newCSV, String location,
                                   String chosenDate, int dayAway, int option) throws ParseException, IOException {
        if (option == 1) {
            String endDate = displayStartEndDate(chosenDate, dayAway);
//                System.out.println(getDatesBetween(chosenDate, endDate));

            //Get and Print the Date Range
            ArrayList<Date> getDatesBetweenArr = getDatesBetween(chosenDate, endDate);
            System.out.println(convertDateToString(getDatesBetweenArr));
            System.out.println("\niso_code, continent, location, date, new_cases, new_deaths, people_vaccinated, " +
                    "population");

            //Start reading 1 row of data & Process immediately
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(pathToNewCSV));
            String row = "";
            csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);

                int i = 0;

                DataDetail dataRow = new DataDetail(data[i],
                        data[i + 1],
                        data[i + 2],
                        data[i + 3],
                        Long.parseLong(data[i + 4]),
                        Long.parseLong(data[i + 5]),
                        Long.parseLong(data[i + 6]),
                        Long.parseLong(data[i + 7]));

                //Deal with 1 row of data
                DataDetail returnRowLocation = getDataFromLocation (dataRow, location);
//                getDataFromLocation(dataRow, location);
                if (returnRowLocation != null) {
                    DataDetail returnRow = getDataFromDate(chosenDate, endDate, returnRowLocation);
                    if (returnRow != null) {
                        System.out.println(returnRow.toPrintString());
                    }
                }
            }

        } else if (option == 2) {
            int groups;
            do {
                Scanner input = new Scanner(System.in);

                System.out.print("Enter the number of groups (smaller than the number of days): ");

                groups = input.nextInt();

            } while (groups > dayAway);

            //Get and Print the Date Range
            String endDate = displayStartEndDate(chosenDate, dayAway);
//                System.out.println(getDatesBetween(chosenDate, endDate));
            ArrayList<Date> getDatesBetweenArr = getDatesBetween(chosenDate, endDate);
            System.out.println(convertDateToString(getDatesBetweenArr));
            System.out.println("\niso_code, continent, location, date, new_cases, new_deaths, people_vaccinated, " +
                    "population");

            //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
            ArrayList<Integer> groupsSplittedArr = splitGroupsEqually(dayAway, groups);
            ArrayList<DataDetail> bigGroup = new ArrayList<DataDetail>();

            //Start reading 1 row of data & Process immediately
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(pathToNewCSV));
            String row = "";
            csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);
                int i = 0;

                DataDetail dataRow = new DataDetail(data[i],
                        data[i + 1],
                        data[i + 2],
                        data[i + 3],
                        Long.parseLong(data[i + 4]),
                        Long.parseLong(data[i + 5]),
                        Long.parseLong(data[i + 6]),
                        Long.parseLong(data[i + 7]));

                //Deal with 1 row of data
                DataDetail returnRowLocation = getDataFromLocation(dataRow, location);
//                getDataFromLocation(dataRow, location);
                if (returnRowLocation != null) {
                    DataDetail returnRow = getDataFromDate(chosenDate, endDate, returnRowLocation);
                    if (returnRow != null) {
                        bigGroup.add(returnRow);
                    }
                }
            }
            putDataInGroup(bigGroup, groupsSplittedArr);

        } else if (option == 3) {
            int daysPerGroup;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter the number of days in each group (larger than 1): ");
                daysPerGroup = input.nextInt();

            } while (daysPerGroup > dayAway && daysPerGroup > 1);

            //Get and Print the Date Range
            String endDate = displayStartEndDate(chosenDate, dayAway);
//                System.out.println(getDatesBetween(chosenDate, endDate));
            ArrayList<Date> getDatesBetweenArr = getDatesBetween(chosenDate, endDate);
            System.out.println(convertDateToString(getDatesBetweenArr));
            System.out.println("\niso_code, continent, location, date, new_cases, new_deaths, people_vaccinated, " +
                    "population");

            //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
            ArrayList<Integer> groupsSplittedArr = splitEqualDays(dayAway, daysPerGroup);
            ArrayList<DataDetail> bigGroup = new ArrayList<DataDetail>();

            //Start reading 1 row of data & Process immediately
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(pathToNewCSV));
            String row = "";
            csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);
                int i = 0;

                DataDetail dataRow = new DataDetail(data[i],
                        data[i + 1],
                        data[i + 2],
                        data[i + 3],
                        Long.parseLong(data[i + 4]),
                        Long.parseLong(data[i + 5]),
                        Long.parseLong(data[i + 6]),
                        Long.parseLong(data[i + 7]));

                //Deal with 1 row of data
                DataDetail returnRowLocation = getDataFromLocation (dataRow, location);
//                getDataFromLocation(dataRow, location);
                if (returnRowLocation != null) {
                    DataDetail returnRow = getDataFromDate(chosenDate, endDate, returnRowLocation);
                    if (returnRow != null) {
                        bigGroup.add(returnRow);
                    }
                }
            }
            putDataInGroup(bigGroup, groupsSplittedArr);

        } else {
            //Just in case sth else happens
            ProcessData(newCSV, location, chosenDate, dayAway, option);
        }
    }

    public static void putDataInGroup(ArrayList<DataDetail> bigGroup,
                                      ArrayList<Integer> groupsSplittedArr) {
        ArrayList<DataDetail> groupsDaysArr = new ArrayList<DataDetail>();
        ArrayList<ArrayList<DataDetail>> groupsDaysArrFinal = new ArrayList<ArrayList<DataDetail>>();

        int count = 1;
        int k = 0;

        for (int outerInd = 0; outerInd < groupsSplittedArr.size(); outerInd++) {
            System.out.println("GROUP " + count);

            for (int innerInd = 0; innerInd < groupsSplittedArr.get(outerInd); innerInd++) {
                DataDetail dateElement = bigGroup.get(k);
                groupsDaysArr.add(dateElement);
                k += 1;
            }

            for (DataDetail gda: groupsDaysArr) {
                System.out.println(gda.toPrintString());
            }
            Collections.addAll(groupsDaysArrFinal, groupsDaysArr);
            groupsDaysArr.clear();
            count += 1;
        }
           System.out.println(groupsDaysArrFinal);
    }

}





