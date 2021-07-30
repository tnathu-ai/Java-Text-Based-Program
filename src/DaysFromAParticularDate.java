import java.io.*;
import java.text.DateFormat;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.UnaryOperator;

public class DaysFromAParticularDate {
    public static void main(String args[]) throws IOException, ParseException {
        String pathToCSV = "Data/covid-data.csv";
        String pathToNewCSV = "Data/covid-data-zero.csv";
        replaceNullCsv(pathToCSV, pathToNewCSV);

        Scanner input = new Scanner(System.in);
        System.out.print("Enter the Continent or Country: ");
        String location = input.nextLine();
        getDataFromLocation(pathToNewCSV, location);
        TimeRange(pathToCSV, pathToNewCSV, location);
    }

    public static void TimeRange(String pathToCSV, String pathToNewCSV, String location) throws ParseException,
            IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Choose a date you want in this format 'M/d/yyyy'(9/2/2020 or 9/20/2020): ");
        String chosenDate = input.nextLine();

        System.out.print("Enter the number of days that are away from the date you chose: ");
        int dayAway = input.nextInt();

        System.out.println("There are 3 ways you can choose to group your" +
                " days: ");
        System.out.println("1. No grouping: each day is a separate group.");
        System.out.println("2. Choose the number of groups that the number of days will be divided equally into each " +
                "group");
        System.out.println("3. Choose the number of days in each divided group (Note: The program will choose the " +
                "most optimal number for grouping equally");
        System.out.println();
        int option;
        do {
            System.out.print("Please enter the number in those 3 options to choose: ");
            option = input.nextInt();
        } while (option != 1 && option != 2 && option != 3);


