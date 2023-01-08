package Route;

import BasicModels.Route;
import Service.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RouteDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    private Connection connection;

    public TreeMap<String, Route> getRoutesDataFromDB() {
        TreeMap<String, Route> routes = new TreeMap<>();
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM route");
            while (resultSet.next()) {
                Route route = new Route();

                String routeNumber = resultSet.getString("number");
                String aPoint = resultSet.getString("a_point");
                String bPoint = resultSet.getString("b_point");
                String scheme = resultSet.getString("scheme");
                route.setNumber(routeNumber);
                route.setaPoint(aPoint);
                route.setbPoint(bPoint);
                route.setScheme(scheme);
                routes.put(routeNumber, route);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return routes;
    }

    public String deleteRoutes() {
        String errorMessage = "";
        try {
            // connection = dataSource.getInstance().getConnection();
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM  route");
            statement.close();
            connection.close();
            return "DONE";
        } catch (SQLException ex) {
            Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage = ex.getMessage();
        }
        return "Last uploaded routes and dates cant be deleted:" + errorMessage;
    }

    public String insertRoutes(TreeMap<String, Route> routes) {
        try {
            connection = dataBaseConnection.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO route (number, a_point, b_point, scheme) VALUES (?,?,?,?)");

            for (Map.Entry<String, Route> entry : routes.entrySet()) {
                Route route = entry.getValue();

                insertStatement.setString(1, route.getNumber());
                insertStatement.setString(2, route.getaPoint());
                insertStatement.setString(3, route.getbPoint());
                insertStatement.setString(4, route.getScheme());
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
            insertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        System.out.println("Routes from excel file has been inserted successfully into database");
        return "DONE";
    }
}
