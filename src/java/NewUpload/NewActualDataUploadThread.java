package NewUpload;

import BasicModels.Route;
import Route.RouteDao;
import Service.StaticsDispatcher;
import TechMan.TechManDao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class NewActualDataUploadThread extends Thread {

    private final String filePath;

    NewActualDataUploadThread(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        String nowStatus;
        String message = "";
        LocalDateTime now;

        now = LocalDateTime.now();
        StaticsDispatcher.setUploading(true);
        nowStatus = now + " : " + "File transfer to server completed ";
        System.out.println(nowStatus);
        message += "<br>" + nowStatus;
        StaticsDispatcher.setUploadingProgressMessage(message);

        now = LocalDateTime.now();
        nowStatus = now + " : " + "Starting working on uploaded file ";
        System.out.println(nowStatus);
        message += "<br>" + nowStatus;
        StaticsDispatcher.setUploadingProgressMessage(message);

        RouteDao routeDao = new RouteDao();
        now = LocalDateTime.now();
        nowStatus = now + " : " + "Getting routes data from database : ....";
        System.out.println(nowStatus);
        message += "<br>" + nowStatus;
        StaticsDispatcher.setUploadingProgressMessage(message);

        TreeMap<String, Route> routesDataFromDB = routeDao.getRoutesDataFromDB();
        now = LocalDateTime.now();
        nowStatus = now + " : " + "----------------------DONE--------------------";
        System.out.println(nowStatus);
        message += "<br>" + nowStatus;
        StaticsDispatcher.setUploadingProgressMessage(message);

        NewActualRouteFactory routeFactory = new NewActualRouteFactory();
        now = LocalDateTime.now();
        nowStatus = now + " : " + "Getting routes numbers from  excel file : ....";
        System.out.println(nowStatus);
        message += "<br>" + nowStatus;
        StaticsDispatcher.setUploadingProgressMessage(message);

        HashMap<String, DataContainer> routeNumbersFromExcelFile = routeFactory.getRouteNumbers(this.filePath);

        now = LocalDateTime.now();
        nowStatus = now + " : " + "----------------------DONE--------------------";
        System.out.println(nowStatus);
        message += "<br>" + nowStatus;
        StaticsDispatcher.setUploadingProgressMessage(message);

        TreeMap<String, Route> routesForInsertion = new TreeMap<>();
        ArrayList<String> unregisteredRoutes = new ArrayList<>();

        now = LocalDateTime.now();
        nowStatus = now + " : " + "Checking if there are new routes in uploaded excel file: ....";
        System.out.println(nowStatus);
        message += "<br>" + nowStatus;
        StaticsDispatcher.setUploadingProgressMessage(message);

        for (Map.Entry<String, DataContainer> routeNumberFromExcelFileEntrySet : routeNumbersFromExcelFile.entrySet()) {
            String routeNumberFromExcelFile = routeNumberFromExcelFileEntrySet.getKey();
            Route routeFromDatabase = routesDataFromDB.get(routeNumberFromExcelFile);
            if (routeFromDatabase == null) {
                unregisteredRoutes.add(routeNumberFromExcelFile);
                Route route = new Route();
                route.setNumber(routeNumberFromExcelFile);
                routesForInsertion.put(routeNumberFromExcelFile, route);
            } else {
                routesForInsertion.put(routeNumberFromExcelFile, routeFromDatabase);
            }
        }

        if (unregisteredRoutes.size() > 0) {
            now = LocalDateTime.now();
            nowStatus = now + " : " + "DONE: NEW ROUTES FOUND IN NEW EXCEL FILE. Starting inserting them into database";
            System.out.println(nowStatus);
            message += "<br>" + nowStatus;
            StaticsDispatcher.setUploadingProgressMessage(message);

            routeDao.deleteRoutes();
            routeDao.insertRoutes(routesForInsertion);
            now = LocalDateTime.now();
            nowStatus = now + " : " + "INSERTION OF NEW ROUTES INTO DATABASE COMPLETED";
            System.out.println(nowStatus);
            message += "<br>" + nowStatus;
            StaticsDispatcher.setUploadingProgressMessage(message);
        } else {
            now = LocalDateTime.now();
            nowStatus = now + " : " + "DONE:NO NEW ROUTES FOUND IN NEW EXCEL FILE. ";
            System.out.println(nowStatus);
            message += "<br>" + nowStatus;
            StaticsDispatcher.setUploadingProgressMessage(message);
        }

        //----------here i delete old data from database
        TechManDao techManDao = new TechManDao();

        String tripPeriodTableDeletionStatus = techManDao.deleteTripPeriodTable();
        now = LocalDateTime.now();
        nowStatus = now + " : " + tripPeriodTableDeletionStatus;
        System.out.println(nowStatus);
        message += "<br>" + nowStatus;
        StaticsDispatcher.setUploadingProgressMessage(message);

        String tripVoucherTableDeletionStatus = techManDao.deleteTripVoucherTable();
        now = LocalDateTime.now();
        nowStatus = now + " : " + tripVoucherTableDeletionStatus;
        System.out.println(nowStatus);
        message += "<br>" + nowStatus;
        StaticsDispatcher.setUploadingProgressMessage(message);

        String createTripVoucherTableStatus = techManDao.createTripVoucherTable();
        String createTripPeriodTableStatus = techManDao.createTripPeriodTable();

        now = LocalDateTime.now();
        nowStatus = now + " : " + createTripVoucherTableStatus + " : " + createTripPeriodTableStatus;
        System.out.println(nowStatus);
        message += "<br>" + nowStatus;
        StaticsDispatcher.setUploadingProgressMessage(message);

        NewActualDataUploadDao newActualDataUploadDao = new NewActualDataUploadDao();

        //-----------------NOW GETTING ROUTES---------------------
        Route route;
        for (Map.Entry<String, DataContainer> routeNumberFromExcelFileEntrySet : routeNumbersFromExcelFile.entrySet()) {

            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            now = LocalDateTime.now();
            nowStatus = now + " : " + "Route Number : " + routeNumberFromExcelFileEntrySet.getKey();
            System.out.println(nowStatus);
            message += "<br>" + nowStatus;
            route = routeFactory.getRoute(this.filePath, routeNumberFromExcelFileEntrySet.getValue());
            //--------------------------SYSTEM OUT--------------------------
            /*    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("ROUTE NUM:" + route.getNumber());
            TreeMap<String, Day> days = route.getDays();
            for (Map.Entry<String, Day> dayEntry : days.entrySet()) {
                Day day = dayEntry.getValue();
                System.out.println("DATE:" + day.getDateStamp());

                TreeMap<Short, Exodus> exoduses = day.getExoduses();
                for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                    Exodus exodus = exodusEntry.getValue();
                    System.out.println("Exodus: " + exodus.getNumber());
                    TreeMap<String, TripVoucher> tripVouchers = exodus.getTripVouchers();
                    for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                        TripVoucher tripVoucher = tripVoucherEntry.getValue();
                        System.out.println("Trip Voucher Number: " + tripVoucher.getNumber());

                        ArrayList<TripPeriod> tripPeriods = tripVoucher.getTripPeriods();
                        System.out.println(".............TripPeriods...........");
                        for (TripPeriod tripPeriod : tripPeriods) {
                            System.out.println("TripPeriodStartingTimeScheduled: " + tripPeriod.getStartTimeScheduledString());
                            System.out.println("TripPeriodStartingTimeActual: " + tripPeriod.getStartTimeActualString());
                            System.out.println("TripPeriodStartingTimeDiffrence: " + tripPeriod.getStartTimeDifference());
                            System.out.println("TripPeriodArivalTimeScheduled: " + tripPeriod.getArrivalTimeScheduledString());
                            System.out.println("TripPeriodArivalTimeActual: " + tripPeriod.getArrivalTimeActualString());
                            System.out.println("TripPeriodArivalTimeDifference: " + tripPeriod.getArrivalTimeDifference());

                        }
                        System.out.println("......,,,,,,,,,,,,,TripPeriods,,,,,,,,...........");
                    }
                }
            }*/
            //    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

            newActualDataUploadDao.insertRoute(route);

            StaticsDispatcher.setUploadingProgressMessage(message);
            System.out.println("##########################################################");
        }

        StaticsDispatcher.setUploading(false);
    }

}
