package Analysis;

import java.util.LinkedHashMap;

public class Driver {

    private LinkedHashMap<String, DriverDataAnalysisHolder> driverDataAnalysisHolders;

    private int tabelNumber;
    private String name;

    private short baseNumber;
    private String status;
    private String period;
    private String busType;

    private String leaveType;
    private String leaveStatus;
    private String leavePeriod;

    private String busNumber;

    public Driver() {
        driverDataAnalysisHolders = new LinkedHashMap<>();

        for (Integer x = 1; x < 32; x++) {
            driverDataAnalysisHolders.put(x.toString(), new DriverDataAnalysisHolder());

        }
    }

    public int getTabelNumber() {
        return tabelNumber;
    }

    public void setTabelNumber(int tabelNumber) {
        this.tabelNumber = tabelNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(short baseNumber) {
        this.baseNumber = baseNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getLeavePeriod() {
        return leavePeriod;
    }

    public void setLeavePeriod(String leavePeriod) {
        this.leavePeriod = leavePeriod;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public LinkedHashMap<String, DriverDataAnalysisHolder> getDriverDataAnalysisHolders() {
        return driverDataAnalysisHolders;
    }

    public void setDriverDataAnalysisHolders(LinkedHashMap<String, DriverDataAnalysisHolder> driverDataAnalysisHolders) {
        this.driverDataAnalysisHolders = driverDataAnalysisHolders;
    }

    
}
