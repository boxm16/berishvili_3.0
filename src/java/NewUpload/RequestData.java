/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewUpload;

import java.util.ArrayList;

/**
 *
 * @author Michail Sitmalidis
 */
public class RequestData {
    
    private ArrayList<String> routes;
    private ArrayList<String> dates;

    public RequestData() {
        this.routes = new ArrayList<>();
        this.dates = new ArrayList<>();
    }
    
    

    public ArrayList<String> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<String> routes) {
        this.routes = routes;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }
    
    
}
