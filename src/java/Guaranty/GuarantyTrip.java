package Guaranty;

import BasicModels.TripPeriod;
import Service.Converter;
import java.time.Duration;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Michail Sitmalidis
 */
public class GuarantyTrip extends TripPeriod {

    private String routeNumber;
    private String dateStamp;
    private String baseNumber;

    private String aPoint;
    private String bPoint;
    private String scheme;

    private String guarantyType;

    private short plannedExodusNumber;
    private short actualExodusNumber;

    public GuarantyTrip() {

    }

    public String getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(String baseNumber) {
        this.baseNumber = baseNumber;
    }

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

    public String getGuarantyType() {
        return guarantyType;
    }

    public void setGuarantyType(String guarantyType) {
        this.guarantyType = guarantyType;
    }

    public short getPlannedExodusNumber() {
        return plannedExodusNumber;
    }

    public void setPlannedExodusNumber(short plannedExodusNumber) {
        this.plannedExodusNumber = plannedExodusNumber;
    }

    public short getActualExodusNumber() {
        return actualExodusNumber;
    }

    public void setActualExodusNumber(short actualExodusNumber) {
        this.actualExodusNumber = actualExodusNumber;
    }

    public Duration getGuarantyStartTimeDifference() {
        if (this.getStartTimeScheduled() == null || this.getStartTimeActual() == null) {
            return null;
        }
        return Duration.between(this.getStartTimeScheduled(), this.getStartTimeActual());
    }

    public String getGuarantyStartTimeDifferenceString() {
        if (this.getStartTimeActual() != null) {
            Converter converter = new Converter();
            return converter.convertDurationToString(Duration.between(this.getStartTimeScheduled(), this.getStartTimeActual()));
        } else {
            return "";
        }
    }

    public String getGuarantyStartTimeDifferenceColorString() {
        if (this.getStartTimeActual() != null) {
            Duration between = Duration.between(this.getStartTimeScheduled(), this.getStartTimeActual());
            long seconds = between.getSeconds();
            if (seconds < 0) {
                seconds = seconds * -1;
            }

            if (seconds < 60) {
                return "inherited";
            }
            if (seconds >= 61 && seconds < 300) {
                return "yellow";
            }
            if (seconds >= 300) {
                return "red";
            }
        } else {
            return "inherited";
        }
        return "inherited";
    }
}
