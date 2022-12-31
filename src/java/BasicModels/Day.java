package BasicModels;

import java.util.Date;
import java.util.TreeMap;

public class Day {

    private Date date;
    private TreeMap<Short, Exodus> exoduses;

    public Day() {
        exoduses = new TreeMap();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TreeMap<Short, Exodus> getExoduses() {
        return exoduses;
    }

    public void setExoduses(TreeMap<Short, Exodus> exoduses) {
        this.exoduses = exoduses;
    }

}
