import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class GetRageOfStartDateAndEndDate {


    public static void main(String[] args) throws ParseException {

//        // Ask for user inputs
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the name of either desired location or continent: ");
//        String name = scanner.nextLine();
//        // set date format that match CSV file
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        System.out.println("Enter your desired start date in format dd/MM/yyyy: ");
//        Date startDate = format.parse(scanner.next());
//        System.out.println("Enter your desired end date in format dd/MM/yyyy: ");
//        Date endDate = format.parse(scanner.next());
////        String endDate = scanner.next();


        String fileName = "Data/covid-data.csv";
        File file = new File(fileName);

        // Create list of list of String - 2 dimensional array of strings
        List<List<String>> lines = new ArrayList<>();
        Scanner covidDataStream;

        try {
            covidDataStream = new Scanner(file);

            while (covidDataStream.hasNext()) {
                String line = covidDataStream.next();
                String[] values = line.split(",");

//                // extract the values.
//                // trim extra spaces after splitting the columns
//                String location = values[1].trim();
//                Date date = format.parse(values[4].trim());
//                Integer population = Integer.parseInt(values[7].trim());


                // Adds the currently parsed line to the 2-dimensional string array
                // Convert String[] into List
                lines.add(Arrays.asList(values));
            }

            covidDataStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Iterate through the 2-dimensional array using for-each loop
        int rowNumber = 1;
        for (List<String> line : lines) {
            int columnNumber = 1;
            for (String value : line) {
//                String s = lines.get(Integer.parseInt(value)).get(1);
                List<List<String>> sub = lines.subList(0,1);
//                    System.out.println("location or country name: " + sub);
//                    s = s + 1;
                System.out.println("Row: " + rowNumber + ", Column: " + columnNumber + ", header: " + value + ", Specific value: " + lines.get(rowNumber).get(columnNumber));
                columnNumber++;
                }
            rowNumber++;
            }
        }
    }

