package Model;

public class Price {
    private String ID;
    private String timeFrom;
    private String timeTo;
    private String price;

    public Price() {
    }

    public Price(String ID, String dateFrom, String dateTo, String price) {
        this.ID = ID;
        this.timeFrom = dateFrom;
        this.timeTo = dateTo;
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String dateFrom) {
        this.timeFrom = dateFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String dateTo) {
        this.timeTo = dateTo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
