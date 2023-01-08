package Planned;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PlannedRouteController {

    @RequestMapping(value = "plannedRoutes")
    public String plannedRoutes(ModelMap model) {
        
        return "planned/plannedRoutes";
    }
}
