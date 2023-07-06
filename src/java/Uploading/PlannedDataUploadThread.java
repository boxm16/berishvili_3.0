package Uploading;

import BasicModels.Route;
import Route.RouteDao;
import Service.StaticsDispatcher;
import java.util.ArrayList;
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
        String uploadingProgressMessage = "Upload Progress ";
        StaticsDispatcher.setUploadingProgressMessage(uploadingProgressMessage);

        RouteDao routeDao = new RouteDao();
        TreeMap<String, Route> routesDataFromDB = routeDao.getRoutesDataFromDB();
        uploadingProgressMessage += "<br> Getting routes data from database : DONE";
        StaticsDispatcher.setUploadingProgressMessage(uploadingProgressMessage);

        PlannedRouteFactory routeFactory = new PlannedRouteFactory();
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
        uploadingProgressMessage += "<br> DATABASE PART STARTED:.....";
        StaticsDispatcher.setUploadingProgressMessage(uploadingProgressMessage);
        PlannedDataUploadDao plannedDataUploadDao = new PlannedDataUploadDao();
        String insertionResult = plannedDataUploadDao.insertNewData(routesFromExcelFile);
        System.out.println("ALL SET");
        StaticsDispatcher.setUploading(false);

    }
}
