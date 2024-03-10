/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DownloadController {

    @RequestMapping(value = "downloadFile", method = RequestMethod.GET)
    public void downloadRequestedFile(@RequestParam String fileName, HttpServletResponse response) {

        File file = new File(new Basement().getBasementDirectory() + "/downloads/" + fileName);
        downloadFile(file, fileName, response);
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
