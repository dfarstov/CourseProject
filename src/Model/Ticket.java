package Model;

public class Ticket {
    private String idSeance;
    private String filmName;
    private String time;
    private String row;
    private String place;
    private String price;
    private String userName;

    public Ticket() {
    }

    public Ticket(String idSeance, String filmName, String time, String row, String place, String price, String userName) {
        this.idSeance = idSeance;
        this.filmName = filmName;
        this.time = time;
        this.row = row;
        this.place = place;
        this.price = price;
        this.userName = userName;
    }

    public String getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(String idSeance) {
        this.idSeance = idSeance;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
