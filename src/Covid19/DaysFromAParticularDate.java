package Covid19;
import java.io.*;
import java.text.DateFormat;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class DaysFromAParticularDate {

    //MAIN METHOD: user input
    public static void main() throws IOException, ParseException {
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
        System.out.println("3. Choose the number of days in each divided group.");
        System.out.println();
        int option;
        do {
            System.out.print("Please enter the number in those 3 options to choose: ");
            option = input.nextInt();
        } while (option != 1 && option != 2 && option != 3);


        ProcessData(pathToNewCSV, location, chosenDate, dayAway, option);
    }

    //ALL METHODS (sorted by Group Types):
    //I.1. ProcessData : the order sequence of processing/handling Data (row by row)
    //I.2. SplitDay, SplitGroup , PutDataIntoGroup
    //I.3. Handle TIME-related functions
    //I.4. Filter Data according to Date, Location
    //I.5. Miscellaneous
    //II.1 Calculate new metrics: TOTAL new cases/new deaths/new vaccinated people in a group.
    public static void ProcessData(String pathToNewCSV, String location,
                                   String chosenDate, int dayAway, int option) throws ParseException, IOException {
        if (option == 1) {
            String endDate = displayStartEndDate(chosenDate, dayAway);
            ArrayList<CovidData> bigGroup = new ArrayList<CovidData>();
            HashSet<Long> metricsArr = new HashSet<Long>();
//                System.out.println(getDatesBetween(chosenDate, endDate));

            //Get and Print the Date Range
            ArrayList<Date> getDatesBetweenArr = getDatesBetween(chosenDate, endDate);
            System.out.println(convertDateToString(getDatesBetweenArr));

            //Start reading 1 row of data & Process immediately
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(pathToNewCSV));
            String row = "";
            csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);

                int i = 0;

                CovidData dataRow = new CovidData(data[i],
                        data[i + 1],
                        data[i + 2],
                        data[i + 3],
                        Long.parseLong(data[i + 4]),
                        Long.parseLong(data[i + 5]),
                        Long.parseLong(data[i + 6]),
                        Long.parseLong(data[i + 7]));


                //Deal with 1 row of data
                CovidData returnRowLocation = getDataFromLocation(dataRow, location);
//                getDataFromLocation(dataRow, location);
                if (returnRowLocation != null) {
                    CovidData returnRow = getDataFromDate(chosenDate, endDate, returnRowLocation);
                    if (returnRow != null) {
                        bigGroup.add(returnRow);
                    }
                }
            }

            //Choose a new metric
            System.out.println("\nEnter one of the number below to calculate an additional metric:");
            System.out.println("1. Total positive cases. ");
            System.out.println("2. Total deaths. ");
            System.out.println("3. Total people vaccinated. ");
            int metricOption;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Please enter the number in those 3 options to choose: ");
                metricOption = input.nextInt();
            } while (metricOption != 1 && metricOption != 2 && metricOption != 3);

            //Print Data
            System.out.println("\n--- Data List ---");
            for (CovidData data : bigGroup) {
                System.out.println(data.toPrintString());
            }
            metricDisplay(metricOption, bigGroup, metricsArr);

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

            //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
            ArrayList<Integer> groupsSplittedArr = splitGroupsEqually(dayAway, groups);
            ArrayList<CovidData> bigGroup = new ArrayList<CovidData>();

            //Start reading 1 row of data & Process immediately
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(pathToNewCSV));
            String row = "";
            csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);
                int i = 0;

                CovidData dataRow = new CovidData(data[i],
                        data[i + 1],
                        data[i + 2],
                        data[i + 3],
                        Long.parseLong(data[i + 4]),
                        Long.parseLong(data[i + 5]),
                        Long.parseLong(data[i + 6]),
                        Long.parseLong(data[i + 7]));

                //Deal with 1 row of data
                CovidData returnRowLocation = getDataFromLocation(dataRow, location);
