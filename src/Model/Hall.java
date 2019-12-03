package Model;

public class Hall {
    private String ID;
    private String name;
    private String rowsCount;
    private String placesCount;

    public Hall() {
    }

    public Hall(String ID, String name, String rowsCount, String placesCount) {
        this.ID = ID;
        this.name = name;
        this.rowsCount = rowsCount;
        this.placesCount = placesCount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(String rowsCount) {
        this.rowsCount = rowsCount;
    }

    public String getPlacesCount() {
        return placesCount;
    }

    public void setPlacesCount(String placesCount) {
        this.placesCount = placesCount;
    }
}
