import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CovidData {
    // private fields
    private String iso_code;
    private String continent;
    private String location;
    private String date;
    private int new_cases;
    private int new_deaths;
    private int people_vaccinated;
    private int population;

    // Setting up 2 constructors
    // Parameterized Constructor 1
    public CovidData(String iso_code, String continent, String location, String date, int new_cases, int new_deaths, int people_vaccinated, int population) {
        this.iso_code = iso_code;
        this.continent = continent;
        this.location = location;
        this.date = date;
        this.new_cases = new_cases;
        this.new_deaths = new_deaths;
        this.people_vaccinated = people_vaccinated;
        this.population = population;
    }

    // Parameterized Constructor 2
    public CovidData(String continent, String location, String date) {
        this.continent = continent;
        this.location = location;
        this.date = date;
    }

    public static ArrayList<String> convertDateToString(ArrayList<Date> ArrList) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        ArrayList<String> dateToStrArr = new ArrayList<String>();

        for (int i = 0; i < ArrList.size(); i++) {
            Date DateInArr = ArrList.get(i);
            String dateToString = df.format(DateInArr);
            dateToStrArr.add(dateToString);
        }
        return dateToStrArr;

    }

    public String getIso_code() {
        return this.iso_code;
    }

    public String getContinent() {
        return this.continent;
    }

    public String getLocation() {
        return this.location;
    }


    public String getDate() {
        return this.date;
    }


    public int getNew_cases() {
        return this.new_cases;
    }

    public int getNew_deaths() {
        return this.new_deaths;
    }

    public int getPeople_vaccinated() {
        return this.people_vaccinated;
    }

    public int getPopulation() {
        return this.population;
    }

    public String toString() {
        return "Covid Data [iso_code=" + this.iso_code + ", date=" + this.date + ", new_cases=" + this.new_cases +
                ", new_deaths=" + this.new_deaths + ", people_vaccinated=" + this.people_vaccinated + ", population=" + this.population + "]";
    }
}