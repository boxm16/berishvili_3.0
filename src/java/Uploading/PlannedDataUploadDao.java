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
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PlannedDataUploadDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;
    private Connection connection;

    public String insertNewData(TreeMap<String, Route> routesFromExcelFile) {
        try {

            connection = dataBaseConnection.getConnection();
            connection.setAutoCommit(false);
            //Statements to insert records
            PreparedStatement deleteTripPeriodPreparedStatement = connection.prepareStatement("DELETE FROM planned_trip_period");
            PreparedStatement deleteTripVoucherPreparedStatement = connection.prepareStatement("DELETE FROM planned_trip_voucher");
            PreparedStatement tripVoucherInsertionPreparedStatement = connection.prepareStatement("INSERT INTO planned_trip_voucher (number, route_number, date_stamp, base_number, exodus_number, driver_number, driver_name, bus_number, bus_type, base_leaving_time, base_return_time, shift, trip_periods_total, kilometrage) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
            PreparedStatement tripPeriodInsertionPreparedStatement = connection.prepareStatement("INSERT INTO planned_trip_period (trip_voucher_number, type, start_time, arrival_time) VALUES (?,?,?,?);");
            System.out.println("DELETING OLD DATA FROM DATABASE:.....");
            deleteTripPeriodPreparedStatement.execute();
            deleteTripVoucherPreparedStatement.execute();
            System.out.println("DELETING OLD DATA FROM DATABASE:DONE");

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
                            tripVoucherInsertionPreparedStatement.setObject(11, tripVoucherEntry.getValue().getBaseReturnTimeScheduled());

                            tripVoucherInsertionPreparedStatement.setObject(12, tripVoucherEntry.getValue().getShift());
                            tripVoucherInsertionPreparedStatement.setObject(13, tripVoucherEntry.getValue().getTripPeriodsScheduledTotal());
                            tripVoucherInsertionPreparedStatement.setObject(14, tripVoucherEntry.getValue().getKilometrageScheduled());

                            //here i cut notes lenght if they are more then 2000 letters, because i have that liit in mysql
                            tripVoucherInsertionPreparedStatement.addBatch();
                            //now trip Period
                            ArrayList<TripPeriod> tripPeriods = tripVoucherEntry.getValue().getTripPeriods();
                            for (TripPeriod tripPeriod : tripPeriods) {
                                tripPeriodInsertionPreparedStatement.setString(1, tripVoucherEntry.getValue().getNumber());
                                tripPeriodInsertionPreparedStatement.setString(2, tripPeriod.getType());

                                tripPeriodInsertionPreparedStatement.setObject(3, tripPeriod.getStartTimeScheduled());
                                tripPeriodInsertionPreparedStatement.setObject(4, tripPeriod.getArrivalTimeScheduled());
                                tripPeriodInsertionPreparedStatement.addBatch();
                            }
                        }
                    }
                }
            }
            //Executing the batch
            System.out.println("INSERTING BATCH:......");
            tripVoucherInsertionPreparedStatement.executeBatch();
            tripPeriodInsertionPreparedStatement.executeBatch();
            System.out.println("INSERTING BATCH:DONE");

            //Saving the changes
            connection.commit();
            deleteTripPeriodPreparedStatement.close();
            deleteTripVoucherPreparedStatement.close();
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