        if (option == 1) {
            String endDate = displayStartEndDate(chosenDate, dayAway);
            System.out.println(getDatesBetween(chosenDate, endDate));
            ArrayList<Date> getDatesBetweenArr = new ArrayList<Date>();
            getDatesBetweenArr = getDatesBetween(chosenDate, endDate);
            System.out.println(convertDateToString(getDatesBetweenArr));

        } else if (option == 2) {
            int groups;
            do {
                System.out.print("Enter the number of groups (smaller than the number of days): ");
                groups = input.nextInt();

                ArrayList<Integer> groupsArr = new ArrayList<Integer>();
                ArrayList<Date> getDatesBetweenArr = new ArrayList<Date>();
                ArrayList<ArrayList<String>> groupsDaysArr = new ArrayList<ArrayList<String>>();
                ArrayList<ArrayList<String>> groupsDaysArrFinal = new ArrayList<ArrayList<String>>();
                ArrayList<ArrayList<String>> dateDataArr = new ArrayList<ArrayList<String>>();

                String endDate = displayStartEndDate(chosenDate, dayAway);
                getDatesBetweenArr = getDatesBetween(chosenDate, endDate);
                groupsArr = splitGroupsEqually(dayAway, groups);

                //get the data of the dates user wants
                dateDataArr = getDataFromDate(chosenDate, endDate, pathToNewCSV, location);
//                System.out.println(groupsDaysArr);
                int count = 1;
                int k = 0;

                    for (int i =0; i<groupsArr.size(); i++) {
                        System.out.println("Group " + count);

                        for (int j = 0; j < groupsArr.get(i); j++) {
                            ArrayList<String> dateElement = new ArrayList<>();
                            dateElement = dateDataArr.get(k);

                            //add dates to 2d arraylist
//                            for (String text: dateElement) {
//                                groupsDaysArr.get(j).add(text);
//                            }
                            Collections.addAll(groupsDaysArr, dateElement);
                            k+=1;
                        }
                        System.out.println(groupsDaysArr);
                        groupsDaysArrFinal.addAll(groupsDaysArr);
                        groupsDaysArr.clear();
//                        System.out.println(groupsDaysArrFinal);
                        count += 1;
                    }
            } while (groups > dayAway);

        } else if (option == 3) {
            System.out.print("Enter the number of days in each divided group: ");
            int daysPerGroup = input.nextInt();
            splitEqualDays(dayAway, daysPerGroup);

        } else {
            TimeRange(pathToCSV, pathToNewCSV, location);
        }
    }

    public static ArrayList<Integer> splitGroupsEqually(int x, int n) {
        //x: dayAway
        //n: groups
        ArrayList<Integer> groupsArr = new ArrayList<Integer>();
        int g;
        // If x % n == 0 then the minimum
        // difference is 0 and all
        // numbers are x / n
        if (x % n == 0) {
            for(int i=0; i<n; i++) {
                g = x/n;
                System.out.print((x / n) + " ");
                groupsArr.add(g);
            }
        } else {
            // upto n-(x % n) the values will be x / n
            // after that the values will be x / n + 1
            int num1 = n - (x % n);
            int num2 = x/n;

            for(int i=0; i<n; i++) {
                if (i >= num1) {
                    g = num2 + 1;
                    System.out.print((num2 + 1) + " ");
                    groupsArr.add(g);
                }
                else {
                    g = num2;
                    System.out.print(num2 + " ");
                    groupsArr.add(g);
                }
            }
        }
        return groupsArr;
    }

    public static void splitEqualDays(int x, int d) {
        //x: dayAway
        //n: groups
        //d: daysPerGroup
        for (int n = 2; n < x; n++) {
            // If x % n == 0 then the minimum
            // difference is 0 and all
            // numbers are x / n
            if (x % d == 0) {
                System.out.print("You can divide into " + (x / d) + " groups");
                break;
            } else if (x % n == 0) {
                for (int i = 0; i < n; i++) {
                    System.out.print((x / n) + " ");
                }
            } else {
                System.out.println("It is not possible to divide equally!!!");
            }
        }
    }



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

        for (int i = 0; i<ArrList.size(); i++) {
            Date DateInArr = ArrList.get(i);
            String dateToString = df.format(DateInArr);
            dateToStrArr.add(dateToString);
        }
        return dateToStrArr;
    }

    public static ArrayList<ArrayList<String>> getDataFromDate(String chosenDate, String endDate, String pathToNewCSV
            , String location) throws ParseException, IOException {
        ArrayList<ArrayList<String>> dateDataArr = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> locationData = new ArrayList<ArrayList<String>>();
        ArrayList<Date> datesInRange = new ArrayList<Date>();
        ArrayList<String> datesInRangeStr = new ArrayList<String>();
//        String datesInRangeStr;

        locationData = getDataFromLocation(pathToNewCSV, location);
        datesInRange = getDatesBetween(chosenDate, endDate);
        datesInRangeStr = convertDateToString(datesInRange);

        for (int i = 0; i < datesInRangeStr.size(); i++) {
            String inputDate = datesInRangeStr.get(i);
            if (locationData.get(i).contains(inputDate)) {
                //store the data which already filtered according to the
                // location to a new array 'locationData'.
                //Convert databyRow.get(i) into Array
                String[] rowData = locationData.get(i).toArray(new String[1]);

                //Allocate a new space for a new data
                dateDataArr.add(new ArrayList<>());
                //Cuz Arrays.asList only accepts Array
                dateDataArr.get(i).addAll(Arrays.asList(rowData));
            } else {
                continue;
            }
        }
        return dateDataArr;
    }

    public static ArrayList<ArrayList<String>> getDataFromLocation(String pathToNewCSV, String location) throws IOException {
        ArrayList<ArrayList<String>> dataByRow = readCSV(pathToNewCSV);
        ArrayList<ArrayList<String>> locationData =
                new ArrayList<ArrayList<String>>();

        // add the data (related to the input location) into
        // a new 'locationData' array
        //check each object array in the dataByRow array if it has the
        // location the user enter
        for (int i = 0; i < dataByRow.size(); i++) {
            if (dataByRow.get(i).contains(location)) {
                //store the data which already filtered according to the
                // location to a new array 'locationData'.
                //Convert databyRow.get(i) into Array
                String[] rowData = dataByRow.get(i).toArray(new String[1]);

                //Allocate a new space for a new data
                locationData.add(new ArrayList<>());
                //Cuz Arrays.asList only accepts Array
                locationData.get(i).addAll(Arrays.asList(rowData));
            } else {
                continue;
            }
        }
        return locationData;
    }


    public static ArrayList<ArrayList<String>> readCSV(String pathToNewCSV) throws IOException {
//        try {
            BufferedReader csvReader =
                    new BufferedReader(new FileReader(pathToNewCSV));
            String row = "";

            //put header in an array
            //readLine the header to skip the 1st line so that the data will
            // be in the array of their own
            String header = csvReader.readLine();
            String[] headerArray = header.split(",");
            System.out.println(Arrays.toString(headerArray));

            //use 2D array to easily choose data
            //dataByRow[][]
            ArrayList<ArrayList<String>> dataByRow =
                    new ArrayList<ArrayList<String>>();
            int i = 0;

            //read line by line till the end
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);

                dataByRow.add(new ArrayList<String>());
                dataByRow.get(i).addAll(Arrays.asList(data));
                i++;

                //test reading file
//                for (String d : data) {
//                    System.out.println(d + "\t");
//                }
//                System.out.println();
            }
//            System.out.println(dataByRow);
            return dataByRow;
//            csvReader.close();

//        } catch (Exception e) {
//            //pinpoint the error in the code
//            e.printStackTrace();
//        }
    }

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
                for (String s: al) {
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




