import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadInclusiveDateRange {

    public static void main(String[] args) throws ParseException {
        // Ask for user inputs
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the name of either desired location (COUNTRY) or CONTINENT: ");
        String nameLocation = scanner.nextLine();
        // set date format that match CSV file
        System.out.println("Please enter your desired start date in format dd/MM/yyyy: ");
        String startDate = scanner.next();
        System.out.println("Please enter your desired end date in format dd/MM/yyyy: ");
        String endDate = scanner.next();
        
        List<CovidData> dataList = getDataFromCSV("Data/covid-data.csv", nameLocation, startDate, endDate);

        for (CovidData c : dataList) {
            System.out.println(c);
        }

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

            CovidData dataRage = new CovidData(data[1], data[2], data[3]);

            //Deal with 1 row of data
//                getDataFromLocation(dataRage, location);
                CovidData returnRow = getInclusiveRageDateFromLocation(nameLocation, startDate, endDate, dataRage);
                    System.out.println(returnRow.toString());

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

}

