/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ActualDataUploadController {

    @Autowired
    private Basement basement;

    @Autowired
    private MemoryUsage memoryUsage;

    @RequestMapping(value = "goForActualDataUpload")
    public String goForActualDataUpload(ModelMap model) {
        model.addAttribute("uploadTitle", "ფაქტიური მონაცემების ატვირთვა");
        model.addAttribute("uploadTarget", "uploadActualData.htm");
        return "upload";
    }

    @RequestMapping(value = "/uploadActualData", method = RequestMethod.POST)
    public String uploadActualData(@RequestParam CommonsMultipartFile file, ModelMap model) {

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Starting working on uploaded excel file (saving as file and saving into database)");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.print("Memory Usage before uploading: ");
        this.memoryUsage.printMemoryUsage();

        String filename = "actualDataExcelFile.xlsx";
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
        //--------------- Starting new Thread For Upload-----------------------
        ActualDataUploadThread actualDataUploadThread = new ActualDataUploadThread(filePath);
        actualDataUploadThread.start();
        return "uploadStatus";
    }
}
