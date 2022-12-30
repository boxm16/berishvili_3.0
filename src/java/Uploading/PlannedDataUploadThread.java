package Uploading;

import ExcelReading.ExcelReader;
import Service.StaticsDispatcher;
import java.util.HashMap;
import java.util.Map;

public class PlannedDataUploadThread extends Thread {

    private String filePath;

    public PlannedDataUploadThread(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        System.out.println("STARTING READING EXCEL FILE");

        StaticsDispatcher.setUploading(true);

        ExcelReader excelReader = new ExcelReader();
        HashMap<String, String> cellsFromExcelFile = excelReader.getCellsFromExcelFile(this.filePath);
        System.out.println(cellsFromExcelFile.size());
        for (Map.Entry<String, String> f : cellsFromExcelFile.entrySet()) {

            System.out.println(f.getKey());
        }
        StaticsDispatcher.setUploading(false);
    }
}
