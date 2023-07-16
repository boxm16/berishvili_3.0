package Index;

import Service.*;
import BasicModels.Day;
import BasicModels.Route;
import Index.IndxDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndxDates {

    @Autowired
    private Converter converter;

    public TreeMap<Date, IndxDate> getIndexDates(TreeMap<String, Route> routes) {
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
