package Covid19;

public class CovidData {
    // private fields to protect our original data objects
    private String iso_code;
    private String continent;
    private String location;
    private String date;
    private long new_cases;
    private long new_deaths;
    private long people_vaccinated;
    private long population;

    // Parameterized Constructor
    public CovidData(String iso_code, String continent, String location, String date, long new_cases, long new_deaths, long people_vaccinated, long population) {
        this.iso_code = iso_code;
        this.continent = continent;
        this.location = location;
        this.date = date;
        this.new_cases = new_cases;
        this.new_deaths = new_deaths;
        this.people_vaccinated = people_vaccinated;
        this.population = population;
    }

    // getter methods for the CSV columns
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

    public long getNew_cases() {
        return this.new_cases;
    }

    public long getNew_deaths() {
        return this.new_deaths;
    }

    public long getPeople_vaccinated() {
        return this.people_vaccinated;
    }

    public long getPopulation() {
        return this.population;
    }

    // print out the CSV data
    public String toPrintString() {
        return "COVID-DATA [iso_code=" + this.iso_code + ", continent=" + this.continent + ", location=" + this.location +
                ", date=" + this.date + ", new_cases=" + this.new_cases + ", new_deaths=" + this.new_deaths +
                ", people_vaccinated=" + this.people_vaccinated + ", " + "population=" + this.population + "]";
    }
}



