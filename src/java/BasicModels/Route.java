package BasicModels;

import java.util.TreeMap;

public class Route {

    private String number;
    private String aPoint;
    private String bPoint;
    private String scheme;

    private TreeMap<String, Day> days;

    public Route() {
        this.days = new TreeMap();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getaPoint() {
        return aPoint;
    }

    public void setaPoint(String aPoint) {
        this.aPoint = aPoint;
    }

    public String getbPoint() {
        return bPoint;
    }

    public void setbPoint(String bPoint) {
        this.bPoint = bPoint;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public TreeMap<String, Day> getDays() {
        return days;
    }

    public void setDays(TreeMap<String, Day> days) {
        this.days = days;
    }

}
