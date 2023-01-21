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
    private LocalDateTime startTime;
    private LocalDateTime arrivalTime;

    public TripPeriod(String type, LocalDateTime startTimeScheduled, LocalDateTime arrivalTimeScheduled) {
        this.type = type;
        this.startTime = startTimeScheduled;
        this.arrivalTime = arrivalTimeScheduled;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    public String getStartTimeString() {
        return startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getArrivalTimeString() {
        return arrivalTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

}
