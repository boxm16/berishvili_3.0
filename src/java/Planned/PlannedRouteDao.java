package Planned;

import BasicModels.Day;
import BasicModels.Exodus;
import BasicModels.Route;
import BasicModels.TripPeriod;
import BasicModels.TripVoucher;
import Service.Converter;
import Service.DataBaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PlannedRouteDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    @Autowired
    private Converter converter;

    private Connection connection;

    TreeMap<String, Route> getPlannedRoutes() {
        TreeMap<String, Route> routes = new TreeMap<>();
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT route_number, date_stamp FROM planned_trip_voucher");
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

    public Route getPlannedRoute(String routeNumber, ArrayList<String> dateStamps) {
        Route plannedRoute = new Route();
        plannedRoute.setNumber(routeNumber);

        StringBuilder query = new StringBuilder();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT exodus_number, date_stamp, t2.number, type, start_time, arrival_time FROM route t1 INNER JOIN planned_trip_voucher t2 ON t1.number=t2.route_number INNER JOIN planned_trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number=");
        StringBuilder queryBuilderDateStampPart = this.converter.buildStringFromArrayList(dateStamps);

        query = queryBuilderInitialPart.append("'" + routeNumber + "'").
                append(" AND date_stamp IN ").append(queryBuilderDateStampPart);
        // append(" ORDER BY date_stamp, exodus_number, start_time ;");
        System.out.println(query);
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {

                String dateStamp = resultSet.getString("date_stamp");
                //    Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                if (!plannedRoute.getDays().containsKey(dateStamp)) {
                    Day newDay = new Day();
                    newDay.setDateStamp(dateStamp);
                    plannedRoute.getDays().put(dateStamp, newDay);
                }
                Day day = plannedRoute.getDays().get(dateStamp);

                short exodusNumber = resultSet.getShort("exodus_number");
                if (!day.getExoduses().containsKey(exodusNumber)) {
                    Exodus newExodus = new Exodus();
                    newExodus.setNumber(exodusNumber);
                    day.getExoduses().put(exodusNumber, newExodus);
                }

                Exodus exodus = day.getExoduses().get(exodusNumber);
                String tripVoucherNumber = resultSet.getString("number");
                if (!exodus.getTripVouchers().containsKey(tripVoucherNumber)) {
                    TripVoucher newTripVoucher = new TripVoucher();
                    newTripVoucher.setNumber(tripVoucherNumber);
                    exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);
                }

                String type = resultSet.getString("type");
                LocalDateTime startTime = converter.convertStringTimeToDate(resultSet.getString("start_time"));
                LocalDateTime arrivalTime = converter.convertStringTimeToDate(resultSet.getString("arrival_time"));

                TripPeriod newTripPeriod = new TripPeriod(type, startTime, null, null, arrivalTime, null, null);

                TripVoucher tripVoucher = exodus.getTripVouchers().get(tripVoucherNumber);
                tripVoucher.getTripPeriods().add(newTripPeriod);

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlannedRouteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plannedRoute;
    }

    /*
    PlannedRoute getPlannedRouteSummary(RoutesPager plannedRoutesSummaryPager) {
        PlannedRoute plannedRoute = new PlannedRoute();
        plannedRoute.setNumber(plannedRoutesSummaryPager.getCurrentRoute());

        StringBuilder query = new StringBuilder();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT t2.number, date_stamp, exodus_number,  base_number, shift, trip_periods_total, kilometrage, driver_number, driver_name, bus_number, bus_type, base_leaving_time, base_return_time FROM route t1 INNER JOIN planned_trip_voucher t2 ON t1.number=t2.route_number  WHERE route_number=");
        StringBuilder queryBuilderDateStampPart = this.converter.buildStringFromArrayList(plannedRoutesSummaryPager.getDateStamps());

        query = queryBuilderInitialPart.append("'" + plannedRoutesSummaryPager.getCurrentRoute() + "'").
                append(" AND date_stamp IN ").append(queryBuilderDateStampPart);
        // append(" ORDER BY date_stamp, exodus_number, start_time ;");
        System.out.println(query);
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {

                String dateStamp = resultSet.getString("date_stamp");
                //    Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                if (!plannedRoute.getDays().containsKey(dateStamp)) {
                    Day newDay = new Day();
                    newDay.setDateStamp(dateStamp);
                    plannedRoute.getDays().put(dateStamp, newDay);
                }
                Day day = plannedRoute.getDays().get(dateStamp);

                short exodusNumber = resultSet.getShort("exodus_number");
                if (!day.getExoduses().containsKey(exodusNumber)) {
                    Exodus newExodus = new Exodus();
                    newExodus.setNumber(exodusNumber);
                    day.getExoduses().put(exodusNumber, newExodus);
                }

                Exodus exodus = day.getExoduses().get(exodusNumber);
                String tripVoucherNumber = resultSet.getString("number");

                TripVoucher newTripVoucher = new TripVoucher();
                newTripVoucher.setNumber(tripVoucherNumber);
                newTripVoucher.setShift(resultSet.getString("shift"));
                newTripVoucher.setTripPeriodsTotal(resultSet.getInt("trip_periods_total"));
                newTripVoucher.setKilometrage(resultSet.getFloat("kilometrage"));
                newTripVoucher.setDriverNumber(resultSet.getString("driver_number"));
                newTripVoucher.setDriverName(resultSet.getString("driver_name"));
                newTripVoucher.setBusNumber(resultSet.getString("bus_number"));
                newTripVoucher.setBusType(resultSet.getString("bus_type"));

                exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlannedRouteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plannedRoute;
    }
     */
}
