package Route;

import BasicModels.Route;
import Service.Basement;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.TreeMap;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class RouteController {

    @Autowired
    private Basement basement;

    @Autowired
    private RouteDao routeDao;

    @RequestMapping(value = "goToUploadRoutes")
    public String goToUploadRoutes(ModelMap model) {

        return "route/uploadRoutes";
    }

    @RequestMapping(value = "uploadRoutes", method = RequestMethod.POST)
    public String upload(@RequestParam CommonsMultipartFile file, ModelMap model) {

        System.out.println("+++++++++++  Starting Routes Upload  +++++++++++");

        String filename = "routesDataExcelFile.xlsx";
        String filePath = this.basement.getBasementDirectory() + "/uploads/" + filename;
        if (file.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "არცერთი ფაილი არ იყო არჩეული");
            return "uploadRoutes";
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

        System.out.println("File Upload Completed");
        //------------------Now Reading Excel File And Building Rotues-----------
        RouteFactory routeFactory = new RouteFactory();
        TreeMap<String, Route> routesFromExcelFile = routeFactory.getRoutesFromExcelFile(filePath);

        //----------------- Now deleting old data in DB --------------------- 
        String deletionStatus = routeDao.deleteRoutes();
        if (!deletionStatus.equals("DONE")) {
            model.addAttribute("errorMessage", deletionStatus);
            return "route/uploadRoutes";
        }
        System.out.println("Old Data Deletion Completed");
        //----------------- Now inserting new data in DB ---------------------
        String insertRoutesStatus = routeDao.insertRoutes(routesFromExcelFile);
        if (!insertRoutesStatus.equals("DONE")) {
            model.addAttribute("errorMessage", insertRoutesStatus);
            return "route/uploadRoutes";
        }
        System.out.println("Insertion Completed");

        return "redirect:routes.htm";
    }

    @RequestMapping(value = "routes")
    public String routes(ModelMap model) {
        TreeMap<String, Route> routes = routeDao.getRoutesDataFromDB();
        model.addAttribute("routes", routes);
        return "route/routes";
    }

    @RequestMapping(value = "downloadRoutes", method = RequestMethod.GET)
    public void downloadRoutes(HttpServletResponse response) {
        File file = new File(this.basement.getBasementDirectory() + "/uploads/routesDataExcelFile.xlsx");
        downloadFile(file, "marabfa.xlsx", response);
    }

    private void downloadFile(File file, String fileName, HttpServletResponse response) {
        try {
            InputStream inputStream = new FileInputStream(file);
            //response.setContentType("application/force-download");
            // response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            response.setContentType("application/ms-excel; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