//                getDataFromLocation(dataRow, location);
                //bigGroup array: all the rows that satisfies user input
                if (returnRowLocation != null) {
                    CovidData returnRow = getDataFromDate(chosenDate, endDate, returnRowLocation);
                    if (returnRow != null) {
                        bigGroup.add(returnRow);
                    }
                }
            }

            //Choose a new metric
            System.out.println("\nEnter one of the number below to calculate an additional metric:");
            System.out.println("1. Total positive cases. ");
            System.out.println("2. Total deaths. ");
            System.out.println("3. Total people vaccinated. ");
            int metricOption;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Please enter the number in those 3 options to choose: ");
                metricOption = input.nextInt();
            } while (metricOption != 1 && metricOption != 2 && metricOption != 3);

            putDataInGroup(bigGroup, groupsSplittedArr, chosenDate, metricOption);

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

            //GETTING THE NUMBER OF NEEDED-SPLITTED GROUPS
            ArrayList<Integer> groupsSplittedArr = splitEqualDays(dayAway, daysPerGroup);
            ArrayList<CovidData> bigGroup = new ArrayList<CovidData>();

            //Start reading 1 row of data & Process immediately
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(pathToNewCSV));
            String row = "";
            csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);
                int i = 0;

                CovidData dataRow = new CovidData(data[i],
                        data[i + 1],
                        data[i + 2],
                        data[i + 3],
                        Long.parseLong(data[i + 4]),
                        Long.parseLong(data[i + 5]),
                        Long.parseLong(data[i + 6]),
                        Long.parseLong(data[i + 7]));

                //Deal with 1 row of data
                CovidData returnRowLocation = getDataFromLocation(dataRow, location);
