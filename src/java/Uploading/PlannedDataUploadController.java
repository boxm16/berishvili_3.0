package Uploading;

import Service.Basement;
import Service.MemoryUsage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class PlannedDataUploadController {

    @Autowired
    private Basement basement;

    @Autowired
    private MemoryUsage memoryUsage;

    @RequestMapping(value = "goForPlannedDataUpload")
    public String intervalsInitialRequest(ModelMap model) {
        model.addAttribute("uploadTitle", "დაგეგმილი მონაცემების ატვირთვა");
        model.addAttribute("uploadTarget", "uploadPlannedData.htm");
        return "upload";
    }

    @RequestMapping(value = "/uploadPlannedData", method = RequestMethod.POST)
    public String upload(@RequestParam CommonsMultipartFile file, ModelMap model) {

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Starting working on uploaded excel file (saving as file and saving into database)");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.print("Memory Usage before uploading: ");
        this.memoryUsage.printMemoryUsage();

        String filename = "plannedDataExcelFile.xlsx";
        if (file.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "არცერთი ფაილი არ იყო არჩეული");
            return "upload";
        }
        try {
            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(this.basement.getBasementDirectory() + "/uploads/" + filename));
            bout.write(barr);
            bout.flush();
            bout.close();
            /*
            RouteFactory routeFactory = new RouteFactory();

            TreeMap<Float, BasicRoute> basicRoutesFromUploadedFile = routeFactory.createBasicRoutesFromUploadedFile();

            String lastUploadDeletionStatus = uploadDao.deleteLastUpload();
            System.out.println("Last Upload Deletion Status:" + lastUploadDeletionStatus);
            String lastUploadInsertionStatus = uploadDao.insertNewUpload(basicRoutesFromUploadedFile);
            System.out.println("New Upload Insertion Status:" + lastUploadInsertionStatus);

            Float insertionStatusCode = routeDao.insertUploadedData(basicRoutesFromUploadedFile, model);
            if (insertionStatusCode == 0.00f) {
                System.out.println("New data from excel file has been successfully uploaded into database");
                //  model.addAttribute("unregisteredRoutesMessage", "lalalalalalalalal");
            }
            Instant end = Instant.now();
            System.out.println("Uploading process Ended. Time needed:" + Duration.between(start, end));
            System.out.print("Memory Usage after uploading: ");
            mu.printMemoryUsage();
            System.out.println("--------------------------------------------------");
            if (insertionStatusCode > 0.00f) {
                return "upload-success";
            }

            //  UploadInsertionThread uit = new UploadInsertionThread();
            // uit.start();

             */
        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("uploadStatus", "Upload could not been completed:" + e);
            return "upload";
        }

        return "uploadStatus";
    }
}
