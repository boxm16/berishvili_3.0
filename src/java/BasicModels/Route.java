package BasicModels;

import java.util.Date;
import java.util.TreeMap;

public class Route {

    private int index;
    private String number;
    private String aPoint;
    private String bPoint;
    private String scheme;

    private TreeMap<Date, Day> days;

    public Route() {
        this.days = new TreeMap();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public TreeMap<Date, Day> getDays() {
        return days;
    }

    public void setDays(TreeMap<Date, Day> days) {
        this.days = days;
    }

}
