import java.util.Date;


public class CovidData {
    // Protected fields
    protected String iso_code;
    protected String continent;
    protected String location;
    protected Date date;
    protected int new_cases;
    protected int new_deaths;
    protected int people_vaccinated;
    protected int population;

    // Setting up 3 constructors
    // Parameterized Constructor 1
    public CovidData() {
        this.iso_code = iso_code;
        this.continent = continent;
        this.location = location;
        this.date = date;
        this.new_cases = new_cases;
        this.new_deaths = new_deaths;
        this.people_vaccinated = people_vaccinated;
        this.population = population;
    }
    // I thought that we can have multiple constructors???
    // Parameterized Constructor 2
//    public CovidData(String continent, Date date) {
//        this(continent, date);            // calling parameterized Constructor
//    }
//    // Parameterized Constructor 3
//    public  CovidData(String location, Date date) {
//        this(location, date); // calling parameterized Constructor
//    }


    public String getIso_code() {
        return this.iso_code;
    }

    public String getContinent() {
        return this.continent;
    }

    public void setContinent() {
        this.continent = continent;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = this.location;
    }

    public Date getDate() {
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
