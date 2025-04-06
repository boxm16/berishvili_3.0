/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import java.util.LinkedHashMap;
import java.util.Map;

public class NamushevariDroebiFactory {

    LinkedHashMap<Integer, Driver> getNamushevariDroebi(Map<String, String> data) {
        LinkedHashMap<Integer, Driver> driversData = new LinkedHashMap<>();
        int rowIndex = 7;
        while (!data.isEmpty()) {

            String driverTabelNumberLocationInTheRow = new StringBuilder("A").append(String.valueOf(rowIndex)).toString();
            String driverTabelNumberString = data.remove(driverTabelNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map
            if (driverTabelNumberString == null) {//in theory this means that you reached the end of rows with data
                System.out.println("Breaking Reading At Row: " + rowIndex);
                break;
            }
            int driverTabelNumber = Integer.parseInt(driverTabelNumberString);
            Driver driver;
            if (driversData.containsKey(driverTabelNumber)) {
                driver = driversData.get(driverTabelNumber);
            } else {
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
        driver.setName(driverName);
        driver = addWorkingHoursToDriver(driver, data, rowIndex);
        return driver;
    }

    private Driver addWorkingHoursToDriver(Driver driver, Map<String, String> data, int rowIndex) {
        for (Integer day = 1; day <= 31; day++) {
            // Convert day number to Excel column (1=C, 2=D, ..., 26=Z, 27=AA, 28=AB, ..., 31=AE)
            String columnLetter = toExcelColumn(day + 2); // +2 because A=1, B=2, C=3 (Day1 starts at C)
            String columnKey = columnLetter + rowIndex;

            String hoursString = data.get(columnKey);
            if (hoursString != null && !hoursString.isEmpty()) {
                try {
                    float hours = Float.parseFloat(hoursString);
                    LinkedHashMap<String, DriverDataAnalysisHolder> driverDataAnalysisHolders = driver.getDriverDataAnalysisHolders();
                    DriverDataAnalysisHolder driverDataAnalysisHolder = driverDataAnalysisHolders.get(day.toString());
                    driverDataAnalysisHolder.setWorkingHours(hours);

                    
                } catch (NumberFormatException e) {
                    LinkedHashMap<String, DriverDataAnalysisHolder> driverDataAnalysisHolders = driver.getDriverDataAnalysisHolders();
                    DriverDataAnalysisHolder driverDataAnalysisHolder = driverDataAnalysisHolders.get(day.toString());
                    driverDataAnalysisHolder.setWorkingHours(0.0f);
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