//                getDataFromLocation(dataRow, location);
                if (returnRowLocation != null) {
                    CovidData returnRow = getDataFromDate(chosenDate, endDate, returnRowLocation);
                    if (returnRow != null) {
                        bigGroup.add(returnRow);
                    }
                }
            }
            //Choose a new metric
            System.out.println("\nEnter one of the number below to calculate an additional metric:");
            System.out.println("1. Total positive cases. ");
            System.out.println("2. Total deaths. ");
            System.out.println("3. Total people vaccinated. ");
            int metricOption;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Please enter the number in those 3 options to choose: ");
                metricOption = input.nextInt();
            } while (metricOption != 1 && metricOption != 2 && metricOption != 3);

            putDataInGroup(bigGroup, groupsSplittedArr, chosenDate, metricOption);

        } else {
            //Just in case sth else happens
            ProcessData(pathToNewCSV, location, chosenDate, dayAway, option);
        }
    }

    ////////////////////////////////////////////////////////////////
    //SPLITDAY, SPLITGROUP FUNCTIONS
    public static void putDataInGroup(ArrayList<CovidData> bigGroup, ArrayList<Integer> groupsSplittedArr,
                                      String originalStartDate, int metricOption) throws ParseException {
        ArrayList<CovidData> groupsDaysArr = new ArrayList<CovidData>();
        ArrayList<ArrayList<CovidData>> groupsDaysArrFinal = new ArrayList<ArrayList<CovidData>>();

        int count = 1;
        int k = 0;
        String osd = originalStartDate;

        for (int outerInd = 0; outerInd < groupsSplittedArr.size(); outerInd++) {
            System.out.println("\n--- GROUP " + count + " ---");
            //plusDay : the number of DAYS that need to be added to the StartDay
            int plusDay = groupsSplittedArr.get(outerInd);
            //Get the new endDay (from the plusDay)
            String newEndDate = displayStartEndDate(osd, plusDay);
            //Get the new temp dayRangeStr
            ArrayList<String> dayRangeStr = convertDateToString(getDatesBetween(osd, newEndDate));
            System.out.println(dayRangeStr);

            //doesn't allow duplicate value
            HashSet<Long> metricsArr = new HashSet<Long>();

            for (int innerInd = 0; innerInd < bigGroup.size(); innerInd++) {
                CovidData dateElement = bigGroup.get(k);
                //Accept dataElement if it is in the Range of osd-oed
                CovidData dateData = getDataFromDate(osd, newEndDate, dateElement);
                if (dateData != null) {
                    groupsDaysArr.add(dateData);
                }
                k += 1;
            }
            k = 0;
            osd = newEndDate;

            for (CovidData gda : groupsDaysArr) {
                //must not null to be able to convert to String
                if (gda != null) {
                    System.out.println(gda.toPrintString());
                } else {
                    ;
                }
            }
            //Display Chosen Metric Result
            metricDisplay(metricOption, groupsDaysArr, metricsArr);

            Collections.addAll(groupsDaysArrFinal, groupsDaysArr);
            groupsDaysArr.clear();
            count += 1;
        }
//            System.out.println(groupsDaysArrFinal);
    }


    public static ArrayList<Integer> splitGroupsEqually(int x, int n) {
        //x: dayAway
        //n: groups
        ArrayList<Integer> groupsSplittedArr = new ArrayList<Integer>();
        int g;
        // If x % n == 0 then the minimum
        // difference is 0 and all
        // numbers are x / n
        if (x % n == 0) {
            for (int i = 0; i < n; i++) {
                g = x / n;
//                        System.out.print((x / n) + " ");
                groupsSplittedArr.add(g);
            }
        } else {
            // upto n-(x % n) the values will be x / n
            // after that the values will be x / n + 1
            int num1 = n - (x % n);
            int num2 = x / n;

            for (int i = 0; i < n; i++) {
                if (i >= num1) {
                    g = num2 + 1;
//                            System.out.print((num2 + 1) + " ");
                    groupsSplittedArr.add(g);
                } else {
                    g = num2;
//                            System.out.print(num2 + " ");
                    groupsSplittedArr.add(g);
                }
            }
        }
        return groupsSplittedArr;
    }

    public static ArrayList<Integer> splitEqualDays(int x, int d) {
        //x: dayAway
        //n: groups
        //d: daysPerGroup
        ArrayList<Integer> groupsSplittedArr = new ArrayList<Integer>();
        if (d==1) {
            for (int i = 0; i <= x; i++) {
                groupsSplittedArr.add(d);
            }
        } else {
            for (int n = 2; n < x; n++) {
                // If x % n == 0 then the minimum
                // difference is 0 and all
                // numbers are x / n
                if (x % d == 0) {
                    System.out.println("You can divide into " + (x / d) + " groups");
                    for (int i = 0; i < x/d; i++) {
                        groupsSplittedArr.add(x / (x/d));
                    }
                    break;

                } else if (x % n == 0) {
                    for (int i = 0; i < n; i++) {
//                                System.out.print((x / n) + " ");
                        groupsSplittedArr.add(x/n);
                    }

                } else {
                    System.out.println("It is not possible to divide equally!!!");
                    break;
                }
            }
        }
//                System.out.println(groupsSplittedArr);
        return groupsSplittedArr;
    }

    ////////////////////////////////////////////////////////////////
    //Handle TIME-related functions
    public static String displayStartEndDate(String chosenDate, int dayAway) {
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

        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, dayAway);
        //Day after adding the days to the input date
        String endDate = sdf.format(c.getTime());
        //Displaying the new Date after addition of Days
        System.out.println("Date after Addition: " + endDate);
        return endDate;
    }

    public static ArrayList<Date> getDatesBetween(String startDate, String endDate) throws ParseException {
        //convert String to Date
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Date sDate = sdf.parse(startDate);
        Date eDate = sdf.parse(endDate);

        ArrayList<Date> datesInRange = new ArrayList<>();
        Calendar c = new GregorianCalendar();
        c.setTime(sDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(eDate);

        while (c.before((endCalendar))) {
            Date result = c.getTime();
            datesInRange.add(result);
            c.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    public static ArrayList<String> convertDateToString(ArrayList<Date> ArrList) {
        DateFormat df = new SimpleDateFormat("M/d/yyyy");
        ArrayList<String> dateToStrArr = new ArrayList<String>();

        for (int i = 0; i < ArrList.size(); i++) {
            Date DateInArr = ArrList.get(i);
            String dateToString = df.format(DateInArr);
            dateToStrArr.add(dateToString);
        }
        return dateToStrArr;
    }

    ////////////////////////////////////////////////////////////////
    //FUNCTIONS: Filter Data according to Date, Location
    public static CovidData getDataFromDate(String chosenDate, String endDate, CovidData dataRow) throws ParseException {
        ArrayList<Date> datesInRange = new ArrayList<Date>();
        ArrayList<String> datesInRangeStr = new ArrayList<String>();
        CovidData locationData = null;

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


    public static CovidData getDataFromLocation(CovidData dataRow,
                                                String inputlocation) {
        if (dataRow.getContinent().equalsIgnoreCase(inputlocation) == false
                && dataRow.getLocation().equalsIgnoreCase(inputlocation) == false) {
            dataRow = null;
        }
//                if (dataRow != null) {
//                    System.out.println(dataRow);
//                }
        return dataRow;
    }


    //II.1 Calculate new metrics: TOTAL new cases/new deaths/new vaccinated people in a group.
    //for both 3 grouping options
    public static void metricDisplay(int metricOption, ArrayList<CovidData> groupsDaysArr,
                                     HashSet<Long> metricsArr) {
        switch (metricOption) {
            //option 1: totalNewCases
            case 1:
                long totalNewCases = totalNewCases(groupsDaysArr);
                metricsArr.add(totalNewCases);
                for (long l : metricsArr) {
                    System.out.println("__TOTAL NEW CASES__: " + totalNewCases );
                }
                break;

            //option 2: totalNewDeaths
            case 2:
                long totalNewDeaths = totalNewDeaths(groupsDaysArr);
                metricsArr.add(totalNewDeaths);
                for (long l : metricsArr) {
                    System.out.println("__TOTAL NEW DEATHS__: " + totalNewDeaths );
                }
                break;

            //option 3: totalNewVaccinated
            case 3:
                long totalNewVaccinated = totalNewVaccinated(groupsDaysArr);
                metricsArr.add(totalNewVaccinated);
                for (long l : metricsArr) {
                    System.out.println("__TOTAL NEW VACCINATED__: " + totalNewVaccinated );
                }
                break;
        }
    }

    //os new data for each date
    public static long totalNewCases(ArrayList<CovidData> groupDataArr) {
        long sum = 0;
        for (int i = 0; i < groupDataArr.size(); i++) {
            sum += groupDataArr.get(i).getNew_cases();
        }
        return sum;
    }

    //is new data for each date
    public static long totalNewDeaths(ArrayList<CovidData> groupDataArr) {
        long sum = 0;
        for (int i = 0; i < groupDataArr.size(); i++) {
            sum += groupDataArr.get(i).getNew_deaths();
        }
        return sum;
    }

    //is an accumulated values up to a date
    public static long totalNewVaccinated(ArrayList<CovidData> groupDataArr) {
        long temp_sum;
        long sum = 0;
        if (groupDataArr.size() > 1) {
            for (int i = 0; i < groupDataArr.size()-1; i++) {
                long vaccinated1 = groupDataArr.get(i).getPeople_vaccinated();
                long vaccinated2 = groupDataArr.get(i+1).getPeople_vaccinated();
                temp_sum = vaccinated2 - vaccinated1;
                sum += temp_sum;
            }
        } else {
            sum += groupDataArr.get(0).getPeople_vaccinated();
        }
        return sum;
    }

    public static long upToNewCases(ArrayList<CovidData> groupDataArr) {
        long upTo = 0;
        // get the last element minus the first element of the array list to get the upto value
        upTo = groupDataArr.get(groupDataArr.size() - 1).getNew_cases() - groupDataArr.get(0).getNew_cases();
        return upTo;
    }

    public static long upToNewDeaths(ArrayList<CovidData> groupDataArr) {
        long upTo = 0;
        // get the last element minus the first element of the array list to get the upto value
        upTo = groupDataArr.get(groupDataArr.size() - 1).getNew_deaths() - groupDataArr.get(0).getNew_deaths();
        return upTo;
    }

    public static long upToNewVaccinated(ArrayList<CovidData> groupDataArr) {
        long upTo = 0;
        // get the last element minus the first element of the array list to get the upto value
        upTo = groupDataArr.get(groupDataArr.size() - 1).getPeople_vaccinated() - groupDataArr.get(0).getPeople_vaccinated();
        return upTo;
    }



    // Miscellanious
    public static void replaceNullCsv(String pathToCSV,
                                      String pathToNewCSV) {
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

class TabularDisplay {
    JFrame f;
    JTable j;
    TabularDisplay()
    {

        f = new JFrame();

        // Frame Title
        f.setTitle("Tabular Display");


        String[][] data = {
                { "Kundan Kumar Jha", "4031" },
                { "Anand Jha", "6014" }
        };


        String[] columnNames = { "Range", "Value" };


        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);


        JScrollPane sp = new JScrollPane(j);
        f.add(sp);

        f.setSize(500, 200);
        f.setVisible(true);
    }

    // Driver  method
    public static void main(String[] args)
    {
        new TabularDisplay();
    }
}

