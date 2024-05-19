/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FirstTrips;

import Guaranty.GuarantyTrip;
import Service.Converter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Michail Sitmalidis
 */
public class FirstTrip extends GuarantyTrip {

    private LocalDateTime baseTripStartTimeScheduled;
    private LocalDateTime baseTripEndTimeScheduled;

    private LocalDateTime baseTripStartTimeActual;
    private LocalDateTime baseTripEndTimeActual;

    private String busNumber;

    private String driverNumber;
    private String driverName;

    public LocalDateTime getBaseTripStartTimeScheduled() {
        return baseTripStartTimeScheduled;
    }

    public String getBaseTripStartTimeScheduledString() {
        if (baseTripStartTimeScheduled == null) {
            return "";
        }
        return baseTripStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaseTripStartTimeScheduled(LocalDateTime baseTripStartTimeScheduled) {
        this.baseTripStartTimeScheduled = baseTripStartTimeScheduled;
    }

    public LocalDateTime getBaseTripEndTimeScheduled() {
        return baseTripEndTimeScheduled;
    }

    public String getBaseTripEndTimeScheduledString() {
        if (baseTripEndTimeScheduled == null) {
            return "";
        }
        return baseTripEndTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaseTripEndTimeScheduled(LocalDateTime baseTripEndTimeScheduled) {
        this.baseTripEndTimeScheduled = baseTripEndTimeScheduled;
    }

    public LocalDateTime getBaseTripStartTimeActual() {
        return baseTripStartTimeActual;
    }

    public String getBaseTripStartTimeActualString() {
        if (baseTripStartTimeActual == null) {
            return "";
        }
        return baseTripStartTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaseTripStartTimeActual(LocalDateTime baseTripStartTimeActual) {
        this.baseTripStartTimeActual = baseTripStartTimeActual;
    }

    public LocalDateTime getBaseTripEndTimeActual() {
        return baseTripEndTimeActual;
    }

    public String getBaseTripEndTimeActualString() {
        if (baseTripEndTimeActual == null) {
            return "";
        }
        return baseTripEndTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaseTripEndTimeActual(LocalDateTime baseTripEndTimeActual) {
        this.baseTripEndTimeActual = baseTripEndTimeActual;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
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

    public String getStartTimeDifferenceString() {
        if (this.getStartTimeActual() != null) {
            Converter converter = new Converter();
            return converter.convertDurationToString(Duration.between(this.getStartTimeScheduled(), this.getStartTimeActual()));
        } else {
            return "";
        }
    }

    public String getStartTimeDifferenceColorString() {

        if (this.getStartTimeActual() != null) {
            Duration between = Duration.between(this.getStartTimeScheduled(), this.getStartTimeActual());
            long seconds = between.getSeconds();
            if (seconds >= 180 || seconds <= -180) {
                return "red";
            }

            return "inherited";
        } else {
            return "red";
        }
    }
}
