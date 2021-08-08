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

    public static String displayStartEndDate (String chosenDate,int dayAway){
        //Specifying date format
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Calendar c = new GregorianCalendar();
        try {
            Date date = sdf.parse(chosenDate);
            //Setting the date to the given date
            c.setTime(date);
//            System.out.println(date);
        } catch (ParseException e) {
            System.out.println("invalid input");
            e.printStackTrace();
        }

        //Number of Days to subtract
        c.subtract(Calendar.DAY_OF_MONTH, dayAway);
        //Day after subtracting the days to the input date
        String endDate = sdf.format(c.getTime());
        //Displaying the new Date after addition of Days
        System.out.println("Date after Subtraction: " + endDate);
        System.out.print("\n");
        return endDate;
    }

    public static DataDetail getDataFromDate (String chosenDate, String endDate, DataDetail dataRow) throws ParseException {
        ArrayList<Date> datesInRange = new ArrayList<Date>();
        ArrayList<String> datesInRangeStr = new ArrayList<String>();
        DataDetail locationData = null;

        if (dataRow == null) {
            locationData = null;

        } else if (dataRow != null) {
            datesInRange = getDatesBetween(chosenDate, endDate);
            datesInRangeStr = convertDateToString(datesInRange);

            for (String d : datesInRangeStr) {
                String inputDate = d;

                if ((dataRow.getDate().toString().equals(inputDate))) {
                    locationData = dataRow;
                    break;
                } else {
                    locationData = null;
                }
            }
        }
        return locationData;
        //        if (locationData != null) {
//            System.out.println(locationData.toPrintString());
//        }
    }


    public static DataDetail getDataFromLocation (DataDetail dataRow, String inputlocation){
        if (dataRow.getContinent().equalsIgnoreCase(inputlocation) == false
                && dataRow.getLocation().equalsIgnoreCase(inputlocation) == false) {
            dataRow = null;
        }
//                if (dataRow != null) {
//                    System.out.println(dataRow);
//                }
        return dataRow;
    }

    ////////////////////////////////////////////////////////////////
    //FUNCTIONS: miscellaneous
    // use for print class Object (cuz else it'll print only the reference add of the object)
    public String toPrintString () {
        return iso_code + "," + continent + "," + location + "," + date + "," + new_cases + "," + new_deaths + "," +
                people_vaccinated + "," + population;
    }

    public static void replaceNullCsv (String pathToCSV,
                                       String pathToNewCSV){
        try {
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(pathToCSV));
            BufferedWriter csvWriter =
                    new BufferedWriter(new FileWriter(pathToNewCSV));
            String line = "";

            while ((line = csvReader.readLine()) != null) {
                String[] values = line.split(",", -1);
                //make an array out of string
                String writableString = "";
                ArrayList<String> al = new ArrayList<>();

                //use array to modify and edit
                for (String element : values) {
                    if (element == null || element.length() == 0) {
                        al.add("0");
                    } else {
                        al.add(element);
                    }
                }
                //add commas between elements
                for (String s : al) {
                    writableString = writableString + s + ",";
                }
                //remove last comma
                writableString = writableString.substring(0,
                        writableString.length() - 1);
                csvWriter.write(writableString + "\n");
//                System.out.println(writableString);
            }
            csvWriter.close();
            csvReader.close();
        } catch (Exception e) {
            //pinpoint the error in the code
            e.printStackTrace();
        }
    }
}


}





