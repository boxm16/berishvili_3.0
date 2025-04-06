/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import java.util.LinkedHashMap;
import java.util.Map;

public class NariadFactory {

    LinkedHashMap<Integer, Driver> addNariadToDriversData(LinkedHashMap<Integer, Driver> driversData, Map<String, String> data) {
        int rowIndex = 3;
        while (!data.isEmpty()) {
            String indexatorLocation = new StringBuilder("D").append(String.valueOf(rowIndex)).toString();
            String indexator = data.remove(indexatorLocation);//at the same time reading and removing the cell from hash Map
            if (indexator == null) {//in theory this means that you reached the end of rows with data
                break;
            }
            String driverTabelNumberLocationInTheRow = new StringBuilder("C").append(String.valueOf(rowIndex)).toString();
            String driverTabelNumberString = data.remove(driverTabelNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map

            Driver driver;
            if (driverTabelNumberString == null || driverTabelNumberString.equals("ტაბ N")) {

            } else {
                int driverTabelNumber = Integer.parseInt(driverTabelNumberString);

                if (driversData.containsKey(driverTabelNumber)) {
                    driver = driversData.get(driverTabelNumber);
                } else {
                    System.out.println("SOmething wrong, there is driver tabel number in NARIADI that didnt exist before:" + driverTabelNumber);
                    driver = new Driver();
                }
                driver.setTabelNumber(driverTabelNumber);
                driver = addBusNumberToDriver(driver, data, rowIndex);
                driversData.put(driverTabelNumber, driver);
            }

            rowIndex++;
        }

        return driversData;
    }

    private Driver addBusNumberToDriver(Driver driver, Map<String, String> data, int rowIndex) {
        String busNumberLocationInTheRow = new StringBuilder("A").append(String.valueOf(rowIndex)).toString();
        String busNumber = data.remove(busNumberLocationInTheRow);
        driver.setBusNumber(busNumber);

        driver = addNariadebiToDriver(driver, data, rowIndex);
        return driver;
    }

    private Driver addNariadebiToDriver(Driver driver, Map<String, String> data, int rowIndex) {
        for (Integer day = 1; day <= 31; day++) {
            // Convert day number to Excel column (1=C, 2=D, ..., 26=Z, 27=AA, 28=AB, ..., 31=AE)
            String columnLetter = toExcelColumn(day + 5); // +2 because A=1, B=2, C=3 (Day1 starts at C)
            String columnKey = columnLetter + rowIndex;

            String nariadi = data.get(columnKey);
            if (nariadi != null && !nariadi.isEmpty()) {
                try {
                    
                    LinkedHashMap<String, DriverDataAnalysisHolder> driverDataAnalysisHolders = driver.getDriverDataAnalysisHolders();
                    DriverDataAnalysisHolder driverDataAnalysisHolder = driverDataAnalysisHolders.get(day.toString());
                    driverDataAnalysisHolder.setNariadi(nariadi);
                } catch (NumberFormatException e) {
                    //driver.getWorkingHours().put(day.toString(), 0.0f); // Default to 0 on invalid input
                }
            } else {
                //driver.getWorkingHours().put(day.toString(), 0.0f); // Default to 0 if empty
            }
        }
        return driver;
    }

// Helper method to convert number to Excel column letters (1=A, 2=B, ..., 27=AA, etc.)
    private String toExcelColumn(int colNum) {
        StringBuilder sb = new StringBuilder();
        while (colNum > 0) {
            int remainder = (colNum - 1) % 26;
            sb.append((char) ('A' + remainder));
            colNum = (colNum - 1) / 26;
        }
        return sb.reverse().toString();
    }

}
