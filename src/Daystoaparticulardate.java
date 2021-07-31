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

public class Daystoaparticulardate {
    public static void main(String[] args) throw IOEXCEPTION {
        string CSV = "Data/covid-data.csv";
        string newCSV = "Data/covid-data-zero.csv";
        replaceCSV (String CSV, String newCSV);
        scanner input = new scanner(System.in);
        System.out.print("Enter a location: ");
        String location = input.nextLine();



        public static void replaceCSV (String CSV, String newCSV) {
            try {
                BufferedReader csvReader = new BufferedReader(new FileReader(CSV));
                BufferedWriter csvWriter = new BufferedWriter(new FileWriter(newCSV));
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


