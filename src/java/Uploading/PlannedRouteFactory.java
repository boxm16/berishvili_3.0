package Uploading;

import BasicModels.Day;
import BasicModels.Exodus;
import BasicModels.Route;
import BasicModels.TripPeriod;
import BasicModels.TripVoucher;
import Service.Converter;
import Service.ExcelReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import org.springframework.stereotype.Service;

@Service
public class PlannedRouteFactory {

    private final Converter converter;

    public PlannedRouteFactory() {
        this.converter = new Converter();
    }

    public TreeMap<String, Route> getRoutesFromExcelFile(String pathFile) {

        System.out.println("STARTING READING EXCEL FILE");
        ExcelReader excelReader = new ExcelReader();
        HashMap<String, String> cellsFromExcelFile = excelReader.getCellsFromExcelFile(pathFile);
        TreeMap<String, Route> routes = convertExcelDataToRoutes(cellsFromExcelFile);
        System.out.println("COMPLETED READING EXCEL FILE");
        return routes;
    }

    private TreeMap<String, Route> convertExcelDataToRoutes(HashMap<String, String> data) {
        TreeMap<String, Route> routes = new TreeMap<>();
        int excelRowIndex = 7;

        while (!data.isEmpty()) {
            String routeNumberLocationInTheRow = new StringBuilder("I").append(String.valueOf(excelRowIndex)).toString();
            String routeNumber = data.remove(routeNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map
            if (routeNumber == null) {//in theory this means that you reached the end of rows with data
                break;
            }

            Route route;
            if (routes.containsKey(routeNumber)) {
                route = routes.get(routeNumber);
            } else {
                route = new Route();
                route.setNumber(routeNumber);

            }
            route = addRowElementsToBasicRoute(route, data, excelRowIndex);
            routes.put(routeNumber, route);
            excelRowIndex++;
        }
        return routes;
    }

    private Route addRowElementsToBasicRoute(Route route, HashMap<String, String> data, int rowIndex) {

        String dateStampLocationInTheRow = new StringBuilder("G").append(String.valueOf(rowIndex)).toString();
        String dateStampExcelFormat = data.remove(dateStampLocationInTheRow);

        String dateStamp = this.converter.convertDateStampExcelFormatToDatabaseFormat(dateStampExcelFormat);

        TreeMap<String, Day> days = route.getDays();
        Day day;
        if (days.containsKey(dateStamp)) {
            day = days.get(dateStamp);
        } else {
            day = new Day();
        }
        day.setDateStamp(dateStamp);
        day = addRowElementsToDay(day, data, rowIndex);
        days.put(dateStamp, day);

        route.setDays(days);
        return route;
    }

    private Day addRowElementsToDay(Day day, HashMap<String, String> data, int rowIndex) {
        String exodusNumberLocationInTheRow = new StringBuilder("J").append(String.valueOf(rowIndex)).toString();
        short exodusNumber = Float.valueOf(data.remove(exodusNumberLocationInTheRow)).shortValue();

        TreeMap<Short, Exodus> exoduses = day.getExoduses();
        Exodus exodus;
        if (exoduses.containsKey(exodusNumber)) {
            exodus = exoduses.get(exodusNumber);
        } else {
            exodus = new Exodus();
            exodus.setNumber(exodusNumber);
        }

        exodus = addRowElementsToExodus(exodus, data, rowIndex);
        exoduses.put(exodusNumber, exodus);
        day.setExoduses(exoduses);
        return day;
    }

    private Exodus addRowElementsToExodus(Exodus exodus, HashMap<String, String> data, int rowIndex) {
        //----first baseNumber
        String baseNumberLocationInTheRow = new StringBuilder("A").append(String.valueOf(rowIndex)).toString();
        short baseNumber = Short.valueOf(data.remove(baseNumberLocationInTheRow));

        String tripVoucherNumberLocationInTheRow = new StringBuilder("H").append(String.valueOf(rowIndex)).toString();
        String tripVoucherNumber = data.remove(tripVoucherNumberLocationInTheRow);
        TreeMap<String, TripVoucher> tripVouchers = exodus.getTripVouchers();
        TripVoucher tripVoucher;
        if (tripVouchers.containsKey(tripVoucherNumber)) {
            tripVoucher = tripVouchers.get(tripVoucherNumber);
        } else {
            tripVoucher = new TripVoucher();
            String busNumberLocationInTheRow = new StringBuilder("C").append(String.valueOf(rowIndex)).toString();
            String busrNumber = data.remove(busNumberLocationInTheRow);

            String busTypeLocationInTheRow = new StringBuilder("B").append(String.valueOf(rowIndex)).toString();
            String busType = data.remove(busTypeLocationInTheRow);

            String driverNumberLocationInTheRow = new StringBuilder("D").append(String.valueOf(rowIndex)).toString();
            String driverNumber = data.remove(driverNumberLocationInTheRow);

            String driverNameLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
            String driverName = data.remove(driverNameLocationInTheRow);

            String shiftLocationInTheRow = new StringBuilder("E").append(String.valueOf(rowIndex)).toString();
            String shift = data.remove(shiftLocationInTheRow);

            String tripPeriodsTotalLocationInTheRow = new StringBuilder("S").append(String.valueOf(rowIndex)).toString();
            float tripPeriodsTotalF = Float.parseFloat(data.remove(tripPeriodsTotalLocationInTheRow));
            int tripPeriodsTotal = (int) tripPeriodsTotalF;

            String kilometrageLocationInTheRow = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
            float kilometrage = Float.parseFloat(data.remove(kilometrageLocationInTheRow));

            String baseLeavingTimeScheduledLocationInTheRow = new StringBuilder("K").append(String.valueOf(rowIndex)).toString();
            LocalDateTime baseLeavingTimeScheduled = this.converter.convertStringTimeToDate(data.remove(baseLeavingTimeScheduledLocationInTheRow));

            // String baseLeavingTimeActualLocationInTheRow = new StringBuilder("K").append(String.valueOf(rowIndex)).toString();
            //LocalDateTime baseLeavingTimeActual = this.converter.convertStringTimeToDate(data.remove(baseLeavingTimeActualLocationInTheRow));
            // String baseLeavingTimeRedactedLocationInTheRow = new StringBuilder("L").append(String.valueOf(rowIndex)).toString();
            //LocalDateTime baseLeavingTimeRedacted = this.converter.convertStringTimeToDate(data.remove(baseLeavingTimeRedactedLocationInTheRow));
            String baseReturnTimeScheduledLocationInTheRow = new StringBuilder("N").append(String.valueOf(rowIndex)).toString();
            LocalDateTime baseReturnTimeScheduled = this.converter.convertStringTimeToDate(data.remove(baseReturnTimeScheduledLocationInTheRow));

            //String baseRetrunTimeActualLocationInTheRow = new StringBuilder("N").append(String.valueOf(rowIndex)).toString();
            //LocalDateTime baseRetrunTimeActual = this.converter.convertStringTimeToDate(data.remove(baseRetrunTimeActualLocationInTheRow));
            //String baseReturnTimeRedactedLocationInTheRow = new StringBuilder("O").append(String.valueOf(rowIndex)).toString();
            //LocalDateTime baseReturnTimeRedacted = this.converter.convertStringTimeToDate(data.remove(baseReturnTimeRedactedLocationInTheRow));
            //   String notesLocationInTheRow = new StringBuilder("AC").append(String.valueOf(rowIndex)).toString();
            //  String notes = data.remove(notesLocationInTheRow);
            tripVoucher.setBusNumber(busrNumber);
            tripVoucher.setBaseNumber(baseNumber);
            tripVoucher.setBusType(busType);
            tripVoucher.setDriverNumber(driverNumber);
            tripVoucher.setDriverName(driverName);
            tripVoucher.setShift(shift);
            tripVoucher.setTripPeriodsScheduledTotal(tripPeriodsTotal);
            tripVoucher.setKilometrageScheduled(kilometrage);
            tripVoucher.setBaseLeavingTimeScheduled(baseLeavingTimeScheduled);

            tripVoucher.setBaseReturnTimeScheduled(baseReturnTimeScheduled);

        }
        tripVoucher.setNumber(tripVoucherNumber);
        //add another elements
        tripVoucher = addRowElementsToTripVoucher(tripVoucher, data, rowIndex);
        tripVouchers.put(tripVoucherNumber, tripVoucher);
        exodus.setTripVouchers(tripVouchers);
        return exodus;
    }

    private TripVoucher addRowElementsToTripVoucher(TripVoucher tripVoucher, HashMap<String, String> data, int rowIndex) {
        String tripPeriodDescriptionLocationInTheRow = new StringBuilder("U").append(String.valueOf(rowIndex)).toString();
        String tripPeriodDescription = data.remove(tripPeriodDescriptionLocationInTheRow);
        String tripPeriodType = getTripPeriodTypeFromTripDescription(tripPeriodDescription);
        ArrayList<TripPeriod> tripPeriods = tripVoucher.getTripPeriods();
        TripPeriod tripPeriod;
        switch (tripPeriodType) {
            case "baseLeaving":
                tripPeriod = createBaseLeavingPeriod(data, rowIndex);
                tripPeriods.add(tripPeriod);
                break;
            case "baseReturn":
                tripPeriod = createBaseReturnPeriod(data, rowIndex);
                tripPeriods.add(tripPeriod);
                break;
            case "break":
                tripPeriod = createBreakPeriod(data, rowIndex);
                tripPeriods.add(tripPeriod);
                break;
            case "round":
                ArrayList<TripPeriod> tripPeriodsOfRound = createTripPeridsOfRound(data, rowIndex);
                for (TripPeriod tp : tripPeriodsOfRound) {
                    tripPeriods.add(tp);
                }
                break;
        }
        tripVoucher.setTripPeriods(tripPeriods);
        return tripVoucher;
    }

    private String getTripPeriodTypeFromTripDescription(String tripPeriodDescription) {
        if (tripPeriodDescription.contains("გასვლა")) {
            return "baseLeaving";
        }
        if (tripPeriodDescription.contains("შესვენება")) {
            return "break";
        }
        if (tripPeriodDescription.contains("დაბრუნება")) {
            return "baseReturn";
        }
        if (tripPeriodDescription.contains("ბრუნი")) {
            return "round";
        }
        return tripPeriodDescription;
    }

    private TripPeriod createBaseLeavingPeriod(HashMap<String, String> data, int rowIndex) {
        String leftSideAnchor = new StringBuilder("V").append(String.valueOf(rowIndex)).toString();
        TripPeriod tripPeriod;
        if (data.containsKey(leftSideAnchor)) {
            String tripPeriodType = "baseLeaving_A";
            tripPeriod = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
        } else {
            String tripPeriodType = "baseLeaving_B";
            tripPeriod = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
        }
        return tripPeriod;
    }

    private TripPeriod createBaseReturnPeriod(HashMap<String, String> data, int rowIndex) {
        String leftSideAnchor = new StringBuilder("V").append(String.valueOf(rowIndex)).toString();
        TripPeriod tripPeriod;
        if (data.containsKey(leftSideAnchor)) {
            String tripPeriodType = "A_baseReturn";
            tripPeriod = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
        } else {
            String tripPeriodType = "B_baseReturn";
            tripPeriod = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
        }
        return tripPeriod;
    }

    private TripPeriod createBreakPeriod(HashMap<String, String> data, int rowIndex) {
        String leftSideAnchor = new StringBuilder("V").append(String.valueOf(rowIndex)).toString();
        TripPeriod tripPeriod;
        String tripPeriodType = "break";
        if (data.containsKey(leftSideAnchor)) {
            tripPeriod = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
        } else {
            tripPeriod = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
        }
        return tripPeriod;
    }

    private ArrayList<TripPeriod> createTripPeridsOfRound(HashMap<String, String> data, int rowIndex) {
        ArrayList<TripPeriod> tripPeriodsOfRound = new ArrayList();
        String leftSideAnchor = new StringBuilder("V").append(String.valueOf(rowIndex)).toString();
        String rightSideAnchor = new StringBuilder("AB").append(String.valueOf(rowIndex)).toString();
        if (data.containsKey(leftSideAnchor) && data.containsKey(rightSideAnchor)) {
            LocalDateTime leftSideTime = this.converter.convertStringTimeToDate(data.get(leftSideAnchor));
            LocalDateTime rightSideTime = this.converter.convertStringTimeToDate(data.get(rightSideAnchor));
            if (leftSideTime.isBefore(rightSideTime)) {
                String tripPeriodType = "ab";
                TripPeriod tripPeriodAB = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
                tripPeriodsOfRound.add(tripPeriodAB);
                tripPeriodType = "ba";
                TripPeriod tripPeriodBA = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
                tripPeriodsOfRound.add(tripPeriodBA);
                return tripPeriodsOfRound;
            } else {
                String tripPeriodType = "ba";
                TripPeriod tripPeriodBA = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
                tripPeriodsOfRound.add(tripPeriodBA);
                tripPeriodType = "ab";
                TripPeriod tripPeriodAB = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
                tripPeriodsOfRound.add(tripPeriodAB);
                return tripPeriodsOfRound;
            }
        }
        if (data.containsKey(leftSideAnchor)) {
            String tripPeriodType = "ab";
            TripPeriod tripPeriod = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
            tripPeriodsOfRound.add(tripPeriod);
            return tripPeriodsOfRound;
        }
        if (data.containsKey(rightSideAnchor)) {
            String tripPeriodType = "ba";
            TripPeriod tripPeriod = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
            tripPeriodsOfRound.add(tripPeriod);
            return tripPeriodsOfRound;
        }
        return tripPeriodsOfRound;
    }

    private TripPeriod createTripPeriodFromLeftSide(HashMap<String, String> data, int rowIndex, String tripPeriodType) {
        String startTimeScheduledLocationInTheRow = new StringBuilder("V").append(String.valueOf(rowIndex)).toString();
        //  String startTimeActualLocationInTheRow = new StringBuilder("R").append(String.valueOf(rowIndex)).toString();
        // String startTimeDifferenceLocationInTheRow = new StringBuilder("S").append(String.valueOf(rowIndex)).toString();

        String arrivalTimeScheduledLocationInTheRow = new StringBuilder("Y").append(String.valueOf(rowIndex)).toString();
        //  String arrivalTimeActualLocationInTheRow = new StringBuilder("U").append(String.valueOf(rowIndex)).toString();
        // String arrivalTimeDifferenceLocationInTheRow = new StringBuilder("V").append(String.valueOf(rowIndex)).toString();

        LocalDateTime startTimeScheduled = this.converter.convertStringTimeToDate(data.remove(startTimeScheduledLocationInTheRow));
        // LocalDateTime startTimeActual = this.converter.convertStringTimeToDate(data.remove(startTimeActualLocationInTheRow));
        // String startTimeDifference = data.remove(startTimeDifferenceLocationInTheRow);
        //if (startTimeDifference != null && startTimeDifference.contains("'")) {
        //  startTimeDifference = startTimeDifference.replace("'", "");
        //}

        LocalDateTime arrivalTimeScheduled = this.converter.convertStringTimeToDate(data.remove(arrivalTimeScheduledLocationInTheRow));
        //   LocalDateTime arrivalTimeActual = this.converter.convertStringTimeToDate(data.remove(arrivalTimeActualLocationInTheRow));
        //  String arrivalTimeDifference = data.remove(arrivalTimeDifferenceLocationInTheRow);
        // if (arrivalTimeDifference != null && arrivalTimeDifference.contains("'")) {
        //    arrivalTimeDifference = arrivalTimeDifference.replace("'", "");
        // }

        return new TripPeriod(tripPeriodType, startTimeScheduled, null, null, arrivalTimeScheduled, null, null);
    }

    private TripPeriod createTripPeriodFromRightSide(HashMap<String, String> data, int rowIndex, String tripPeriodType) {
        String startTimeScheduledLocationInTheRow = new StringBuilder("AB").append(String.valueOf(rowIndex)).toString();
        // String startTimeActualLocationInTheRow = new StringBuilder("X").append(String.valueOf(rowIndex)).toString();
        //String startTimeDifferenceLocationInTheRow = new StringBuilder("Y").append(String.valueOf(rowIndex)).toString();

        String arrivalTimeScheduledLocationInTheRow = new StringBuilder("AE").append(String.valueOf(rowIndex)).toString();
        //  String arrivalTimeActualLocationInTheRow = new StringBuilder("AA").append(String.valueOf(rowIndex)).toString();
        //String arrivalTimeDifferenceLocationInTheRow = new StringBuilder("AB").append(String.valueOf(rowIndex)).toString();

        LocalDateTime startTimeScheduled = this.converter.convertStringTimeToDate(data.remove(startTimeScheduledLocationInTheRow));
        // LocalDateTime startTimeActual = this.converter.convertStringTimeToDate(data.remove(startTimeActualLocationInTheRow));
        //String startTimeDifference = data.remove(startTimeDifferenceLocationInTheRow);
        //if (startTimeDifference != null && startTimeDifference.contains("'")) {
        //   startTimeDifference = startTimeDifference.replace("'", "");
        // }

        LocalDateTime arrivalTimeScheduled = this.converter.convertStringTimeToDate(data.remove(arrivalTimeScheduledLocationInTheRow));
        //   LocalDateTime arrivalTimeActual = this.converter.convertStringTimeToDate(data.remove(arrivalTimeActualLocationInTheRow));
        //  String arrivalTimeDifference = data.remove(arrivalTimeDifferenceLocationInTheRow);
        // if (arrivalTimeDifference != null && arrivalTimeDifference.contains("'")) {
        //   arrivalTimeDifference = arrivalTimeDifference.replace("'", "");
        //}
        return new TripPeriod(tripPeriodType, startTimeScheduled, null, null, arrivalTimeScheduled, null, null);
    }
}
