/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Guaranty;

import BasicModels.Day;
import BasicModels.TripPeriod;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class GuarantyDay extends Day {

    private GuarantyTrip abSubguarantyTrip;
    private GuarantyTrip abGuarantyTrip;

    private GuarantyTrip baSubguarantyTrip;
    private GuarantyTrip baGuarantyTrip;

    public GuarantyDay() {
        this.abSubguarantyTrip = new GuarantyTrip();

        this.abGuarantyTrip = new GuarantyTrip();

    }

    void calculateGuarantyTrips() {
        createPlannedTimetebles();
        TreeMap<LocalDateTime, TripPeriod> fakeAbPlannedTimetable = (TreeMap<LocalDateTime, TripPeriod>) this.getAbPlannedTimetable().clone();
        TreeMap<LocalDateTime, TripPeriod> fakeBaPlannedTimetable = (TreeMap<LocalDateTime, TripPeriod>) this.getBaPlannedTimetable().clone();

        TripPeriod abPlannedGuarantyTrip = fakeAbPlannedTimetable.pollLastEntry().getValue();
        TripPeriod abPlannedSubGuarantyTrip = fakeAbPlannedTimetable.pollLastEntry().getValue();

        this.abSubguarantyTrip.setStartTimeScheduled(abPlannedSubGuarantyTrip.getStartTimeScheduled());
        this.abSubguarantyTrip.setType(abPlannedSubGuarantyTrip.getType());
        this.abSubguarantyTrip.setPlannedExodusNumber(abPlannedSubGuarantyTrip.getExoudsNumber());
        this.abSubguarantyTrip.setBaseNumber(abPlannedSubGuarantyTrip.getBaseNumber());
        this.abSubguarantyTrip.setGuarantyType("ქვე-საგარანტიო");

        this.abGuarantyTrip.setStartTimeScheduled(abPlannedGuarantyTrip.getStartTimeScheduled());
        this.abGuarantyTrip.setType(abPlannedGuarantyTrip.getType());
        this.abGuarantyTrip.setPlannedExodusNumber(abPlannedGuarantyTrip.getExoudsNumber());
        this.abGuarantyTrip.setBaseNumber(abPlannedGuarantyTrip.getBaseNumber());
        this.abGuarantyTrip.setGuarantyType("საგარანტიო");

        TripPeriod baPlannedGuarantyTrip = null;
        TripPeriod baPlannedSubGuarantyTrip = null;
        if (fakeBaPlannedTimetable.size() > 0) {
            baPlannedGuarantyTrip = fakeBaPlannedTimetable.pollLastEntry().getValue();
            baPlannedSubGuarantyTrip = fakeBaPlannedTimetable.pollLastEntry().getValue();

            this.baSubguarantyTrip = new GuarantyTrip();
            this.baGuarantyTrip = new GuarantyTrip();

            this.baSubguarantyTrip.setStartTimeScheduled(baPlannedSubGuarantyTrip.getStartTimeScheduled());
            this.baSubguarantyTrip.setType(baPlannedSubGuarantyTrip.getType());
            this.baSubguarantyTrip.setPlannedExodusNumber(baPlannedSubGuarantyTrip.getExoudsNumber());
            this.baSubguarantyTrip.setBaseNumber(baPlannedSubGuarantyTrip.getBaseNumber());
            this.baSubguarantyTrip.setGuarantyType("ქვე-საგარანტიო");

            this.baGuarantyTrip.setStartTimeScheduled(baPlannedGuarantyTrip.getStartTimeScheduled());
            this.baGuarantyTrip.setType(baPlannedGuarantyTrip.getType());
            this.baGuarantyTrip.setPlannedExodusNumber(baPlannedGuarantyTrip.getExoudsNumber());
            this.baGuarantyTrip.setBaseNumber(baPlannedGuarantyTrip.getBaseNumber());
            this.baGuarantyTrip.setGuarantyType("საგარანტიო");

        } else {
            System.out.println("Round route");
        }

        createActualTimetables();

        TreeMap<LocalDateTime, TripPeriod> fakeAbActualTimetable = (TreeMap<LocalDateTime, TripPeriod>) this.getAbActualTimetable().clone();

        if (fakeAbActualTimetable.size() == 0) {

        } else {
            TripPeriod abActualGuarantyTrip = fakeAbActualTimetable.pollLastEntry().getValue();
            TripPeriod abActualSubGuarantyTrip = fakeAbActualTimetable.pollLastEntry().getValue();

            if (abActualGuarantyTrip.getStartTimeActual().isAfter(abPlannedGuarantyTrip.getStartTimeScheduled())) {
                this.abSubguarantyTrip.setStartTimeActual(abActualSubGuarantyTrip.getStartTimeActual());
                this.abSubguarantyTrip.setActualExodusNumber(abActualSubGuarantyTrip.getExoudsNumber());
                this.abSubguarantyTrip.setDriver(abActualSubGuarantyTrip.getDriver());

                this.abGuarantyTrip.setStartTimeActual(abActualGuarantyTrip.getStartTimeActual());
                this.abGuarantyTrip.setActualExodusNumber(abActualGuarantyTrip.getExoudsNumber());
                this.abGuarantyTrip.setDriver(abActualGuarantyTrip.getDriver());

            } else {
                Duration durationTillGuarantyTrip = Duration.between(abActualGuarantyTrip.getStartTimeActual(), abPlannedGuarantyTrip.getStartTimeScheduled());
                Duration durationTillSubGuarantyTrip = Duration.between(abActualGuarantyTrip.getStartTimeActual(), abPlannedSubGuarantyTrip.getStartTimeScheduled());
                long durationTillGuarantyTripInSeconds = durationTillGuarantyTrip.getSeconds();
                long durationTillSubGuarantyTripInSeconds = durationTillSubGuarantyTrip.getSeconds();
                if (durationTillGuarantyTripInSeconds < 0) {
                    durationTillGuarantyTripInSeconds = durationTillGuarantyTripInSeconds * -1;
                }
                if (durationTillSubGuarantyTripInSeconds < 0) {
                    durationTillSubGuarantyTripInSeconds = durationTillSubGuarantyTripInSeconds * -1;
                }
                if (durationTillGuarantyTripInSeconds <= durationTillSubGuarantyTripInSeconds) {
                    this.abSubguarantyTrip.setStartTimeActual(abActualSubGuarantyTrip.getStartTimeActual());
                    this.abSubguarantyTrip.setActualExodusNumber(abActualSubGuarantyTrip.getExoudsNumber());
                    this.abSubguarantyTrip.setDriver(abActualSubGuarantyTrip.getDriver());

                    this.abGuarantyTrip.setStartTimeActual(abActualGuarantyTrip.getStartTimeActual());
                    this.abGuarantyTrip.setActualExodusNumber(abActualGuarantyTrip.getExoudsNumber());
                    this.abGuarantyTrip.setDriver(abActualGuarantyTrip.getDriver());
                } else {
                    this.abSubguarantyTrip.setStartTimeActual(abActualGuarantyTrip.getStartTimeActual());
                    this.abSubguarantyTrip.setActualExodusNumber(abActualGuarantyTrip.getExoudsNumber());
                    this.abSubguarantyTrip.setDriver(abActualSubGuarantyTrip.getDriver());

                    this.abGuarantyTrip.setStartTimeActual(null);
                }
            }
        }

        TreeMap<LocalDateTime, TripPeriod> fakeBaActualTimetable = (TreeMap<LocalDateTime, TripPeriod>) this.getBaActualTimetable().clone();

        if (fakeBaActualTimetable.size() > 0) {
            TripPeriod baActualGuarantyTrip = fakeBaActualTimetable.pollLastEntry().getValue();
            TripPeriod baActualSubGuarantyTrip = fakeBaActualTimetable.pollLastEntry().getValue();

            if (baActualGuarantyTrip.getStartTimeActual().isAfter(baPlannedGuarantyTrip.getStartTimeScheduled())) {
                this.baSubguarantyTrip.setStartTimeActual(baActualSubGuarantyTrip.getStartTimeActual());
                this.baSubguarantyTrip.setActualExodusNumber(baActualSubGuarantyTrip.getExoudsNumber());
                this.baSubguarantyTrip.setDriver(baActualSubGuarantyTrip.getDriver());

                this.baGuarantyTrip.setStartTimeActual(baActualGuarantyTrip.getStartTimeActual());
                this.baGuarantyTrip.setActualExodusNumber(baActualGuarantyTrip.getExoudsNumber());
                this.baGuarantyTrip.setDriver(baActualGuarantyTrip.getDriver());
            } else {
                Duration durationTillGuarantyTrip = Duration.between(baActualGuarantyTrip.getStartTimeActual(), baPlannedGuarantyTrip.getStartTimeScheduled());
                Duration durationTillSubGuarantyTrip = Duration.between(baActualGuarantyTrip.getStartTimeActual(), baPlannedSubGuarantyTrip.getStartTimeScheduled());
                long durationTillGuarantyTripInSeconds = durationTillGuarantyTrip.getSeconds();
                long durationTillSubGuarantyTripInSeconds = durationTillSubGuarantyTrip.getSeconds();
                if (durationTillGuarantyTripInSeconds < 0) {
                    durationTillGuarantyTripInSeconds = durationTillGuarantyTripInSeconds * -1;
                }
                if (durationTillSubGuarantyTripInSeconds < 0) {
                    durationTillSubGuarantyTripInSeconds = durationTillSubGuarantyTripInSeconds * -1;
                }
                if (durationTillGuarantyTripInSeconds <= durationTillSubGuarantyTripInSeconds) {
                    this.baSubguarantyTrip.setStartTimeActual(baActualSubGuarantyTrip.getStartTimeActual());
                    this.baSubguarantyTrip.setActualExodusNumber(baActualSubGuarantyTrip.getExoudsNumber());
                    this.baSubguarantyTrip.setDriver(baActualSubGuarantyTrip.getDriver());

                    this.baGuarantyTrip.setStartTimeActual(baActualGuarantyTrip.getStartTimeActual());
                    this.baGuarantyTrip.setActualExodusNumber(baActualGuarantyTrip.getExoudsNumber());
                    this.baGuarantyTrip.setDriver(baActualGuarantyTrip.getDriver());

                } else {
                    this.baSubguarantyTrip.setStartTimeActual(baActualGuarantyTrip.getStartTimeActual());
                    this.baSubguarantyTrip.setActualExodusNumber(baActualGuarantyTrip.getExoudsNumber());
                    this.baSubguarantyTrip.setDriver(baActualSubGuarantyTrip.getDriver());

                    this.baGuarantyTrip.setStartTimeActual(null);
                }
            }

        } else {
            System.out.println("Round route Or Guaranty Trip could not be calculated");
        }

    }

    public GuarantyTrip getAbSubguarantyTrip() {
        return abSubguarantyTrip;
    }

    public void setAbSubguarantyTrip(GuarantyTrip abSubguarantyTrip) {
        this.abSubguarantyTrip = abSubguarantyTrip;
    }

    public GuarantyTrip getAbGuarantyTrip() {
        return abGuarantyTrip;
    }

    public void setAbGuarantyTrip(GuarantyTrip abGuarantyTrip) {
        this.abGuarantyTrip = abGuarantyTrip;
    }

    public GuarantyTrip getBaSubguarantyTrip() {
        return baSubguarantyTrip;
    }

    public void setBaSubguarantyTrip(GuarantyTrip baSubguarantyTrip) {
        this.baSubguarantyTrip = baSubguarantyTrip;
    }

    public GuarantyTrip getBaGuarantyTrip() {
        return baGuarantyTrip;
    }

    public void setBaGuarantyTrip(GuarantyTrip baGuarantyTrip) {
        this.baGuarantyTrip = baGuarantyTrip;
    }

}
