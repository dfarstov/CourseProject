package Model;

public class Seance {
    private String ID;
    private String filmName;
    private String hallName;
    private String date;
    private String time;

    public Seance() {
    }

    public Seance(String ID, String filmName, String hallName, String date, String time) {
        this.ID = ID;
        this.filmName = filmName;
        this.hallName = hallName;
        this.date = date;
        this.time = time;
    }

    public String getID(){
        return ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
