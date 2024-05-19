package FirstTrips;

import BasicModels.Day;
import BasicModels.Exodus;
import BasicModels.Route;
import BasicModels.TripPeriod;
import BasicModels.TripVoucher;
import NewUpload.RequestData;
import Service.ExcelWriter;
import Service.RequestDataDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FirstTripController {

    private RequestData requestedData;

    @RequestMapping(value = "firstTrips")
    public String giarantyTrips(@RequestParam("routes:dates") String requestedRoutesDates, ModelMap modelMap, HttpSession session) {

        RequestDataDecoder requestDataDecoder = new RequestDataDecoder();

        this.requestedData = requestDataDecoder.getRequestedData(requestedRoutesDates);
        ArrayList<FirstTrip> firstTrips = getFirstTrips();
        modelMap.addAttribute("firstTrips", firstTrips);

        return "morning/firstTrips";
    }

    private ArrayList<FirstTrip> getFirstTrips() {
        ArrayList<FirstTrip> firstTrips = new ArrayList();

        FirstTripDao firstTripDao = new FirstTripDao();
        TreeMap<String, Route> plannedRoutes = firstTripDao.getPlannedRoutes(requestedData);
        HashMap<String, Route> actualRoutes = firstTripDao.getActualRoutes(requestedData);
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
                TreeMap<Short, Exodus> plannedExoduses = plannedDay.getPlannedExoduses();
                TreeMap<Short, Exodus> actualExoduses = actualDay.getActualExoduses();

                for (Map.Entry<Short, Exodus> plannedExodusesEntry : plannedExoduses.entrySet()) {
                    Short exodusNumber = plannedExodusesEntry.getKey();

                    Exodus plannedExodus = plannedExodusesEntry.getValue();
                    Exodus actualExodus = actualExoduses.get(exodusNumber);

                    TreeMap<String, TripVoucher> plannedTripVouchers = plannedExodus.getTripVouchers();
                    TreeMap<String, TripVoucher> actualTripVouchers = actualExodus.getTripVouchers();

                    Map.Entry<String, TripVoucher> plannedTripVoucher = plannedTripVouchers.pollFirstEntry();
                    ArrayList<TripPeriod> plannedTripPeriods = plannedTripVoucher.getValue().getTripPeriods();

                    TripPeriod plannedBaseTripPeriod = plannedTripPeriods.get(0);
                    TripPeriod plannedFirstTripPeriod = plannedTripPeriods.get(1);

                    Map.Entry<String, TripVoucher> actualTripVoucher = actualTripVouchers.pollFirstEntry();
                    ArrayList<TripPeriod> actualTripPeriods = actualTripVoucher.getValue().getTripPeriods();

                    TripPeriod actualBaseTripPeriod = actualTripPeriods.get(0);
                    TripPeriod actualFirstTripPeriod = actualTripPeriods.get(1);

                    FirstTrip firstTrip = new FirstTrip();
                    firstTrip.setDateStamp(plannedDay.getDateStamp());
                    firstTrip.setRouteNumber(plannedRoutesEntry.getValue().getNumber());
                    firstTrip.setBaseNumber(plannedTripVoucher.getValue().getBaseNumber());

                    firstTrip.setBaseTripStartTimeScheduled(plannedBaseTripPeriod.getStartTimeScheduled());
                    firstTrip.setBaseTripEndTimeScheduled(plannedBaseTripPeriod.getArrivalTimeScheduled());

                    firstTrip.setBaseTripStartTimeActual(actualBaseTripPeriod.getStartTimeActual());
                    firstTrip.setBaseTripEndTimeActual(actualBaseTripPeriod.getArrivalTimeActual());

                    firstTrip.setStartTimeScheduled(plannedFirstTripPeriod.getStartTimeScheduled());
                    firstTrip.setStartTimeActual(actualFirstTripPeriod.getStartTimeActual());

                    firstTrip.setBusNumber(actualTripVoucher.getValue().getBusNumber());
                    firstTrip.setDriverNumber(actualTripVoucher.getValue().getDriverNumber());
                    firstTrip.setDriverName(actualTripVoucher.getValue().getDriverName());

                    firstTrips.add(firstTrip);
                }
            }
        }
        return firstTrips;
    }

    //-------------------------------
    @RequestMapping(value = "firstTripsExcelExportDashboard")
    public String firstTripsExcelExportDashboardInitialRequest(ModelMap model) {
        model.addAttribute("excelExportLink", "exportFirstTrips.htm");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

    @RequestMapping(value = "exportFirstTrips", method = RequestMethod.POST)
    public String exportFirstTrips(String fileName, ModelMap model, HttpServletRequest request) {
        System.out.println("---------------First Trips Excel Export Starting ------------------------------");
        ArrayList data = getFirstTrips();
        System.out.println("---First Trips Excel Export Data Created------");
        System.out.println("GDS: " + data.size());
        //now write the results
        ExcelWriter excelWriter = new ExcelWriter();

        System.out.println("---Writing Excel File Started---");

        //excelWriter.exportTripPeriodsAndRoutesAverages(tripPeriods, routesAveragesTreeMap, percents, fileName);
        excelWriter.SXSSF_FirstTrips(data, fileName, request);

        model.addAttribute("excelExportLink", "exportFirstTrips.htm");
        model.addAttribute("fileName", fileName);
        model.addAttribute("message", "");
        System.out.println("---Writing Excel File DONE---");
        return "excelExportDashboard";
    }

}
