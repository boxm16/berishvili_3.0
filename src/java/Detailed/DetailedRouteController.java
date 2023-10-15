package Detailed;

import BasicModels.Day;
import BasicModels.Exodus;
import BasicModels.Route;
import BasicModels.TripPeriod;
import BasicModels.TripVoucher;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DetailedRouteController {

    LinkedHashMap<String, ArrayList<String>> routesDates;

    @Autowired
    private DetailedRouteDao detailedRouteDao;

    @RequestMapping(value = "detailedRoutes")
    public String detailedRoutes(@RequestParam("routes:dates") String requestedRoutesDates, ModelMap model, HttpSession session) {
        this.routesDates = decodeRequestedRoutesDates(requestedRoutesDates);
        DetailedRoute detailedRoute;
        if (this.routesDates.size() == 0) {
            detailedRoute = new DetailedRoute();
        } else {
            Map.Entry<String, ArrayList<String>> firstEntry = this.routesDates.entrySet().stream().findFirst().get();
            detailedRoute = detailedRouteDao.getDetailedRoute(firstEntry.getKey(), firstEntry.getValue());
        }

        model.addAttribute("routeNumbers", this.routesDates.keySet());
        //model.addAttribute("detailedRoute", detailedRoute);
        model.addAttribute("detailedRouteNumber", detailedRoute.getNumber());
        return "detailed/detailedRoutes";
    }

    @RequestMapping(value = "/requestDetailedRoute", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String requestDetailedRoute(@RequestParam("number") String requestedRouteNumber) {
        System.out.println(requestedRouteNumber);
        StringBuilder response = new StringBuilder();
        response.append("<tr style=\"background-color:lightgreen\"'><td colspan='16' align=\"center\">მარშრუტი #:").append(requestedRouteNumber).append("</td></tr> ");
        ArrayList<String> dates = this.routesDates.get(requestedRouteNumber);
        Route plannedRoute = detailedRouteDao.getDetailedRoute(requestedRouteNumber, dates);
        TreeMap<String, Day> days = plannedRoute.getDays();
        for (Map.Entry<String, Day> dayEntry : days.entrySet()) {
            response.append("<tr style=\"background-color:#4863A0\"> <td colspan='16' align=\"center\">თარიღი:").append(dayEntry.getValue().getDateStamp()).append("</td></tr>");
            TreeMap<Short, Exodus> exoduses = dayEntry.getValue().getExoduses();
            for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                response.append("<tr style=\"background-color:lightblue\"><td colspan='16' align=\"center\">გასვლა #:").append(exodusEntry.getValue().getNumber()).append("</td></tr>");
                TreeMap<String, TripVoucher> tripVouchers = exodusEntry.getValue().getTripVouchers();
                for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                    response.append("<tr style=\"background-color:lightblue\"><td colspan='16' align=\"center\">მარშრუტი#:").append(requestedRouteNumber).append(". თარიღი:").append(dayEntry.getValue().getDateStamp()).append(". გასვლა#:").append(exodusEntry.getValue().getNumber()).append(". საგზურის #:").append(tripVoucherEntry.getValue().getNumber()).append(".</td></tr>");
                    ArrayList<TripPeriod> tripPeriods = tripVoucherEntry.getValue().getTripPeriods();
                    for (TripPeriod tripPeriod : tripPeriods) {
                        response.append("<tr>").
                                append("<td align=\"center\">").
                                append(tripPeriod.getStartTimeScheduledString()).
                                append("</td>").
                                append("<td align=\"center\">").
                                append(tripPeriod.getStartTimeActualString()).
                                append("</td>").
                                append("<td align=\"center\">").
                                append(tripPeriod.getTypeG()).
                                append("</td>").
                                append("<td align=\"center\">").
                                append(tripPeriod.getArrivalTimeScheduledString()).
                                append("</td>").
                                append("<td align=\"center\">").
                                append(tripPeriod.getArrivalTimeActualString()).
                                append("</td>").
                                append("</tr>");
                    }
                }
            }
        }
        return response.toString();
    }

    private LinkedHashMap<String, ArrayList<String>> decodeRequestedRoutesDates(String requestedRoutesDates) {
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
