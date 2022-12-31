package Uploading;

import BasicModels.Day;
import BasicModels.Exodus;
import BasicModels.Route;
import BasicModels.TripPeriod;
import BasicModels.TripVoucher;
import Service.StaticsDispatcher;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class PlannedDataUploadThread extends Thread {

    private final String filePath;

    public PlannedDataUploadThread(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {

        StaticsDispatcher.setUploading(true);

        PlannedRouteFactory routeFactory = new PlannedRouteFactory();
        TreeMap<String, Route> routesFromExcelFile = routeFactory.getRoutesFromExcelFile(this.filePath);
       
        for (Map.Entry<String, Route> routeEntry : routesFromExcelFile.entrySet()) {

            Route route = routeEntry.getValue();
            TreeMap<Date, Day> days = route.getDays();
            System.out.println("Route:"+route.getNumber());
            for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
                Day day = dayEntry.getValue();
                TreeMap<Short, Exodus> exoduses = day.getExoduses();
                System.out.println("Date"+day.getDate());
                for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                    Exodus exodus = exodusEntry.getValue();
                    TreeMap<String, TripVoucher> tripVouchers = exodus.getTripVouchers();
                    System.out.println("Exodus Number" + exodus.getNumber());
                    for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                        TripVoucher tripVoucher = tripVoucherEntry.getValue();
                        ArrayList<TripPeriod> tripPeriods = tripVoucher.getTripPeriods();
                        System.out.println("TripVoucherNumber" + tripVoucher.getNumber());
                        for (TripPeriod tripPeriod : tripPeriods) {
                            System.out.println(tripPeriod.getStartTimeScheduled());
                        }
                        System.out.println("------------------------");
                    }
                    System.out.println("++++++++++++++++++++++++++");
                }
                 System.out.println("=========================");
            }
              System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }

        StaticsDispatcher.setUploading(false);
    }
}
