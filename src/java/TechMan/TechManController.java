package TechMan;

import Service.Basement;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TechManController {

    @Autowired
    private Basement basement;

    @Autowired
    private TechManDao techManDao;

    @RequestMapping(value = "/techMan", method = RequestMethod.GET)
    public String techMan(ModelMap model) {
        String hostName = this.basement.getApplicationHostName();
        model.addAttribute("hostName", hostName);

        Boolean uploadsDirectoryExists = uploadsDirectoryExists();
        model.addAttribute("uploadsDirectoryExists", uploadsDirectoryExists);
        return "techMan";
    }

    private Boolean uploadsDirectoryExists() {
        File dir = new File(this.basement.getBasementDirectory() + "/uploads");
        return dir.exists();
    }

    @RequestMapping(value = "createUploadDirecotry", method = RequestMethod.GET)
    public String createUploadDirectory(ModelMap model) {
        if (uploadsDirectoryExists()) {
            model.addAttribute("uploadsDirectoryExists", true);
            return "techMan";
        }
        //Creating a File object
        File file = new File(this.basement.getBasementDirectory() + "/uploads");
        //Creating the directory
        boolean bool = file.mkdir();
        System.out.println(bool);
        String status = "Uploads directory could not been created.";
        if (bool) {
            return "redirect:/techMan.htm";
        }
        model.addAttribute("uploadsDirectoryExists", status);

        return "techMan";
    }

    //---------------database manipulations part--------------
    @RequestMapping(value = "/createDatabaseSchema", method = RequestMethod.GET)
    public String createDatabaseSchema(ModelMap model) {
        String databaseSchemaCreationStatus = techManDao.createSchema();
        model.addAttribute("databaseSchemaCreationStatus", databaseSchemaCreationStatus);
        return "techMan";
    }

    @RequestMapping(value = "/createDatabaseTables", method = RequestMethod.GET)
    public String createDatabaseTables(ModelMap model) {
        String routeTableCreationStatus = techManDao.createRouteTable();
        String plannedTripVoucherTableCreationStatus = techManDao.createPlannedTripVoucherTable();
        String plannedTripPeriodTableCreationStatus = techManDao.createPlannedTripPeriodTable();

        String tripVoucherTableCreationStatus = techManDao.createTripVoucherTable();
        String tripPeriodTableCreationStatus = techManDao.createTripPeriodTable();
        model.addAttribute("databaseTablesCreationStatus",
                routeTableCreationStatus
                + "<br>" + plannedTripVoucherTableCreationStatus
                + "<br>" + plannedTripPeriodTableCreationStatus
                + "<br>" + tripVoucherTableCreationStatus
                + "<br>" + tripPeriodTableCreationStatus);

        return "techMan";
    }

    @RequestMapping(value = "deleteDatabaseTables", method = RequestMethod.GET)
    public String deleteTables(ModelMap model) {
        //sequence is important (i think :) )
        String plannedTripPeriodTableDeletionStatus = techManDao.deletePlannedTripPeriodTable();
        String plannedTripVoucherTableDeletionStatus = techManDao.deletePlannedTripVoucherTable();

        String tripPeriodTableDeletionStatus = techManDao.deleteTripPeriodTable();
        String tripVoucherTableDeletionStatus = techManDao.deleteTripVoucherTable();
        String routeTableDeletionStatus = techManDao.deleteRouteTable();

        model.addAttribute("databaseTablesDeletionStatus",
                routeTableDeletionStatus
                + "<br>" + plannedTripPeriodTableDeletionStatus
                + "<br>" + plannedTripVoucherTableDeletionStatus
                + "<br>" + tripVoucherTableDeletionStatus
                + "<br>" + tripPeriodTableDeletionStatus);

        return "techMan";
    }
}
