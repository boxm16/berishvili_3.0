package TechMan;

import Service.DataBaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TechManDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    private Connection connection;

    public String createSchema() {
        try {
            connection = dataBaseConnection.getInitConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE SCHEMA berishvili_v3_db";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return "Schema 'berishvili_v3_db' created.";

        } catch (SQLException ex) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }

    public String createRouteTable() {
        String sql = "CREATE TABLE `route` ("
                + "`number` VARCHAR(10) NOT NULL, "
                + "`a_point` VARCHAR(100) NULL, "
                + "`b_point` VARCHAR(100) NULL, "
                + "`scheme` VARCHAR(5000) NULL, "
                + "PRIMARY KEY (`number`)) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return "Table 'route' created successfully";
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("42S01")) {
                return "Table 'route' already exists";
            } else {
                Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }
        }
    }

    public String createPlannedTripVoucherTable() {
        String sql = "CREATE TABLE `planned_trip_voucher` ("
                + "`number` VARCHAR(20) NOT NULL, "
                + "`route_number` VARCHAR(10) NOT NULL, "
                + "`date_stamp` DATE NOT NULL, "
                + "`base_number` INT(2) NOT NULL, "
                + "`exodus_number` INT(2) NOT NULL, "
                + "`driver_number` VARCHAR(10) NULL, "
                + "`driver_name` VARCHAR(45) NULL, "
                + "`bus_number` VARCHAR(15) NULL, "
                + "`bus_type` VARCHAR(35) NULL, "
                + "`shift` VARCHAR(5) NULL , "
                + "`trip_periods_total` INT(2) NULL , "
                + "`kilometrage` DECIMAL(7,3) NULL , "
                + "`base_leaving_time` TIME(0) NULL  DEFAULT NULL, "
                + "`base_return_time` TIME(0) NULL  DEFAULT NULL, "
                + "`notes` VARCHAR(2000) NULL, "
                + "PRIMARY KEY (`number`), "
                + "CONSTRAINT `route_number_2` "
                + "FOREIGN KEY (`route_number`) "
                + "REFERENCES `route` (`number`) "
                + "ON DELETE CASCADE "
                + "ON UPDATE NO ACTION) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return "Table 'planned_trip_voucher' created successfully";
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("42S01")) {
                return "Table 'planned_trip_voucher' already exists";
            } else {
                Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }
        }
    }

    public String createPlannedTripPeriodTable() {
        String sql = "CREATE TABLE `planned_trip_period` ("
                + "`trip_voucher_number` VARCHAR(20) NOT NULL, "
                + "`type` VARCHAR(15) NOT NULL, "
                + "`start_time` TIME(0) NULL  DEFAULT NULL, "
                + "`arrival_time` TIME(0) NULL  DEFAULT NULL, "
                + "CONSTRAINT `planned_trip_voucher` "
                + "FOREIGN KEY (`trip_voucher_number`) "
                + "REFERENCES `planned_trip_voucher` (`number`) "
                + "ON DELETE CASCADE "
                + "ON UPDATE NO ACTION) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8";

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return "Table 'planned_trip_period' created successfully";
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("42S01")) {
                return "Table 'planned_trip_period' already exists";
            } else {
                Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }
        }
    }

    public String deletePlannedTripPeriodTable() {
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE planned_trip_period";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, e);
            return e.getMessage();
        }
        return "Table planned_trip_period deleted successfully";
    }

    public String deletePlannedTripVoucherTable() {
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE planned_trip_voucher";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, e);
            return e.getMessage();
        }
        return "Table planned_trip_voucherd deleted successfully";
    }

    public String createTripVoucherTable() {
        String sql = "CREATE TABLE `trip_voucher` ("
                + "`number` VARCHAR(20) NOT NULL, "
                + "`route_number` VARCHAR(10) NOT NULL, "
                + "`date_stamp` DATE NOT NULL, "
                + "`base_number` INT(2) NOT NULL, "
                + "`exodus_number` INT(2) NOT NULL, "
                + "`driver_number` VARCHAR(10) NULL, "
                + "`driver_name` VARCHAR(45) NULL, "
                + "`bus_number` VARCHAR(15) NULL, "
                + "`bus_type` VARCHAR(35) NULL, "
                + "`shift` VARCHAR(5) NULL , "
                + "`trip_periods_scheduled_total` INT(2) NULL , "
                + "`trip_periods_actual_total` INT(2) NULL , "
                + "`kilometrage_scheduled` DECIMAL(7,3) NULL , "
                + "`kilometrage_actual` DECIMAL(7,3) NULL , "
                + "`base_leaving_time_scheduled` TIME(0) NULL  DEFAULT NULL, "
                + "`base_leaving_time_actual` TIME(0) NULL  DEFAULT NULL, "
                + "`base_leaving_time_redacted` TIME(0) NULL  DEFAULT NULL, "
                + "`base_return_time_scheduled` TIME(0) NULL  DEFAULT NULL, "
                + "`base_return_time_actual` TIME(0) NULL  DEFAULT NULL, "
                + "`base_return_time_redacted` TIME(0) NULL  DEFAULT NULL, "
                + "`notes` VARCHAR(2000) NULL, "
                + "PRIMARY KEY (`number`), "
                + "CONSTRAINT `route_number` "
                + "FOREIGN KEY (`route_number`) "
                + "REFERENCES `route` (`number`) "
                + "ON DELETE CASCADE "
                + "ON UPDATE NO ACTION) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8;";

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return "Table 'trip_voucher' created successfully";
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("42S01")) {
                return "Table 'trip_voucher' already exists";
            } else {
                Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }
        }
    }

    public String createTripPeriodTable() {
        String sql = "CREATE TABLE `trip_period` ("
                + "`trip_voucher_number` VARCHAR(20) NOT NULL, "
                + "`type` VARCHAR(15) NOT NULL, "
                + "`start_time_scheduled` TIME(0) NULL  DEFAULT NULL, "
                + "`start_time_actual` TIME(0) NULL  DEFAULT NULL, "
                + "`start_time_difference` VARCHAR(10) NULL, "
                + "`arrival_time_scheduled` TIME(0) NULL  DEFAULT NULL, "
                + "`arrival_time_actual` TIME(0) NULL  DEFAULT NULL, "
                + "`arrival_time_difference` VARCHAR(10) NULL, "
                + "CONSTRAINT `trip_voucher` "
                + "FOREIGN KEY (`trip_voucher_number`) "
                + "REFERENCES `trip_voucher` (`number`) "
                + "ON DELETE CASCADE "
                + "ON UPDATE NO ACTION) "
                + "ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8";

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return "Table 'trip_period' created successfully";
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("42S01")) {
                return "Table 'trip_period' already exists";
            } else {
                Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }
        }
    }

    public String deleteRouteTable() {
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE route";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, e);
            return e.getMessage();
        }
        return "Table route deleted successfully";
    }

    public String deleteTripVoucherTable() {
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE trip_voucher";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, e);
            return e.getMessage();
        }
        return "Table trip_voucher deleted successfully";
    }

    public String deleteTripPeriodTable() {
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE trip_period";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            Logger.getLogger(TechManDao.class.getName()).log(Level.SEVERE, null, e);
            return e.getMessage();
        }
        return "Table trip_period deleted successfully";
    }
    

}
