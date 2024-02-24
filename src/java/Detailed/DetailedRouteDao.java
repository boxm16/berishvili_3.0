/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Detailed;

import BasicModels.Day;
import BasicModels.Exodus;
import BasicModels.TripPeriod;
import BasicModels.TripVoucher;
import Planned.PlannedRouteDao;
import Service.Converter;
import Service.DataBaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DetailedRouteDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    @Autowired
    private Converter converter;

    private Connection connection;

    public DetailedRoute getDetailedRoute(String routeNumber, ArrayList<String> dateStamps) {
        DetailedRoute detailedRoute = new DetailedRoute();
        detailedRoute.setNumber(routeNumber);

        StringBuilder query = new StringBuilder();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT exodus_number, date_stamp, t2.number,  notes, type, start_time_scheduled, start_time_actual,start_time_difference, arrival_time_scheduled, arrival_time_actual, arrival_time_difference FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number=");
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
                if (!detailedRoute.getDays().containsKey(dateStamp)) {
                    Day newDay = new Day();
                    newDay.setDateStamp(dateStamp);
                    detailedRoute.getDays().put(dateStamp, newDay);
                }
                Day day = detailedRoute.getDays().get(dateStamp);

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
                }

                String type = resultSet.getString("type");
                LocalDateTime startTimeScheduled = converter.convertStringTimeToDate(resultSet.getString("start_time_scheduled"));
                LocalDateTime startTimeActual = converter.convertStringTimeToDate(resultSet.getString("start_time_actual"));
                LocalDateTime arrivalTimeScheduled = converter.convertStringTimeToDate(resultSet.getString("arrival_time_scheduled"));
                LocalDateTime arrivalTimeActual = converter.convertStringTimeToDate(resultSet.getString("arrival_time_actual"));
                

                TripPeriod newTripPeriod = new TripPeriod(type, startTimeScheduled, startTimeActual, null, arrivalTimeScheduled, arrivalTimeActual, null);

                TripVoucher tripVoucher = exodus.getTripVouchers().get(tripVoucherNumber);
                tripVoucher.getTripPeriods().add(newTripPeriod);

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlannedRouteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detailedRoute;
    }

}
