package Uploading;

import Service.StaticsDispatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UploadStatusAjaxController {
  
    @RequestMapping(value = "/uploadStatusAjax", method = RequestMethod.GET)
    public @ResponseBody
    String getTime() {
        if (StaticsDispatcher.isUploading()) {
            return "Reading Uploaded Excel File ...........";
        } else {
            return "completed";
        }

    }
}
