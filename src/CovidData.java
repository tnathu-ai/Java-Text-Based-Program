import java.util.Date;


public class CovidData {
    protected String iso_code;
    protected String continent;
    protected String location;
    protected Date date;
    protected int new_cases;
    protected int new_deaths;
    protected int people_vaccinated;
    protected int population;

    public CovidData(String iso_code, String location, Date date, int new_cases, int new_deaths, int people_vaccinated, int population) {
        this.iso_code = iso_code;
        this.date = date;
        this.new_cases = new_cases;
        this.new_deaths = new_deaths;
        this.people_vaccinated = people_vaccinated;
        this.population = population;
    }


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

    public void setLocation() {
        this.location = location;
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

    @Override
    public String toString() {
        return "Covid Data [iso_code=" + iso_code + ", date=" + date + ", new_cases=" + new_cases +
                ", new_deaths=" + new_deaths + ", people_vaccinated=" + people_vaccinated + ", population=" + population + "]";
    }
}
