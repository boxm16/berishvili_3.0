package Planned;

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
public class PlannedRouteController {

    LinkedHashMap<String, ArrayList<String>> routesDates;

    @Autowired
    private PlannedRouteDao plannedRouteDao;

    @RequestMapping(value = "plannedRoutes")
    public String plannedRoutes(@RequestParam("routes:dates") String requestedRoutesDates, ModelMap model, HttpSession session) {
        this.routesDates = decodeRequestedRoutesDates(requestedRoutesDates);
        Map.Entry<String, ArrayList<String>> firstEntry = this.routesDates.entrySet().stream().findFirst().get();
        Route plannedRoute = plannedRouteDao.getPlannedRoute(firstEntry.getKey(), firstEntry.getValue());

        model.addAttribute("routeNumbers", this.routesDates.keySet());
        model.addAttribute("plannedRoutes", plannedRoute);

        return "planned/plannedRoutes";
    }
    
     @RequestMapping(value = "/requestRoute", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getTimeX(@RequestParam("number") String requestedRouteNumber) {
        System.out.println(requestedRouteNumber);
        StringBuilder response = new StringBuilder();
        response.append("<tr style='background-color:green'><td colspan='3' align=\"center\">marshgurti #:"+requestedRouteNumber+"</td></tr> ");
        ArrayList<String> dates = this.routesDates.get(requestedRouteNumber);
        Route plannedRoute = plannedRouteDao.getPlannedRoute(requestedRouteNumber, dates);
        TreeMap<String, Day> days = plannedRoute.getDays();
        for (Map.Entry<String, Day> dayEntry : days.entrySet()) {
            response.append("<tr style='background-color:red'> <td colspan='3' align=\"center\">თარიღი:" + dayEntry.getValue().getDateStamp() + "</td></tr>");
            TreeMap<Short, Exodus> exoduses = dayEntry.getValue().getExoduses();
            for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                response.append("<tr><td colspan='3' align=\"center\">გასვლა #:" + exodusEntry.getValue().getNumber() + "</td></tr>");
                TreeMap<String, TripVoucher> tripVouchers = exodusEntry.getValue().getTripVouchers();
                for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                    response.append("<tr><td colspan='3' align=\"center\">" + tripVoucherEntry.getValue().getNumber() + "</td></tr>");
                    ArrayList<TripPeriod> tripPeriods = tripVoucherEntry.getValue().getTripPeriods();
                    for (TripPeriod tripPeriod : tripPeriods) {
                        response.append("<tr><td align=\"center\">" + tripPeriod.getStartTimeString() + "</td>"
                                + "<td align=\"center\">" + tripPeriod.getTypeG() + "</td>"
                                + "<td align=\"center\">" + tripPeriod.getArrivalTimeString() + "</td>"
                                + "</tr>");
                    }
                }
            }
        }
        return response.toString();
    }

    @RequestMapping(value = "plannedRoutesSummary")
    public String plannedRoutesSummary(@RequestParam("routes:dates") String routesDates, ModelMap model, HttpSession session) {
        //    RoutesPager plannedRoutesSummaryPager = pagerFactory.createRoutesPager(routesDates, "plannedRoutesSummary");

        //   PlannedRoute plannedRoutesSummary = plannedRouteDao.getPlannedRouteSummary(plannedRoutesSummaryPager);
        // session.setAttribute("plannedRoutesSummaryPager", plannedRoutesSummaryPager);
        //  model.addAttribute("plannedRoutesSummaryPager", plannedRoutesSummaryPager);
        //  model.addAttribute("plannedRoutesSummary", plannedRoutesSummary);
        return "planned/plannedRoutesSummary";
    }

    @RequestMapping(value = "plannedRoutesSummaryRequest")
    public String plannedRoutesSummaryRequest(@RequestParam("requestedRoute") String requestedRoute, ModelMap model, HttpSession session) {
        // RoutesPager plannedRoutesSummaryPager = (RoutesPager) session.getAttribute("plannedRoutesSummaryPager");
        //if (plannedRoutesSummaryPager == null) {
        //    return "errorPage";
        // }
        //
        //  plannedRoutesSummaryPager.setCurrentRoute (requestedRoute);
        // PlannedRoute plannedRoutesSummary = plannedRouteDao.getPlannedRouteSummary(plannedRoutesSummaryPager);
        //session.setAttribute (
        //"plannedRoutesSummaryPager", plannedRoutesSummaryPager);
        //model.addAttribute (
        // "plannedRoutesSummaryPager", plannedRoutesSummaryPager);
        // model.addAttribute (
        // "plannedRoutesSummary", plannedRoutesSummary);

        return "planned/plannedRoutesSummary";
    }

    private LinkedHashMap<String, ArrayList<String>> decodeRequestedRoutesDates(String requestedRoutesDates) {
        LinkedHashMap<String, ArrayList<String>> routesDates = new LinkedHashMap<>();

        //trimming and cleaning input
        requestedRoutesDates = requestedRoutesDates.trim();
        if (requestedRoutesDates.length() == 0) {
            return null;
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
