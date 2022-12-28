package Uploading;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UploadStatusAjaxController {

    private int x = 0;

    @RequestMapping(value = "/uploadStatusAjax", method = RequestMethod.GET)
    public @ResponseBody
    String getTime() {
        //   uploadTime++;
        // String uploadStatus = uploadDao.getUploadStatus();
        x++;
        if (x > 20) {
            x=0;
           return "dddd:completed";
        }
        return x + "YOU STILL NEED TO DO SOME WORK HERE";
    }
}
