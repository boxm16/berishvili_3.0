/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Index;

import BasicModels.Route;
import java.util.Date;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndxController {

    @Autowired
    private IndxDates indexDates;

    @Autowired
    private IndxDao indxDao;

    @RequestMapping(value = "index")
    public String index(ModelMap model) {

        TreeMap<String, Route> routes = indxDao.getActualRoutes();
        //   TreeMap<String, Route> routes = new TreeMap<>();

        TreeMap<Date, IndxDate> dates = this.indexDates.getIndexDates(routes);
        model.addAttribute("routes", routes);
        model.addAttribute("dates", dates);
        return "index";
    }

}
