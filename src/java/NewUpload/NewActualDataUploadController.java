/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewUpload;

import Service.Basement;
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
public class NewActualDataUploadController {
      @Autowired
    private Basement basement;

    @RequestMapping(value = "goForNewActualDataUpload")
    public String goForActualDataUpload(ModelMap model) {
        model.addAttribute("uploadTitle", "ფაქტიური მონაცემების ატვირთვა");
        model.addAttribute("uploadTarget", "uploadNewActualData.htm");
        return "upload";
    }

    @RequestMapping(value = "/uploadNewActualData", method = RequestMethod.POST)
    public String uploadNewActualData(@RequestParam CommonsMultipartFile file, ModelMap model) {

        System.out.println("   +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Starting working on uploaded excel file (saving as file and saving into database)");
        System.out.println("    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

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
        NewActualDataUploadThread actualDataUploadThread = new NewActualDataUploadThread(filePath);
        actualDataUploadThread.start();
        return "uploadStatus";
    }
}
