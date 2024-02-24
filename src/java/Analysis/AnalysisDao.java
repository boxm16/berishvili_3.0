package Analysis;

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
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnalysisDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    @Autowired
    private Converter converter;

    private Connection connection;

    public LinkedHashMap<String, Route> getPlannedRoutes(LinkedHashMap<String, ArrayList<String>> routesDates) {
        LinkedHashMap<String, Route> routes = new LinkedHashMap<>();

        Set<String> keySet = routesDates.keySet();
        ArrayList<String> routeNumers = new ArrayList<>(keySet);
        String firstRouteNumber = routeNumers.get(0);
        StringBuilder query;
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT route_number, exodus_number, date_stamp, t2.number, type, start_time, arrival_time, driver_number, driver_name, base_leaving_time, base_return_time FROM route t1 INNER JOIN planned_trip_voucher t2 ON t1.number=t2.route_number INNER JOIN planned_trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number IN ");

        StringBuilder queryBuilderRouteNumberPart = this.converter.buildStringFromArrayList(routeNumers);
        StringBuilder queryBuilderDateStampPart = this.converter.buildStringFromArrayList(routesDates.get(firstRouteNumber));

        query = queryBuilderInitialPart.append(queryBuilderRouteNumberPart).
                append(" AND date_stamp IN ").append(queryBuilderDateStampPart);
//  append(" ORDER BY prefix, suffix, date_stamp, exodus_number, start_time_scheduled ");
        //  System.out.println(query);
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                String routeNumber = resultSet.getString("route_number");

                if (!routes.containsKey(routeNumber)) {
                    System.out.println("CREATING NEW PLANNED ROUTE  FROM DATABASE: " + routeNumber);
                    Route route = new Route();
                    route.setNumber(routeNumber);
                    routes.put(routeNumber, route);
                }
                Route route = routes.get(routeNumber);
                String dateStamp = resultSet.getString("date_stamp");
                //    Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                TreeMap<String, Day> days = route.getDays();
                if (!days.containsKey(dateStamp)) {
                    Day newDay = new Day();
                    newDay.setDateStamp(dateStamp);
                    route.getDays().put(dateStamp, newDay);
                }
                Day day = route.getDays().get(dateStamp);

                short exodusNumber = resultSet.getShort("exodus_number");
                if (!day.getPlannedExoduses().containsKey(exodusNumber)) {
                    Exodus newExodus = new Exodus();
                    newExodus.setNumber(exodusNumber);
                    day.getPlannedExoduses().put(exodusNumber, newExodus);
                }

                Exodus exodus = day.getPlannedExoduses().get(exodusNumber);
                String tripVoucherNumber = resultSet.getString("number");
                if (!exodus.getTripVouchers().containsKey(tripVoucherNumber)) {
                    TripVoucher newTripVoucher = new TripVoucher();
                    newTripVoucher.setNumber(tripVoucherNumber);

                    String driverNumber = resultSet.getString("driver_number");
                    newTripVoucher.setDriverNumber(driverNumber);

                    String driverName = resultSet.getString("driver_name");
                    newTripVoucher.setDriverName(driverName);

                    LocalDateTime baseLeavingTime = converter.convertStringTimeToDate(resultSet.getString("base_leaving_time"));
                    newTripVoucher.setBaseLeavingTimeScheduled(baseLeavingTime);

                    LocalDateTime baseReturnTime = converter.convertStringTimeToDate(resultSet.getString("base_return_time"));
                    newTripVoucher.setBaseLeavingTimeScheduled(baseReturnTime);

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
            Logger.getLogger(AnalysisDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return routes;
    }

    LinkedHashMap<String, Route> getActualRoutes(LinkedHashMap<String, ArrayList<String>> routesDates) {
        LinkedHashMap<String, Route> routes = new LinkedHashMap<>();

        Set<String> keySet = routesDates.keySet();
        ArrayList<String> routeNumers = new ArrayList<String>(keySet);
        String firstRouteNumber = routeNumers.get(0);
        StringBuilder query;
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT route_number, exodus_number, date_stamp, t2.number,  notes, type, start_time_scheduled, start_time_actual,start_time_difference, arrival_time_scheduled, arrival_time_actual, arrival_time_difference, driver_number, driver_name, base_leaving_time_actual, base_return_time_actual, base_return_time_redacted  FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number IN ");

        StringBuilder queryBuilderRouteNumberPart = this.converter.buildStringFromArrayList(routeNumers);
        StringBuilder queryBuilderDateStampPart = this.converter.buildStringFromArrayList(routesDates.get(firstRouteNumber));

        query = queryBuilderInitialPart.append(queryBuilderRouteNumberPart).
                append(" AND date_stamp IN ").append(queryBuilderDateStampPart);
//  append(" ORDER BY prefix, suffix, date_stamp, exodus_number, start_time_scheduled ");
        //  System.out.println(query);
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                String routeNumber = resultSet.getString("route_number");

                if (!routes.containsKey(routeNumber)) {
                    System.out.println("CREATING NEW ACTUAL ROUTE  FROM DATABASE: " + routeNumber);

                    Route route = new Route();
                    route.setNumber(routeNumber);
                    routes.put(routeNumber, route);
                }
                Route route = routes.get(routeNumber);
                String dateStamp = resultSet.getString("date_stamp");
                //    Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                TreeMap<String, Day> days = route.getDays();
                if (!days.containsKey(dateStamp)) {
                    Day newDay = new Day();
                    newDay.setDateStamp(dateStamp);
                    route.getDays().put(dateStamp, newDay);
                }
                Day day = route.getDays().get(dateStamp);

                short exodusNumber = resultSet.getShort("exodus_number");
                if (!day.getActualExoduses().containsKey(exodusNumber)) {
                    Exodus newExodus = new Exodus();
                    newExodus.setNumber(exodusNumber);
                    day.getActualExoduses().put(exodusNumber, newExodus);
                }

                Exodus exodus = day.getActualExoduses().get(exodusNumber);
                String tripVoucherNumber = resultSet.getString("number");
                if (!exodus.getTripVouchers().containsKey(tripVoucherNumber)) {
                    TripVoucher newTripVoucher = new TripVoucher();
                    newTripVoucher.setNumber(tripVoucherNumber);

                    String driverNumber = resultSet.getString("driver_number");
                    newTripVoucher.setDriverNumber(driverNumber);

                    String driverName = resultSet.getString("driver_name");
                    newTripVoucher.setDriverName(driverName);

                    String baseLeavingTimeActualString = resultSet.getString("base_leaving_time_actual");
                    LocalDateTime baseLeavingTimeActual = converter.convertStringTimeToDate(baseLeavingTimeActualString);
                    newTripVoucher.setBaseLeavingTimeActual(baseLeavingTimeActual);

                    String baseReturnTimeActualString = resultSet.getString("base_return_time_actual");
                    LocalDateTime baseReturnTimeActual = converter.convertStringTimeToDate(baseReturnTimeActualString);
                    newTripVoucher.setBaseReturnTimeActual(baseReturnTimeActual);

                    String baseReturnTimeRedactedString = resultSet.getString("base_return_time_redacted");
                    LocalDateTime baseReturnTimeRedacted = converter.convertStringTimeToDate(baseReturnTimeRedactedString);
                    newTripVoucher.setBaseReturnTimeRedacted(baseReturnTimeRedacted);

                    exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);
                }

                String type = resultSet.getString("type");
                LocalDateTime startTime = converter.convertStringTimeToDate(resultSet.getString("start_time_actual"));
                LocalDateTime arrivalTime = converter.convertStringTimeToDate(resultSet.getString("arrival_time_actual"));

                TripPeriod newTripPeriod = new TripPeriod(type, startTime, null, null, arrivalTime, null, null);

                TripVoucher tripVoucher = exodus.getTripVouchers().get(tripVoucherNumber);
                tripVoucher.getTripPeriods().add(newTripPeriod);

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(AnalysisDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return routes;
    }

}
