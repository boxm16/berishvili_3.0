package Service;

import BasicModels.RoutesPager;
import org.springframework.stereotype.Service;

@Service
public class PagerFactory {

    public RoutesPager createRoutesPager(String routesDates, String type) {

        RoutesPager routesPager = new RoutesPager(type);

        //trimming and cleaning input
        routesDates = routesDates.trim();
        if (routesDates.length() == 0) {
            return routesPager;
        }
        if (routesDates.substring(routesDates.length() - 1, routesDates.length()).equals(",")) {
            routesDates = routesDates.substring(0, routesDates.length() - 1).trim();
        }
        String[] routeDatesArray = routesDates.split(",");
        for (String routeDate : routeDatesArray) {

            String[] routeDateArray = routeDate.split(":");
            String routeNumber = routeDateArray[0];
            String dateStamp = routeDateArray[1];
            routesPager.addRouteNumber(routeNumber);
            routesPager.addDateStamp(dateStamp);
        }
        return routesPager;
    }
}
