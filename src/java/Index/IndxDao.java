package Index;

import BasicModels.Day;
import BasicModels.Route;
import Planned.PlannedRouteDao;
import Service.Converter;
import Service.DataBaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IndxDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    @Autowired
    private Converter converter;

    private Connection connection;

    TreeMap<String, Route> getActualRoutes() {
        TreeMap<String, Route> routes = new TreeMap<>();
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT route_number, date_stamp FROM trip_voucher");
            while (resultSet.next()) {
                String routeNumber = resultSet.getString("route_number");
                if (routes.containsKey(routeNumber)) {
                    Route route = routes.get(routeNumber);
                    Day day = new Day();
                    String dateStamp = resultSet.getString("date_stamp");
                    day.setDateStamp(dateStamp);
                    TreeMap<String, Day> days = route.getDays();
                    days.put(dateStamp, day);
                    route.setDays(days);
                    routes.put(routeNumber, route);
                } else {
                    Route route = new Route();
                    route.setNumber(routeNumber);
                    Day day = new Day();
                    String dateStamp = resultSet.getString("date_stamp");
                    day.setDateStamp(dateStamp);
                    TreeMap<String, Day> days = route.getDays();
                    days.put(dateStamp, day);
                    route.setDays(days);
                    routes.put(routeNumber, route);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlannedRouteDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return routes;
    }

}
