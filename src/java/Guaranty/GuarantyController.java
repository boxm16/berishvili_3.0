package Guaranty;

import BasicModels.Day;
import BasicModels.Route;
import NewUpload.RequestData;
import Service.RequestDataDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GuarantyController {

    @RequestMapping(value = "guarantyTrips")
    public String giarantyTrips(@RequestParam("routes:dates") String requestedRoutesDates, ModelMap modelMap, HttpSession session) {
        ArrayList<GuarantyTrip> guarantyTrips = new ArrayList();
        RequestDataDecoder requestDataDecoder = new RequestDataDecoder();

        RequestData requestedData = requestDataDecoder.getRequestedData(requestedRoutesDates);

        GuarantyDao guarantyDao = new GuarantyDao();
        TreeMap<String, Route> plannedRoutes = guarantyDao.getPlannedRoutes(requestedData);
        HashMap<String, Route> actualRoutes = guarantyDao.getActualRoutes(requestedData);
        System.out.println("PLANNED ROUTES SIZE: " + plannedRoutes.size());
        System.out.println("Actual ROUTES SIZE: " + actualRoutes.size());
        for (Map.Entry<String, Route> plannedRoutesEntry : plannedRoutes.entrySet()) {
            Route actualRoute = actualRoutes.get(plannedRoutesEntry.getKey());
            TreeMap<String, Day> plannedDays = plannedRoutesEntry.getValue().getDays();
            TreeMap<String, Day> actualDays = actualRoute.getDays();

            System.out.println("ROUTE" + plannedRoutesEntry.getValue().getNumber());

            for (Map.Entry<String, Day> plannedDaysEntry : plannedDays.entrySet()) {
                Day plannedDay = plannedDaysEntry.getValue();
                Day actualDay = actualDays.get(plannedDaysEntry.getKey());

                GuarantyDay guarantyDay = new GuarantyDay();
                guarantyDay.setPlannedExoduses(plannedDay.getPlannedExoduses());
                guarantyDay.setActualExoduses(actualDay.getActualExoduses());

                guarantyDay.calculateGuarantyTrips();
                GuarantyTrip abSubguarantyTrip = guarantyDay.getAbSubguarantyTrip();

                abSubguarantyTrip.setRouteNumber(plannedRoutesEntry.getValue().getNumber());
                abSubguarantyTrip.setDateStamp(plannedDay.getDateStamp());
                guarantyTrips.add(abSubguarantyTrip);

                GuarantyTrip abGuarantyTrip = guarantyDay.getAbGuarantyTrip();
                abGuarantyTrip.setRouteNumber(plannedRoutesEntry.getValue().getNumber());
                abGuarantyTrip.setDateStamp(plannedDay.getDateStamp());
                guarantyTrips.add(abGuarantyTrip);
                if (guarantyDay.getBaGuarantyTrip() == null) {
                } else {
                    GuarantyTrip baSubguarantyTrip = guarantyDay.getBaSubguarantyTrip();
                    baSubguarantyTrip.setRouteNumber(plannedRoutesEntry.getValue().getNumber());
                    baSubguarantyTrip.setDateStamp(plannedDay.getDateStamp());
                    guarantyTrips.add(baSubguarantyTrip);

                    GuarantyTrip baGuarantyTrip = guarantyDay.getBaGuarantyTrip();
                    baGuarantyTrip.setRouteNumber(plannedRoutesEntry.getValue().getNumber());
                    baGuarantyTrip.setDateStamp(plannedDay.getDateStamp());
                    guarantyTrips.add(baGuarantyTrip);
                }
            }
        }

        modelMap.addAttribute("guarantyData", guarantyTrips);

        return "guaranty/guarantyTrips";
    }

}
