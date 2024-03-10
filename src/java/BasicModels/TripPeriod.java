/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicModels;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Michail Sitmalidis
 */
public class TripPeriod {

    private String type;
    private LocalDateTime startTimeScheduled;
    private LocalDateTime startTimeActual;
    private String startTimeDifference;

    private LocalDateTime arrivalTimeScheduled;
    private LocalDateTime arrivalTimeActual;
    private String arrivalTimeDifference;

    private short exoudsNumber;
    private boolean spacialCase;
    private LocalDateTime fakeStartTimeActual;

    private String driver;

    private short baseNumber;

    public TripPeriod() {
    }

    public TripPeriod(String type, LocalDateTime startTimeScheduled, LocalDateTime startTimeActual, String startTimeDifference, LocalDateTime arrivalTimeScheduled, LocalDateTime arrivalTimeActual, String arrivalTimeDifference) {
        this.type = type;
        this.startTimeScheduled = startTimeScheduled;
        this.startTimeActual = startTimeActual;
        this.startTimeDifference = startTimeDifference;
        this.arrivalTimeScheduled = arrivalTimeScheduled;
        this.arrivalTimeActual = arrivalTimeActual;
        this.arrivalTimeDifference = arrivalTimeDifference;
    }

    public LocalDateTime getStartTimeScheduled() {
        return startTimeScheduled;
    }

    public void setStartTimeScheduled(LocalDateTime startTimeScheduled) {
        this.startTimeScheduled = startTimeScheduled;
    }

    public LocalDateTime getStartTimeActual() {
        return startTimeActual;
    }

    public void setStartTimeActual(LocalDateTime startTimeActual) {
        this.startTimeActual = startTimeActual;
    }

    public String getStartTimeDifference() {
        return startTimeDifference;
    }

    public void setStartTimeDifference(String startTimeDifference) {
        this.startTimeDifference = startTimeDifference;
    }

    public LocalDateTime getArrivalTimeScheduled() {
        return arrivalTimeScheduled;
    }

    public void setArrivalTimeScheduled(LocalDateTime arrivalTimeScheduled) {
        this.arrivalTimeScheduled = arrivalTimeScheduled;
    }

    public LocalDateTime getArrivalTimeActual() {
        return arrivalTimeActual;
    }

    public void setArrivalTimeActual(LocalDateTime arrivalTimeActual) {
        this.arrivalTimeActual = arrivalTimeActual;
    }

    public String getArrivalTimeDifference() {
        return arrivalTimeDifference;
    }

    public void setArrivalTimeDifference(String arrivalTimeDifference) {
        this.arrivalTimeDifference = arrivalTimeDifference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeG() {
        switch (type) {
            case "baseLeaving_A":
                return "ბაზა_A";
            case "baseLeaving_B":
                return "ბაზა_B";
            case "break":
                return "შესვენება";
            case "ab":
                return "A_B";
            case "ba":
                return "B_A";
            case "A_baseReturn":
                return "A_ბაზა";
            case "B_baseReturn":
                return "B_ბაზა";
            default:
                return "";
        }
    }

    public String getStartTimeScheduledString() {

        return startTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getStartTimeActualString() {
        if (startTimeActual == null) {
            return "";
        }
        return startTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getArrivalTimeScheduledString() {

        return arrivalTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getArrivalTimeActualString() {
        if (arrivalTimeActual == null) {
            return "";
        }
        return arrivalTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public short getExoudsNumber() {
        return exoudsNumber;
    }

    public void setExoudsNumber(short exoudsNumber) {
        this.exoudsNumber = exoudsNumber;
    }

    public boolean isSpacialCase() {
        return spacialCase;
    }

    public void setSpacialCase(boolean spacialCase) {
        this.spacialCase = spacialCase;
    }

    public LocalDateTime getFakeStartTimeActual() {
        return fakeStartTimeActual;
    }

    public void setFakeStartTimeActual(LocalDateTime fakeStartTimeActual) {
        this.fakeStartTimeActual = fakeStartTimeActual;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public short getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(short baseNumber) {
        this.baseNumber = baseNumber;
    }

}
