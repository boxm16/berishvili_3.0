/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicModels;

import java.time.LocalDateTime;

/**
 *
 * @author Michail Sitmalidis
 */
public class TripPeriod {

    private String type;
    private LocalDateTime startTimeScheduled;
    private LocalDateTime arrivalTimeScheduled;

    public TripPeriod(String type, LocalDateTime startTimeScheduled, LocalDateTime arrivalTimeScheduled) {
        this.type = type;
        this.startTimeScheduled = startTimeScheduled;
        this.arrivalTimeScheduled = arrivalTimeScheduled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartTimeScheduled() {
        return startTimeScheduled;
    }

    public void setStartTimeScheduled(LocalDateTime startTimeScheduled) {
        this.startTimeScheduled = startTimeScheduled;
    }

    public LocalDateTime getArrivalTimeScheduled() {
        return arrivalTimeScheduled;
    }

    public void setArrivalTimeScheduled(LocalDateTime arrivalTimeScheduled) {
        this.arrivalTimeScheduled = arrivalTimeScheduled;
    }
    
    
}
