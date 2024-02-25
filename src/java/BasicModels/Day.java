package BasicModels;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Day {

    private String dateStamp;
    private TreeMap<Short, Exodus> plannedExoduses;
    private TreeMap<Short, Exodus> actualExoduses;

    private boolean plannedTimetableCreated;
    private boolean actualTimetableCreated;

    private boolean plannedGuarantyTripsCreated;
    private boolean actualGuarantyTripsCreated;

    private TreeMap<LocalDateTime, TripPeriod> abPlannedTimetable;
    private TreeMap<LocalDateTime, TripPeriod> baPlannedTimetable;
    private TreeMap<LocalDateTime, TripPeriod> abActualTimetable;
    private TreeMap<LocalDateTime, TripPeriod> baActualTimetable;

    private TripPeriod abPlannedSubGuarantyTrip;
    private TripPeriod abPlannedGuarantyTrip;

    private TripPeriod baPlannedSubGuarantyTrip;
    private TripPeriod baPlannedGuarantyTrip;

    private TripPeriod abActualSubGuarantyTrip;
    private TripPeriod abActualGuarantyTrip;

    private TripPeriod baActualSubGuarantyTrip;
    private TripPeriod baActualGuarantyTrip;

    public Day() {
        this.plannedExoduses = new TreeMap();
        this.actualExoduses = new TreeMap();

        this.abPlannedTimetable = new TreeMap();;
        this.baPlannedTimetable = new TreeMap();;
        this.abActualTimetable = new TreeMap();;
        this.baActualTimetable = new TreeMap();;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public TreeMap<Short, Exodus> getPlannedExoduses() {
        return plannedExoduses;
    }

    public void setPlannedExoduses(TreeMap<Short, Exodus> plannedExoduses) {
        this.plannedExoduses = plannedExoduses;
    }

    public TreeMap<Short, Exodus> getActualExoduses() {
        return actualExoduses;
    }

    public void setActualExoduses(TreeMap<Short, Exodus> actualExoduses) {
        this.actualExoduses = actualExoduses;
    }

    public boolean isPlannedTimetableCreated() {
        return plannedTimetableCreated;
    }

    public void setPlannedTimetableCreated(boolean plannedTimetableCreated) {
        this.plannedTimetableCreated = plannedTimetableCreated;
    }

    public boolean isActualTimetableCreated() {
        return actualTimetableCreated;
    }

    public void setActualTimetableCreated(boolean actualTimetableCreated) {
        this.actualTimetableCreated = actualTimetableCreated;
    }

    public TreeMap<LocalDateTime, TripPeriod> getAbPlannedTimetable() {
        return abPlannedTimetable;
    }

    public void setAbPlannedTimetable(TreeMap<LocalDateTime, TripPeriod> abPlannedTimetable) {
        this.abPlannedTimetable = abPlannedTimetable;
    }

    public TreeMap<LocalDateTime, TripPeriod> getBaPlannedTimetable() {
        return baPlannedTimetable;
    }

    public void setBaPlannedTimetable(TreeMap<LocalDateTime, TripPeriod> baPlannedTimetable) {
        this.baPlannedTimetable = baPlannedTimetable;
    }

    public TreeMap<LocalDateTime, TripPeriod> getAbActualTimetable() {
        return abActualTimetable;
    }

    public void setAbActualTimetable(TreeMap<LocalDateTime, TripPeriod> abActualTimetable) {
        this.abActualTimetable = abActualTimetable;
    }

    public TreeMap<LocalDateTime, TripPeriod> getBaActualTimetable() {
        return baActualTimetable;
    }

    public void setBaActualTimetable(TreeMap<LocalDateTime, TripPeriod> baActualTimetable) {
        this.baActualTimetable = baActualTimetable;
    }

    public TreeMap<LocalDateTime, TripPeriod> getPlannedABTimetable() {
        if (!plannedTimetableCreated) {
            createPlannedTimetebles();
        }
        return this.abPlannedTimetable;
    }

    public TreeMap<LocalDateTime, TripPeriod> getPlannedBATimetable() {
        if (!plannedTimetableCreated) {
            createPlannedTimetebles();
        }
        return this.baPlannedTimetable;
    }

    public TreeMap<LocalDateTime, TripPeriod> getActualABTimetable() {
        if (!actualTimetableCreated) {
            createActualTimetables();
        }
        return this.abActualTimetable;
    }

    public TreeMap<LocalDateTime, TripPeriod> getActualBATimetable() {
        if (!actualTimetableCreated) {
            createActualTimetables();
        }
        return this.baActualTimetable;
    }

    private void createPlannedTimetebles() {

        for (Map.Entry<Short, Exodus> exoduseEntry : this.plannedExoduses.entrySet()) {
            Exodus exodus = exoduseEntry.getValue();
            TreeMap<String, TripVoucher> tripVouchers = exodus.getTripVouchers();
            for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                TripVoucher tripVoucher = tripVoucherEntry.getValue();
                ArrayList<TripPeriod> tripPeriods = tripVoucher.getTripPeriods();
                for (TripPeriod tripPeriod : tripPeriods) {
                    String type = tripPeriod.getType();
                    LocalDateTime startTimeScheduled = tripPeriod.getStartTimeScheduled();
                    tripPeriod.setExoudsNumber(exoduseEntry.getValue().getNumber());
                    if (type.equals("ab")) {
                        this.abPlannedTimetable.put(startTimeScheduled, tripPeriod);
                    }
                    if (type.equals("ba")) {
                        this.baPlannedTimetable.put(startTimeScheduled, tripPeriod);
                    }
                }
            }
        }
        this.plannedTimetableCreated = true;
    }

    private void createActualTimetables() {

        ArrayList<TripPeriod> abNullers = new ArrayList();
        ArrayList<TripPeriod> baNullers = new ArrayList();

        for (Map.Entry<Short, Exodus> exoduseEntry : this.actualExoduses.entrySet()) {
            Exodus exodus = exoduseEntry.getValue();
            TreeMap<String, TripVoucher> tripVouchers = exodus.getTripVouchers();
            for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                TripVoucher tripVoucher = tripVoucherEntry.getValue();
                ArrayList<TripPeriod> tripPeriods = tripVoucher.getTripPeriods();
                for (TripPeriod tripPeriod : tripPeriods) {
                    String type = tripPeriod.getType();
                    LocalDateTime startTimeActual = tripPeriod.getStartTimeActual();
                    LocalDateTime arrivalTimeActual = tripPeriod.getArrivalTimeActual();

                    if (startTimeActual != null && arrivalTimeActual != null) {

                        tripPeriod.setExoudsNumber(exoduseEntry.getValue().getNumber());
                        if (type.equals("ab")) {
                            this.abActualTimetable.put(startTimeActual, tripPeriod);
                        }
                        if (type.equals("ba")) {
                            this.baActualTimetable.put(startTimeActual, tripPeriod);
                        }

                    }
                }
                //this.abActualTimetable = addNullers(this.abActualTimetable, abNullers);
                // this.baActualTimetable = addNullers(this.baActualTimetable, baNullers);
                this.actualTimetableCreated = true;
            }

        }
    }

    public TripPeriod getAbPlannedSubGuarantyTrip() {

        if (!this.plannedGuarantyTripsCreated) {
            calculatePlannedGuarantyTrips();
        }

        return abPlannedSubGuarantyTrip;
    }

    public void setAbPlannedSubGuarantyTrip(TripPeriod abPlannedSubGuarantyTrip) {
        this.abPlannedSubGuarantyTrip = abPlannedSubGuarantyTrip;
    }

    public TripPeriod getAbPlannedGuarantyTrip() {
        if (!this.plannedGuarantyTripsCreated) {
            calculatePlannedGuarantyTrips();
        }

        return abPlannedGuarantyTrip;
    }

    public void setAbPlannedGuarantyTrip(TripPeriod abPlannedGuarantyTrip) {
        this.abPlannedGuarantyTrip = abPlannedGuarantyTrip;
    }

    public TripPeriod getBaPlannedSubGuarantyTrip() {
        if (!this.plannedGuarantyTripsCreated) {
            calculatePlannedGuarantyTrips();
        }

        return baPlannedSubGuarantyTrip;
    }

    public void setBaPlannedSubGuarantyTrip(TripPeriod baPlannedSubGuarantyTrip) {
        this.baPlannedSubGuarantyTrip = baPlannedSubGuarantyTrip;
    }

    public TripPeriod getBaPlannedGuarantyTrip() {

        if (!this.plannedGuarantyTripsCreated) {
            calculatePlannedGuarantyTrips();
        }
        return baPlannedGuarantyTrip;
    }

    public void setBaPlannedGuarantyTrip(TripPeriod baPlannedGuarantyTrip) {
        this.baPlannedGuarantyTrip = baPlannedGuarantyTrip;
    }

    public TripPeriod getAbActualSubGuarantyTrip() {
        if (!this.actualGuarantyTripsCreated) {
            calculateActualGuarantyTrips();
        }

        return abActualSubGuarantyTrip;
    }

    public void setAbActualSubGuarantyTrip(TripPeriod abActualSubGuarantyTrip) {
        this.abActualSubGuarantyTrip = abActualSubGuarantyTrip;
    }

    public TripPeriod getAbActualGuarantyTrip() {

        if (!this.plannedGuarantyTripsCreated) {
            calculateActualGuarantyTrips();
        }
        return abActualGuarantyTrip;
    }

    public void setAbActualGuarantyTrip(TripPeriod abActualGuarantyTrip) {
        this.abActualGuarantyTrip = abActualGuarantyTrip;
    }

    public TripPeriod getBaActualSubGuarantyTrip() {

        if (!this.plannedGuarantyTripsCreated) {
            calculateActualGuarantyTrips();
        }
        return baActualSubGuarantyTrip;
    }

    public void setBaActualSubGuarantyTrip(TripPeriod baActualSubGuarantyTrip) {
        this.baActualSubGuarantyTrip = baActualSubGuarantyTrip;
    }

    public TripPeriod getBaActualGuarantyTrip() {

        if (!this.plannedGuarantyTripsCreated) {
            calculateActualGuarantyTrips();
        }
        return baActualGuarantyTrip;
    }

    public void setBaActualGuarantyTrip(TripPeriod baActualGuarantyTrip) {
        this.baActualGuarantyTrip = baActualGuarantyTrip;
    }

    public boolean isPlannedGuarantyTripsCreated() {
        return plannedGuarantyTripsCreated;
    }

    public void setPlannedGuarantyTripsCreated(boolean plannedGuarantyTripsCreated) {
        this.plannedGuarantyTripsCreated = plannedGuarantyTripsCreated;
    }

    public boolean isActualGuarantyTripsCreated() {
        return actualGuarantyTripsCreated;
    }

    public void setActualGuarantyTripsCreated(boolean actualGuarantyTripsCreated) {
        this.actualGuarantyTripsCreated = actualGuarantyTripsCreated;
    }

    private void calculatePlannedGuarantyTrips() {
        createPlannedTimetebles();
        TreeMap<LocalDateTime, TripPeriod> fakeAbPlannedTimetable = (TreeMap<LocalDateTime, TripPeriod>) this.abPlannedTimetable.clone();
        TreeMap<LocalDateTime, TripPeriod> fakeBaPlannedTimetable = (TreeMap<LocalDateTime, TripPeriod>) this.baPlannedTimetable.clone();

        this.abPlannedGuarantyTrip = fakeAbPlannedTimetable.pollLastEntry().getValue();
        this.abPlannedSubGuarantyTrip = fakeAbPlannedTimetable.pollLastEntry().getValue();
        if (fakeBaPlannedTimetable.size() > 0) {
            this.baPlannedGuarantyTrip = fakeBaPlannedTimetable.pollLastEntry().getValue();
            this.baPlannedSubGuarantyTrip = fakeBaPlannedTimetable.pollLastEntry().getValue();
        } else {
            System.out.println("Round route");
        }
        this.plannedGuarantyTripsCreated = true;
    }

    private void calculateActualGuarantyTrips() {
        createActualTimetables();
        TreeMap<LocalDateTime, TripPeriod> fakeAbActualTimetable = (TreeMap<LocalDateTime, TripPeriod>) this.abActualTimetable.clone();
        TreeMap<LocalDateTime, TripPeriod> fakeBaActualTimetable = (TreeMap<LocalDateTime, TripPeriod>) this.baActualTimetable.clone();
        this.abActualGuarantyTrip = fakeAbActualTimetable.pollLastEntry().getValue();
        this.abActualSubGuarantyTrip = fakeAbActualTimetable.pollLastEntry().getValue();
        if (fakeBaActualTimetable.size() > 0) {
            this.baActualGuarantyTrip = fakeBaActualTimetable.pollLastEntry().getValue();
            this.baActualSubGuarantyTrip = fakeBaActualTimetable.pollLastEntry().getValue();
        } else {
            System.out.println("Round route");
        }
        this.actualGuarantyTripsCreated = true;
    }

    private TreeMap<LocalDateTime, TripPeriod> addNullers(TreeMap<LocalDateTime, TripPeriod> timetable, ArrayList<TripPeriod> nullers) {
        if (nullers.isEmpty()) {
            return timetable;
        }
        for (TripPeriod nullerTripPeriod : nullers) {
            nullerTripPeriod.setSpacialCase(true);
            ArrayList<TripPeriod> timeTabledTripPeriods = new ArrayList<>(timetable.values());
            int x = timeTabledTripPeriods.size() - 1;
            while (x > 0) {
                LocalDateTime fakeStartTimeActual;
                LocalDateTime startTimeActual;

                TripPeriod timeTabledTripPeriod = timeTabledTripPeriods.get(x);
                if (x != 0) {

                    if (timeTabledTripPeriod.isSpacialCase()) {
                        startTimeActual = timeTabledTripPeriod.getFakeStartTimeActual();

                    } else {
                        startTimeActual = timeTabledTripPeriod.getStartTimeActual();
                    }

                    if (nullerTripPeriod.getArrivalTimeActual().isAfter(timeTabledTripPeriod.getArrivalTimeActual())) {

                        fakeStartTimeActual = startTimeActual.plusSeconds(1);
                        nullerTripPeriod.setFakeStartTimeActual(fakeStartTimeActual);

                        timetable.put(fakeStartTimeActual, nullerTripPeriod);
                        break;
                    }
                } else {
                    //if we reached the last TripPeriod in gpsTimeTable and still arrivalTime is not after

                    if (timeTabledTripPeriod.isSpacialCase()) {
                        startTimeActual = timeTabledTripPeriod.getFakeStartTimeActual();
                    } else {
                        startTimeActual = timeTabledTripPeriod.getStartTimeActual();
                    }

                    if (nullerTripPeriod.getArrivalTimeActual().isAfter(timeTabledTripPeriod.getArrivalTimeActual())) {
                        fakeStartTimeActual = startTimeActual.plusSeconds(1);
                    } else {
                        fakeStartTimeActual = startTimeActual.minusSeconds(1);
                    }
                    nullerTripPeriod.setFakeStartTimeActual(fakeStartTimeActual);
                    timetable.put(fakeStartTimeActual, nullerTripPeriod);
                }
                x--;
            }
        }
        return timetable;
    }

}
