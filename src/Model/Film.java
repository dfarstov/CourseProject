package Model;

public class Film {
    private String name;
    private String country;
    private String date;
    private int boxOffice;
    private String time;
    private String screenWriter;
    private String producer;
    private String poster;
    private String description;

    public Film() {
    }

    public Film(String name, String country, String date, int boxOffice, String time, String screenWriter, String producer, String poster, String description) {
        this.name = name;
        this.country = country;
        this.date = date;
        this.boxOffice = boxOffice;
        this.time = time;
        this.screenWriter = screenWriter;
        this.producer = producer;
        this.poster = poster;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getDate() {
        return date;
    }

    public int getBoxOffice() {
        return boxOffice;
    }

    public String getTime() {
        return time;
    }

    public String getScreenWriter() {
        return screenWriter;
    }

    public String getProducer() {
        return producer;
    }

    public String getPoster() {
        return poster;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBoxOffice(int boxOffice) {
        this.boxOffice = boxOffice;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setScreenWriter(String screenWriter) {
        this.screenWriter = screenWriter;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
