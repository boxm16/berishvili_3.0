/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Michail Sitmalidis
 */
public class GacdenebiFactory {

    LinkedHashMap<Integer, Driver> addGacdenebiToDriversData(LinkedHashMap<Integer, Driver> driversData, Map<String, String> data) {
        int rowIndex = 2;
        while (!data.isEmpty()) {

            String driverTabelNumberLocationInTheRow = new StringBuilder("C").append(String.valueOf(rowIndex)).toString();
            String driverTabelNumberString = data.remove(driverTabelNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map
            if (driverTabelNumberString == null) {//in theory this means that you reached the end of rows with data
                break;
            }
            int driverTabelNumber = Integer.parseInt(driverTabelNumberString);
            Driver driver;
            if (driversData.containsKey(driverTabelNumber)) {
                driver = driversData.get(driverTabelNumber);
            } else {
                System.out.println("SOmething wrong, there is driver tabel number in GACDENEBI that didnt exist before:" + driverTabelNumber);
                driver = new Driver();
            }
            driver.setTabelNumber(driverTabelNumber);

            driver = addLeaveTypeToDriver(driver, data, rowIndex);
            driversData.put(driverTabelNumber, driver);
            rowIndex++;
        }

        return driversData;
    }

    private Driver addLeaveTypeToDriver(Driver driver, Map<String, String> data, int rowIndex) {
        String leaveTypeLocationInTheRow = new StringBuilder("D").append(String.valueOf(rowIndex)).toString();
        String leaveType = data.remove(leaveTypeLocationInTheRow);
        driver.setLeaveType(leaveType);

        driver = leaveStatusToDriver(driver, data, rowIndex);
        return driver;
    }

    private Driver leaveStatusToDriver(Driver driver, Map<String, String> data, int rowIndex) {
        String leaveStatusLocationInTheRow = new StringBuilder("E").append(String.valueOf(rowIndex)).toString();
        String leaveStatus = data.remove(leaveStatusLocationInTheRow);
        driver.setLeaveStatus(leaveStatus);

        driver = leavePeriodToDriver(driver, data, rowIndex);
        return driver;
    }

    private Driver leavePeriodToDriver(Driver driver, Map<String, String> data, int rowIndex) {
        String leavePeriodLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
        String leavePeriod = data.remove(leavePeriodLocationInTheRow);
        driver.setLeavePeriod(leavePeriod);

        return driver;
    }

}
