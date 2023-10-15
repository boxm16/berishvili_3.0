package BasicModels;

import Service.Converter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TripVoucher {

    private String number;
    private short baseNumber;
    private String busNumber;
    private String busType;
    private String driverNumber;
    private String driverName;

    private LocalDateTime baseLeavingTimeScheduled;
    private LocalDateTime baseLeavingTimeActual;
    private LocalDateTime baseLeavingTimeRedacted;

    private LocalDateTime baseReturnTimeScheduled;
    private LocalDateTime baseReturnTimeActual;
    private LocalDateTime baseReturnTimeRedacted;

    private ArrayList<TripPeriod> tripPeriods;

    private String shift;
    private int tripPeriodsScheduledTotal;
    private int tripPeriodsActualTotal;
    private float kilometrageScheduled;
    private float kilometrageActual;

    private String note;

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

    public LocalDateTime getBaseLeavingTimeActual() {
        return baseLeavingTimeActual;
    }

    public void setBaseLeavingTimeActual(LocalDateTime baseLeavingTimeActual) {
        this.baseLeavingTimeActual = baseLeavingTimeActual;
    }

    public LocalDateTime getBaseLeavingTimeRedacted() {
        return baseLeavingTimeRedacted;
    }

    public void setBaseLeavingTimeRedacted(LocalDateTime baseLeavingTimeRedacted) {
        this.baseLeavingTimeRedacted = baseLeavingTimeRedacted;
    }

    public LocalDateTime getBaseReturnTimeScheduled() {
        return baseReturnTimeScheduled;
    }

    public void setBaseReturnTimeScheduled(LocalDateTime baseReturnTimeScheduled) {
        this.baseReturnTimeScheduled = baseReturnTimeScheduled;
    }

    public LocalDateTime getBaseReturnTimeActual() {
        return baseReturnTimeActual;
    }

    public void setBaseReturnTimeActual(LocalDateTime baseReturnTimeActual) {
        this.baseReturnTimeActual = baseReturnTimeActual;
    }

    public LocalDateTime getBaseReturnTimeRedacted() {
        return baseReturnTimeRedacted;
    }

    public void setBaseReturnTimeRedacted(LocalDateTime baseReturnTimeRedacted) {
        this.baseReturnTimeRedacted = baseReturnTimeRedacted;
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

    public int getTripPeriodsScheduledTotal() {
        return tripPeriodsScheduledTotal;
    }

    public void setTripPeriodsScheduledTotal(int tripPeriodsScheduledTotal) {
        this.tripPeriodsScheduledTotal = tripPeriodsScheduledTotal;
    }

    public int getTripPeriodsActualTotal() {
        return tripPeriodsActualTotal;
    }

    public void setTripPeriodsActualTotal(int tripPeriodsActualTotal) {
        this.tripPeriodsActualTotal = tripPeriodsActualTotal;
    }

    public float getKilometrageScheduled() {
        return kilometrageScheduled;
    }

    public void setKilometrageScheduled(float kilometrageScheduled) {
        this.kilometrageScheduled = kilometrageScheduled;
    }

    public float getKilometrageActual() {
        return kilometrageActual;
    }

    public void setKilometrageActual(float kilometrageActual) {
        this.kilometrageActual = kilometrageActual;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
//---------------------------------------------

    public String getBaseLeavingTimeActualString() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return this.baseLeavingTimeActual.format(dateTimeFormatter);
    }

    public String getBaseReturnTimeActualString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return this.baseReturnTimeActual.format(dateTimeFormatter);
    }

    public String getBaseReturnTimeVerifiedString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (this.baseReturnTimeRedacted == null) {
            return this.baseReturnTimeActual.format(dateTimeFormatter);
        } else {
            return this.baseReturnTimeRedacted.format(dateTimeFormatter);
        }
    }

    public String getWorkedHoursVerifiedString() {
        LocalDateTime baseReturnTimeVerified;
        if (this.baseReturnTimeRedacted == null) {
            baseReturnTimeVerified = this.baseReturnTimeActual;
        } else {
            baseReturnTimeVerified = baseReturnTimeRedacted;
        }
        Duration workingHours = Duration.between(this.baseLeavingTimeActual, baseReturnTimeVerified);
        Converter converter = new Converter();
        return converter.convertDurationToString(workingHours);

    }

}
