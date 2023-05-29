package dao;

public class Location {
    final private int loc;
    final private String locName;
    final private String locZn;

    public Location(int loc, String locName, String locZn) {
        this.loc = loc;
        this.locName = locName;
        this.locZn = locZn;
    }

    public int getLoc() {
        return loc;
    }

    public String getLocName() {
        return locName;
    }

    public String getLocZn() {
        return locZn;
    }
}
