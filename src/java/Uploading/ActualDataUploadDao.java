/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uploading;

import BasicModels.Day;
import BasicModels.Exodus;
import BasicModels.Route;
import BasicModels.TripPeriod;
import BasicModels.TripVoucher;
import Service.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActualDataUploadDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;
    private Connection connection;

    public String insertNewData(TreeMap<String, Route> routesFromExcelFile) {
        try {

            connection = dataBaseConnection.getConnection();
            connection.setAutoCommit(false);
            //Statements to insert records
            //  PreparedStatement deleteTripPeriodPreparedStatement = connection.prepareStatement("DELETE FROM trip_period");
            //PreparedStatement deleteTripVoucherPreparedStatement = connection.prepareStatement("DELETE FROM trip_voucher");
            PreparedStatement tripVoucherInsertionPreparedStatement = connection.prepareStatement("INSERT INTO trip_voucher (number, route_number, date_stamp, base_number, exodus_number, driver_number, driver_name, bus_number, bus_type, base_leaving_time_scheduled,base_leaving_time_actual, base_leaving_time_redacted, base_return_time_scheduled, base_return_time_actual,base_return_time_redacted, shift, trip_periods_scheduled_total, trip_periods_actual_total, kilometrage_scheduled, kilometrage_actual) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
            PreparedStatement tripPeriodInsertionPreparedStatement = connection.prepareStatement("INSERT INTO trip_period (trip_voucher_number, type, start_time_scheduled,start_time_actual,start_time_difference, arrival_time_scheduled, arrival_time_actual, arrival_time_difference) VALUES (?,?,?,?,?,?,?,?);");

            // System.out.println("DELETING OLD DATA FORM DATABASE:...");
            //deleteTripPeriodPreparedStatement.execute();
            //deleteTripVoucherPreparedStatement.execute();
            //System.out.println("DELETING OLD DATA FORM DATABASE:DONE");
            LocalTime now1 = LocalTime.now();

            System.out.println(now1 + ": Starting INSERTION: ....");
            for (Map.Entry<String, Route> routeEntry : routesFromExcelFile.entrySet()) {
                System.out.println("Inserting route data. Route Number:" + routeEntry.getValue().getNumber());
                TreeMap<String, Day> days = routeEntry.getValue().getDays();
                for (Map.Entry<String, Day> dayEntry : days.entrySet()) {
                    String dateStamp = dayEntry.getValue().getDateStamp();
                    TreeMap<Short, Exodus> exoduses = dayEntry.getValue().getExoduses();
                    for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                        TreeMap<String, TripVoucher> tripVouchers = exodusEntry.getValue().getTripVouchers();
                        for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                            tripVoucherInsertionPreparedStatement.setString(1, tripVoucherEntry.getValue().getNumber());
                            tripVoucherInsertionPreparedStatement.setString(2, routeEntry.getValue().getNumber());
                            tripVoucherInsertionPreparedStatement.setString(3, dateStamp);
                            tripVoucherInsertionPreparedStatement.setShort(4, tripVoucherEntry.getValue().getBaseNumber());
                            tripVoucherInsertionPreparedStatement.setShort(5, exodusEntry.getValue().getNumber());

                            tripVoucherInsertionPreparedStatement.setString(6, tripVoucherEntry.getValue().getDriverNumber());
                            tripVoucherInsertionPreparedStatement.setString(7, tripVoucherEntry.getValue().getDriverName());

                            tripVoucherInsertionPreparedStatement.setString(8, tripVoucherEntry.getValue().getBusNumber());
                            tripVoucherInsertionPreparedStatement.setString(9, tripVoucherEntry.getValue().getBusType());

                            tripVoucherInsertionPreparedStatement.setObject(10, tripVoucherEntry.getValue().getBaseLeavingTimeScheduled());
                            tripVoucherInsertionPreparedStatement.setObject(11, tripVoucherEntry.getValue().getBaseLeavingTimeActual());
                            tripVoucherInsertionPreparedStatement.setObject(12, tripVoucherEntry.getValue().getBaseLeavingTimeRedacted());

                            tripVoucherInsertionPreparedStatement.setObject(13, tripVoucherEntry.getValue().getBaseReturnTimeScheduled());
                            tripVoucherInsertionPreparedStatement.setObject(14, tripVoucherEntry.getValue().getBaseReturnTimeActual());
                            tripVoucherInsertionPreparedStatement.setObject(15, tripVoucherEntry.getValue().getBaseReturnTimeRedacted());

                            tripVoucherInsertionPreparedStatement.setObject(16, tripVoucherEntry.getValue().getShift());
                            tripVoucherInsertionPreparedStatement.setObject(17, tripVoucherEntry.getValue().getTripPeriodsScheduledTotal());

                            tripVoucherInsertionPreparedStatement.setObject(18, tripVoucherEntry.getValue().getTripPeriodsActualTotal());
                            tripVoucherInsertionPreparedStatement.setObject(19, tripVoucherEntry.getValue().getKilometrageScheduled());
                            tripVoucherInsertionPreparedStatement.setObject(20, tripVoucherEntry.getValue().getKilometrageActual());

                            //here i used to cut notes lenght if they are more then 2000 letters, because i have that liit in mysql
                            tripVoucherInsertionPreparedStatement.addBatch();
                            //now trip Period
                            ArrayList<TripPeriod> tripPeriods = tripVoucherEntry.getValue().getTripPeriods();
                            for (TripPeriod tripPeriod : tripPeriods) {
                                tripPeriodInsertionPreparedStatement.setString(1, tripVoucherEntry.getValue().getNumber());
                                tripPeriodInsertionPreparedStatement.setString(2, tripPeriod.getType());

                                tripPeriodInsertionPreparedStatement.setObject(3, tripPeriod.getStartTimeScheduled());

                                tripPeriodInsertionPreparedStatement.setObject(4, tripPeriod.getStartTimeActual());

                                tripPeriodInsertionPreparedStatement.setObject(5, tripPeriod.getStartTimeDifference());
                                tripPeriodInsertionPreparedStatement.setObject(6, tripPeriod.getArrivalTimeScheduled());

                                tripPeriodInsertionPreparedStatement.setObject(7, tripPeriod.getArrivalTimeActual());
                                tripPeriodInsertionPreparedStatement.setObject(8, tripPeriod.getArrivalTimeDifference());

                                tripPeriodInsertionPreparedStatement.addBatch();
                            }
                        }
                    }
                }
            }
            //Executing the batch
            LocalTime now2 = LocalTime.now();
            System.out.println(now2 + ": Starting Batch Insertion:....");
            tripVoucherInsertionPreparedStatement.executeBatch();
            LocalTime now3 = LocalTime.now();
            System.out.println(now2 + ": TripVoucherInsertion Batch DONE, STARTING TripPeriodInsertion Batch:....");
            tripPeriodInsertionPreparedStatement.executeBatch();
             LocalTime now4 = LocalTime.now();
            System.out.println(now4+": Batch Insertion: DONE");

            //Saving the changes
            connection.commit();
            //  deleteTripPeriodPreparedStatement.close();
            // deleteTripVoucherPreparedStatement.close();
            tripVoucherInsertionPreparedStatement.close();
            tripPeriodInsertionPreparedStatement.close();
            connection.close();
            return "";
        } catch (SQLException ex) {
            Logger.getLogger(PlannedDataUploadDao.class.getName()).log(Level.SEVERE, null, ex);

            return ex.getMessage();
        }
    }

}
