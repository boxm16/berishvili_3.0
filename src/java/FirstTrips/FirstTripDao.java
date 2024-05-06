/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FirstTrips;

import BasicModels.Day;
import BasicModels.Exodus;
import BasicModels.Route;
import BasicModels.TripPeriod;
import BasicModels.TripVoucher;
import NewUpload.RequestData;
import Service.Converter;
import Service.DataBaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Michail Sitmalidis
 */
public class FirstTripDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    private Connection connection;

    public TreeMap<String, Route> getPlannedRoutes(RequestData requestedData) {
        TreeMap<String, Route> routes = new TreeMap();
        StringBuilder query = new StringBuilder();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT a_point, b_point, base_number, route_number, bus_number, driver_name, exodus_number, date_stamp, t2.number,  notes, type, start_time, arrival_time FROM route t1 INNER JOIN planned_trip_voucher t2 ON t1.number=t2.route_number INNER JOIN planned_trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number IN ");

        StringBuilder queryBuilderRouteNumberPart = buildStringFromArrayList(requestedData.getRoutes());
        StringBuilder queryBuilderDateStampPart = buildStringFromArrayList(requestedData.getDates());

        query = queryBuilderInitialPart.append(queryBuilderRouteNumberPart).
                append(" AND date_stamp IN ").append(queryBuilderDateStampPart).
                append(" ORDER BY  route_number, date_stamp, exodus_number;");

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                String routeNumber = resultSet.getString("route_number");

                if (!routes.containsKey(routeNumber)) {
                    Route guarantyRoute = new Route();
                    guarantyRoute.setNumber(routeNumber);
                    guarantyRoute.setaPoint(resultSet.getString("a_point"));
                    guarantyRoute.setbPoint(resultSet.getString("b_point"));
                    routes.put(routeNumber, guarantyRoute);
                }
                Route route = routes.get(routeNumber);
                String dateStamp = resultSet.getString("date_stamp");
                Converter converter = new Converter();
                if (!route.getDays().containsKey(dateStamp)) {
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

                    exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);
                    exodus.setNumber(exodusNumber);
                }
                TripVoucher tripVoucher = exodus.getTripVouchers().get(tripVoucherNumber);

                TripPeriod newTripPeriod = new TripPeriod();
                tripVoucher.setBaseNumber(resultSet.getShort("base_number"));

                newTripPeriod.setType(resultSet.getString("type"));
                tripVoucher.setBusNumber(resultSet.getString("bus_number"));
                tripVoucher.setDriverName(resultSet.getString("driver_name"));

                newTripPeriod.setStartTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("start_time")));

                newTripPeriod.setArrivalTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("arrival_time")));

                tripVoucher.getTripPeriods().add(newTripPeriod);

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(FirstTripDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return routes;
    }

    public HashMap<String, Route> getActualRoutes(RequestData requestedData) {
        HashMap<String, Route> routes = new HashMap();
        StringBuilder query = new StringBuilder();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT base_number, route_number, bus_number, driver_number, driver_name, exodus_number, date_stamp, t2.number,  notes, type, start_time_actual, arrival_time_actual FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number IN ");

        StringBuilder queryBuilderRouteNumberPart = buildStringFromArrayList(requestedData.getRoutes());
        StringBuilder queryBuilderDateStampPart = buildStringFromArrayList(requestedData.getDates());

        query = queryBuilderInitialPart.append(queryBuilderRouteNumberPart).
                append(" AND date_stamp IN ").append(queryBuilderDateStampPart).
                append(" ORDER BY route_number, date_stamp, exodus_number;");

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                String routeNumber = resultSet.getString("route_number");

                if (!routes.containsKey(routeNumber)) {
                    Route guarantyRoute = new Route();
                    guarantyRoute.setNumber(routeNumber);
                    routes.put(routeNumber, guarantyRoute);
                }
                Route route = routes.get(routeNumber);
                String dateStamp = resultSet.getString("date_stamp");
                Converter converter = new Converter();
                if (!route.getDays().containsKey(dateStamp)) {
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

                    exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);
                    exodus.setNumber(exodusNumber);
                }
                TripVoucher tripVoucher = exodus.getTripVouchers().get(tripVoucherNumber);

                TripPeriod newTripPeriod = new TripPeriod();
                tripVoucher.setBaseNumber(resultSet.getShort("base_number"));

                newTripPeriod.setType(resultSet.getString("type"));
                tripVoucher.setBusNumber(resultSet.getString("bus_number"));
                tripVoucher.setDriverNumber(resultSet.getString("driver_number"));
                tripVoucher.setDriverName(resultSet.getString("driver_name"));

                newTripPeriod.setStartTimeActual(converter.convertStringTimeToDate(resultSet.getString("start_time_actual")));

                newTripPeriod.setArrivalTimeActual(converter.convertStringTimeToDate(resultSet.getString("arrival_time_actual")));

                tripVoucher.getTripPeriods().add(newTripPeriod);

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(FirstTripDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return routes;
    }

    private StringBuilder buildStringFromArrayList(ArrayList<String> arrayList) {

        StringBuilder stringBuilder = new StringBuilder("(");
        if (arrayList.isEmpty()) {
            stringBuilder.append(")");
            return stringBuilder;
        }
        int x = 0;
        for (String entry : arrayList) {
            if (x == 0) {
                stringBuilder.append("'").append(entry).append("'");
            } else {
                stringBuilder.append(",'").append(entry).append("'");
            }
            if (x == arrayList.size() - 1) {
                stringBuilder.append(")");
            }
            x++;
        }
        return stringBuilder;
    }

}
