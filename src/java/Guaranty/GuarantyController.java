package Guaranty;

import BasicModels.Day;
import BasicModels.Route;
import NewUpload.RequestData;
import Service.ExcelWriter;
import Service.RequestDataDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GuarantyController {

    private RequestData requestedData;

    @RequestMapping(value = "guarantyTrips")
    public String giarantyTrips(@RequestParam("routes:dates") String requestedRoutesDates, ModelMap modelMap, HttpSession session) {

        RequestDataDecoder requestDataDecoder = new RequestDataDecoder();

        this.requestedData = requestDataDecoder.getRequestedData(requestedRoutesDates);
        ArrayList<GuarantyTrip> guarantyTrips = getGuarantyTrips();
        modelMap.addAttribute("guarantyData", guarantyTrips);

        return "guaranty/guarantyTrips";
    }

    private ArrayList getGuarantyTrips() {
        ArrayList<GuarantyTrip> guarantyTrips = new ArrayList();

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
                abSubguarantyTrip.setaPoint(plannedRoutesEntry.getValue().getaPoint());
                abSubguarantyTrip.setbPoint(plannedRoutesEntry.getValue().getbPoint());
                guarantyTrips.add(abSubguarantyTrip);

                GuarantyTrip abGuarantyTrip = guarantyDay.getAbGuarantyTrip();
                abGuarantyTrip.setRouteNumber(plannedRoutesEntry.getValue().getNumber());
                abGuarantyTrip.setDateStamp(plannedDay.getDateStamp());
                abGuarantyTrip.setaPoint(plannedRoutesEntry.getValue().getaPoint());
                abGuarantyTrip.setbPoint(plannedRoutesEntry.getValue().getbPoint());

                guarantyTrips.add(abGuarantyTrip);
                if (guarantyDay.getBaGuarantyTrip() == null) {
                } else {
                    GuarantyTrip baSubguarantyTrip = guarantyDay.getBaSubguarantyTrip();
                    baSubguarantyTrip.setRouteNumber(plannedRoutesEntry.getValue().getNumber());
                    baSubguarantyTrip.setDateStamp(plannedDay.getDateStamp());
                    baSubguarantyTrip.setaPoint(plannedRoutesEntry.getValue().getaPoint());
                    baSubguarantyTrip.setbPoint(plannedRoutesEntry.getValue().getbPoint());

                    guarantyTrips.add(baSubguarantyTrip);

                    GuarantyTrip baGuarantyTrip = guarantyDay.getBaGuarantyTrip();
                    baGuarantyTrip.setRouteNumber(plannedRoutesEntry.getValue().getNumber());
                    baGuarantyTrip.setDateStamp(plannedDay.getDateStamp());
                    baGuarantyTrip.setaPoint(plannedRoutesEntry.getValue().getaPoint());
                    baGuarantyTrip.setbPoint(plannedRoutesEntry.getValue().getbPoint());

                    guarantyTrips.add(baGuarantyTrip);
                }
            }
        }
        return guarantyTrips;
    }

    //-------------------------------
    @RequestMapping(value = "guarantyTripsExcelExportDashboard")
    public String guarantyTripsExcelExportDashboardInitialRequest(ModelMap model) {
        model.addAttribute("excelExportLink", "exportGuarantyTrips.htm");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

    @RequestMapping(value = "exportGuarantyTrips", method = RequestMethod.POST)
    public String exportGuarantyTrips(String fileName, ModelMap model) {
        System.out.println("---------------Guaranty Trips Excel Export Starting ------------------------------");
        ArrayList guarantyData = getGuarantyTrips();
        System.out.println("---Guaranty Trips Excel Export Data Created------");

        //now write the results
        ExcelWriter excelWriter = new ExcelWriter();

        System.out.println("---Writing Excel File Started---");

        //excelWriter.exportTripPeriodsAndRoutesAverages(tripPeriods, routesAveragesTreeMap, percents, fileName);
        excelWriter.SXSSF_GuarantyTrips(guarantyData, fileName);

        model.addAttribute("excelExportLink", "exportGuarantyTrips.htm");
        model.addAttribute("fileName", fileName);
        model.addAttribute("message", "");
        System.out.println("---Writing Excel File DONE---");
        return "excelExportDashboard";
    }

}
