package Analysis;

import BasicModels.Day;
import BasicModels.Exodus;
import BasicModels.Route;
import BasicModels.TripVoucher;
import Service.Basement;
import Service.Converter;
import Service.MemoryUsage;
import Service.RequestDataDecoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class AnalysisController {

    @Autowired
    private Basement basement;

    @Autowired
    private MemoryUsage memoryUsage;

    @Autowired
    private AnalysisDao analysisDao;

    @RequestMapping(value = "goForDriverAnalysisDataUpload")
    public String goForDriverAnalysisDataUpload(ModelMap model) {
        model.addAttribute("uploadTitle", "მძღოლის მონაცემების ატვირთვა");
        model.addAttribute("uploadTarget", "uploadDriverAnalysisData.htm");
        return "upload";
    }

    @RequestMapping(value = "/uploadDriverAnalysisData", method = RequestMethod.POST)
    public String uploadPlannedData(@RequestParam CommonsMultipartFile file, ModelMap model) {

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Starting working on uploaded excel file (saving as file and saving into database)");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.print("Memory Usage before uploading: ");
        this.memoryUsage.printMemoryUsage();

        String filename = "driverAnalysisDataExcelFile.xlsx";
        String filePath = this.basement.getBasementDirectory() + "/uploads/" + filename;
        if (file.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "არცერთი ფაილი არ იყო არჩეული");
            return "upload";
        }
        try {
            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bout.write(barr);
            bout.flush();
            bout.close();

        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("uploadStatus", "Upload could not been completed:" + e);
            return "upload";
        }

        return "uploadStatus";
    }

    @RequestMapping(value = "driverDataAnalysis")
    public String driverDataAnalysis(ModelMap modelMap) {
        ExcelReader excelReader = new ExcelReader();
        String filename = "driverAnalysisDataExcelFile.xlsx";
        String filePath = this.basement.getBasementDirectory() + "/uploads/" + filename;

        Map<String, Map<String, String>> sheetsDataFromExcelFile = excelReader.getSheetsDataFromExcelFile(filePath);

        NamushevariDroebiFactory namushevariDroebiFactory = new NamushevariDroebiFactory();
        LinkedHashMap<Integer, Driver> driversData = namushevariDroebiFactory.getNamushevariDroebi(sheetsDataFromExcelFile.get("namushevari droebi"));

        DanishvnaGatavisuplebaFactory danishvnaGatavisuplebaFactory = new DanishvnaGatavisuplebaFactory();
        driversData = danishvnaGatavisuplebaFactory.getDanishvnaGatavisupleba(driversData, sheetsDataFromExcelFile.get("danishvna gatavisupleba"));

        GacdenebiFactory gacdenebiFactory = new GacdenebiFactory();
        driversData = gacdenebiFactory.addGacdenebiToDriversData(driversData, sheetsDataFromExcelFile.get("gacdenebi"));

        NariadFactory nariadFactory = new NariadFactory();
        driversData = nariadFactory.addNariadToDriversData(driversData, sheetsDataFromExcelFile.get("nariadi"));

        modelMap.addAttribute("driversData", driversData);
        return "analysis/driverDataAnalysis";
    }

    //---------------------++++++++++++++++++--------------------+++++++++++++++++
    @RequestMapping(value = "analysis")

    public String plannedRoutes(@RequestParam("routes:dates") String requestedRoutesDates, ModelMap model) {
        RequestDataDecoder requestDataDecoder = new RequestDataDecoder();

        TreeMap<Integer, DriverAnalys> driversAnalysis = new TreeMap();

        LinkedHashMap<String, ArrayList<String>> routesDates = requestDataDecoder.decodeRequestedRoutesDates(requestedRoutesDates);
        //  LinkedHashMap<String, Route> plannedRoutes = this.analysisDao.getPlannedRoutes(routesDates);
        LinkedHashMap<String, Route> actualRoutes = this.analysisDao.getActualRoutes(routesDates);

        for (Map.Entry<String, Route> actualRouteEntry : actualRoutes.entrySet()) {
            Route actualRoute = actualRouteEntry.getValue();
            TreeMap<String, Day> days = actualRoute.getDays();
            for (Map.Entry<String, Day> dayEntry : days.entrySet()) {
                TreeMap<Short, Exodus> exoduses = dayEntry.getValue().getActualExoduses();
                for (Map.Entry<Short, Exodus> exoduseEntry : exoduses.entrySet()) {
                    TreeMap<String, TripVoucher> tripVouchers = exoduseEntry.getValue().getTripVouchers();
                    for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                        String driverNumberString = tripVoucherEntry.getValue().getDriverNumber();
                        Integer driverNumber = Integer.parseInt(driverNumberString);

                        if (!driversAnalysis.containsKey(driverNumber)) {
                            DriverAnalys driverAnalys = new DriverAnalys();

                            driverAnalys.setDriverName(tripVoucherEntry.getValue().getDriverName());

                            TreeMap<Date, TripVoucher> monthPlan = createMonthPlan(dayEntry.getValue().getDateStamp());
                            driverAnalys.setTripVouchers(monthPlan);

                            driversAnalysis.put(driverNumber, driverAnalys);
                        }
                        DriverAnalys driverAnalys = driversAnalysis.get(driverNumber);

                        TripVoucher tripVoucher = tripVoucherEntry.getValue();

                        String dateStamp = dayEntry.getValue().getDateStamp();
                        Converter converter = new Converter();
                        Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);

                        driverAnalys.addTripVoucher(date, tripVoucher);

                    }
                }
            }
            System.out.println("ANALIZING ACTUAL ROUTE NUMBER: " + actualRoute.getNumber());
        }

        System.out.println("Actual ROUTE:" + actualRoutes.size());

        System.out.println("DRIVERS :" + driversAnalysis.size());

        model.addAttribute("driversAnalysis", driversAnalysis);

        return "analysis/analysis";
    }

    private TreeMap<Date, TripVoucher> createMonthPlan(String dateStamp) {
        TreeMap<Date, TripVoucher> monthPlan = new TreeMap<>();
        Converter converter = new Converter();
        Date convertedDate = converter.convertDateStampDatabaseFormatToDate(dateStamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertedDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int x = 0;
        while (x < actualMaximum + 1) {

            Date date = calendar.getTime();

            monthPlan.put(date, null);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            x++;
        }
        return monthPlan;
    }
}
