package Planned;

import BasicModels.RoutesPager;
import Service.PagerFactory;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlannedRouteController {

    @Autowired
    private PlannedRouteDao plannedRouteDao;

    @Autowired
    private PagerFactory pagerFactory;

    @RequestMapping(value = "plannedRoutes")
    public String plannedRoutes(@RequestParam("routes:dates") String routesDates, ModelMap model, HttpSession session) {
        RoutesPager plannedRoutesPager = pagerFactory.createRoutesPager(routesDates, "plannedRoutes");

        PlannedRoute plannedRoutes = plannedRouteDao.getPlannedRoute(plannedRoutesPager);

        session.setAttribute("plannedRoutesPager", plannedRoutesPager);

        model.addAttribute("plannedRoutesPager", plannedRoutesPager);
        model.addAttribute("plannedRoutes", plannedRoutes);

        return "planned/plannedRoutes";
    }

    @RequestMapping(value = "plannedRoutesRequest")
    public String detailedRoutesRequest(@RequestParam("requestedRoute") String requestedRoute, ModelMap model, HttpSession session) {
        RoutesPager plannedRoutesPager = (RoutesPager) session.getAttribute("plannedRoutesPager");
        if (plannedRoutesPager == null) {
            return "errorPage";
        }
        plannedRoutesPager.setCurrentRoute(requestedRoute);
        PlannedRoute plannedRoute = plannedRouteDao.getPlannedRoute(plannedRoutesPager);
        session.setAttribute("plannedRoutesPager", plannedRoutesPager);

        model.addAttribute("plannedRoutesPager", plannedRoutesPager);
        model.addAttribute("plannedRoutes", plannedRoute);
        return "planned/plannedRoutes";
    }

    @RequestMapping(value = "plannedRoutesSummary")
    public String plannedRoutesSummary(@RequestParam("routes:dates") String routesDates, ModelMap model, HttpSession session) {
        RoutesPager plannedRoutesSummaryPager = pagerFactory.createRoutesPager(routesDates, "plannedRoutesSummary");

        PlannedRoute plannedRoutesSummary = plannedRouteDao.getPlannedRouteSummary(plannedRoutesSummaryPager);

        session.setAttribute("plannedRoutesSummaryPager", plannedRoutesSummaryPager);

        model.addAttribute("plannedRoutesSummaryPager", plannedRoutesSummaryPager);
        model.addAttribute("plannedRoutesSummary", plannedRoutesSummary);
        return "planned/plannedRoutesSummary";
    }

    @RequestMapping(value = "plannedRoutesSummaryRequest")
    public String plannedRoutesSummaryRequest(@RequestParam("requestedRoute") String requestedRoute, ModelMap model, HttpSession session) {
        RoutesPager plannedRoutesSummaryPager = (RoutesPager) session.getAttribute("plannedRoutesSummaryPager");
        if (plannedRoutesSummaryPager == null) {
            return "errorPage";
        }
        plannedRoutesSummaryPager.setCurrentRoute(requestedRoute);
        PlannedRoute plannedRoutesSummary = plannedRouteDao.getPlannedRouteSummary(plannedRoutesSummaryPager);
        session.setAttribute("plannedRoutesSummaryPager", plannedRoutesSummaryPager);

        model.addAttribute("plannedRoutesSummaryPager", plannedRoutesSummaryPager);
        model.addAttribute("plannedRoutesSummary", plannedRoutesSummary);
        return "planned/plannedRoutesSummary";
    }
}
