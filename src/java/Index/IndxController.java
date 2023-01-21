/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Index;

import BasicModels.Day;
import BasicModels.Route;
import Service.Converter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndxController {

    @Autowired
    private IndxDao indxDao;

    @Autowired
    private Converter converter;

    @RequestMapping(value = "index")
    public String index(ModelMap model) {

        TreeMap<String, Route> routes = indxDao.getPlannedRoutes();

        TreeMap<Date, IndxDate> indexDates = getIndexDates(routes);
        model.addAttribute("routes", routes);
        model.addAttribute("dates", indexDates);
        return "index";
    }

    private TreeMap<Date, IndxDate> getIndexDates(TreeMap<String, Route> routes) {
        TreeMap<Date, IndxDate> dates = new TreeMap<>();

        for (Map.Entry<String, Route> routeEntry : routes.entrySet()) {
            TreeMap<String, Day> days = routeEntry.getValue().getDays();
            for (Map.Entry<String, Day> dayEntry : days.entrySet()) {
                Date date = this.converter.convertDateStampDatabaseFormatToDate(dayEntry.getValue().getDateStamp());

                IndxDate indexDate = new IndxDate();
                indexDate.setDateStampExcelFormat(dayEntry.getValue().getDateStamp());

                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                switch (dayOfWeek) {
                    case 1:
                        indexDate.setDayOfWeek("კვირა");
                        indexDate.setDayColor("red");
                        break;
                    case 2:
                        indexDate.setDayOfWeek("ორშაბათი");
                        indexDate.setDayColor("black");
                        break;
                    case 3:
                        indexDate.setDayOfWeek("სამშაბათი");
                        indexDate.setDayColor("black");
                        break;
                    case 4:
                        indexDate.setDayOfWeek("ოთხშაბათი");
                        indexDate.setDayColor("black");
                        break;
                    case 5:
                        indexDate.setDayOfWeek("ხუთშაბათი");
                        indexDate.setDayColor("black");
                        break;
                    case 6:
                        indexDate.setDayOfWeek("პარასკევი");
                        indexDate.setDayColor("black");
                        break;
                    case 7:
                        indexDate.setDayOfWeek("შაბათი");
                        indexDate.setDayColor("red");
                        break;
                }

                dates.put(date, indexDate);
            }
        }
        return dates;
    }
}
