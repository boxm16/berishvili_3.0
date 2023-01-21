package BasicModels;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TripVoucher {

    private String number;
    private short baseNumber;
    private String busNumber;
    private String busType;
    private String driverNumber;
    private String driverName;

    private LocalDateTime baseLeavingTimeScheduled;
    private LocalDateTime baseReturnTimeScheduled;
    private ArrayList<TripPeriod> tripPeriods;
    
    private String shift;
    private int tripPeriodsTotal;
    private float kilometrage;
    

    public TripVoucher() {
        this.tripPeriods = new ArrayList();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public short getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(short baseNumber) {
        this.baseNumber = baseNumber;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public LocalDateTime getBaseLeavingTimeScheduled() {
        return baseLeavingTimeScheduled;
    }

    public void setBaseLeavingTimeScheduled(LocalDateTime baseLeavingTimeScheduled) {
        this.baseLeavingTimeScheduled = baseLeavingTimeScheduled;
    }

    public LocalDateTime getBaseReturnTimeScheduled() {
        return baseReturnTimeScheduled;
    }

    public void setBaseReturnTimeScheduled(LocalDateTime baseReturnTimeScheduled) {
        this.baseReturnTimeScheduled = baseReturnTimeScheduled;
    }

    public ArrayList<TripPeriod> getTripPeriods() {
        return tripPeriods;
    }

    public void setTripPeriods(ArrayList<TripPeriod> tripPeriods) {
        this.tripPeriods = tripPeriods;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public int getTripPeriodsTotal() {
        return tripPeriodsTotal;
    }

    public void setTripPeriodsTotal(int tripPeriodsTotal) {
        this.tripPeriodsTotal = tripPeriodsTotal;
    }

    public float getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(float kilometrage) {
        this.kilometrage = kilometrage;
    }

   
    

}
