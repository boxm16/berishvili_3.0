/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Service;

@Service
public class RequestDataDecoder {

    public LinkedHashMap<String, ArrayList<String>> decodeRequestedRoutesDates(String requestedRoutesDates) {
        LinkedHashMap<String, ArrayList<String>> routesDates = new LinkedHashMap<>();

        //trimming and cleaning input
        requestedRoutesDates = requestedRoutesDates.trim();
        if (requestedRoutesDates.length() == 0) {
            return routesDates;
        }
        if (requestedRoutesDates.substring(requestedRoutesDates.length() - 1, requestedRoutesDates.length()).equals(",")) {
            requestedRoutesDates = requestedRoutesDates.substring(0, requestedRoutesDates.length() - 1).trim();
        }
        String[] routeDatesArray = requestedRoutesDates.split(",");
        for (String routeDate : routeDatesArray) {

            String[] routeDateArray = routeDate.split(":");
            String routeNumber = routeDateArray[0];
            String dateStamp = routeDateArray[1];

            ArrayList<String> dateStamps;
            if (routesDates.containsKey(routeNumber)) {
                dateStamps = routesDates.get(routeNumber);
            } else {
                dateStamps = new ArrayList<>();
            }
            dateStamps.add(dateStamp);
            routesDates.put(routeNumber, dateStamps);

        }
        return routesDates;
    }
}
