package Guaranty;

import BasicModels.Day;
import BasicModels.Route;
import BasicModels.TripPeriod;
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

                TripPeriod abPlannedSubGuarantyTrip = plannedDay.getAbPlannedSubGuarantyTrip();
                TripPeriod abActualSubGuarantyTrip = actualDay.getAbActualSubGuarantyTrip();

                GuarantyTrip guarantyTrip = new GuarantyTrip();
                guarantyTrip.setDateStamp(plannedDay.getDateStamp());
                guarantyTrip.setaPoint(plannedRoutesEntry.getValue().getaPoint());
                guarantyTrip.setbPoint(plannedRoutesEntry.getValue().getbPoint());
                guarantyTrip.setRouteNumber(plannedRoutesEntry.getKey());
                guarantyTrip.setGuarantyType("ქვესაგარანტიო");

                guarantyTrip.setType(abPlannedSubGuarantyTrip.getType());
                guarantyTrip.setPlannedExodusNumber(abPlannedSubGuarantyTrip.getExoudsNumber());
                guarantyTrip.setStartTimeScheduled(abPlannedSubGuarantyTrip.getStartTimeScheduled());

                guarantyTrip.setStartTimeActual(abActualSubGuarantyTrip.getStartTimeActual());
                guarantyTrip.setActualExodusNumber(abActualSubGuarantyTrip.getExoudsNumber());

                guarantyTrips.add(guarantyTrip);

                TripPeriod abPlannedGuarantyTrip = plannedDay.getAbPlannedGuarantyTrip();
                TripPeriod abActualGuarantyTrip = actualDay.getAbActualGuarantyTrip();
                GuarantyTrip guarantyTrip1 = new GuarantyTrip();
                guarantyTrip1.setDateStamp(plannedDay.getDateStamp());
                guarantyTrip1.setaPoint(plannedRoutesEntry.getValue().getaPoint());
                guarantyTrip1.setbPoint(plannedRoutesEntry.getValue().getbPoint());
                guarantyTrip1.setRouteNumber(plannedRoutesEntry.getKey());
                guarantyTrip1.setGuarantyType("საგარანტიო");

                guarantyTrip1.setType(abPlannedGuarantyTrip.getType());
                guarantyTrip1.setPlannedExodusNumber(abPlannedGuarantyTrip.getExoudsNumber());
                guarantyTrip1.setStartTimeScheduled(abPlannedGuarantyTrip.getStartTimeScheduled());

                guarantyTrip1.setStartTimeActual(abActualGuarantyTrip.getStartTimeActual());
                guarantyTrip1.setActualExodusNumber(abActualGuarantyTrip.getExoudsNumber());

                guarantyTrips.add(guarantyTrip1);

                TripPeriod baPlannedSubGuarantyTrip = plannedDay.getBaPlannedSubGuarantyTrip();
                TripPeriod baActualSubGuarantyTrip = actualDay.getBaActualSubGuarantyTrip();
                if (baPlannedSubGuarantyTrip != null) {
                    GuarantyTrip guarantyTrip2 = new GuarantyTrip();
                    guarantyTrip2.setDateStamp(plannedDay.getDateStamp());
                    guarantyTrip2.setaPoint(plannedRoutesEntry.getValue().getaPoint());
                    guarantyTrip2.setbPoint(plannedRoutesEntry.getValue().getbPoint());
                    guarantyTrip2.setRouteNumber(plannedRoutesEntry.getKey());
                    guarantyTrip2.setGuarantyType("ქვესაგარანტიო");

                    guarantyTrip2.setType(baPlannedSubGuarantyTrip.getType());
                    guarantyTrip2.setPlannedExodusNumber(baPlannedSubGuarantyTrip.getExoudsNumber());
                    guarantyTrip2.setStartTimeScheduled(baPlannedSubGuarantyTrip.getStartTimeScheduled());

                    guarantyTrip2.setStartTimeActual(baActualSubGuarantyTrip.getStartTimeActual());
                    guarantyTrip2.setActualExodusNumber(baActualSubGuarantyTrip.getExoudsNumber());

                    guarantyTrips.add(guarantyTrip2);
                }

                TripPeriod baPlannedGuarantyTrip = plannedDay.getBaPlannedGuarantyTrip();
                TripPeriod baActualGuarantyTrip = actualDay.getBaActualGuarantyTrip();
                if (baPlannedGuarantyTrip != null) {
                    GuarantyTrip guarantyTrip3 = new GuarantyTrip();
                    guarantyTrip3.setDateStamp(plannedDay.getDateStamp());
                    guarantyTrip3.setaPoint(plannedRoutesEntry.getValue().getaPoint());
                    guarantyTrip3.setbPoint(plannedRoutesEntry.getValue().getbPoint());
                    guarantyTrip3.setRouteNumber(plannedRoutesEntry.getKey());
                    guarantyTrip3.setGuarantyType("საგარანტიო");

                    guarantyTrip3.setType(baPlannedGuarantyTrip.getType());
                    guarantyTrip3.setPlannedExodusNumber(baPlannedGuarantyTrip.getExoudsNumber());
                    guarantyTrip3.setStartTimeScheduled(baPlannedGuarantyTrip.getStartTimeScheduled());

                    guarantyTrip3.setStartTimeActual(baActualGuarantyTrip.getStartTimeActual());
                    guarantyTrip3.setActualExodusNumber(baActualGuarantyTrip.getExoudsNumber());

                    guarantyTrips.add(guarantyTrip3);
                }

            }
        }

        modelMap.addAttribute("guarantyData", guarantyTrips);

        return "guaranty/guarantyTrips";
    }

}
