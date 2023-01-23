package Planned;

import BasicModels.Route;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
