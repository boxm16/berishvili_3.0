/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import FirstTrips.FirstTrip;
import Guaranty.GuarantyTrip;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;

/**
 *
 * @author Michail Sitmalidis
 */
public class ExcelWriter {

    Converter converter = new Converter();

    public void SXSSF_GuarantyTrips(ArrayList<GuarantyTrip> guarantyData, String fileName, HttpServletRequest request) {
        long begin = System.currentTimeMillis();
        Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

        // keep 100 rows in memory, exceeding rows will be flushed to disk
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
                OutputStream os = new FileOutputStream(new Basement().getBasementDirectory() + "/downloads/" + fileName + ".xlsx")) {

//setting date 1904 system (to show negative duration in excel workbook)
            workbook.getXSSFWorkbook().getCTWorkbook().getWorkbookPr().setDate1904(true);

            Sheet sheet = workbook.createSheet("საგარანტიო გასვლები");
            String path;
            path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";

            //  int rowIndex = 0;
            //------------------------------------------------------------------
            int rowIndex = 0;
            int columnIndex = 0;
            int rowHeigth = 0;
            int columnWidth = 0;

            //column width
            while (columnIndex < 14) {
                switch (columnIndex) {
                    case 0:
                        columnWidth = 3000;
                        break;
                    case 1:
                        columnWidth = 1000;
                        break;
                    case 2:
                        columnWidth = 1500;
                        break;
                    case 3:
                        columnWidth = 7000;
                        break;
                    case 4:
                        columnWidth = 7000;
                        break;
                    case 5:
                        columnWidth = 2000;
                        break;
                    case 6:
                        columnWidth = 5000;
                        break;
                    case 7:
                        columnWidth = 2800;
                        break;
                    case 8:
                        columnWidth = 2800;
                        break;
                    case 9:
                        columnWidth = 2800;
                        break;
                    case 10:
                        columnWidth = 1500;
                        break;
                    case 11:
                        columnWidth = 1500;
                        break;
                    case 12:
                        columnWidth = 7000;
                        break;
                    case 13:
                        columnWidth = 3500;
                        break;

                }
                sheet.setColumnWidth(columnIndex, columnWidth);
                columnIndex++;
            }
            //now headers
            XSSFCellStyle headerStyle = getHeaderStyle(workbook, 74, 229, 55, 0, false);
            XSSFCellStyle headerStyleVertical = getHeaderStyle(workbook, 74, 229, 55, 90, false);

            //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
            XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");
            XSSFCellStyle rowStyleWhiteRegularLightOn = getRowStyle(workbook, 220, 220, 220, false, false, "");

            XSSFCellStyle rowStyleWhiteNumber = getRowStyle(workbook, 255, 255, 255, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleWhiteNumberLightOn = getRowStyle(workbook, 220, 220, 220, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleRedNumber = getRowStyle(workbook, 255, 0, 0, false, false, "0");

            XSSFCellStyle rowStyleWhiteTimeHHmmss = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm:ss");
            XSSFCellStyle rowStyleWhiteTimeHHmmssLightOn = getRowStyle(workbook, 220, 220, 220, false, false, "[hh]:mm:ss");

            XSSFCellStyle rowStyleRedTimeHHmmss = getRowStyle(workbook, 255, 0, 0, false, false, "[hh]:mm:ss");

//first header row
            Row headerRow = sheet.createRow(rowIndex);
            columnIndex = 0;
            while (columnIndex < 37) {

                switch (columnIndex) {
                    case 0:
                        Cell cell_0 = headerRow.createCell(columnIndex);
                        cell_0.setCellValue("თარიღი");
                        cell_0.setCellStyle(headerStyle);
                        break;
                    case 1:
                        Cell cell_1 = headerRow.createCell(columnIndex);
                        cell_1.setCellValue("ავტობაზა");
                        cell_1.setCellStyle(headerStyleVertical);
                        break;
                    case 2:
                        Cell cell_2 = headerRow.createCell(columnIndex);
                        cell_2.setCellValue("მარშრუტის #");
                        cell_2.setCellStyle(headerStyleVertical);
                        break;

                    case 3:
                        Cell cell_3 = headerRow.createCell(columnIndex);
                        cell_3.setCellValue("A პუნქტი");
                        cell_3.setCellStyle(headerStyle);
                        break;
                    case 4:
                        Cell cell_4 = headerRow.createCell(columnIndex);
                        cell_4.setCellValue("B პუნქტი");
                        cell_4.setCellStyle(headerStyle);
                        break;
                    case 5:
                        Cell cell_5 = headerRow.createCell(columnIndex);
                        cell_5.setCellValue("მიმართულება");
                        cell_5.setCellStyle(headerStyleVertical);
                        break;
                    case 6:
                        Cell cell_6 = headerRow.createCell(columnIndex);
                        cell_6.setCellValue("საგარანტიო გასვლის ტიპი");
                        cell_6.setCellStyle(headerStyle);
                        break;
                    case 7:
                        Cell cell_7 = headerRow.createCell(columnIndex);
                        cell_7.setCellValue("გეგმიური გასვლის დრო");
                        cell_7.setCellStyle(headerStyleVertical);
                        break;
                    case 8:
                        Cell cell_8 = headerRow.createCell(columnIndex);
                        cell_8.setCellValue("ფაქტიური გასვლის დრო");
                        cell_8.setCellStyle(headerStyleVertical);
                        break;
                    case 9:
                        Cell cell_9 = headerRow.createCell(columnIndex);
                        cell_9.setCellValue("სხვაობა");
                        cell_9.setCellStyle(headerStyleVertical);
                        break;
                    case 10:
                        Cell cell_10 = headerRow.createCell(columnIndex);
                        cell_10.setCellValue("გეგმიური გასვლის ნომერი");
                        cell_10.setCellStyle(headerStyleVertical);
                        break;

                    case 11:
                        Cell cell_11 = headerRow.createCell(columnIndex);
                        cell_11.setCellValue("ფაქტიური გასვლის ნომერი");
                        cell_11.setCellStyle(headerStyleVertical);
                        break;
                    case 12:
                        Cell cell_12 = headerRow.createCell(columnIndex);
                        cell_12.setCellValue("ფაქტიური გასვლის მძღოლი");
                        cell_12.setCellStyle(headerStyle);
                        break;
                    case 13:
                        Cell cell_13 = headerRow.createCell(columnIndex);
                        cell_13.setCellValue("ფაქტიური გასვლის სახ. ნომერი");
                        cell_13.setCellStyle(headerStyleVertical);
                        break;
                }
                columnIndex++;
            }
            //--------------------
            //now rows

            rowIndex++;
            int dayIndex = 0;
            for (GuarantyTrip guarantyTrip : guarantyData) {
                Row row = sheet.createRow(rowIndex);
                Cell cell_0 = row.createCell(0);
                cell_0.setCellValue(guarantyTrip.getDateStamp());
                if (dayIndex % 2 == 0) {
                    cell_0.setCellStyle(rowStyleWhiteRegular);
                } else {
                    cell_0.setCellStyle(rowStyleWhiteRegularLightOn);
                }

                Cell cell_1 = row.createCell(1);
                cell_1.setCellValue(guarantyTrip.getBaseNumber());
                if (dayIndex % 2 == 0) {
                    cell_1.setCellStyle(rowStyleWhiteRegular);
                } else {
                    cell_1.setCellStyle(rowStyleWhiteRegularLightOn);
                }

                Cell cell_2 = row.createCell(2);
                cell_2.setCellValue(guarantyTrip.getRouteNumber());
                if (dayIndex % 2 == 0) {
                    cell_2.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_2.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_3 = row.createCell(3);
                cell_3.setCellValue(guarantyTrip.getaPoint());
                if (dayIndex % 2 == 0) {
                    cell_3.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_3.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_4 = row.createCell(4);
                cell_4.setCellValue(guarantyTrip.getbPoint());
                if (dayIndex % 2 == 0) {
                    cell_4.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_4.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_5 = row.createCell(5);
                cell_5.setCellValue(guarantyTrip.getTypeG());
                if (dayIndex % 2 == 0) {
                    cell_5.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_5.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_6 = row.createCell(6);
                cell_6.setCellValue(guarantyTrip.getGuarantyType());
                if (dayIndex % 2 == 0) {
                    cell_6.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_6.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_7 = row.createCell(7);
                cell_7.setCellValue(guarantyTrip.getStartTimeScheduledString());
                if (dayIndex % 2 == 0) {
                    cell_7.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_7.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_8 = row.createCell(8);
                cell_8.setCellValue(guarantyTrip.getStartTimeActualString());
                if (guarantyTrip.isSpacialCase()) {
                    cell_8.setCellStyle(rowStyleRedTimeHHmmss);
                } else {
                    if (dayIndex % 2 == 0) {
                        cell_8.setCellStyle(rowStyleWhiteTimeHHmmss);
                    } else {
                        cell_8.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                    }
                }

                Cell cell_9 = row.createCell(9);
                cell_9.setCellValue(guarantyTrip.getGuarantyStartTimeDifferenceString());
                if (guarantyTrip.getGuarantyStartTimeDifferenceColorString().equals("red")) {
                    cell_9.setCellStyle(rowStyleRedTimeHHmmss);
                } else {
                    if (dayIndex % 2 == 0) {
                        cell_9.setCellStyle(rowStyleWhiteTimeHHmmss);
                    } else {
                        cell_9.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                    }
                }

                Cell cell_10 = row.createCell(10);
                cell_10.setCellValue(guarantyTrip.getPlannedExodusNumber());
                if (dayIndex % 2 == 0) {
                    cell_10.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_10.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_11 = row.createCell(11);
                if (guarantyTrip.getActualExodusNumber() == 0) {
                    cell_11.setCellValue("");
                } else {
                    cell_11.setCellValue(guarantyTrip.getActualExodusNumber());
                }
                if (guarantyTrip.getPlannedExodusNumber() != guarantyTrip.getActualExodusNumber()) {
                    cell_11.setCellStyle(rowStyleRedNumber);
                } else {
                    if (dayIndex % 2 == 0) {
                        cell_11.setCellStyle(rowStyleWhiteNumber);
                    } else {
                        cell_11.setCellStyle(rowStyleWhiteNumberLightOn);
                    }
                }

                Cell cell_12 = row.createCell(12);
                cell_12.setCellValue(guarantyTrip.getDriver());
                if (dayIndex % 2 == 0) {
                    cell_12.setCellStyle(rowStyleWhiteRegular);
                } else {
                    cell_12.setCellStyle(rowStyleWhiteRegularLightOn);
                }

                Cell cell_13 = row.createCell(13);
                cell_13.setCellValue("");
                if (dayIndex % 2 == 0) {
                    cell_13.setCellStyle(rowStyleWhiteRegular);
                } else {
                    cell_13.setCellStyle(rowStyleWhiteRegularLightOn);
                }
                rowIndex++;
            }

//--------------------------
            workbook.write(os);
            System.out.println("++++Guaranty Trips Excel Writing Completed++++");

            LOGGER.info("Time needed:" + (System.currentTimeMillis() - begin) / 1000);

        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private XSSFCellStyle getHeaderStyle(SXSSFWorkbook workbook, int rgbA, int rgbB, int rgbC, int rotation, boolean bold) {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        byte[] rgb = new byte[]{(byte) rgbA, (byte) rgbB, (byte) rgbC};
        XSSFColor color = new XSSFColor(rgb, null); //IndexedColorMap has no usage until now. So it can be set null.
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setRotation((short) rotation);

        style.setWrapText(true);

        style.setRotation((short) rotation);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        // font
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Sylfaen");
        font.setBold(bold);
        style.setFont(font);

        return style;
    }

    private XSSFCellStyle getRowStyle(SXSSFWorkbook workbook, int rgbA, int rgbB, int rgbC, boolean italic, boolean bold, String format) {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        byte[] rgb = new byte[]{(byte) rgbA, (byte) rgbB, (byte) rgbC};
        XSSFColor color = new XSSFColor(rgb, null); //IndexedColorMap has no usage until now. So it can be set null.
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setWrapText(true);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        // font
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Sylfaen");
        font.setItalic(italic);
        font.setBold(bold);
        style.setFont(font);

        //time formats "[hh]:mm"   "[mm]:ss"
        XSSFDataFormat fmts = (XSSFDataFormat) workbook.createDataFormat();
        style.setDataFormat(fmts.getFormat(format));
        return style;
    }

    public void SXSSF_FirstTrips(ArrayList<FirstTrip> data, String fileName, HttpServletRequest request) {
        long begin = System.currentTimeMillis();
        Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

        // keep 100 rows in memory, exceeding rows will be flushed to disk
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
                OutputStream os = new FileOutputStream(new Basement().getBasementDirectory() + "/downloads/" + fileName + ".xlsx")) {

//setting date 1904 system (to show negative duration in excel workbook)
            workbook.getXSSFWorkbook().getCTWorkbook().getWorkbookPr().setDate1904(true);

            Sheet sheet = workbook.createSheet("პირველი გასვლები");
            String path;
            path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";

            //  int rowIndex = 0;
            //------------------------------------------------------------------
            int rowIndex = 0;
            int columnIndex = 0;
            int rowHeigth = 0;
            int columnWidth = 0;

            //column width
            while (columnIndex < 15) {
                switch (columnIndex) {
                    case 0:
                        columnWidth = 3000;
                        break;
                    case 1:
                        columnWidth = 2000;
                        break;
                    case 2:
                        columnWidth = 2000;
                        break;
                    case 3:
                        columnWidth = 2000;
                        break;
                    case 4:
                        columnWidth = 3000;
                        break;
                    case 5:
                        columnWidth = 3000;
                        break;
                    case 6:
                        columnWidth = 7000;
                        break;
                    case 7:
                        columnWidth = 3000;
                        break;
                    case 8:
                        columnWidth = 3000;
                        break;
                    case 9:
                        columnWidth = 3000;
                        break;
                    case 10:
                        columnWidth = 3000;
                        break;
                    case 11:
                        columnWidth = 3000;
                        break;
                    case 12:
                        columnWidth = 3000;
                        break;
                    case 13:
                        columnWidth = 3000;
                        break;
                    case 14:
                        columnWidth = 7000;
                        break;

                }
                sheet.setColumnWidth(columnIndex, columnWidth);
                columnIndex++;
            }
            //now headers
            XSSFCellStyle headerStyle = getHeaderStyle(workbook, 74, 229, 55, 0, false);
            XSSFCellStyle headerStyleVertical = getHeaderStyle(workbook, 74, 229, 55, 90, false);

            //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
            XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");
            XSSFCellStyle rowStyleWhiteRegularLightOn = getRowStyle(workbook, 220, 220, 220, false, false, "");

            XSSFCellStyle rowStyleWhiteNumber = getRowStyle(workbook, 255, 255, 255, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleWhiteNumberLightOn = getRowStyle(workbook, 220, 220, 220, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleRedNumber = getRowStyle(workbook, 255, 0, 0, false, false, "0");

            XSSFCellStyle rowStyleWhiteTimeHHmmss = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm:ss");
            XSSFCellStyle rowStyleWhiteTimeHHmmssLightOn = getRowStyle(workbook, 220, 220, 220, false, false, "[hh]:mm:ss");

            XSSFCellStyle rowStyleRedTimeHHmmss = getRowStyle(workbook, 255, 0, 0, false, false, "[hh]:mm:ss");

//first header row
            Row headerRow = sheet.createRow(rowIndex);
            columnIndex = 0;
            while (columnIndex < 37) {

                switch (columnIndex) {
                    case 0:
                        Cell cell_0 = headerRow.createCell(columnIndex);
                        cell_0.setCellValue("თარიღი");
                        cell_0.setCellStyle(headerStyle);
                        break;
                    case 1:
                        Cell cell_1 = headerRow.createCell(columnIndex);
                        cell_1.setCellValue("ავტობაზა");
                        cell_1.setCellStyle(headerStyle);
                        break;
                    case 2:
                        Cell cell_2 = headerRow.createCell(columnIndex);
                        cell_2.setCellValue("მარშრუტის #");
                        cell_2.setCellStyle(headerStyle);
                        break;
                    case 3:
                        Cell cell_3 = headerRow.createCell(columnIndex);
                        cell_3.setCellValue("გასვლ. #");
                        cell_3.setCellStyle(headerStyle);
                        break;

                    case 4:
                        Cell cell_4 = headerRow.createCell(columnIndex);
                        cell_4.setCellValue("სახ.#");
                        cell_4.setCellStyle(headerStyle);
                        break;
                    case 5:
                        Cell cell_5 = headerRow.createCell(columnIndex);
                        cell_5.setCellValue("ტაბელის #");
                        cell_5.setCellStyle(headerStyle);
                        break;
                    case 6:
                        Cell cell_6 = headerRow.createCell(columnIndex);
                        cell_6.setCellValue("სახელი გვარი");
                        cell_6.setCellStyle(headerStyle);
                        break;
                    case 7:
                        Cell cell_7 = headerRow.createCell(columnIndex);
                        cell_7.setCellValue("ბაზიდან გამოსვლის დრო გეგმიური");
                        cell_7.setCellStyle(headerStyle);
                        break;
                    case 8:
                        Cell cell_8 = headerRow.createCell(columnIndex);
                        cell_8.setCellValue("ბაზიდან გამოსვლის დრო ფაქტიური");
                        cell_8.setCellStyle(headerStyle);
                        break;
                    case 9:
                        Cell cell_9 = headerRow.createCell(columnIndex);
                        cell_9.setCellValue("პუნქტში მისვლის დრო გეგმიური");
                        cell_9.setCellStyle(headerStyle);
                        break;
                    case 10:
                        Cell cell_10 = headerRow.createCell(columnIndex);
                        cell_10.setCellValue("პუნქტში მისვლის დრო ფაქტიური");
                        cell_10.setCellStyle(headerStyle);
                        break;
                    case 11:
                        Cell cell_11 = headerRow.createCell(columnIndex);
                        cell_11.setCellValue("ხაზზე გასვლის დრო გეგმიური");
                        cell_11.setCellStyle(headerStyle);
                        break;

                    case 12:
                        Cell cell_12 = headerRow.createCell(columnIndex);
                        cell_12.setCellValue("ხაზზე გასვლის დრო ფაქტიური");
                        cell_12.setCellStyle(headerStyle);
                        break;
                    case 13:
                        Cell cell_13 = headerRow.createCell(columnIndex);
                        cell_13.setCellValue("დარღვევა");
                        cell_13.setCellStyle(headerStyle);
                        break;
                    case 14:
                        Cell cell_14 = headerRow.createCell(columnIndex);
                        cell_14.setCellValue("გატარებული ღონისძიება");
                        cell_14.setCellStyle(headerStyle);
                        break;
                }
                columnIndex++;
            }
            //--------------------
            //now rows

            rowIndex++;
            int dayIndex = 0;
            for (FirstTrip trip : data) {
                Row row = sheet.createRow(rowIndex);
                Cell cell_0 = row.createCell(0);
                cell_0.setCellValue(trip.getDateStamp());
                if (dayIndex % 2 == 0) {
                    cell_0.setCellStyle(rowStyleWhiteRegular);
                } else {
                    cell_0.setCellStyle(rowStyleWhiteRegularLightOn);
                }

                Cell cell_1 = row.createCell(1);
                cell_1.setCellValue(trip.getBaseNumber());
                if (dayIndex % 2 == 0) {
                    cell_1.setCellStyle(rowStyleWhiteRegular);
                } else {
                    cell_1.setCellStyle(rowStyleWhiteRegularLightOn);
                }

                Cell cell_2 = row.createCell(2);
                cell_2.setCellValue(trip.getRouteNumber());
                if (dayIndex % 2 == 0) {
                    cell_2.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_2.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_3 = row.createCell(3);
                cell_3.setCellValue(trip.getExoudsNumber());
                if (dayIndex % 2 == 0) {
                    cell_3.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_3.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_4 = row.createCell(4);
                cell_4.setCellValue(trip.getBusNumber());
                if (dayIndex % 2 == 0) {
                    cell_4.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_4.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_5 = row.createCell(5);
                cell_5.setCellValue(trip.getDriverNumber());
                if (dayIndex % 2 == 0) {
                    cell_5.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_5.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_6 = row.createCell(6);
                cell_6.setCellValue(trip.getDriverName());
                if (dayIndex % 2 == 0) {
                    cell_6.setCellStyle(rowStyleWhiteNumber);
                } else {
                    cell_6.setCellStyle(rowStyleWhiteNumberLightOn);
                }

                Cell cell_7 = row.createCell(7);
                cell_7.setCellValue(trip.getBaseTripStartTimeScheduledString());
                if (dayIndex % 2 == 0) {
                    cell_7.setCellStyle(rowStyleWhiteTimeHHmmss);
                } else {
                    cell_7.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_8 = row.createCell(8);
                cell_8.setCellValue(trip.getBaseTripStartTimeActualString());
                if (dayIndex % 2 == 0) {
                    cell_8.setCellStyle(rowStyleWhiteTimeHHmmss);
                } else {
                    cell_8.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_9 = row.createCell(9);
                cell_9.setCellValue(trip.getBaseTripEndTimeScheduledString());
                if (dayIndex % 2 == 0) {
                    cell_9.setCellStyle(rowStyleWhiteTimeHHmmss);
                } else {
                    cell_9.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_10 = row.createCell(10);
                cell_10.setCellValue(trip.getBaseTripEndTimeActualString());
                if (dayIndex % 2 == 0) {
                    cell_10.setCellStyle(rowStyleWhiteTimeHHmmss);
                } else {
                    cell_10.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_11 = row.createCell(11);
                cell_11.setCellValue(trip.getStartTimeScheduledString());
                if (dayIndex % 2 == 0) {
                    cell_11.setCellStyle(rowStyleWhiteTimeHHmmss);
                } else {
                    cell_11.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_12 = row.createCell(12);
                cell_12.setCellValue(trip.getStartTimeActualString());
                if (trip.getActualArrivalTimeColor().equals("red")) {
                    cell_12.setCellStyle(rowStyleRedTimeHHmmss);
                } else {
                    if (dayIndex % 2 == 0) {
                        cell_12.setCellStyle(rowStyleWhiteTimeHHmmss);
                    } else {
                        cell_12.setCellStyle(rowStyleWhiteTimeHHmmss);
                    }
                }
                Cell cell_13 = row.createCell(13);
                cell_13.setCellValue(trip.getStartTimeDifferenceString());
                if (trip.getStartTimeDifferenceColorString().equals("red")) {
                    cell_13.setCellStyle(rowStyleRedTimeHHmmss);
                } else {
                    if (dayIndex % 2 == 0) {
                        cell_13.setCellStyle(rowStyleWhiteRegular);
                    } else {
                        cell_13.setCellStyle(rowStyleWhiteRegularLightOn);
                    }
                }
                Cell cell_14 = row.createCell(14);
                cell_14.setCellValue("");
                if (dayIndex % 2 == 0) {
                    cell_14.setCellStyle(rowStyleWhiteRegular);
                } else {
                    cell_14.setCellStyle(rowStyleWhiteRegularLightOn);
                }
                rowIndex++;
            }

//--------------------------
            workbook.write(os);
            System.out.println("++++First Trips Excel Writing Completed++++");

            LOGGER.info("Time needed:" + (System.currentTimeMillis() - begin) / 1000);

        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
