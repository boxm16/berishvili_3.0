package Service;

import org.springframework.stereotype.Service;

@Service
public class MemoryUsage {

    public void printMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        System.out.print("Used Memory:" + memoryUsed / (1024 * 1024) + "MB  #");
        System.out.print("Free Memory:" + runtime.freeMemory() / (1024 * 1024) + "MB  #");
        System.out.print("Total Memory:" + runtime.totalMemory() / (1024 * 1024) + "MB  #");
        System.out.print("Max Memory:" + memoryMax / (1024 * 1024) + "MB  #");
        System.out.print("Used %: " + Math.round(memoryUsedPercent) + "%");
        System.out.println("");
    }
}
