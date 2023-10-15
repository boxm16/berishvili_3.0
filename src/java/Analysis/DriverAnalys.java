package Analysis;

import BasicModels.TripVoucher;
import java.util.Date;
import java.util.TreeMap;

public class DriverAnalys {

    private String driverName;
    private String routeNumber;
    private String dateStamp;

    private TreeMap<Date, TripVoucher> tripVouchers;

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public TreeMap<Date, TripVoucher> getTripVouchers() {
        return tripVouchers;
    }

    public void setTripVouchers(TreeMap<Date, TripVoucher> tripVouchers) {
        this.tripVouchers = tripVouchers;
    }

    public void addTripVoucher(Date date, TripVoucher tripVoucher) {
        this.tripVouchers.put(date, tripVoucher);
    }
}
