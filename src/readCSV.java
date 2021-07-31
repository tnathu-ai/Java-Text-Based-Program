import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class readCSV {

    /**
     * Read in a line and process it as a CSV header.
     *
     * @param bufferedReader
     *            Where to read the header from. It needs to be closed by the caller.
     * @return Array of header column names or null on error.
     * @try ParseException
     *             Thrown on any parsing problems. If parseError is not null then the error will be added there and an
     *             exception should not be thrown.
     * @catchIOException
     *             If there are any IO exceptions thrown when reading.
     */
    public static void main(String[] args) {
        String file = "Data/covid-data.csv";
        String headers;
        String line;
        try (BufferedReader br =
                     new BufferedReader(new FileReader(file))) {
            headers = br.readLine();
            ArrayList<CovidData> covidData = new ArrayList<>();
            while((line = br.readLine()) != null){
                // use split(“,”) method to split row and separate each field.
                String[] data = line.split(",");
                System.out.println(line);

                // return an array which will be column wise data
//                System.out.println(data[3]);
            }
        } catch (Exception e){
            System.out.println(e);
        }

}
}