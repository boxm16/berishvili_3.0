/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uploading;

import BasicModels.Route;
import Route.RouteDao;
import Service.StaticsDispatcher;
import TechMan.TechManDao;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class ActualDataUploadThread extends Thread {

    private final String filePath;

    public ActualDataUploadThread(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {

        StaticsDispatcher.setUploading(true);
        String uploadingProgressMessage = "Upload Progress ";
        StaticsDispatcher.setUploadingProgressMessage(uploadingProgressMessage);

        RouteDao routeDao = new RouteDao();
        uploadingProgressMessage += "<br> Getting routes data from database : ....";
        TreeMap<String, Route> routesDataFromDB = routeDao.getRoutesDataFromDB();
        uploadingProgressMessage += "<br> Getting routes data from database : DONE";
        StaticsDispatcher.setUploadingProgressMessage(uploadingProgressMessage);

        ActualRouteFactory routeFactory = new ActualRouteFactory();
        uploadingProgressMessage += "<br> Getting routes data from excel File : ....";
        TreeMap<String, Route> routesFromExcelFile = routeFactory.getRoutesFromExcelFile(this.filePath);
        uploadingProgressMessage += "<br> Getting routes data from excel File : DONE";
        StaticsDispatcher.setUploadingProgressMessage(uploadingProgressMessage);

        // Here i check if in excel file there are new, unregistered in DB routes
        //and parrallely i take route data from database and adding this data to route from excel file
        ArrayList<String> unregisteredRoutes = new ArrayList<>();
        for (Map.Entry<String, Route> routesFromExcelFileEntry : routesFromExcelFile.entrySet()) {
            Route routeFromExcelFile = routesFromExcelFileEntry.getValue();
            String routeNumberFromExcelFile = routesFromExcelFileEntry.getKey();
            Route routeFromDatabase = routesDataFromDB.get(routeNumberFromExcelFile);
            if (routeFromDatabase == null) {
                unregisteredRoutes.add(routeNumberFromExcelFile);
            } else {
                routeFromExcelFile.setScheme(routeFromDatabase.getScheme());
                routeFromExcelFile.setaPoint(routeFromDatabase.getaPoint());
                routeFromExcelFile.setbPoint(routeFromDatabase.getbPoint());
            }
        }
        if (unregisteredRoutes.size() > 0) {
            System.out.println("-----------");
            uploadingProgressMessage += "<br> Comparing routes data from database and excel file: ROUTES DATA DIFFER";
            StaticsDispatcher.setUploadingProgressMessage(uploadingProgressMessage);

            routeDao.deleteRoutes();
            routeDao.insertRoutes(routesFromExcelFile);
            uploadingProgressMessage += "<br> Updating routes data in database: DONE";
            StaticsDispatcher.setUploadingProgressMessage(uploadingProgressMessage);

        } else {
            uploadingProgressMessage += "<br> Comparing routes data from database and excel file: ROUTES DATA MATCH";
            StaticsDispatcher.setUploadingProgressMessage(uploadingProgressMessage);
        }
        LocalTime now1 = LocalTime.now();
        System.out.println(now1 + ":Starting DATABASE PART");

        TechManDao techManDao = new TechManDao();
        String tripPeriodTableDeletionStatus = techManDao.deleteTripPeriodTable();
        LocalTime now2 = LocalTime.now();
        System.out.println(now2 + ": " + tripPeriodTableDeletionStatus);
        String tripVoucherTableDeletionStatus = techManDao.deleteTripVoucherTable();
        LocalTime now3 = LocalTime.now();
        System.out.println(now3 + ": " + tripVoucherTableDeletionStatus);
        String createTripVoucherTableStatus = techManDao.createTripVoucherTable();
        String createTripPeriodTableStatus = techManDao.createTripPeriodTable();
        LocalTime now4 = LocalTime.now();
        System.out.println(now3 + ": " + createTripVoucherTableStatus+" : "+createTripPeriodTableStatus);

        ActualDataUploadDao actualDataUploadDao = new ActualDataUploadDao();

        String insertionResult = actualDataUploadDao.insertNewData(routesFromExcelFile);
        if (insertionResult.isEmpty()) {
              LocalTime now5 = LocalTime.now();
            System.out.println(now5+": ALL SET");
        } else {
            System.out.println(insertionResult);
        }
        StaticsDispatcher.setUploading(false);

    }
}
