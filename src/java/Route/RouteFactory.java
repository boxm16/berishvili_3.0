package Route;

import BasicModels.Route;
import Service.ExcelReader;
import java.util.HashMap;
import java.util.TreeMap;

public class RouteFactory {

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
        int excelRowIndex = 2;

        while (!data.isEmpty()) {
            String routeNumberLocationInTheRow = new StringBuilder("A").append(String.valueOf(excelRowIndex)).toString();
            String routeNumber = data.remove(routeNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String schemeLocationInTheRow = new StringBuilder("B").append(String.valueOf(excelRowIndex)).toString();
            String scheme = data.remove(schemeLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String aPointLocationInTheRow = new StringBuilder("C").append(String.valueOf(excelRowIndex)).toString();
            String aPoint = data.remove(aPointLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String bPointLocationInTheRow = new StringBuilder("D").append(String.valueOf(excelRowIndex)).toString();
            String bPoint = data.remove(bPointLocationInTheRow);//at the same time reading and removing the cell from hash Map

            if (routeNumber == null) {//in theory this means that you reached the end of rows with data
                break;
            }
            Route route = new Route();
            route.setNumber(routeNumber);
            route.setScheme(scheme);
            route.setaPoint(aPoint);
            route.setbPoint(bPoint);
            routes.put(routeNumber, route);
            excelRowIndex++;
        }
        return routes;
    }

}
