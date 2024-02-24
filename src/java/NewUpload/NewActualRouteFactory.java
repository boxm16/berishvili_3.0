package NewUpload;

import BasicModels.Day;
import BasicModels.Exodus;
import BasicModels.Route;
import BasicModels.TripPeriod;
import BasicModels.TripVoucher;
import Service.Converter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class NewActualRouteFactory {

    private final Converter converter;

    private NewExcelReader newExcelReader;
    private int excelRowIndex;

    public NewActualRouteFactory() {
        this.converter = new Converter();
        this.excelRowIndex = 6;
    }

    LinkedHashMap<String, DataContainer> getRouteNumbers(String filePath) {
        newExcelReader = new NewExcelReader();
        LinkedHashMap<String, DataContainer> routeNumbers = newExcelReader.getRouteNumbersAndIndices(filePath);

        return routeNumbers;
    }

    Route getRoute(String filePath, DataContainer dataContainer) {
        System.out.println("-+-+-+-+-+-+-+-+-+-STARTING CREATING ROUTE-+-+-+-+-+-+-+-+-+-");
        System.out.println("                Getting Data From Excel File");
        HashMap<String, String> cellsForRoute = newExcelReader.getCellsFromExcelFile(filePath, dataContainer);
        System.out.println("             Excel Data Size: " + cellsForRoute.size());
        System.out.println("+++++++++++++++++CREATING ROUTE FROM EXCEL DATA++++++++++++++++");
        Route route = convertExcelDataToRoute(dataContainer, cellsForRoute);
        System.out.println("___________________CREATING ROUTE COMPLETED___________________");

        return route;
    }

    private Route convertExcelDataToRoute(DataContainer dataContainer, HashMap<String, String> data) {

        Route route = new Route();

        while (!data.isEmpty()) {
            excelRowIndex++;
            // System.out.println("ROW INDEX: " + excelRowIndex);
            String routeNumberLocationInTheRow = new StringBuilder("I").append(String.valueOf(excelRowIndex)).toString();
            String routeNumber = data.remove(routeNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map
            if (routeNumber == null || !routeNumber.equals(dataContainer.getRouteNumber())) {//in theory this means that you reached the end of rows with data
                System.out.println("LEFT DATA SIZE++: " + data.size());
                data = new HashMap<>();
                System.out.println("LEFT DATA SIZE--: " + data.size());
                break;
            }
            route.setNumber(routeNumber);
            route = addRowElementsToBasicRoute(route, data, excelRowIndex);

        }

        return route;
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

        TreeMap<Short, Exodus> exoduses = day.getActualExoduses();
        Exodus exodus;
        if (exoduses.containsKey(exodusNumber)) {
            exodus = exoduses.get(exodusNumber);
        } else {
            exodus = new Exodus();
            exodus.setNumber(exodusNumber);
        }

        exodus = addRowElementsToExodus(exodus, data, rowIndex);
        exoduses.put(exodusNumber, exodus);
        day.setActualExoduses(exoduses);
        return day;
    }

    private Exodus addRowElementsToExodus(Exodus exodus, HashMap<String, String> data, int rowIndex) {
        //----first baseNumber

        String baseNumberLocationInTheRow = new StringBuilder("A").append(String.valueOf(rowIndex)).toString();
        short baseNumber = Short.valueOf(data.remove(baseNumberLocationInTheRow));
        //  System.out.println("Nase Number: " + baseNumber);

        String tripVoucherNumberLocationInTheRow = new StringBuilder("H").append(String.valueOf(rowIndex)).toString();
        String tripVoucherNumber = data.remove(tripVoucherNumberLocationInTheRow);
        //  System.out.println("TripVoucherNumber at reading: " + tripVoucherNumber);

        String shiftLocationInTheRow = new StringBuilder("E").append(String.valueOf(rowIndex)).toString();
        String shift = data.remove(shiftLocationInTheRow);
        if (shift.contains(".")) {
            tripVoucherNumber = tripVoucherNumber + "@" + shift;
        }

        TreeMap<String, TripVoucher> tripVouchers = exodus.getTripVouchers();
        TripVoucher tripVoucher;
        if (tripVouchers.containsKey(tripVoucherNumber)) {
            tripVoucher = tripVouchers.get(tripVoucherNumber);
        } else {
            tripVoucher = new TripVoucher();
            String busNumberLocationInTheRow = new StringBuilder("C").append(String.valueOf(rowIndex)).toString();
            String busNumber = data.remove(busNumberLocationInTheRow);
            //  System.out.println("BusNumber: " + busNumber);

            String busTypeLocationInTheRow = new StringBuilder("B").append(String.valueOf(rowIndex)).toString();
            String busType = data.remove(busTypeLocationInTheRow);
            //   System.out.println("BusType: " + busType);

            String driverNumberLocationInTheRow = new StringBuilder("D").append(String.valueOf(rowIndex)).toString();
            String driverNumber = data.remove(driverNumberLocationInTheRow);
            //   System.out.println("DirverNumber: " + driverNumber);

            String driverNameLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
            String driverName = data.remove(driverNameLocationInTheRow);
            //   System.out.println("DriverName: " + driverName);

            String tripPeriodsScheduledTotalLocationInTheRow = new StringBuilder("S").append(String.valueOf(rowIndex)).toString();
            String tripPeriodsScheduledTotalString = data.remove(tripPeriodsScheduledTotalLocationInTheRow);
            int tripPeriodsScheduledTotal = 0;
            if (tripPeriodsScheduledTotalString == null) {
                //  System.out.println("tripPeriodsScheduledTotalString is null at: " + rowIndex);
            } else {
                float tripPeriodsScheduledTotalF = Float.parseFloat(tripPeriodsScheduledTotalString);
                tripPeriodsScheduledTotal = (int) tripPeriodsScheduledTotalF;
            }
            //  System.out.println("tripPeriodsScheduledTotal: " + tripPeriodsScheduledTotal);

            String tripPeriodsActualTotalLocationInTheRow = new StringBuilder("T").append(String.valueOf(rowIndex)).toString();
            int tripPeriodsActualTotal = 0;
            String tripPeriodsActualTotalString = data.remove(tripPeriodsActualTotalLocationInTheRow);
            if (tripPeriodsActualTotalString == null) {
                //    System.out.println("tripPeriodsActualTotalString is null at: " + rowIndex);
            } else {
                float tripPeriodsActualTotalF = Float.parseFloat(tripPeriodsActualTotalString);
                tripPeriodsActualTotal = (int) tripPeriodsActualTotalF;
            }
            //   System.out.println("TripPeriodsActualTotal: " + tripPeriodsActualTotal);

            float kilometrageScheduled = 0f;
            String kilometrageScheduledLocationInTheRow = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
            String kilommetrageString = data.remove(kilometrageScheduledLocationInTheRow);
            if (kilommetrageString == null) {
                //   System.out.println("KILOMMERAGE SCHEDULED NULL: " + rowIndex);
            } else {
                kilometrageScheduled = Float.parseFloat(kilommetrageString);
            }
            //  System.out.println("KilometrageScheduled: " + kilometrageScheduled);

            float kilometrageActual = 0f;
            String kilometrageActualLocationInTheRow = new StringBuilder("R").append(String.valueOf(rowIndex)).toString();
            String kilometrageActualString = data.remove(kilometrageActualLocationInTheRow);
            if (kilometrageActualString == null) {
                //  System.out.println("kilometrageActualString NULLLLLS:" + rowIndex);
            } else {
                kilometrageActual = Float.parseFloat(kilometrageActualString);
            }
            //  System.out.println("KilometrageActual: " + kilometrageActual);

            String baseLeavingTimeScheduledLocationInTheRow = new StringBuilder("K").append(String.valueOf(rowIndex)).toString();
            LocalDateTime baseLeavingTimeScheduled = null;
            String baseLeavingTimeScheduledString = data.remove(baseLeavingTimeScheduledLocationInTheRow);
            if (baseLeavingTimeScheduledString == null) {
                //     System.out.println("baseLeavingTimeScheduledString Is NULL:" + rowIndex);
            } else {
                baseLeavingTimeScheduled = this.converter.convertStringTimeToDate(baseLeavingTimeScheduledString);
            }
            //   System.out.println("BaseLeavingTimeScheduled: " + baseLeavingTimeScheduled);

            LocalDateTime baseLeavingTimeActual = null;
            String baseLeavingTimeActualLocationInTheRow = new StringBuilder("L").append(String.valueOf(rowIndex)).toString();
            String baseLeavingTimeActualString = data.remove(baseLeavingTimeActualLocationInTheRow);
            if (baseLeavingTimeActualString == null) {
                //    System.out.println("baseLeavingTimeActualString Is NULL:" + rowIndex);
            } else {
                baseLeavingTimeActual = this.converter.convertStringTimeToDate(baseLeavingTimeActualString);
            }
            //    System.out.println("BaseLeavingTimeActual: " + baseLeavingTimeActual);

            LocalDateTime baseLeavingTimeRedacted = null;
            String baseLeavingTimeRedactedLocationInTheRow = new StringBuilder("M").append(String.valueOf(rowIndex)).toString();
            String baseLeavingTimeRedactedString = data.remove(baseLeavingTimeRedactedLocationInTheRow);
            if (baseLeavingTimeRedactedString == null) {
                //  System.out.println("baseLeavingTimeRedactedString IS NULL at:" + rowIndex);
            } else {
                baseLeavingTimeRedacted = this.converter.convertStringTimeToDate(baseLeavingTimeRedactedString);
            }
            // System.out.println("BaseLeavingTimeRedacted: " + baseLeavingTimeRedacted);

            LocalDateTime baseReturnTimeScheduled = null;
            String baseReturnTimeScheduledLocationInTheRow = new StringBuilder("N").append(String.valueOf(rowIndex)).toString();
            String baseReturnTimeScheduledString = data.remove(baseReturnTimeScheduledLocationInTheRow);
            if (baseReturnTimeScheduledString == null) {
                //     System.out.println("baseReturnTimeScheduledString IS NULL AT: " + rowIndex);
            } else {
                baseReturnTimeScheduled = this.converter.convertStringTimeToDate(baseReturnTimeScheduledString);
            }
            //   System.out.println("BaseReturnTimeScheduled: " + baseReturnTimeScheduled);

            LocalDateTime baseReturnTimeActual = null;
            String baseRetrunTimeActualLocationInTheRow = new StringBuilder("O").append(String.valueOf(rowIndex)).toString();
            String baseRetrunTimeActualString = data.remove(baseRetrunTimeActualLocationInTheRow);
            if (baseRetrunTimeActualString == null) {
                //     System.out.println("baseRetrunTimeActualString is NULL at: " + rowIndex);
            } else {
                baseReturnTimeActual = this.converter.convertStringTimeToDate(baseRetrunTimeActualString);
            }
            // System.out.println("BaseRetrunTimeActual: " + baseRetrunTimeActual);

            LocalDateTime baseReturnTimeRedacted = null;
            String baseReturnTimeRedactedLocationInTheRow = new StringBuilder("P").append(String.valueOf(rowIndex)).toString();
            String baseReturnTimeRedactedString = data.remove(baseReturnTimeRedactedLocationInTheRow);
            if (baseReturnTimeRedactedString == null) {
                //    System.out.println("baseReturnTimeRedactedString is NULL at: " + rowIndex);
            } else {
                baseReturnTimeRedacted = this.converter.convertStringTimeToDate(baseReturnTimeRedactedString);
            }
            //  System.out.println("BaseReturnTimeRedacted: " + baseReturnTimeRedacted);

            ////  String noteLocationInTheRow = new StringBuilder("AH").append(String.valueOf(rowIndex)).toString();
            //  String note = data.remove(noteLocationInTheRow);
            tripVoucher.setBusNumber(busNumber);
            tripVoucher.setBaseNumber(baseNumber);
            tripVoucher.setBusType(busType);
            tripVoucher.setDriverNumber(driverNumber);
            tripVoucher.setDriverName(driverName);
            tripVoucher.setShift(shift);

            tripVoucher.setTripPeriodsScheduledTotal(tripPeriodsScheduledTotal);
            tripVoucher.setTripPeriodsActualTotal(tripPeriodsActualTotal);

            tripVoucher.setKilometrageScheduled(kilometrageScheduled);
            tripVoucher.setKilometrageActual(kilometrageActual);

            tripVoucher.setBaseLeavingTimeScheduled(baseLeavingTimeScheduled);
            tripVoucher.setBaseLeavingTimeActual(baseLeavingTimeActual);
            tripVoucher.setBaseLeavingTimeRedacted(baseLeavingTimeRedacted);

            tripVoucher.setBaseReturnTimeScheduled(baseReturnTimeScheduled);
            tripVoucher.setBaseReturnTimeActual(baseReturnTimeActual);
            tripVoucher.setBaseReturnTimeRedacted(baseReturnTimeRedacted);

            //   tripVoucher.setNote(note);
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
        String startTimeActualLocationInTheRow = new StringBuilder("W").append(String.valueOf(rowIndex)).toString();
        String startTimeDifferenceLocationInTheRow = new StringBuilder("X").append(String.valueOf(rowIndex)).toString();

        String arrivalTimeScheduledLocationInTheRow = new StringBuilder("Y").append(String.valueOf(rowIndex)).toString();
        String arrivalTimeActualLocationInTheRow = new StringBuilder("Z").append(String.valueOf(rowIndex)).toString();
        String arrivalTimeDifferenceLocationInTheRow = new StringBuilder("AA").append(String.valueOf(rowIndex)).toString();

        LocalDateTime startTimeScheduled = this.converter.convertStringTimeToDate(data.remove(startTimeScheduledLocationInTheRow));
        LocalDateTime startTimeActual = this.converter.convertStringTimeToDate(data.remove(startTimeActualLocationInTheRow));
        String startTimeDifference = data.remove(startTimeDifferenceLocationInTheRow);
        if (startTimeDifference != null && startTimeDifference.contains("'")) {
            startTimeDifference = startTimeDifference.replace("'", "");
        }

        LocalDateTime arrivalTimeScheduled = this.converter.convertStringTimeToDate(data.remove(arrivalTimeScheduledLocationInTheRow));
        LocalDateTime arrivalTimeActual = this.converter.convertStringTimeToDate(data.remove(arrivalTimeActualLocationInTheRow));
        String arrivalTimeDifference = data.remove(arrivalTimeDifferenceLocationInTheRow);
        if (arrivalTimeDifference != null && arrivalTimeDifference.contains("'")) {
            arrivalTimeDifference = arrivalTimeDifference.replace("'", "");
        }

        return new TripPeriod(tripPeriodType, startTimeScheduled, startTimeActual, startTimeDifference, arrivalTimeScheduled, arrivalTimeActual, arrivalTimeDifference);
    }

    private TripPeriod createTripPeriodFromRightSide(HashMap<String, String> data, int rowIndex, String tripPeriodType) {
        String startTimeScheduledLocationInTheRow = new StringBuilder("AB").append(String.valueOf(rowIndex)).toString();
        String startTimeActualLocationInTheRow = new StringBuilder("AC").append(String.valueOf(rowIndex)).toString();
        String startTimeDifferenceLocationInTheRow = new StringBuilder("AD").append(String.valueOf(rowIndex)).toString();

        String arrivalTimeScheduledLocationInTheRow = new StringBuilder("AE").append(String.valueOf(rowIndex)).toString();
        String arrivalTimeActualLocationInTheRow = new StringBuilder("AF").append(String.valueOf(rowIndex)).toString();
        String arrivalTimeDifferenceLocationInTheRow = new StringBuilder("AG").append(String.valueOf(rowIndex)).toString();

        LocalDateTime startTimeScheduled = this.converter.convertStringTimeToDate(data.remove(startTimeScheduledLocationInTheRow));
        LocalDateTime startTimeActual = this.converter.convertStringTimeToDate(data.remove(startTimeActualLocationInTheRow));
        String startTimeDifference = data.remove(startTimeDifferenceLocationInTheRow);
        if (startTimeDifference != null && startTimeDifference.contains("'")) {
            startTimeDifference = startTimeDifference.replace("'", "");
        }

        LocalDateTime arrivalTimeScheduled = this.converter.convertStringTimeToDate(data.remove(arrivalTimeScheduledLocationInTheRow));
        LocalDateTime arrivalTimeActual = this.converter.convertStringTimeToDate(data.remove(arrivalTimeActualLocationInTheRow));
        String arrivalTimeDifference = data.remove(arrivalTimeDifferenceLocationInTheRow);
        if (arrivalTimeDifference != null && arrivalTimeDifference.contains("'")) {
            arrivalTimeDifference = arrivalTimeDifference.replace("'", "");
        }
        return new TripPeriod(tripPeriodType, startTimeScheduled, startTimeActual, startTimeDifference, arrivalTimeScheduled, arrivalTimeActual, arrivalTimeDifference);
    }

}
