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
public class DanishvnaGatavisuplebaFactory {

    LinkedHashMap<Integer, Driver> getDanishvnaGatavisupleba(LinkedHashMap<Integer, Driver> driversData, Map<String, String> data) {

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
                System.out.println("Something wrong, there is driver tabel number in DanishvnaGatstenebi that didnt exist before :" + driverTabelNumber);

                driver = new Driver();
            }
            driver.setTabelNumber(driverTabelNumber);

            driver = addNameToDriver(driver, data, rowIndex);
            driversData.put(driverTabelNumber, driver);
            rowIndex++;
        }

        return driversData;
    }

    private Driver addNameToDriver(Driver driver, Map<String, String> data, int rowIndex) {
        String driverNameLocationInTheRow = new StringBuilder("B").append(String.valueOf(rowIndex)).toString();
        String driverName = data.remove(driverNameLocationInTheRow);
        //  driver.setName(driverName);

        driver = addBaseNumberToDriver(driver, data, rowIndex);
        return driver;
    }

    private Driver addBaseNumberToDriver(Driver driver, Map<String, String> data, int rowIndex) {
        String baseNumberLocationInTheRow = new StringBuilder("A").append(String.valueOf(rowIndex)).toString();
        String baseNumber = data.remove(baseNumberLocationInTheRow);
        if (baseNumber == null) {
          
            driver.setBaseNumber(Short.parseShort("0"));
        } else {
            driver.setBaseNumber(Short.parseShort(baseNumber));
        }

        driver = addStatusToDriver(driver, data, rowIndex);
        return driver;
    }

    private Driver addStatusToDriver(Driver driver, Map<String, String> data, int rowIndex) {
        String statusLocationInTheRow = new StringBuilder("D").append(String.valueOf(rowIndex)).toString();
        String status = data.remove(statusLocationInTheRow);
        driver.setStatus(status);
        driver = addPeriodToDriver(driver, data, rowIndex);
        return driver;
    }

    private Driver addPeriodToDriver(Driver driver, Map<String, String> data, int rowIndex) {
        String periodLocationInTheRow = new StringBuilder("E").append(String.valueOf(rowIndex)).toString();
        String periodString = data.remove(periodLocationInTheRow);
       
        driver.setPeriod(periodString);
        driver = addPBusTypeToDriver(driver, data, rowIndex);
        return driver;
    }

    private Driver addPBusTypeToDriver(Driver driver, Map<String, String> data, int rowIndex) {
        String busTypeLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
        String busType = data.remove(busTypeLocationInTheRow);
        driver.setBusType(busType);

        return driver;
    }

}
