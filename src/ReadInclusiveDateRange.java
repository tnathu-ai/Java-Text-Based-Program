import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadInclusiveDateRange {

    public static void main(String[] args) throws ParseException {

        String pathToCSV = "Data/covid-data.csv";
        String pathToNewCSV = "Data/covid-data-zero.csv";
        replaceNullCsv(pathToCSV, pathToNewCSV);
        // Ask for user inputs
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the name of either a desired location (country) or continent: ");
        String nameLocation = scanner.nextLine();
        // set date format that match CSV file
        System.out.println("Please enter your desired start date in format MM/dd/yyyy: ");
        String startDate = scanner.next();
        System.out.println("Please enter your desired end date in format MM/dd/yyyy: ");
        String endDate = scanner.next();
        
        getDataFromCSV(pathToNewCSV, nameLocation, startDate, endDate);
    }


public static List<CovidData> getDataFromCSV(String file, String nameLocation, String startDate, String endDate) throws ParseException {
    List<CovidData> dataList = new ArrayList<>();

    // Declaring a variable to lookup for
    // number of lines in the CSV file
    String headers;
    String line;
//        ArrayList<String> covidData = null;

    try (BufferedReader br =  new BufferedReader(new FileReader(file))) {
        headers = br.readLine();
        while ((line = br.readLine()) != null) {
            // use split(“,”) method to split row and separate each field.
            String[] data = line.split(",");

            CovidData dataRage = new CovidData(data[0], data[1], data[2],
                    data[3], Long.parseLong(data[4]), Long.parseLong(data[5]), Long.parseLong(data[6]),
                    Long.parseLong(data[7]));

            //Deal with 1 row of data
//                getDataFromLocation(dataRage, location);
                CovidData returnRow = getInclusiveRageDateFromLocation(nameLocation, startDate, endDate, dataRage);
                    System.out.println(returnRow.toPrintString());

            // return an array which will be column data
//            System.out.println("Continent: " + data[1] + " Location: " + data[2] + " Rage of Dates: " + data[3]);
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return dataList;

}

    public static CovidData getInclusiveRageDateFromLocation (String nameLocation, String startDate, String endDate, CovidData dataRage) throws ParseException {
        ArrayList<Date> datesInRange = new ArrayList<Date>();
        ArrayList<String> datesInRangeStr = new ArrayList<String>();
        CovidData locationData = null;
        if (dataRage == null) {
            dataRage = null;

        } else if (dataRage != null &&
                (dataRage.getContinent().equalsIgnoreCase(nameLocation) ||
                        dataRage.getLocation().equalsIgnoreCase(nameLocation))) {
            datesInRange = getDatesBetween(startDate, endDate);
            datesInRangeStr = CovidData.convertDateToString(datesInRange);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date sd = df.parse(startDate);
            Date ed = df.parse(endDate);

            for (Date d : datesInRange) {
                Date inputDate = d;

                if(!sd.after(d) && !ed.before(d)) {
                    locationData = dataRage;
                    break;
                } else {
                    locationData = null;
                }
            }
        }
        return dataRage;
    }

    public static ArrayList<Date> getDatesBetween (String startDate, String endDate) throws ParseException {
        //convert String to Date
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
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

